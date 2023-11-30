package put.dea.robustness;

import tech.tablesaw.api.Table;

import java.util.Random;


/**
 * Finds the pairwise efficiency outranking indices for all pairs of DMUs
 * in standard (precise) DEA problems with CCR model
 */
public class CCRSmaaPreferenceRelations extends CCRSmaaBase implements SmaaPreferenceRelations<ProblemData> {

    /**
     * Creates the {@link CCRSmaaPreferenceRelations} object with given number of samples to generate
     *
     * @param numberOfSamples number of samples to generate
     */
    public CCRSmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates the {@link CCRSmaaPreferenceRelations} object with given number of samples to generate
     * and specified {@link Random} object for generation
     *
     * @param numberOfSamples number of samples to generate
     * @param random          {@link Random} object
     */
    public CCRSmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public Table peoi(ProblemData data) {
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        var efficiencies = calculateEfficiencyMatrix(data);
        return preferenceRelationsBase.calculatePeois(efficiencies);
    }
}
