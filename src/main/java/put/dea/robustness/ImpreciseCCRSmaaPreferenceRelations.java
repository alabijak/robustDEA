package put.dea.robustness;

import joinery.DataFrame;

import java.util.Random;

/**
 * Finds the pairwise efficiency outranking indices for all pairs of DMUs
 * in imprecise DEA problems with CCR model
 */
public class ImpreciseCCRSmaaPreferenceRelations extends CCRSmaaBase implements SmaaPreferenceRelations<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    /**
     * Creates new object with given number of samples to generate
     *
     * @param numberOfSamples number of samples to generate
     */
    public ImpreciseCCRSmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates new object with given number of samples to generate
     * and specified {@link Random} object for generation
     *
     * @param numberOfSamples number of samples to generate
     * @param random          {@link Random} object
     */
    public ImpreciseCCRSmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DataFrame<Double> peoi(CCRImpreciseProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), false);
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        var efficiencies = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                data.getDmuCount());
        return preferenceRelationsBase.calculatePeois(efficiencies);
    }
}
