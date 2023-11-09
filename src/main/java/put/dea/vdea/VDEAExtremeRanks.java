package put.dea.vdea;

import put.dea.common.ExtremeRanks;
import put.dea.common.ExtremeRanksBase;
import put.dea.common.OptimizationSense;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class VDEAExtremeRanks
        extends VDEABase
        implements ExtremeRanks<VDEAProblemData> {

    @Override
    public int minRank(VDEAProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MINIMIZE,
                NEGATIVE_INFINITY, 0.0);
    }

    @Override
    public int maxRank(VDEAProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MAXIMIZE,
                -C, POSITIVE_INFINITY);
    }

    public int createModel(VDEAProblemData data, int subjectDmuIdx, OptimizationSense sense, Double constraintsLower, Double constraintUpper) {
        var extremeRanksBase = new ExtremeRanksBase();
        var inputs = transformInputsToUtilities(data);
        var outputs = transformOutputsToUtilities(data);

        var model = makeModel(sense);

        var inputWeights = makeWeightVariables(model, inputs);
        var outputWeights = makeWeightVariables(model, outputs);
        var binVariables = extremeRanksBase.
                createBinaryVariablesAndObjective(model, data.getDmuCount(), subjectDmuIdx);
        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (k != subjectDmuIdx) {
                var constraint = createEffDistanceConstraint(model, inputs, outputs,
                        subjectDmuIdx, k, inputWeights, outputWeights);
                constraint.setBounds(constraintsLower, constraintUpper);
                constraint.setCoefficient(binVariables[k], -C);
            }
        }
        addCustomWeightConstraints(data, model);
        return (int) Math.round(getModelResult(model));
    }


}
