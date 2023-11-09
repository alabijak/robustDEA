package put.dea.vdea;

import put.dea.common.ExtremeEfficiency;
import put.dea.common.OptimizationSense;

public class VDEAExtremeEfficiencies
        extends VDEABase
        implements ExtremeEfficiency<VDEAProblemData> {

    @Override
    public double maxEfficiency(VDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxEfficiency(data, subjectDmuIdx, OptimizationSense.MAXIMIZE);
    }

    @Override
    public double minEfficiency(VDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxEfficiency(data, subjectDmuIdx, OptimizationSense.MINIMIZE);
    }

    private Double findMinOrMaxEfficiency(VDEAProblemData data, int subjectDmuIdx, OptimizationSense sense) {
        var model = makeModel(sense);

        var inputs = transformInputsToUtilities(data);
        var outputs = transformOutputsToUtilities(data);
        var inputWeights = makeWeightVariables(model, inputs);
        var outputWeights = makeWeightVariables(model, outputs);

        var objective = model.objective();
        objective.setOptimizationDirection(sense.isMaximize());
        for (int i = 0; i < data.getInputCount(); i++)
            objective.setCoefficient(inputWeights.get(i), inputs.get(subjectDmuIdx, i));
        for (int i = 0; i < data.getOutputCount(); i++)
            objective.setCoefficient(outputWeights.get(i), outputs.get(subjectDmuIdx, i));

        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);
        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }
}
