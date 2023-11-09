package put.dea.ccr;

import put.dea.common.OptimizationSense;
import put.dea.common.PreferenceRelations;
import put.dea.common.ProblemData;

import java.util.stream.IntStream;

public class CCRPreferenceRelations extends CCRRobustnessBase implements PreferenceRelations<ProblemData> {
    private static final double EPSILON = 1e-10;

    @Override
    public boolean isNecessarilyPreferred(ProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MINIMIZE);
    }

    @Override
    public boolean isPossiblyPreferred(ProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MAXIMIZE);
    }

    private boolean createPreferenceRelationProblem(ProblemData data, int subjectDmuIdx, int relativeDmuIdx, OptimizationSense sense) {
        var model = makeModel(sense);

        var inputWeights = createWeightVariablesWithEqualToOneConstraint(model,
                data.getInputData(), subjectDmuIdx);

        var outputWeights = makeWeightVariables(model, data.getOutputData());

        var objective = model.objective();
        IntStream.range(0, outputWeights.size())
                .forEach(idx -> objective.setCoefficient(outputWeights.get(idx),
                        data.getOutputData().get(subjectDmuIdx, idx)));

        var constraint = model.makeConstraint(0, 0);
        IntStream.range(0, data.getInputCount())
                .forEach(idx -> constraint.setCoefficient(inputWeights.get(idx), data.getInputData().get(relativeDmuIdx, idx)));
        IntStream.range(0, data.getOutputCount())
                .forEach(idx -> constraint.setCoefficient(outputWeights.get(idx), -data.getOutputData().get(relativeDmuIdx, idx)));
        addCustomWeightConstraints(data, model);
        return getModelResult(model) >= 1 - EPSILON;
    }
}
