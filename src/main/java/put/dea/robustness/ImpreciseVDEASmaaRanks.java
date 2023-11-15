package put.dea.robustness;

import java.util.Random;

/**
 * Class for calculation the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 * for problems with imprecise information and VDEA efficiency model
 */
public class ImpreciseVDEASmaaRanks extends VDEASmaaBase implements SmaaRanks<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    /**
     * Creates a new object with given number of samples
     *
     * @param numberOfSamples number of samples
     */
    public ImpreciseVDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    /**
     * Creates a new object with given number of samples
     * and a specific {@link Random} object for sampling
     *
     * @param numberOfSamples number of samples
     * @param random          object used for sampling
     */
    public ImpreciseVDEASmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DistributionResult rankDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var rankBase = new SmaaRankBase(numberOfSamples);
        var ranks = rankBase.calculateRanksMatrix(efficiencyMatrix);
        var distribution = rankBase.calculateRankDistribution(ranks);
        var expectedEfficiency = calculateExpectedValues(ranks.apply(x -> x + 1).cast(Double.class));
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
