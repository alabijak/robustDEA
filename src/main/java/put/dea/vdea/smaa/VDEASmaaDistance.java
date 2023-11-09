package put.dea.vdea.smaa;

import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaDistance;
import put.dea.common.smaa.SmaaEfficiencyBase;
import put.dea.vdea.VDEAProblemData;

import java.util.Random;

public class VDEASmaaDistance extends VDEASmaaBase implements SmaaDistance<VDEAProblemData> {
    private int numberOfIntervals;

    public VDEASmaaDistance(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    public VDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public VDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    @Override
    public void setNumberOfIntervals(int numberOfIntervals) {
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public DistributionResult distanceDistribution(VDEAProblemData data) {
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var distanceMatrix = calculateDistanceMatrix(efficiencyMatrix);
        var distribution = efficiencyBase.calculateDistribution(distanceMatrix);
        var expectedEfficiency = calculateExpectedValues(distanceMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }
}
