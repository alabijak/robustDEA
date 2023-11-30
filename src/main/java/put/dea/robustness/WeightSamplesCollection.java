package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.Arrays;

class WeightSamplesCollection {
    private final Table inputSamples;
    private final Table outputSamples;

    public WeightSamplesCollection(double[][] samples, int inputCount) {
        var tmpInputSamples = Table.create();
        var tmpOutputSamples = Table.create();
        for (var sample : samples) {
            var inSample = Arrays.stream(sample).boxed().limit(inputCount).toList();
            var outSample = Arrays.stream(sample).boxed().skip(inputCount).toList();
            tmpInputSamples.addColumns(DoubleColumn.create(tmpInputSamples.columnCount() + "", inSample));
            tmpOutputSamples.addColumns(DoubleColumn.create(tmpOutputSamples.columnCount() + "", outSample));
        }
        inputSamples = tmpInputSamples.transpose();
        outputSamples = tmpOutputSamples.transpose();
    }

    public Table getInputSamples() {
        return inputSamples;
    }

    public Table getOutputSamples() {
        return outputSamples;
    }
}

