package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation the distribution of efficiency scores and their expected values
 * for problems with imprecise data and CCR efficiency model
 */
public class ImpreciseCCRSmaaEfficiency extends CCRSmaaBase implements SmaaEfficiency<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private final int numberOfIntervals;

    /**
     * Creates new object with given number of samples and 10 intervals
     *
     * @param numberOfSamples number of samples
     */
    public ImpreciseCCRSmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates new object with given number of samples and intervals
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public ImpreciseCCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates new object with given number of samples and intervals and random object
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     * @param random            {@link Random} object for sampling
     */
    public ImpreciseCCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public int getNumberOfIntervals() {
        return this.numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(CCRImpreciseProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(),
                false);
        return calculateDistributionFromSamples(weightSamples, performanceSamples, data.getDmuCount());
    }

    private DistributionResult calculateDistributionFromSamples(WeightSamplesCollection weightSamples,
                                                                PerformanceSamplesCollection performanceSamples,
                                                                int dmuCount) {
        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                dmuCount);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var normalizedEfficiencies = normalizeEfficiencies(efficiencyMatrix);
        var distribution = efficiencyBase.calculateDistribution(normalizedEfficiencies);
        var expectedEfficiency = calculateExpectedValues(normalizedEfficiencies);
        return new DistributionResult(distribution, expectedEfficiency);
    }


}
