package put.dea.ccr.imprecise;

import com.google.ortools.linearsolver.MPSolver;
import put.dea.ccr.CCRRobustnessBase;
import put.dea.common.ExtremeRanks;
import put.dea.common.ExtremeRanksBase;
import put.dea.common.OptimizationSense;
import put.dea.common.imprecise.ImpreciseCommonUtils;
import put.dea.common.imprecise.ImprecisePerformanceConverter;
import put.dea.common.imprecise.ResultType;

public class ImpreciseCCRExtremeRanks extends CCRRobustnessBase implements ExtremeRanks<CCRImpreciseProblemData> {
    private final ExtremeRanksBase extremeRanksBase = new ExtremeRanksBase();
    private final ImprecisePerformanceConverter performanceConverter = new ImprecisePerformanceConverter();

    private final ImpreciseCommonUtils impreciseCommonUtils;

    public ImpreciseCCRExtremeRanks() {
        this(1.00001, 1e-4);
    }

    public ImpreciseCCRExtremeRanks(double alpha, double epsilon) {
        super();
        impreciseCommonUtils = new ImpreciseCommonUtils(alpha, epsilon);
    }

    @Override
    public int minRank(CCRImpreciseProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MINIMIZE, 0.0, MPSolver.infinity());
    }

    @Override
    public int maxRank(CCRImpreciseProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MAXIMIZE, -MPSolver.infinity(), C);
    }

    public int createModel(CCRImpreciseProblemData data, int subjectDmuIdx, OptimizationSense sense,
                           Double constraintsLower, Double constraintUpper) {
        var model = makeModel(sense);
        var resultType = ResultType.OPTIMISTIC;
        if (sense == OptimizationSense.MAXIMIZE)
            resultType = ResultType.PESSIMISTIC;

        var preciseValues = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx, resultType);

        makeWeightVariables(model, data.getInputData());
        makeWeightVariables(model, data.getOutputData());
        impreciseCommonUtils.makeOrdinalFactorVariables(model,
                data.getImpreciseInformation(),
                data.getDmuCount());
        var binVariables = extremeRanksBase
                .createBinaryVariablesAndObjective(model, data.getDmuCount(), subjectDmuIdx);

        var inputSumConstraint = model.makeConstraint(1, 1);
        impreciseCommonUtils.setConstraintCoefficients(model, inputSumConstraint, preciseValues.getInputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);
        var outputSumConstraint = model.makeConstraint(1, 1);
        impreciseCommonUtils.setConstraintCoefficients(model, outputSumConstraint, preciseValues.getOutputData(),
                data.getImpreciseInformation().getOrdinalFactors(), subjectDmuIdx, false);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (k != subjectDmuIdx) {
                var constraint = model.makeConstraint(constraintsLower, constraintUpper);
                impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getInputData(),
                        data.getImpreciseInformation().getOrdinalFactors(), k, false);
                impreciseCommonUtils.setConstraintCoefficients(model, constraint, preciseValues.getOutputData(),
                        data.getImpreciseInformation().getOrdinalFactors(), k, true);
                constraint.setCoefficient(binVariables[k], C);
            }
        }
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        addCustomWeightConstraints(data, model);
        return (int) Math.round(getModelResult(model));
    }
}
