package put.dea.vdea;

import joinery.DataFrame;
import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.List;

public class PerformanceToValueConverter {
    public DataFrame<Double> transformInputsToUtilities(VDEAProblemData data) {
        return transformPerformanceToUtilities(data, true);
    }

    private DataFrame<Double> transformPerformanceToUtilities(VDEAProblemData data,
                                                              boolean input) {
        DataFrame<Double> utilities = new DataFrame<>();
        var performances = input ? data.getInputData() : data.getOutputData();
        for (var columnName : performances.columns()) {
            var shape = data.getFunctionShape(columnName.toString());
            var df = input ? data.getInputData() : data.getOutputData();
            utilities.add(columnName,
                    transformColumnToUtilities(df, columnName.toString(), shape));
        }
        return utilities;
    }

    public List<Double> transformColumnToUtilities(DataFrame<Double> data,
                                                   String columnName,
                                                   List<Pair<Double, Double>> shape) {
        var sortedShape = shape.stream().sorted(Comparator.comparing(Pair::getFirst)).toList();
        return data.col(columnName).stream().map(v -> transformValueToUtilities(v, sortedShape)).toList();
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

    public DataFrame<Double> transformOutputsToUtilities(VDEAProblemData data) {
        return transformPerformanceToUtilities(data, false);
    }
}
