package put.dea.robustness;

import com.google.ortools.linearsolver.MPSolver;

import java.util.Arrays;

/**
 * Calculates the extreme efficiencies and super-efficiency scores
 * for problems with imprecise information and CCR efficiency model
 */
public class ImpreciseCCRExtremeEfficiency extends CCRRobustnessBase
        implements ExtremeEfficiencyCCR<CCRImpreciseProblemData> {

    private final ImprecisePerformanceConverter performanceConverter;

    private final ImpreciseCommonUtils impreciseCommonUtils;

    /**
     * Creates new object with default alpha (1.00001) and epsilon(0.0001)
     */
    public ImpreciseCCRExtremeEfficiency() {
        this(1.00001, 1e-4);
    }

    /**
     * Creates new object with given alpha and epsilon
     *
     * @param alpha   minimal ratio between subsequent ordinal performances
     * @param epsilon minimal value for lowest performance in ordinal factors
     */
    public ImpreciseCCRExtremeEfficiency(double alpha, double epsilon) {
        impreciseCommonUtils = new ImpreciseCommonUtils(alpha, epsilon);
        performanceConverter = new ImprecisePerformanceConverter();
    }

    @Override
    public double maxEfficiency(CCRImpreciseProblemData data, int subjectDmuIdx) {
        var model = createMaxOrSuperEfficiencyModel(data, subjectDmuIdx, false);
        return model.objective().value();
    }

    @Override
    public double minEfficiency(CCRImpreciseProblemData data, int subjectDmuIdx) {
        var preciseValues = performanceConverter.convertPerformanceToPrecise(
                data.getImpreciseInformation(),
                subjectDmuIdx,
                ResultType.PESSIMISTIC);

        var model = makeModel(OptimizationSense.MINIMIZE);

        prepareWeightAndOrdinalVariables(data, model);
        var binVariables = model.makeBoolVarArray(data.getDmuCount());

        impreciseCommonUtils.prepareModelObjective(model, preciseValues,
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx);

        var constraint = model.makeConstraint(1, 1);
        impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);

        for (int k = 0; k < data.getDmuCount(); k++) {
            constraint = model.makeConstraint(-MPSolver.infinity(), C);
            constraint.setCoefficient(binVariables[k], C);
            impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                    data.getImpreciseInformation().getOrdinalFactors(), k, false);
            impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getOutputData(),
                    data.getImpreciseInformation().getOrdinalFactors(), k, true);
        }
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        var binSumConstraint = model.makeConstraint(1, MPSolver.infinity());
        Arrays.stream(binVariables).forEach(b -> binSumConstraint.setCoefficient(b, 1));

        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }

    private MPSolver createMaxOrSuperEfficiencyModel(CCRImpreciseProblemData data,
                                                     int subjectDmuIdx,
                                                     boolean superEfficiency) {

        var preciseValues = performanceConverter.convertPerformanceToPrecise(
                data.getImpreciseInformation(),
                subjectDmuIdx,
                ResultType.OPTIMISTIC);

        var model = makeModel(OptimizationSense.MAXIMIZE);
        prepareWeightAndOrdinalVariables(data, model);
        impreciseCommonUtils.prepareModelObjective(model, preciseValues,
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx);

        var constraint = model.makeConstraint(1, 1);
        impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (!superEfficiency || k != subjectDmuIdx) {
                constraint = model.makeConstraint(0, MPSolver.infinity());
                impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                        data.getImpreciseInformation().getOrdinalFactors(), k, false);
                impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getOutputData(),
                        data.getImpreciseInformation().getOrdinalFactors(), k, true);
            }
        }
        addCustomWeightConstraints(data, model);
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        solveModel(model);
        return model;
    }

    private void prepareWeightAndOrdinalVariables(CCRImpreciseProblemData data, MPSolver model) {
        makeWeightVariables(model, data.getInputData());
        makeWeightVariables(model, data.getOutputData());
        impreciseCommonUtils.makeOrdinalFactorVariables(model,
                data.getImpreciseInformation(),
                data.getDmuCount());
    }

    @Override
    public double superEfficiency(CCRImpreciseProblemData data, int subjectDmuIdx) {
        var model = createMaxOrSuperEfficiencyModel(data, subjectDmuIdx, true);
        return model.objective().value();
    }
}
