package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import tech.tablesaw.api.Table;

import java.util.Random;
import java.util.stream.IntStream;

public class SmaaTestUtils {
    public static final int NUMBER_OF_SAMPLES = 5;
    public static final int NUMBER_OF_INTERVALS = 5;

    public static final long RANDOM_SEED = 1234L;

    static void verifySamplesShape(ProblemData data, WeightSamplesCollection samples, int expectedSamplesNo) {
        Assertions.assertEquals(data.getInputCount(), samples.getInputSamples().columnCount());
        Assertions.assertEquals(data.getOutputCount(), samples.getOutputSamples().columnCount());
        Assertions.assertEquals(expectedSamplesNo, samples.getInputSamples().rowCount());
        Assertions.assertEquals(expectedSamplesNo, samples.getOutputSamples().rowCount());
    }

    static void verifyNonNegativeSamples(Table inputSamples) {
        Assertions.assertTrue(
                inputSamples.stream().noneMatch(row ->
                        IntStream.range(0, row.columnCount()).anyMatch(x -> row.getDouble(x) < 0)
                )
        );
    }

    static void verifyExpectedValuesAndDistribution(double[][] distribution,
                                                    double[] expectedValues,
                                                    DistributionResult result) {
        var actualDistribution = TestUtils.tranformTableToArray(result.distribution());
        Assertions.assertArrayEquals(distribution, actualDistribution);
        Assertions.assertArrayEquals(
                expectedValues,
                result.expectedValues().stream().mapToDouble(x -> x).toArray(),
                1e-6);
    }

    static Random getRandom() {
        return new Random(RANDOM_SEED);
    }
}
