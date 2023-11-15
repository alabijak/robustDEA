package put.dea.robustness;

import com.google.ortools.linearsolver.MPVariable;

import java.util.Arrays;

/**
 * Class providing methods for finding the extreme (minimal and maximal) efficiency distance
 * of analysed DMU to the best one
 * in standard (precise) problems with VDEA efficiency model
 */
public class VDEAExtremeDistances extends VDEABase implements ExtremeDistances<VDEAProblemData> {

    @Override
    public double minDistance(VDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MINIMIZE, false);
    }

    @Override
    public double maxDistance(VDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MAXIMIZE, false);
    }

    @Override
    public double superDistance(VDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MINIMIZE, true);
    }

    private double findMinOrMaxDistance(VDEAProblemData data, int subjectDmuIdx, OptimizationSense sense, boolean superDistance) {
        var inputs = transformInputsToUtilities(data);
        var outputs = transformOutputsToUtilities(data);
        var model = makeModel(sense);
        var dVariable = model.makeNumVar(-1, 1, "d");
        model.objective().setCoefficient(dVariable, 1);
        var inputWeights = makeWeightVariables(model, inputs, 1);
        var outputWeights = makeWeightVariables(model, outputs, 1);

        MPVariable[] binVariables = new MPVariable[0];
        if (sense.isMaximize()) {
            binVariables = model.makeBoolVarArray(data.getDmuCount());
            var binSumConstraint = model.makeConstraint(1, 1);
            Arrays.stream(binVariables).forEach(variable -> binSumConstraint.setCoefficient(variable, 1));
        }
        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (!superDistance || k != subjectDmuIdx) {
                var constraint = createEffDistanceConstraint(model, inputs, outputs,
                        subjectDmuIdx, k, inputWeights, outputWeights);
                if (sense.isMaximize())
                    constraint.setLb(-C);
                else
                    constraint.setUb(0);

                constraint.setCoefficient(dVariable, -1);
                if (sense.isMaximize())
                    constraint.setCoefficient(binVariables[k], -C);
            }
        }

        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }
}
