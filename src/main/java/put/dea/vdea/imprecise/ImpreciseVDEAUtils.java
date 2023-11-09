package put.dea.vdea.imprecise;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import put.dea.common.ProblemData;
import put.dea.common.imprecise.ImpreciseCommonUtils;
import put.dea.common.imprecise.ImpreciseInformation;
import put.dea.vdea.PerformanceToValueConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ImpreciseVDEAUtils extends ImpreciseCommonUtils {

    private final PerformanceToValueConverter performanceToValueConverter = new PerformanceToValueConverter();
    private final double functionValuesAlpha;

    public ImpreciseVDEAUtils(Double alpha, Double epsilon, double functionValuesAlpha) {
        super(alpha, epsilon);
        this.functionValuesAlpha = functionValuesAlpha;
    }

    public void addFunctionRangeConstraints(MPSolver model,
                                            ImpreciseVDEAProblemData data,
                                            ProblemData preciseData,
                                            Map<String, List<MPVariable>> variables) {
        data.getInputNames()
                .stream()
                .filter(input -> !data.getImpreciseInformation().getOrdinalFactors().contains(input))
                .forEach(input -> addFunctionRangeConstraintsForColumn(
                        model, input, data, preciseData, variables.get(input), true
                ));
        data.getOutputNames()
                .stream()
                .filter(output -> !data.getImpreciseInformation().getOrdinalFactors().contains(output))
                .forEach(output -> addFunctionRangeConstraintsForColumn(
                        model, output, data, preciseData, variables.get(output), false
                ));
    }

    public void addFunctionRangeConstraintsForColumn(MPSolver model,
                                                     String column,
                                                     ImpreciseVDEAProblemData data,
                                                     ProblemData preciseData,
                                                     List<MPVariable> variables,
                                                     boolean input) {
        var lowerValues = performanceToValueConverter.transformColumnToUtilities(
                input ? preciseData.getInputData() : preciseData.getOutputData(),
                column,
                data.getLowerFunctionShape(column));
        var upperValues = performanceToValueConverter.transformColumnToUtilities(
                input ? preciseData.getInputData() : preciseData.getOutputData(),
                column,
                data.getUpperFunctionShape(column));
        for (int dmu = 0; dmu < data.getDmuCount(); dmu++) {
            var variable = variables.get(dmu);
            var weightVariable = model.lookupVariableOrNull(column);

            var constraint = model.makeConstraint(0, MPSolver.infinity());
            constraint.setCoefficient(variable, 1);
            constraint.setCoefficient(weightVariable, -lowerValues.get(dmu));

            constraint = model.makeConstraint(-MPSolver.infinity(), 0);
            constraint.setCoefficient(variable, 1);
            constraint.setCoefficient(weightVariable, -upperValues.get(dmu));
        }
        var alpha = this.alpha;
        this.alpha = functionValuesAlpha;
        addMonotonicityConstraints(model, preciseData, column, input);
        this.alpha = alpha;
    }

    public Map<String, List<MPVariable>> makeOrdinalAndFunctionRangeVariables(MPSolver model, ImpreciseVDEAProblemData data) {
        var result = new HashMap<String, List<MPVariable>>();
        data.getInputNames()
                .forEach(input -> result.put(input, makeFunctionRangeVariables(model, input, data.getDmuCount())));
        data.getOutputNames()
                .forEach(output -> result.put(output, makeFunctionRangeVariables(model, output, data.getDmuCount())));
        return result;
    }

    private List<MPVariable> makeFunctionRangeVariables(MPSolver model, String column, int dmuCount) {
        return IntStream.range(0, dmuCount)
                .mapToObj(dmu -> model.makeNumVar(0, 1, column + "_" + dmu))
                .toList();
    }

    public MPConstraint createImpreciseDistanceConstraint(
            MPSolver model,
            ImpreciseVDEAProblemData data,
            int subjectDmuIdx,
            int relativeDmuIdx) {
        var constraint = model.makeConstraint();
        var allColumns = new HashSet<>(data.getInputNames());
        allColumns.addAll(data.getOutputNames());
        for (var factor : allColumns) {
            if (relativeDmuIdx != subjectDmuIdx) {
                var subjectVariable = model.lookupVariableOrNull(factor + "_" + subjectDmuIdx);
                var relativeVariable = model.lookupVariableOrNull(factor + "_" + relativeDmuIdx);
                constraint.setCoefficient(subjectVariable, -1);
                constraint.setCoefficient(relativeVariable, 1);
            }
        }
        return constraint;
    }

    public void addMonotonicityConstraints(MPSolver model,
                                           ImpreciseInformation impreciseInformation) {
        impreciseInformation.getOrdinalFactors()
                .forEach(factor -> addMonotonicityConstraints(model, impreciseInformation.getData(), factor,
                        impreciseInformation.getData().getInputNames().contains(factor)));
    }

    @Override
    protected void addMonotonicityMaxConstraint(MPSolver model, String factor, int dmuIdx) {
        var variable = model.lookupVariableOrNull(factor + "_" + dmuIdx);
        var constraint = model.makeConstraint(-MPSolver.infinity(), 0);
        constraint.setCoefficient(variable, 1);
        constraint.setCoefficient(model.lookupVariableOrNull(factor), -1);
    }
}
