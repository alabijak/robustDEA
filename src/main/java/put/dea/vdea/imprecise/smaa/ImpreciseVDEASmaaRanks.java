package put.dea.vdea.imprecise.smaa;

import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaRankBase;
import put.dea.common.smaa.SmaaRanks;
import put.dea.vdea.imprecise.ImpreciseVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;

import java.util.Random;

public class ImpreciseVDEASmaaRanks extends VDEASmaaBase implements SmaaRanks<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    public ImpreciseVDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public ImpreciseVDEASmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DistributionResult rankDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var rankBase = new SmaaRankBase(numberOfSamples);
        var ranks = rankBase.calculateRanksMatrix(efficiencyMatrix);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedEfficiency = calculateExpectedValues(ranks.apply(x -> x + 1).cast(Double.class));
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
