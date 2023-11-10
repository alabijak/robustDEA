package put.dea.robustness;

import com.google.ortools.linearsolver.MPSolver;

/**
 * implementation of extreme ranks calculation for standard (precise) problems with CCR model
 */
public class CCRExtremeRanks extends CCRRobustnessBase implements ExtremeRanks<ProblemData> {

    @Override
    public int minRank(ProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MINIMIZE, 0.0, MPSolver.infinity());
    }

    @Override
    public int maxRank(ProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MAXIMIZE, -MPSolver.infinity(), C);
    }

    private int createModel(ProblemData data, int subjectDmuIdx, OptimizationSense sense,
                            Double constraintsLower, Double constraintUpper) {
        var extremeRanksBase = new ExtremeRanksBase();
        var model = makeModel(sense);

        var inputWeights = createWeightVariablesWithEqualToOneConstraint(model,
                data.getInputData(), subjectDmuIdx);
        var outputWeights = createWeightVariablesWithEqualToOneConstraint(model,
                data.getOutputData(), subjectDmuIdx);
        var binVariables = extremeRanksBase
                .createBinaryVariablesAndObjective(model, data.getDmuCount(), subjectDmuIdx);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (k != subjectDmuIdx) {
                var constraint = model.makeConstraint(constraintsLower, constraintUpper);
                constraint.setCoefficient(binVariables[k], C);
                for (int i = 0; i < inputWeights.size(); i++)
                    constraint.setCoefficient(inputWeights.get(i), data.getInputData().get(k, i));
                for (int i = 0; i < outputWeights.size(); i++)
                    constraint.setCoefficient(outputWeights.get(i), -data.getOutputData().get(k, i));
            }
        }
        addCustomWeightConstraints(data, model);
        return (int) Math.round(getModelResult(model));
    }
}
