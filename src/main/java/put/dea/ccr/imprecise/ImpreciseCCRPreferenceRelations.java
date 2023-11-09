package put.dea.ccr.imprecise;

import put.dea.ccr.CCRRobustnessBase;
import put.dea.common.OptimizationSense;
import put.dea.common.PreferenceRelations;
import put.dea.common.imprecise.ImpreciseCommonUtils;
import put.dea.common.imprecise.ImprecisePerformanceConverter;
import put.dea.common.imprecise.ResultType;

public class ImpreciseCCRPreferenceRelations extends CCRRobustnessBase implements PreferenceRelations<CCRImpreciseProblemData> {
    private static final double EPSILON = 1e-10;

    private final ImpreciseCommonUtils impreciseCommonUtils;
    private final ImprecisePerformanceConverter performanceConverter = new ImprecisePerformanceConverter();

    public ImpreciseCCRPreferenceRelations() {
        this(1.0001, 1e-4);
    }

    public ImpreciseCCRPreferenceRelations(double alpha, double epsilon) {
        super();
        impreciseCommonUtils = new ImpreciseCommonUtils(alpha, epsilon);
    }

    @Override
    public boolean isNecessarilyPreferred(CCRImpreciseProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MINIMIZE);
    }

    @Override
    public boolean isPossiblyPreferred(CCRImpreciseProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MAXIMIZE);
    }

    private boolean createPreferenceRelationProblem(CCRImpreciseProblemData data, int subjectDmuIdx, int relativeDmuIdx, OptimizationSense sense) {
        if (subjectDmuIdx == relativeDmuIdx)
            return true;

        var model = makeModel(sense);
        var resultType = ResultType.OPTIMISTIC;
        if (sense == OptimizationSense.MINIMIZE)
            resultType = ResultType.PESSIMISTIC;

        var preciseValues = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx, resultType);

        makeWeightVariables(model, data.getInputData());
        makeWeightVariables(model, data.getOutputData());
        impreciseCommonUtils.addPairwiseMonotonicityConstraints(model,
                data.getImpreciseInformation().getOrdinalFactors(),
                preciseValues, subjectDmuIdx, relativeDmuIdx);

        var inputSumConstrains = model.makeConstraint(1, 1);
        impreciseCommonUtils.setConstraintCoefficients(model, inputSumConstrains, preciseValues.getInputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);

        impreciseCommonUtils.prepareModelObjective(model, preciseValues,
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx);
        var outputSumConstraint = model.makeConstraint(0, C);
        impreciseCommonUtils.setConstraintCoefficients(model, outputSumConstraint, preciseValues.getOutputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);

        var constraint = model.makeConstraint(0, 0);
        impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                data.getImpreciseInformation().getOrdinalFactors(), relativeDmuIdx, false);
        impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getOutputData(),
                data.getImpreciseInformation().getOrdinalFactors(), relativeDmuIdx, true);

        addCustomWeightConstraints(data, model);
        return getModelResult(model) >= 1 - EPSILON;
    }
}
