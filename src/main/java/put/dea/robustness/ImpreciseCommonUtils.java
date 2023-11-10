package put.dea.robustness;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import joinery.DataFrame;
import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


class ImpreciseCommonUtils {

    private final Double epsilon;
    protected Double alpha;

    public ImpreciseCommonUtils(Double alpha, Double epsilon) {
        this.alpha = alpha;
        this.epsilon = epsilon;
    }

    public void addMonotonicityConstraints(MPSolver model,
                                           ImpreciseInformation impreciseInformation) {
        impreciseInformation.getOrdinalFactors()
                .forEach(factor -> addMonotonicityConstraints(model, impreciseInformation.getData(), factor));
    }


    protected void addMonotonicityConstraints(MPSolver model, ProblemData data, String factor) {
        addMonotonicityConstraints(model, data, factor, false);
    }

    protected void addMonotonicityConstraints(MPSolver model, ProblemData data, String factor,
                                              boolean descending) {

        var sortedIndices = sortIndicesByValues(data, factor, descending);
        var constraint = model.makeConstraint(epsilon, MPSolver.infinity());
        var variable = model.lookupVariableOrNull(factor + "_" + sortedIndices.get(0));
        constraint.setCoefficient(variable, 1);

        var table = getInputOrOutputTable(data, factor);
        for (int i = 1; i < sortedIndices.size(); i++) {
            constraint = model.makeConstraint();
            constraint.setLb(0);
            if (table.get(sortedIndices.get(i), factor).equals(table.get(sortedIndices.get(i - 1), factor))) {
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
                        -alpha
                );
            }
        }

        addMonotonicityMaxConstraint(model, factor, sortedIndices.get(sortedIndices.size() - 1));
    }

    public List<Integer> sortIndicesByValues(ProblemData data, String factor, boolean descending) {
        var table = getInputOrOutputTable(data, factor);

        var columnValues = table.col(factor);

        var comparator = Comparator.comparing(Pair<Integer, Double>::getSecond);
        if (descending)
            comparator = comparator.reversed();
        return IntStream.range(0, columnValues.size())
                .mapToObj(idx -> new Pair<>(idx, columnValues.get(idx)))
                .sorted(comparator)
                .map(Pair::getFirst)
                .toList();
    }

    public DataFrame<Double> getInputOrOutputTable(ProblemData data, String factor) {
        if (data.getInputData().columns().contains(factor))
            return data.getInputData();
        return data.getOutputData();
    }

    protected void addMonotonicityMaxConstraint(MPSolver model, String factor, int dmuIdx) {

    }

    public void setConstraintCoefficients(MPSolver model,
                                          MPConstraint constraint,
                                          DataFrame<Double> data,
                                          Set<String> ordinalFactors,
                                          int rowIdx,
                                          boolean negative) {
        int sign = negative ? -1 : 1;

        for (var column : data.columns()) {
            if (ordinalFactors.contains(column.toString())) {
                var variable = model.lookupVariableOrNull(column + "_" + rowIdx);
                constraint.setCoefficient(variable, sign);
            } else {
                var variable = model.lookupVariableOrNull(column.toString());
                constraint.setCoefficient(variable,
                        sign * data.get(rowIdx, column));
            }
        }

    }

    public void prepareModelObjective(MPSolver model,
                                      ProblemData preciseValues,
                                      Set<String> ordinalFactors,
                                      int subjectDmuIdx) {
        for (var column : preciseValues.getOutputData().columns()) {
            if (ordinalFactors.contains(column.toString())) {
                var variable = model.lookupVariableOrNull(column + "_" + subjectDmuIdx);
                model.objective().setCoefficient(variable, 1);
            } else {
                var variable = model.lookupVariableOrNull(column.toString());
                model.objective().setCoefficient(variable,
                        preciseValues.getOutputData().get(subjectDmuIdx, column));
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
            if (data.getInputData().columns().contains(factor))
                table = data.getInputData();

            var subjectValue = table.get(subjectDmuIdx, factor);
            var relativeValue = table.get(relativeDmuIdx, factor);

            var orderedVariables = List.of(subjectVariable, relativeVariable);
            if (subjectValue > relativeValue)
                orderedVariables = List.of(relativeVariable, subjectVariable);

            var constraint = model.makeConstraint(epsilon, MPSolver.infinity());
            constraint.setCoefficient(orderedVariables.get(0), 1);

            if (subjectValue.equals(relativeValue)) {
                constraint = model.makeConstraint(0, 0);
                constraint.setCoefficient(subjectVariable, 1);
                constraint.setCoefficient(relativeVariable, -1);
            } else {
                constraint = model.makeConstraint(0, MPSolver.infinity());
                constraint.setCoefficient(orderedVariables.get(1), 1);
                constraint.setCoefficient(orderedVariables.get(0), -alpha);
            }
        }
    }


}
