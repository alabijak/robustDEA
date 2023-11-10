package put.dea.robustness;

public class ImpreciseVDEAPreferenceRelations extends VDEABase
        implements PreferenceRelations<ImpreciseVDEAProblemData> {

    private final ImprecisePerformanceConverter performanceConverter;
    private final ImpreciseVDEAUtils impreciseCommonUtils;

    ImpreciseVDEAPreferenceRelations() {
        this(1.00001, 1e-4, 1.0);
    }

    public ImpreciseVDEAPreferenceRelations(double alpha, double epsilon, double functionValuesAlpha) {
        impreciseCommonUtils = new ImpreciseVDEAUtils(alpha, epsilon, functionValuesAlpha);
        performanceConverter = new ImprecisePerformanceConverter();
    }

    @Override
    public boolean isNecessarilyPreferred(ImpreciseVDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MINIMIZE);
    }

    @Override
    public boolean isPossiblyPreferred(ImpreciseVDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return createPreferenceRelationProblem(data, subjectDmuIdx, relativeDmuIdx, OptimizationSense.MAXIMIZE);
    }

    private boolean createPreferenceRelationProblem(ImpreciseVDEAProblemData data,
                                                    int subjectDmuIdx,
                                                    int relativeDmuIdx,
                                                    OptimizationSense sense) {
        var preciseData = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx,
                sense.isMaximize() ? ResultType.OPTIMISTIC : ResultType.PESSIMISTIC);

        var model = makeModel(sense);

        var dVariable = model.makeNumVar(-1, 1, "d");
        model.objective().setCoefficient(dVariable, 1);

        var inputWeights = makeWeightVariables(model, preciseData.getInputData());
        var outputWeights = makeWeightVariables(model, preciseData.getOutputData());
        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);

        var performanceVariables = impreciseCommonUtils.makeOrdinalAndFunctionRangeVariables(model, data);
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        impreciseCommonUtils.addFunctionRangeConstraints(model, data, preciseData, performanceVariables);

        var constraint = impreciseCommonUtils.createImpreciseDistanceConstraint(model, data,
                subjectDmuIdx, relativeDmuIdx);
        constraint.setCoefficient(dVariable, 1);
        if (sense.isMaximize())
            constraint.setUb(0);
        else
            constraint.setLb(0);

        addCustomWeightConstraints(data, model);

        return getModelResult(model) >= 0;

    }

}
