package put.dea.robustness;

/**
 * Verifies the presence of the necessary and possible efficiency preference relations
 * in problems with imprecise information and CCR model
 */
public class ImpreciseCCRPreferenceRelations extends CCRRobustnessBase implements PreferenceRelations<CCRImpreciseProblemData> {
    private static final double MODEL_SOLUTION_EPSILON = 1e-10;

    private final ImpreciseCommonUtils impreciseCommonUtils;
    private final ImprecisePerformanceConverter performanceConverter = new ImprecisePerformanceConverter();

    /**
     * Creates new object with default alpha (1.00001) and epsilon(0.0001)
     */
    public ImpreciseCCRPreferenceRelations() {
        this(1.0001, 1e-4);
    }

    /**
     * Creates new object with given alpha and epsilon
     *
     * @param alpha   minimal ratio between subsequent ordinal performances
     * @param epsilon minimal value for lowest performance in ordinal factors
     */
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
        return getModelResult(model) >= 1 - MODEL_SOLUTION_EPSILON;
    }
}
