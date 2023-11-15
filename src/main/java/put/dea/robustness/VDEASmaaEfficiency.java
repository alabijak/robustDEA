package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation the distribution of efficiency scores
 * for standard (precise) problems with VDEA efficiency model
 */
public class VDEASmaaEfficiency extends VDEASmaaBase implements SmaaEfficiency<VDEAProblemData> {

    private final int numberOfIntervals;

    /**
     * Creates new object with given number of samples and default (10) number of intervals
     *
     * @param numberOfSamples number of samples
     */
    public VDEASmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates new object with given number of samples and intervals
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public VDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
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
    public VDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(VDEAProblemData data) {
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var distribution = efficiencyBase.calculateDistribution(efficiencyMatrix);
        var expectedEfficiency = calculateExpectedValues(efficiencyMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }

}
