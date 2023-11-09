package put.dea;

import joinery.DataFrame;
import org.junit.jupiter.api.Assertions;
import put.dea.common.ProblemData;
import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.WeightSamplesCollection;

import java.util.Arrays;
import java.util.Random;

public class SmaaTestUtils {
    public static final int NUMBER_OF_SAMPLES = 5;
    public static final int NUMBER_OF_INTERVALS = 5;

    public static final long RANDOM_SEED = 1234L;

    public static void verifySamplesShape(ProblemData data, WeightSamplesCollection samples, int expectedSamplesNo) {
        Assertions.assertEquals(data.getInputCount(), samples.getInputSamples().size());
        Assertions.assertEquals(data.getOutputCount(), samples.getOutputSamples().size());
        Assertions.assertEquals(expectedSamplesNo, samples.getInputSamples().length());
        Assertions.assertEquals(expectedSamplesNo, samples.getOutputSamples().length());
    }

    public static void verifySamplesSumToOne(DataFrame<Double> samples) {
        for (var sample : samples) {
            Assertions.assertEquals(1.0, sample.stream().mapToDouble(x -> x).sum(), 1e-6);
        }
    }

    public static void verifyNonNegativeSamples(DataFrame<Double> inputSamples) {
        Assertions.assertTrue(
                Arrays.stream(inputSamples.toArray(new Double[0][]))
                        .allMatch(row -> Arrays.stream(row).allMatch(value -> value >= 0))
        );
    }

    public static void verifyExpectedValuesAndDistribution(double[][] distribution,
                                                           double[] expectedValues,
                                                           DistributionResult result) {
        var actualDistribution = result.distribution().toModelMatrix(0);
        Assertions.assertArrayEquals(distribution, actualDistribution);
        Assertions.assertArrayEquals(
                expectedValues,
                result.expectedValues().stream().mapToDouble(x -> x).toArray(),
                1e-6);
    }

    public static Random getRandom() {
        return new Random(RANDOM_SEED);
    }
}
