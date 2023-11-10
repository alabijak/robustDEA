package put.dea.robustness;

import java.util.Random;

/**
 * Finds the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 * for imprecise DEA problems with CCR model
 */
public class ImpreciseCCRSmaaRanks extends CCRSmaaBase implements SmaaRanks<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    /**
     * Creates new object with defined number of samples
     *
     * @param numberOfSamples number of samples
     */
    public ImpreciseCCRSmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates new object with defined number of samples
     * and specific {@link Random} object
     *
     * @param numberOfSamples number of samples
     * @param random          random object for sampling
     */
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
