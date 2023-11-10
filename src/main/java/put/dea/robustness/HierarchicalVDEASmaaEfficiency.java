package put.dea.robustness;

import java.util.Random;

/**
 * Calculates the distribution (Efficiency Acceptability Interval Indices)
 * and the expected values of the efficiency scores
 * for all DMUs in the problems with hierarchical structure of inputs and outputs
 */
public class HierarchicalVDEASmaaEfficiency extends VDEASmaaBase
        implements SmaaEfficiency<HierarchicalVDEAProblemData> {

    private final int numberOfIntervals;

    /**
     * Creates the {@link HierarchicalVDEASmaaEfficiency} object with given number of samples
     * and 10 buckets
     *
     * @param numberOfSamples number of samples
     */
    public HierarchicalVDEASmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaEfficiency} object with given number of samples
     * and intervals (buckets)
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public HierarchicalVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaEfficiency} object with given number of samples,
     * intervals (buckets) and predefined {@link Random} object for sampling
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     * @param random            {@link Random} object to use in sampling
     */
    public HierarchicalVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    /**
     * calculates the distribution and expected values of efficiency scores
     * for all DMUs
     * at the root level of the input/output hierarchy
     *
     * @param data data set specification
     * @return distribution of the efficiency distance to the best DMU and distance expected values
     */
    @Override
    public DistributionResult efficiencyDistribution(HierarchicalVDEAProblemData data) {
        return efficiencyDistribution(data, data.getHierarchy().getName());
    }

    /**
     * calculates the distribution and expected values of efficiency scores
     * for all DMUs
     * at the specified level of the input/output hierarchy
     *
     * @param data           data set specification
     * @param hierarchyLevel level of the input/output hierarchy
     * @return distribution of the efficiency distance to the best DMU and distance expected values
     */
    public DistributionResult efficiencyDistribution(HierarchicalVDEAProblemData data,
                                                     String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaEfficiency = new VDEASmaaEfficiency(numberOfSamples,
                numberOfIntervals, random);
        return smaaEfficiency.efficiencyDistribution(dataForModel);
    }
}
