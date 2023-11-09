package put.dea.ccr.smaa;

import put.dea.common.ProblemData;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaRankBase;
import put.dea.common.smaa.SmaaRanks;

import java.util.Random;

public class CCRSmaaRanks extends CCRSmaaBase implements SmaaRanks<ProblemData> {


    public CCRSmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public CCRSmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    public DistributionResult rankDistribution(ProblemData data) {
        var rankBase = new SmaaRankBase(numberOfSamples);
        var efficiencies = calculateEfficiencyMatrix(data);
        var ranks = rankBase.calculateRanksMatrix(efficiencies);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedRanks = calculateExpectedValues(ranks.apply(x -> x + 1).cast(Double.class));
        return new DistributionResult(distribution, expectedRanks);
    }


}
