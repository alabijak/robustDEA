package put.dea.robustness;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import joinery.DataFrame;

import java.util.List;

abstract class VDEABase extends RobustnessLPBase {
    public static final Double C = 10000.0;
    private final PerformanceToValueConverter performanceConverter = new PerformanceToValueConverter();

    protected void addSumWeightsToOneConstraint(MPSolver model,
                                                List<MPVariable> inputWeights,
                                                List<MPVariable> outputWeights) {
        var weightsSumConstraint = model.makeConstraint(1, 1);
        inputWeights.forEach(w -> weightsSumConstraint.setCoefficient(w, 1));
        outputWeights.forEach(w -> weightsSumConstraint.setCoefficient(w, 1));
    }

    protected MPConstraint createEffDistanceConstraint(MPSolver model,
                                                       DataFrame<Double> inputs,
                                                       DataFrame<Double> outputs,
                                                       int subjectDmuIdx,
                                                       int relativeDmuIdx,
                                                       List<MPVariable> inputWeights,
                                                       List<MPVariable> outputWeights) {
        var constraint = model.makeConstraint();
        for (int i = 0; i < inputs.size(); i++)
            constraint.setCoefficient(inputWeights.get(i), inputs.get(relativeDmuIdx, i) - inputs.get(subjectDmuIdx, i));
        for (int i = 0; i < outputs.size(); i++)
            constraint.setCoefficient(outputWeights.get(i), outputs.get(relativeDmuIdx, i) - outputs.get(subjectDmuIdx, i));
        return constraint;
    }

    protected List<MPVariable> makeWeightVariables(MPSolver model, DataFrame<Double> data) {
        return makeWeightVariables(model, data, 1);
    }

    protected DataFrame<Double> transformInputsToUtilities(VDEAProblemData data) {
        return performanceConverter.transformInputsToUtilities(data);
    }

    protected DataFrame<Double> transformOutputsToUtilities(VDEAProblemData data) {
        return performanceConverter.transformOutputsToUtilities(data);
    }
}
