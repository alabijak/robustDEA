package put.dea.robustness;

import java.util.Random;

public class VDEASmaaRanks extends VDEASmaaBase implements SmaaRanks<VDEAProblemData> {


    public VDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public VDEASmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DistributionResult rankDistribution(VDEAProblemData data) {
        var rankBase = new SmaaRankBase(numberOfSamples);
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var ranks = rankBase.calculateRanksMatrix(efficiencyMatrix);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedEfficiency = calculateExpectedValues(ranks.apply(x -> x + 1).cast(Double.class));
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
