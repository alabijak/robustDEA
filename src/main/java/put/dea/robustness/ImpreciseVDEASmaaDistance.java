package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation the distribution of efficiency distance to the best DMU
 * (Distance Acceptability Interval Indices)
 * for problems with imprecise information and VDEA efficiency model
 */
public class ImpreciseVDEASmaaDistance extends VDEASmaaBase implements SmaaDistance<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private final int numberOfIntervals;

    /**
     * Creates a new object with given number of samples and number of intervals (buckets)
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals (buckets)
     */
    public ImpreciseVDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
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
    public ImpreciseVDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public int getNumberOfIntervals() {
        return this.numberOfIntervals;
    }

    @Override
    public DistributionResult distanceDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var distanceMatrix = calculateDistanceMatrix(efficiencyMatrix);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var distribution = efficiencyBase.calculateDistribution(distanceMatrix);
        var expectedEfficiency = calculateExpectedValues(distanceMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }

}
