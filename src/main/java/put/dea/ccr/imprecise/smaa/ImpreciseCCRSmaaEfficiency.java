package put.dea.ccr.imprecise.smaa;

import put.dea.ccr.imprecise.CCRImpreciseProblemData;
import put.dea.ccr.smaa.CCRSmaaBase;
import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.imprecise.smaa.PerformanceSamplesCollection;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaEfficiency;
import put.dea.common.smaa.SmaaEfficiencyBase;
import put.dea.common.smaa.WeightSamplesCollection;

import java.util.Random;

public class ImpreciseCCRSmaaEfficiency extends CCRSmaaBase implements SmaaEfficiency<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private int numberOfIntervals;

    public ImpreciseCCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public ImpreciseCCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public int getNumberOfIntervals() {
        return this.numberOfIntervals;
    }

    @Override
    public void setNumberOfIntervals(int numberOfIntervals) {
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(CCRImpreciseProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(),
                false);
        return calculateDistributionFromSamples(weightSamples, performanceSamples, data.getDmuCount());
    }

    public DistributionResult calculateDistributionFromSamples(WeightSamplesCollection weightSamples,
                                                               PerformanceSamplesCollection performanceSamples,
                                                               int dmuCount) {
        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                dmuCount);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var normalizedEfficiencies = normalizeEfficiencies(efficiencyMatrix);
        var distribution = efficiencyBase.calculateDistribution(normalizedEfficiencies);
        var expectedEfficiency = calculateExpectedValues(normalizedEfficiencies);
        return new DistributionResult(distribution, expectedEfficiency);
    }


}
