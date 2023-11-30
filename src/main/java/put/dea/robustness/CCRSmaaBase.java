package put.dea.robustness;


import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

abstract class CCRSmaaBase extends SmaaBase {

    protected CCRSmaaBase(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    protected double calculateEfficiency(Table inputs,
                                         Table outputs,
                                         Row inputsSample,
                                         Row outputsSample,
                                         int dmuIdx) {
        var inputsValue = calculateWeightedSum(inputs.row(dmuIdx), inputsSample);
        var outputsValue = calculateWeightedSum(outputs.row(dmuIdx), outputsSample);
        return outputsValue / inputsValue;
    }

    private double calculateWeightedSum(Row performances, Row weights) {
        return IntStream.range(0, performances.columnCount())
                .mapToDouble(idx -> performances.getDouble(idx) * weights.getDouble(idx))
                .sum();
    }

    @Override
    protected ConstraintsSet createModelSpecificConstraints(ProblemData data) {
        return new ConstraintsSet(
                List.of(createInputsSumConstraint(data), createOutputsSumConstraint(data)),
                List.of("=", "="),
                List.of(1.0, 1.0)
        );
    }

    private double[] createInputsSumConstraint(ProblemData data) {
        return createInputsOrOutputsSumConstraint(data, true);
    }

    private double[] createOutputsSumConstraint(ProblemData data) {
        return createInputsOrOutputsSumConstraint(data, false);
    }

    private double[] createInputsOrOutputsSumConstraint(ProblemData data, boolean inputs) {
        var value = inputs ? 1 : 0;
        return Stream.concat(Collections.nCopies(data.getInputCount(), value).stream(),
                        Collections.nCopies(data.getOutputCount(), 1 - value).stream())
                .mapToDouble(x -> x)
                .toArray();
    }

    protected Table normalizeEfficiencies(Table efficiencies) {
        return Table.create(efficiencies.columns().stream().map(this::normalizeColumn));
    }

    private DoubleColumn normalizeColumn(Column<?> column) {
        var doubleColumn = (DoubleColumn) column;
        var max = doubleColumn.max();
        return doubleColumn.divide(max);
    }

}
