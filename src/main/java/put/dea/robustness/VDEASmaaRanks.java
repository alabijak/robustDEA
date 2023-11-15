package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation of the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 * for standard (precise) information and VDEA efficiency model
 */
public class VDEASmaaRanks extends VDEASmaaBase implements SmaaRanks<VDEAProblemData> {


    /**
     * Creates new object with given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public VDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates new object with given number of samples
     * and a specific {@link Random} object used for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          {@link Random} object for sampling
     */
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
