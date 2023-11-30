package put.dea.robustness;

import org.apache.commons.math3.util.Pair;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

class SmaaRankBase {

    private final int numberOfSamples;

    public SmaaRankBase(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    public Table calculateRankDistribution(Table ranks) {
        var distribution = Table.create();
        ranks.forEach(row -> distribution.addColumns(
                        DoubleColumn.create(distribution.columnCount() + "",
                                calculateDistributionForRow(row, ranks.rowCount()))
                )
        );
        return distribution.transpose();
    }

    private List<Double> calculateDistributionForRow(Row row, int dmuCount) {
        var distribution = new double[dmuCount];
        IntStream.range(0, row.columnCount())
                .mapToDouble(row::getDouble)
                .forEach(rank -> distribution[(int) rank] += 1);
        return Arrays.stream(distribution).map(x -> x / numberOfSamples).boxed().toList();
    }

    public Table calculateRanksMatrix(Table efficiencies) {
        var result = Table.create();
        for (int sampleIdx = 0; sampleIdx < numberOfSamples; sampleIdx++) {
            var sample = efficiencies.doubleColumn(sampleIdx).asDoubleArray();
            var sortedSample = indicesSortedByValue(sample, true);

            var rankList = indicesSortedByValue(sortedSample.stream().mapToDouble(x -> (double) x).toArray(),
                    false);
            result.addColumns(DoubleColumn.create(sampleIdx + "", rankList.stream().mapToDouble(x -> x)));
        }
        return result;
    }

    private List<Integer> indicesSortedByValue(double[] values, boolean descending) {
        var sign = descending ? -1 : 1;
        return IntStream.range(0, values.length)
                .mapToObj(idx -> new Pair<>(values[idx], idx))
                .sorted(Comparator.comparing(pair -> sign * pair.getFirst()))
                .map(Pair::getSecond)
                .toList();
    }
}
