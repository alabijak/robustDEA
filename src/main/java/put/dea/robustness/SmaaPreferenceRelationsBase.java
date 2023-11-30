package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.function.Predicate;

class SmaaPreferenceRelationsBase {
    private final int numberOfSamples;

    public SmaaPreferenceRelationsBase(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }


    public Table calculatePeois(Table efficiencies) {
        var peoi = Table.create();
        efficiencies = efficiencies.transpose();

        for (int dmuB = 0; dmuB < efficiencies.columnCount(); dmuB++) {
            var efficienciesB = efficiencies.doubleColumn(dmuB);
            var values = new double[efficiencies.columnCount()];
            for (int dmuA = 0; dmuA < efficiencies.columnCount(); dmuA++) {
                var efficienciesA = efficiencies.doubleColumn(dmuA);
                var count = efficienciesA.subtract(efficienciesB).count((Predicate<? super Double>) x -> x >= 0.0);
                values[dmuA] = (double) count / numberOfSamples;
            }
            peoi.addColumns(DoubleColumn.create(dmuB + "", values));
        }
        return peoi;

    }
}
