package put.dea.robustness;

import joinery.DataFrame;

import java.util.Random;

/**
 * Finds the pairwise efficiency outranking indices for all pairs of DMUs
 * in the problems with hierarchical structure of inputs and outputs
 */
public class HierarchicalVDEASmaaPreferenceRelations extends VDEASmaaBase
        implements SmaaPreferenceRelations<HierarchicalVDEAProblemData> {
    /**
     * Creates the {@link HierarchicalVDEASmaaPreferenceRelations} object with given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public HierarchicalVDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates the {@link HierarchicalVDEASmaaPreferenceRelations} object with given number of samples
     * and predefined {@link Random} object for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          {@link Random} object to use in sampling
     */
    public HierarchicalVDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    /**
     * calculates the matrix of pairwise efficiency outranking indices (PEOIs) for all pairs of DMUs
     * at the root level of factors' hierarchy
     *
     * @param data data set specification
     * @return matrix of PEOIs
     */
    @Override
    public DataFrame<Double> peoi(HierarchicalVDEAProblemData data) {
        return peoi(data, data.getHierarchy().getName());
    }

    /**
     * calculates the matrix of pairwise efficiency outranking indices (PEOIs) for all pairs of DMUs
     * at the specified level of factors' hierarchy
     *
     * @param data data set specification
     * @return matrix of PEOIs
     */
    public DataFrame<Double> peoi(HierarchicalVDEAProblemData data, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaPreferenceRelations = new VDEASmaaPreferenceRelations(numberOfSamples, random);
        return smaaPreferenceRelations.peoi(dataForModel);
    }
}
