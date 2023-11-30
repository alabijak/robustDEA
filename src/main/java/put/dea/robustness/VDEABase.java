package put.dea.robustness;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import tech.tablesaw.api.Table;

import java.util.List;

abstract class VDEABase extends RobustnessLPBase {
    protected static final Double C = 10000.0;
    private final PerformanceToValueConverter performanceConverter = new PerformanceToValueConverter();

    protected void addSumWeightsToOneConstraint(MPSolver model,
                                                List<MPVariable> inputWeights,
                                                List<MPVariable> outputWeights) {
        var weightsSumConstraint = model.makeConstraint(1, 1);
        inputWeights.forEach(w -> weightsSumConstraint.setCoefficient(w, 1));
        outputWeights.forEach(w -> weightsSumConstraint.setCoefficient(w, 1));
    }

    protected MPConstraint createEffDistanceConstraint(MPSolver model,
                                                       Table inputs,
                                                       Table outputs,
                                                       int subjectDmuIdx,
                                                       int relativeDmuIdx,
                                                       List<MPVariable> inputWeights,
                                                       List<MPVariable> outputWeights) {
        var constraint = model.makeConstraint();
        for (int i = 0; i < inputs.columnCount(); i++)
            constraint.setCoefficient(inputWeights.get(i), inputs.row(relativeDmuIdx).getDouble(i)
                    - inputs.row(subjectDmuIdx).getDouble(i));
        for (int i = 0; i < outputs.columnCount(); i++)
            constraint.setCoefficient(outputWeights.get(i), outputs.row(relativeDmuIdx).getDouble(i)
                    - outputs.row(subjectDmuIdx).getDouble(i));
        return constraint;
    }

    protected List<MPVariable> makeWeightVariables(MPSolver model, Table data) {
        return makeWeightVariables(model, data, 1);
    }

    protected Table transformInputsToUtilities(VDEAProblemData data) {
        return performanceConverter.transformInputsToUtilities(data);
    }

    protected Table transformOutputsToUtilities(VDEAProblemData data) {
        return performanceConverter.transformOutputsToUtilities(data);
    }
}
