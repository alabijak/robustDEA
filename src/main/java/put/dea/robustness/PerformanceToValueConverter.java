package put.dea.robustness;

import org.apache.commons.math3.util.Pair;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.Comparator;
import java.util.List;

class PerformanceToValueConverter {
    public Table transformInputsToUtilities(VDEAProblemData data) {
        return transformPerformanceToUtilities(data, true);
    }

    private Table transformPerformanceToUtilities(VDEAProblemData data,
                                                  boolean input) {
        Table utilities = Table.create();
        var performances = input ? data.getInputData() : data.getOutputData();
        for (var columnName : performances.columnNames()) {
            var shape = data.getFunctionShape(columnName);
            var df = input ? data.getInputData() : data.getOutputData();
            utilities.addColumns(transformColumnToUtilities(df, columnName, shape));
        }
        return utilities;
    }

    public DoubleColumn transformColumnToUtilities(Table data,
                                                   String columnName,
                                                   List<Pair<Double, Double>> shape) {
        var sortedShape = shape.stream().sorted(Comparator.comparing(Pair::getFirst)).toList();
        return data.doubleColumn(columnName).map(v -> transformValueToUtilities(v, sortedShape));
    }

    private Double transformValueToUtilities(Double value,
                                             List<Pair<Double, Double>> shape) {
        var lb = shape.get(0);
        for (var point : shape.stream().skip(1).toList()) {
            var ub = point.getFirst();
            if (value <= ub) {
                var linearRatio = (value - lb.getFirst()) / (ub - lb.getFirst());
                return lb.getSecond() + linearRatio * (point.getSecond() - lb.getSecond());
            }
            lb = point;
        }
        throw new IllegalArgumentException("Given input/output value out of defined column bounds");
    }

    public Table transformOutputsToUtilities(VDEAProblemData data) {
        return transformPerformanceToUtilities(data, false);
    }
}
