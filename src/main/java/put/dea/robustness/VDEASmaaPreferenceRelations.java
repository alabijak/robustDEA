package put.dea.robustness;

import joinery.DataFrame;

import java.util.Random;

/**
 * Class for calculation of the pairwise efficiency outranking indices for all pairs of DMUs
 * for standard (precise) problems with VDEA efficiency model
 */
public class VDEASmaaPreferenceRelations extends VDEASmaaBase implements SmaaPreferenceRelations<VDEAProblemData> {

    /**
     * Creates new object with given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public VDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates new object with given number of samples
     * and a specific {@link Random} object used for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          {@link Random} object for sampling
     */
    public VDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DataFrame<Double> peoi(VDEAProblemData data) {
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        return preferenceRelationsBase.calculatePeois(efficiencyMatrix);
    }
}
