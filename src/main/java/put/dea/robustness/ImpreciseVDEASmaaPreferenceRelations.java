package put.dea.robustness;

import tech.tablesaw.api.Table;

import java.util.Random;

/**
 * Class for calculation of the pairwise efficiency outranking indices for all pairs of DMUs
 * in problems with imprecise information and VDEA efficiency model
 */
public class ImpreciseVDEASmaaPreferenceRelations extends VDEASmaaBase implements SmaaPreferenceRelations<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    /**
     * Creates a new object with given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public ImpreciseVDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates a new object with given number of samples
     * and a specific {@link Random} object for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          object used for sampling
     */
    public ImpreciseVDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public Table peoi(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        return preferenceRelationsBase.calculatePeois(efficiencyMatrix);
    }
}
