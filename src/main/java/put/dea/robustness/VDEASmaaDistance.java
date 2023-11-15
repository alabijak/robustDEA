package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation the distribution of efficiency distance to the best DMU
 * (Distance Acceptability Interval Indices)
 * for standard (precise) problems with VDEA efficiency model
 */
public class VDEASmaaDistance extends VDEASmaaBase implements SmaaDistance<VDEAProblemData> {
    private final int numberOfIntervals;

    /**
     * Creates new object with given number of samples and 10 intervals
     *
     * @param numberOfSamples number of samples
     */
    public VDEASmaaDistance(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates new object with given number of samples and intervals
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public VDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates new object with given number of samples and intervals
     * and a specific {@link Random} object used for sampling
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     * @param random            {@link Random} object for sampling
     */
    public VDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    @Override
    public DistributionResult distanceDistribution(VDEAProblemData data) {
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var distanceMatrix = calculateDistanceMatrix(efficiencyMatrix);
        var distribution = efficiencyBase.calculateDistribution(distanceMatrix);
        var expectedEfficiency = calculateExpectedValues(distanceMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
