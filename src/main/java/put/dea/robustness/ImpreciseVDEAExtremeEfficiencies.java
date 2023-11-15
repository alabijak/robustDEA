package put.dea.robustness;

/**
 * Calculates the extreme (minimal and maximal) efficiency scores
 * of analysed DMUs
 * for problems with imprecise information and VDEA model
 */
public class ImpreciseVDEAExtremeEfficiencies extends VDEABase
        implements ExtremeEfficiency<ImpreciseVDEAProblemData> {

    private final ImprecisePerformanceConverter performanceConverter;
    private final ImpreciseVDEAUtils impreciseCommonUtils;

    /**
     * Creates a new object with default minimal ratios for ordinal factors (1.0001)
     * and marginal functions (0.0001)
     * and minimal ordinal performance (1.0)
     */
    public ImpreciseVDEAExtremeEfficiencies() {
        this(1.00001, 1e-4, 1.0);
    }

    /**
     * Creates a new object with given minimal ratios for ordinal factors and marginal function values
     * and minimal ordinal performance
     *
     * @param alpha               minimal ratio between subsequent performances in ordinal factors
     * @param epsilon             minimal performance for ordinal factors
     * @param functionValuesAlpha minimal ratio between subsequent performances in marginal value function
     */
    public ImpreciseVDEAExtremeEfficiencies(double alpha, double epsilon, double functionValuesAlpha) {
        impreciseCommonUtils = new ImpreciseVDEAUtils(alpha, epsilon, functionValuesAlpha);
        performanceConverter = new ImprecisePerformanceConverter();
    }

    @Override
    public double maxEfficiency(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxEfficiency(data, subjectDmuIdx, OptimizationSense.MAXIMIZE);
    }

    private Double findMinOrMaxEfficiency(ImpreciseVDEAProblemData data,
                                          int subjectDmuIdx,
                                          OptimizationSense sense) {
        var preciseData = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx,
                sense.isMaximize() ? ResultType.OPTIMISTIC : ResultType.PESSIMISTIC);

        var model = makeModel(sense);

        var inputWeights = makeWeightVariables(model, preciseData.getInputData());
        var outputWeights = makeWeightVariables(model, preciseData.getOutputData());

        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);
        var performanceVariables = impreciseCommonUtils.makeOrdinalAndFunctionRangeVariables(model, data);

        var objective = model.objective();
        objective.setOptimizationDirection(sense.isMaximize());
        for (var input : data.getInputNames()) {
            var subjectVariable = model.lookupVariableOrNull(input + "_" + subjectDmuIdx);
            objective.setCoefficient(subjectVariable, 1);
        }
        for (var output : data.getOutputNames()) {
            var subjectVariable = model.lookupVariableOrNull(output + "_" + subjectDmuIdx);
            objective.setCoefficient(subjectVariable, 1);
        }

        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        impreciseCommonUtils.addFunctionRangeConstraints(model, data, preciseData, performanceVariables);
        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }

    @Override
    public double minEfficiency(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxEfficiency(data, subjectDmuIdx, OptimizationSense.MINIMIZE);
    }

}
