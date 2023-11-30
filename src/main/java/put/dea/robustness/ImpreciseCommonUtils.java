package put.dea.robustness;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.apache.commons.math3.util.Pair;
import tech.tablesaw.api.Table;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


class ImpreciseCommonUtils {

    private final Double epsilon;
    protected Double minimalSubsequentRatio;

    public ImpreciseCommonUtils(Double minimalSubsequentRatio, Double epsilon) {
        this.minimalSubsequentRatio = minimalSubsequentRatio;
        this.epsilon = epsilon;
    }

    public void addMonotonicityConstraints(MPSolver model,
                                           ImpreciseInformation impreciseInformation) {
        impreciseInformation.getOrdinalFactors()
                .forEach(factor -> addMonotonicityConstraints(model,
                        impreciseInformation.getData(),
                        factor,
                        false)
                );
    }

    protected void addMonotonicityConstraints(MPSolver model, ProblemData data, String factor, boolean descending) {
        addMonotonicityConstraints(model, data, factor, descending, this.minimalSubsequentRatio);
    }

    protected void addMonotonicityConstraints(MPSolver model, ProblemData data, String factor,
                                              boolean descending, double minimalRatio) {

        var sortedIndices = sortIndicesByValues(data, factor, descending);
        var constraint = model.makeConstraint(epsilon, MPSolver.infinity());
        var variable = model.lookupVariableOrNull(factor + "_" + sortedIndices.get(0));
        constraint.setCoefficient(variable, 1);

        var table = getInputOrOutputTable(data, factor);
        var column = table.doubleColumn(factor);
        for (int i = 1; i < sortedIndices.size(); i++) {
            constraint = model.makeConstraint();
            constraint.setLb(0);
            if (Math.abs(column.getDouble(sortedIndices.get(i)) - column.getDouble(sortedIndices.get(i - 1))) < 1e-9) {
                constraint.setUb(0);
                constraint.setCoefficient(
                        model.lookupVariableOrNull(factor + "_" + sortedIndices.get(i)),
                        1
                );
                constraint.setCoefficient(
                        model.lookupVariableOrNull(factor + "_" + sortedIndices.get(i - 1)),
                        -1
                );
            } else {
                constraint.setCoefficient(
                        model.lookupVariableOrNull(factor + "_" + sortedIndices.get(i)),
                        1
                );
                constraint.setCoefficient(
                        model.lookupVariableOrNull(factor + "_" + sortedIndices.get(i - 1)),
                        -minimalRatio
                );
            }
        }

        addMonotonicityMaxConstraint(model, factor, sortedIndices.get(sortedIndices.size() - 1));
    }

    public List<Integer> sortIndicesByValues(ProblemData data, String factor, boolean descending) {
        var table = getInputOrOutputTable(data, factor);

        var columnValues = table.doubleColumn(factor).asDoubleArray();

        var comparator = Comparator.comparing(Pair<Integer, Double>::getSecond);
        if (descending)
            comparator = comparator.reversed();
        return IntStream.range(0, columnValues.length)
                .mapToObj(idx -> new Pair<>(idx, columnValues[idx]))
                .sorted(comparator)
                .map(Pair::getFirst)
                .toList();
    }

    public Table getInputOrOutputTable(ProblemData data, String factor) {
        if (data.getInputData().containsColumn(factor))
            return data.getInputData();
        return data.getOutputData();
    }

    protected void addMonotonicityMaxConstraint(MPSolver model, String factor, int dmuIdx) {

    }

    public void setConstraintCoefficients(MPSolver model,
                                          MPConstraint constraint,
                                          Table data,
                                          Set<String> ordinalFactors,
                                          int rowIdx,
                                          boolean negative) {
        int sign = negative ? -1 : 1;

        for (var column : data.columnNames()) {
            if (ordinalFactors.contains(column)) {
                var variable = model.lookupVariableOrNull(column + "_" + rowIdx);
                constraint.setCoefficient(variable, sign);
            } else {
                var variable = model.lookupVariableOrNull(column);
                constraint.setCoefficient(variable,
                        sign * data.row(rowIdx).getDouble(column));
            }
        }

    }

    public void prepareModelObjective(MPSolver model,
                                      ProblemData preciseValues,
                                      Set<String> ordinalFactors,
                                      int subjectDmuIdx) {
        for (var column : preciseValues.getOutputData().columnNames()) {
            if (ordinalFactors.contains(column)) {
                var variable = model.lookupVariableOrNull(column + "_" + subjectDmuIdx);
                model.objective().setCoefficient(variable, 1);
            } else {
                var variable = model.lookupVariableOrNull(column);
                model.objective().setCoefficient(variable,
                        preciseValues.getOutputData().row(subjectDmuIdx).getDouble(column));
            }
        }
    }

    public void makeOrdinalFactorVariables(MPSolver model, ImpreciseInformation impreciseInformation,
                                           int dmuCount) {
        for (var ordinalFactor : impreciseInformation.getOrdinalFactors()) {
            IntStream.range(0, dmuCount)
                    .forEach(idx -> model.makeNumVar(0, CCRRobustnessBase.C, ordinalFactor + "_" + idx));
        }
    }

    public void addPairwiseMonotonicityConstraints(MPSolver model,
                                                   Set<String> ordinalFactors,
                                                   ProblemData data,
                                                   int subjectDmuIdx,
                                                   int relativeDmuIdx) {
        for (var factor : ordinalFactors) {
            var subjectVariable = model.makeNumVar(0, MPSolver.infinity(), factor + "_" + subjectDmuIdx);
            var relativeVariable = model.makeNumVar(0, MPSolver.infinity(), factor + "_" + relativeDmuIdx);
            var table = data.getOutputData();
            if (data.getInputData().containsColumn(factor))
                table = data.getInputData();

            var subjectValue = table.row(subjectDmuIdx).getDouble(factor);
            var relativeValue = table.row(relativeDmuIdx).getDouble(factor);

            var orderedVariables = List.of(subjectVariable, relativeVariable);
            if (subjectValue > relativeValue)
                orderedVariables = List.of(relativeVariable, subjectVariable);

            var constraint = model.makeConstraint(epsilon, MPSolver.infinity());
            constraint.setCoefficient(orderedVariables.get(0), 1);

            if (Math.abs(subjectValue - relativeValue) < 1e-9) {
                constraint = model.makeConstraint(0, 0);
                constraint.setCoefficient(subjectVariable, 1);
                constraint.setCoefficient(relativeVariable, -1);
            } else {
                constraint = model.makeConstraint(0, MPSolver.infinity());
                constraint.setCoefficient(orderedVariables.get(1), 1);
                constraint.setCoefficient(orderedVariables.get(0), -minimalSubsequentRatio);
            }
        }
    }


}
