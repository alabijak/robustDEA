package put.dea.vdea.imprecise.smaa;

import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.imprecise.smaa.PerformanceSamplesCollection;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaEfficiency;
import put.dea.common.smaa.SmaaEfficiencyBase;
import put.dea.common.smaa.WeightSamplesCollection;
import put.dea.vdea.imprecise.ImpreciseVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;

import java.util.Random;

public class ImpreciseVDEASmaaEfficiency extends VDEASmaaBase implements SmaaEfficiency<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private int numberOfIntervals;

    public ImpreciseVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public ImpreciseVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
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
    public DistributionResult efficiencyDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);
        return calculateDistributionFromSamples(weightSamples, valueFunctionsSamples, data.getDmuCount());
    }

    public DistributionResult calculateDistributionFromSamples(WeightSamplesCollection weightSamples,
                                                               PerformanceSamplesCollection performanceSamples,
                                                               int dmuCount) {
        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                dmuCount);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var distribution = efficiencyBase.calculateDistribution(efficiencyMatrix);
        var expectedEfficiency = calculateExpectedValues(efficiencyMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
