package put.dea.robustness;

import joinery.DataFrame;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class SmaaEfficiencyBase {

    private final int numberOfIntervals;
    private final int numberOfSamples;

    public SmaaEfficiencyBase(int numberOfSamples, int numberOfIntervals) {
        this.numberOfIntervals = numberOfIntervals;
        this.numberOfSamples = numberOfSamples;
    }

    public DataFrame<Double> calculateDistribution(DataFrame<Double> efficiencies) {
        var distribution = new DataFrame<Double>(IntStream.range(0, numberOfIntervals).mapToObj(idx -> "interval" + idx).toList());
        efficiencies.forEach(row -> distribution.append(calculateDistributionForRow(row)));
        return distribution;
    }

    private List<Double> calculateDistributionForRow(List<Double> row) {
        var distribution = new double[numberOfIntervals];
        var indices = row.stream().map(x -> (int) Math.max(Math.ceil(x * numberOfIntervals) - 1, 0));
        indices.forEach(idx -> distribution[idx] += 1);
        return Arrays.stream(distribution).map(x -> x / numberOfSamples).boxed().toList();
    }
}
