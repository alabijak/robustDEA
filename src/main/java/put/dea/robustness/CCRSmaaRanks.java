package put.dea.robustness;

import java.util.Random;

/**
 * Finds the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 * for standard (precise) DEA problems with CCR model
 */
public class CCRSmaaRanks extends CCRSmaaBase implements SmaaRanks<ProblemData> {

    /**
     * Creates the {@link CCRSmaaRanks} object with defined number of samples
     *
     * @param numberOfSamples number of samples
     */
    public CCRSmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates the {@link CCRSmaaRanks} object with defined number of samples
     * and specific {@link Random} object
     *
     * @param numberOfSamples number of samples
     * @param random          random object for sampling
     */
    public CCRSmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    public DistributionResult rankDistribution(ProblemData data) {
        var rankBase = new SmaaRankBase(numberOfSamples);
        var efficiencies = calculateEfficiencyMatrix(data);
        var ranks = rankBase.calculateRanksMatrix(efficiencies);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedRanks = calculateExpectedValues(ranks).stream().map(x -> x + 1).toList();
        return new DistributionResult(distribution, expectedRanks);
    }


}
