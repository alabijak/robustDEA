package put.dea.robustness;

import java.util.Random;

public class VDEASmaaEfficiency extends VDEASmaaBase implements SmaaEfficiency<VDEAProblemData> {

    private final int numberOfIntervals;

    public VDEASmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    public VDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public VDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(VDEAProblemData data) {
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var distribution = efficiencyBase.calculateDistribution(efficiencyMatrix);
        var expectedEfficiency = calculateExpectedValues(efficiencyMatrix);
        return new DistributionResult(distribution, expectedEfficiency);
    }

}
