package put.dea.vdea.imprecise.smaa;

import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaDistance;
import put.dea.common.smaa.SmaaEfficiencyBase;
import put.dea.vdea.imprecise.ImpreciseVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;

import java.util.Random;

public class ImpreciseVDEASmaaDistance extends VDEASmaaBase implements SmaaDistance<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;
    private int numberOfIntervals;

    public ImpreciseVDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public ImpreciseVDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
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
    public DistributionResult distanceDistribution(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var distanceMatrix = calculateDistanceMatrix(efficiencyMatrix);
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var distribution = efficiencyBase.calculateDistribution(distanceMatrix);
        var expectedEfficiency = calculateExpectedValues(distanceMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }

}
