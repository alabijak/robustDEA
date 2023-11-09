package put.dea.common.smaa;

import joinery.DataFrame;

import java.util.Arrays;

public class WeightSamplesCollection {
    private final DataFrame<Double> inputSamples;
    private final DataFrame<Double> outputSamples;

    public WeightSamplesCollection(double[][] samples, int inputCount) {
        inputSamples = new DataFrame<>();
        outputSamples = new DataFrame<>();
        for (var sample : samples) {
            inputSamples.append(Arrays.stream(sample).boxed().limit(inputCount).toList());
            outputSamples.append(Arrays.stream(sample).boxed().skip(inputCount).toList());
        }
    }

    public DataFrame<Double> getInputSamples() {
        return inputSamples;
    }

    public DataFrame<Double> getOutputSamples() {
        return outputSamples;
    }
}

