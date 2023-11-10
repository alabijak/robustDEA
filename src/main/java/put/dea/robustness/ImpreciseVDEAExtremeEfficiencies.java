package put.dea.robustness;

public class ImpreciseVDEAExtremeEfficiencies extends VDEABase
        implements ExtremeEfficiency<ImpreciseVDEAProblemData> {

    private final ImprecisePerformanceConverter performanceConverter;
    private final ImpreciseVDEAUtils impreciseCommonUtils;

    ImpreciseVDEAExtremeEfficiencies() {
        this(1.00001, 1e-4, 1.0);
    }

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
