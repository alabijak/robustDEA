package put.dea.robustness;

import joinery.DataFrame;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

class SmaaRankBase {

    private final int numberOfSamples;

    public SmaaRankBase(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    public DataFrame<Double> calculateRankDistribution(DataFrame<Integer> ranks) {
        var distribution = new DataFrame<Double>(
                IntStream.range(1, ranks.length() + 1)
                        .boxed()
                        .map(Object::toString)
                        .toList());
        ranks.forEach(row -> distribution.append(calculateDistributionForRow(row, ranks.length())));
        return distribution;
    }

    private List<Double> calculateDistributionForRow(List<Integer> row, int dmuCount) {
        var distribution = new double[dmuCount];
        row.forEach(rank -> distribution[rank] += 1);
        return Arrays.stream(distribution).map(x -> x / numberOfSamples).boxed().toList();
    }

    public DataFrame<Integer> calculateRanksMatrix(DataFrame<Double> efficiencies) {
        var result = new DataFrame<Integer>();
        for (int sampleIdx = 0; sampleIdx < numberOfSamples; sampleIdx++) {
            var sample = efficiencies.col(sampleIdx);
            var sortedSample = indicesSortedByValue(sample, true);

            var rankList = indicesSortedByValue(sortedSample.stream().map(x -> (double) x).toList(),
                    false);
            result.add(sampleIdx, rankList);
        }
        return result;
    }

    private List<Integer> indicesSortedByValue(List<Double> values, boolean descending) {
        var sign = descending ? -1 : 1;
        return IntStream.range(0, values.size())
                .mapToObj(idx -> new Pair<>(values.get(idx), idx))
                .sorted(Comparator.comparing(pair -> sign * pair.getFirst()))
                .map(Pair::getSecond)
                .toList();
    }
}
