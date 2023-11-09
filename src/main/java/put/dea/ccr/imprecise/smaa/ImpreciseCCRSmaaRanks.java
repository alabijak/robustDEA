package put.dea.ccr.imprecise.smaa;

import put.dea.ccr.imprecise.CCRImpreciseProblemData;
import put.dea.ccr.smaa.CCRSmaaBase;
import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaRankBase;
import put.dea.common.smaa.SmaaRanks;

import java.util.Random;

public class ImpreciseCCRSmaaRanks extends CCRSmaaBase implements SmaaRanks<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    public ImpreciseCCRSmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public ImpreciseCCRSmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DistributionResult rankDistribution(CCRImpreciseProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), false);

        var rankBase = new SmaaRankBase(numberOfSamples);
        var efficiencies = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                data.getDmuCount());
        var ranks = rankBase.calculateRanksMatrix(efficiencies);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedRanks = calculateExpectedValues(ranks.apply(x -> x + 1).cast(Double.class));
        return new DistributionResult(distribution, expectedRanks);
    }
}
