package put.dea.robustness;

import joinery.DataFrame;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Finds the efficiency distribution (efficiency acceptability interval indices)
 * and expected efficiency scores for all pairs of DMUs
 * in standard (precise) DEA problems with CCR model
 */
public class CCRSmaaEfficiency extends CCRSmaaBase implements SmaaEfficiency<ProblemData> {

    private final int numberOfIntervals;

    /**
     * Creates {@link CCRSmaaEfficiency} object with given number of samples and 10 intervals
     *
     * @param numberOfSamples number of samples
     */
    public CCRSmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    /**
     * Creates {@link CCRSmaaEfficiency} object with given number of samples and intervals
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     */
    public CCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    /**
     * Creates {@link CCRSmaaEfficiency} object with given number of samples and intervals and random object
     *
     * @param numberOfSamples   number of samples
     * @param numberOfIntervals number of intervals
     * @param random            {@link Random} object for sampling
     */
    public CCRSmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
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
