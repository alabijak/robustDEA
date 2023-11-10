package put.dea.robustness;

public class VDEAPreferenceRelations extends VDEABase implements PreferenceRelations<VDEAProblemData> {
    @Override
    public boolean isNecessarilyPreferred(VDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MINIMIZE);
    }

    @Override
    public boolean isPossiblyPreferred(VDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MAXIMIZE);

    }

    private boolean createPreferenceRelationProblem(VDEAProblemData data,
                                                    int subjectDmuIdx,
                                                    int relativeDmuIdx,
                                                    OptimizationSense sense) {
        var inputs = transformInputsToUtilities(data);
        var outputs = transformOutputsToUtilities(data);

        var model = makeModel(sense);

        var dVariable = model.makeNumVar(-1, 1, "d");
        model.objective().setCoefficient(dVariable, 1);

        var inputWeights = makeWeightVariables(model, inputs);
        var outputWeights = makeWeightVariables(model, outputs);
        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);

        var constraint = createEffDistanceConstraint(model, inputs, outputs,
                subjectDmuIdx, relativeDmuIdx, inputWeights, outputWeights);
        constraint.setCoefficient(dVariable, 1);
        if (sense.isMaximize())
            constraint.setUb(0);
        else
            constraint.setLb(0);

        addCustomWeightConstraints(data, model);

        return getModelResult(model) >= 0;
    }
}
