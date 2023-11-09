package put.dea.ccr.smaa;

import joinery.DataFrame;
import put.dea.common.ProblemData;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaEfficiency;
import put.dea.common.smaa.SmaaEfficiencyBase;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class CCRSmaaEfficiency extends CCRSmaaBase implements SmaaEfficiency<ProblemData> {

    private int numberOfIntervals;

    public CCRSmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    public CCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public CCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
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
    public DistributionResult efficiencyDistribution(ProblemData data) {
        var efficiencyBase = new SmaaEfficiencyBase(numberOfSamples, numberOfIntervals);
        var efficiencyMatrix = calculateEfficiencyMatrix(data);
        var normalizedEfficiencies = normalizeEfficiencies(efficiencyMatrix);
        var distribution = efficiencyBase.calculateDistribution(normalizedEfficiencies);
        var expectedEfficiency = calculateExpectedValues(normalizedEfficiencies);
        return new DistributionResult(distribution, expectedEfficiency);
    }

    protected DataFrame<Double> normalizeEfficiencies(DataFrame<Double> efficiencies) {
        var max = efficiencies.max().row(0);
        return efficiencies.transform(row -> List.of(normalizeRow(row, max)));
    }


    private List<Double> normalizeRow(List<Double> row, List<Double> max) {
        return IntStream.range(0, row.size())
                .mapToDouble(idx -> row.get(idx) / max.get(idx))
                .boxed()
                .toList();
    }


}
