package put.dea.robustness;

import com.google.ortools.linearsolver.MPVariable;

import java.util.Arrays;

/**
 * Calculates the extreme (minimal and maximal) efficiency distance
 * of analysed DMU to the best one
 * in the problems with imprecise information and VDEA model
 */
public class ImpreciseVDEAExtremeDistances extends VDEABase
        implements ExtremeDistances<ImpreciseVDEAProblemData> {

    private final ImprecisePerformanceConverter performanceConverter;
    private final ImpreciseVDEAUtils impreciseCommonUtils;

    /**
     * Creates a new object with default minimal ratios for ordinal factors (1.0001)
     * and marginal functions (0.0001)
     * and minimal ordinal performance (1.0)
     */
    public ImpreciseVDEAExtremeDistances() {
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
    public ImpreciseVDEAExtremeDistances(double alpha, double epsilon, double functionValuesAlpha) {
        impreciseCommonUtils = new ImpreciseVDEAUtils(alpha, epsilon, functionValuesAlpha);
        performanceConverter = new ImprecisePerformanceConverter();
    }

    @Override
    public double minDistance(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MINIMIZE, false);
    }

    @Override
    public double maxDistance(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MAXIMIZE, false);
    }

    @Override
    public double superDistance(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return findMinOrMaxDistance(data, subjectDmuIdx, OptimizationSense.MINIMIZE, true);
    }

    private double findMinOrMaxDistance(ImpreciseVDEAProblemData data, int subjectDmuIdx,
                                        OptimizationSense sense, boolean superDistance) {
        var preciseData = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx,
                sense.isMaximize() ? ResultType.PESSIMISTIC : ResultType.OPTIMISTIC);

        var model = makeModel(sense);
        var dVariable = model.makeNumVar(0, 1, "d");
        model.objective().setCoefficient(dVariable, 1);
        var inputWeights = makeWeightVariables(model, preciseData.getInputData());
        var outputWeights = makeWeightVariables(model, preciseData.getOutputData());

        MPVariable[] binVariables = new MPVariable[0];
        if (sense.isMaximize()) {
            binVariables = model.makeBoolVarArray(data.getDmuCount());
            var binSumConstraint = model.makeConstraint(1, 1);
            Arrays.stream(binVariables).forEach(variable -> binSumConstraint.setCoefficient(variable, 1));
        }
        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);
        var performanceVariables = impreciseCommonUtils.makeOrdinalAndFunctionRangeVariables(model, data);
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        impreciseCommonUtils.addFunctionRangeConstraints(model, data, preciseData, performanceVariables);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (!superDistance || k != subjectDmuIdx) {
                var constraint = impreciseCommonUtils.createImpreciseDistanceConstraint(model, data,
                        subjectDmuIdx, k);
                if (sense.isMaximize())
                    constraint.setLb(-C);
                else
                    constraint.setUb(0);

                constraint.setCoefficient(dVariable, -1);
                if (sense.isMaximize())
                    constraint.setCoefficient(binVariables[k], -C);
            }
        }
        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }

}
