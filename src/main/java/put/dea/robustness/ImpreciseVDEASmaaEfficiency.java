package put.dea.robustness;

import java.util.Random;

/**
 * Interface for methods for calculation the distribution of efficiency scores
 * for problems with imprecise information and VDEA efficiency model
 */
public class ImpreciseVDEASmaaEfficiency extends VDEASmaaBase implements SmaaEfficiency<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private final int numberOfIntervals;

    /**
     * Creates a new object with given number of samples and number of intervals (buckets)
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals (buckets)
     */
    public ImpreciseVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates a new object with given number of samples and number of intervals (buckets)
     * and a specific {@link Random} object for sampling
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals (buckets)
     * @param random            {@link Random} object for sampling
     */

    public ImpreciseVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public int getNumberOfIntervals() {
        return this.numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);
        return calculateDistributionFromSamples(weightSamples, valueFunctionsSamples, data.getDmuCount());
    }

    private DistributionResult calculateDistributionFromSamples(WeightSamplesCollection weightSamples,
                                                                PerformanceSamplesCollection performanceSamples,
                                                                int dmuCount) {
        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                dmuCount);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var distribution = efficiencyBase.calculateDistribution(efficiencyMatrix);
        var expectedEfficiency = calculateExpectedValues(efficiencyMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
