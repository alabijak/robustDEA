package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

abstract class VDEASmaaBase extends SmaaBase {

    protected final PerformanceToValueConverter performanceToValueConverter = new PerformanceToValueConverter();

    protected VDEASmaaBase(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    protected Table calculateEfficiencyMatrix(ProblemData data, Table inputValues, Table outputValues) {
        var samples = generateWeightSamples(data);
        return calculateEfficiencyMatrixForSamples(inputValues, outputValues, samples);
    }

    @Override
    protected double calculateEfficiency(Table inputs, Table outputs,
                                         Row inputsSample, Row outputsSample, int dmuIdx) {
        var efficiency = IntStream.range(0, inputs.columnCount())
                .mapToDouble(idx -> inputs.row(dmuIdx).getDouble(idx) * inputsSample.getDouble(idx))
                .sum();
        efficiency += IntStream.range(0, outputs.columnCount())
                .mapToDouble(idx -> outputs.row(dmuIdx).getDouble(idx) * outputsSample.getDouble(idx))
                .sum();
        return efficiency;
    }

    @Override
    protected ConstraintsSet createModelSpecificConstraints(ProblemData data) {
        return new ConstraintsSet(
                List.of(Collections
                        .nCopies(data.getInputCount() + data.getOutputCount(), 1.0)
                        .stream()
                        .mapToDouble(x -> x)
                        .toArray()),
                List.of("="),
                List.of(1.0)
        );
    }

    protected Table calculateDistanceMatrix(Table efficiencyMatrix) {
        return Table.create(
                efficiencyMatrix.columns()
                        .stream()
                        .map(this::calculateDistancesForColumn)
        );
    }

    private DoubleColumn calculateDistancesForColumn(Column<?> column) {
        var doubleColumn = (DoubleColumn) column;
        var max = doubleColumn.max();
        return doubleColumn.map(v -> max - v);
    }
}
