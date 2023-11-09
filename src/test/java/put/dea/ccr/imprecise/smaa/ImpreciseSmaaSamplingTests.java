package put.dea.ccr.imprecise.smaa;

import joinery.DataFrame;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.ccr.imprecise.ImpreciseCCRTestBase;
import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.imprecise.smaa.PerformanceSamplesCollection;
import put.dea.common.smaa.WeightSamplesCollection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ImpreciseSmaaSamplingTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyTestSamples() {
        ImpreciseSmaaUtils smaaUtils = new ImpreciseSmaaUtils(new ImpreciseCCRSmaaEfficiency(
                1000,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom()));
        var samples = smaaUtils.generatePerformanceSamples(data.getImpreciseInformation(), false);
        verifyOrdinalInput(samples.getInputPerformances().stream().map(x -> x.col("reputation")).toList());
        verifyIntervalOutput(samples.getOutputPerformances().stream().map(x -> x.col("capacity")).toList());
        verifyPreciseFactors(samples);
    }

    private void verifyOrdinalInput(List<List<Double>> reputation) {
        var expectedSortedIndices = new int[]{16, 9, 5, 24, 19, 26, 1, 6, 8, 11, 14, 18, 21,
                23, 0, 3, 7, 10, 13, 15, 17, 25, 2, 4, 12, 20, 22};
        for (var sample : reputation) {
            var actualSortedIndices = IntStream.range(0, data.getDmuCount())
                    .boxed()
                    .map(idx -> new Pair<>(idx, sample.get(idx)))
                    .sorted(Comparator.comparing(Pair::getSecond))
                    .mapToInt(Pair::getFirst)
                    .toArray();
            Assertions.assertArrayEquals(expectedSortedIndices, actualSortedIndices);

        }

    }

    private void verifyIntervalOutput(List<List<Double>> capacity) {
        var expectedMinValues = data.getOutputData().col("capacity");
        var expectedMaxValues = data.getImpreciseInformation().getMaxOutputs().col("capacity");
        for (var sample : capacity) {
            for (int i = 0; i < data.getDmuCount(); i++) {
                Assertions.assertTrue(sample.get(i) >= expectedMinValues.get(i));
                Assertions.assertTrue(sample.get(i) <= expectedMaxValues.get(i));
            }
        }
    }

    private void verifyPreciseFactors(PerformanceSamplesCollection samples) {
        var actualVelocity = samples.getOutputPerformances()
                .stream().map(x -> x.col("velocity"))
                .toList();
        for (var sample : actualVelocity) {
            Assertions.assertIterableEquals(data.getOutputData().col("velocity"), sample);
        }

        var actualCost = samples.getInputPerformances()
                .stream().map(x -> x.col("cost"))
                .toList();
        for (var sample : actualCost) {
            Assertions.assertIterableEquals(data.getInputData().col("cost"), sample);
        }
    }

    @Test
    public void verifyEfficiencyCalculation() {
        var weightSamples = new WeightSamplesCollection(new double[][]{
                new double[]{0.71, 0.29, 0.909, 0.091},
                new double[]{0.745, 0.255, 0.001, 0.999},
                new double[]{0.271, 0.729, 0.14, 0.86},
                new double[]{0.005, 0.995, 0, 1},
                new double[]{0.035, 0.965, 0.192, 0.808},
                new double[]{0.31, 0.69, 0.741, 0.259},
                new double[]{0.931, 0.069, 0.116, 0.884},
                new double[]{0.026, 0.974, 0.002, 0.998},
                new double[]{0.976, 0.024, 0.214, 0.786},
                new double[]{0.064, 0.936, 0.798, 0.202}
        }, 2);

        var i1Performance = new double[]{100, 150, 150, 200, 200};
        var i2Samples = new double[][]{
                new double[]{0.666, 0.862, 1, 0.712, 1},
                new double[]{0.645, 0.856, 1, 0.796, 1},
                new double[]{0.663, 0.861, 1, 0.78, 1},
                new double[]{0.602, 0.882, 1, 0.768, 1},
                new double[]{0.642, 0.873, 1, 0.716, 1},
                new double[]{0.657, 0.844, 1, 0.768, 1},
                new double[]{0.689, 0.813, 1, 0.741, 1},
                new double[]{0.648, 0.8, 1, 0.726, 1},
                new double[]{0.671, 0.811, 1, 0.786, 1},
                new double[]{0.661, 0.884, 1, 0.779, 1}
        };

        var o1Performance = new double[]{2000, 1000, 1200, 900, 600};

        var o2Samples = new double[][]{
                new double[]{0.519, 0.3, 0.903, 0.18, 0.445},
                new double[]{0.732, 0.2, 0.936, 0.026, 0.52},
                new double[]{0.418, 0.187, 0.963, 0.035, 0.313},
                new double[]{0.582, 0.187, 0.844, 0.148, 0.472},
                new double[]{0.794, 0.151, 0.923, 0.02, 0.182},
                new double[]{0.741, 0.376, 0.894, 0.265, 0.658},
                new double[]{0.664, 0.32, 0.754, 0.193, 0.597},
                new double[]{0.892, 0.492, 0.951, 0.063, 0.793},
                new double[]{0.616, 0.133, 0.828, 0.076, 0.201},
                new double[]{0.729, 0.537, 0.929, 0.329, 0.606}
        };

        var performanceSamples = new PerformanceSamplesCollection();

        for (int i = 0; i < 10; i++) {
            var inputSample = new DataFrame<Double>("i1", "i2");
            var outputSample = new DataFrame<Double>("i1", "i2");
            for (int dmu = 0; dmu < 5; dmu++) {
                inputSample.append(List.of(i1Performance[dmu], i2Samples[i][dmu]));
            }
            for (int dmu = 0; dmu < 5; dmu++) {
                outputSample.append(List.of(o1Performance[dmu], o2Samples[i][dmu]));
            }
            performanceSamples.getInputPerformances().add(inputSample);
            performanceSamples.getOutputPerformances().add(outputSample);
        }

        var expectedEfficiencyMatrix = new double[][]{
                new double[]{25.536832, 0.036581, 10.164092, 0.529577, 93.370251, 47.123529, 2.496974, 1.513459, 4.389482, 227.413647},
                new double[]{8.51548, 0.010716, 3.39556, 0.114894, 31.534467, 15.740447, 0.832339, 0.532359, 1.462268, 76.539371},
                new double[]{10.215209, 0.019062, 4.080045, 0.483668, 37.191598, 18.847882, 1.001056, 0.687135, 1.758256, 90.906194},
                new double[]{5.753018, 0.006206, 2.301137, 0.083893, 22.470096, 10.666392, 0.56145, 0.315361, 0.986891, 53.090311},
                new double[]{3.833302, 0.0075, 1.534147, 0.236591, 14.48174, 7.094759, 0.376486, 0.322548, 0.658515, 34.866221}
        };
        var efficiency = new ImpreciseCCRSmaaEfficiency(
                10,
                5,
                SmaaTestUtils.getRandom());
        var smaaUtils = new ImpreciseSmaaUtils(efficiency);

        var efficiencyMatrix = smaaUtils.calculateEfficiencyMatrixForSamples(weightSamples, performanceSamples, 5);
        IntStream.range(0, efficiencyMatrix.length())
                .forEach(rowIdx -> Assertions.assertArrayEquals(expectedEfficiencyMatrix[rowIdx],
                        efficiencyMatrix.row(rowIdx).stream().mapToDouble(x -> x).toArray(), 1e-6));
    }

}
