package put.dea.robustness;

import java.util.Random;

/**
 * Calculates the distribution (Distance Acceptability Interval Indices)
 * and the expected values of the distance to the best unit
 * for all DMUs in the problems with hierarchical structure of inputs and outputs
 */
public class HierarchicalVDEASmaaDistance extends VDEASmaaBase
        implements SmaaDistance<HierarchicalVDEAProblemData> {

    private final int numberOfIntervals;

    /**
     * Creates the {@link HierarchicalVDEASmaaDistance} object with given number of samples
     * and 10 buckets
     *
     * @param numberOfSamples number of samples
     */
    public HierarchicalVDEASmaaDistance(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaDistance} object with given number of samples
     * and intervals (buckets)
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public HierarchicalVDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaDistance} object with given number of samples
     * and intervals (buckets) and predefined {@link Random} object for sampling
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     * @param random            {@link Random} object to use in sampling
     */
    public HierarchicalVDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    /**
     * calculates the distribution and expected values of distance to the best DMU for all DMUs
     * at the root level of the input/output hierarchy
     *
     * @param data data set specification
     * @return distribution of the efficiency distance to the best DMU and distance expected values
     */
    @Override
    public DistributionResult distanceDistribution(HierarchicalVDEAProblemData data) {
        return distanceDistribution(data, data.getHierarchy().getName());
    }

    /**
     * calculates the distribution and expected values of distance to the best DMU for all DMUs
     * at the specified level of the input/output hierarchy
     *
     * @param data           data set specification
     * @param hierarchyLevel level of the hierarchy
     * @return distribution of the efficiency distance to the best DMU and distance expected values
     */
    public DistributionResult distanceDistribution(HierarchicalVDEAProblemData data,
                                                   String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaDistance = new VDEASmaaDistance(numberOfSamples,
                numberOfIntervals, random);
        return smaaDistance.distanceDistribution(dataForModel);
    }
}
