package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.Arrays;

class SmaaEfficiencyBase {

    private final int numberOfIntervals;
    private final int numberOfSamples;

    public SmaaEfficiencyBase(int numberOfSamples, int numberOfIntervals) {
        this.numberOfIntervals = numberOfIntervals;
        this.numberOfSamples = numberOfSamples;
    }

    public Table calculateDistribution(Table efficiencies) {
        var distribution = Table.create();
        efficiencies.transpose().columns()
                .stream()
                .map(column -> calculateDistributionForRow((DoubleColumn) column))
                .forEach(values -> distribution.addColumns(
                        DoubleColumn.create(distribution.columnCount() + "", values)));
        return distribution.transpose();
    }

    private double[] calculateDistributionForRow(DoubleColumn efficiencies) {
        var distribution = new double[numberOfIntervals];
        var indices = efficiencies.map(x -> Math.max(Math.ceil(x * numberOfIntervals) - 1, 0));
        indices.forEach(idx -> distribution[idx.intValue()] += 1);
        return Arrays.stream(distribution).map(x -> x / numberOfSamples).toArray();
    }
}
