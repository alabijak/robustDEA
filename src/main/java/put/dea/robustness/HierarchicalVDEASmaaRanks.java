package put.dea.robustness;

import java.util.Random;

/**
 * Finds the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 * in problems with hierarchical structure of inputs and outputs
 */
public class HierarchicalVDEASmaaRanks extends VDEASmaaBase
        implements SmaaRanks<HierarchicalVDEAProblemData> {
    /**
     * Creates the {@link HierarchicalVDEASmaaRanks} object with
     * given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public HierarchicalVDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaRanks} object with
     * given number of samples and {@link Random} object for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          {@link Random} object used for sampling
     */
    public HierarchicalVDEASmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    /**
     * Finds the rank distribution (efficiency rank acceptability indices)
     * and expected ranks for all DMUs in the data set
     * at the root level of input/output hierarchy
     *
     * @param data data set specification
     * @return rank distribution and expected ranks
     */
    @Override
    public DistributionResult rankDistribution(HierarchicalVDEAProblemData data) {
        return rankDistribution(data, data.getHierarchy().getName());
    }

    /**
     * Finds the rank distribution (efficiency rank acceptability indices)
     * and expected ranks for all DMUs in the data set
     * at the specified level of input/output hierarchy
     *
     * @param data data set specification
     * @return rank distribution and expected ranks
     */
    public DistributionResult rankDistribution(HierarchicalVDEAProblemData data,
                                               String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaRanks = new VDEASmaaRanks(numberOfSamples, random);
        return smaaRanks.rankDistribution(dataForModel);
    }
}
