package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.Arrays;

public class CCRSamplingTests extends CCRTestBase {
    private static CCRSmaaBase sampling;

    @BeforeAll
    public static void initializeSampling() {
        sampling = new CCRSmaaEfficiency(10);
    }

    @Test
    public void checkSamplesWithoutWeightConstraints() {
        var samples = sampling.generateWeightSamples(data);
        verifySizeAndBasicConstraints(samples);
    }

    private void verifySizeAndBasicConstraints(WeightSamplesCollection samples) {
        verifySamplesShape(samples);
        verifySamplesSumToOne(samples.getInputSamples());
        verifySamplesSumToOne(samples.getOutputSamples());
        verifyNonNegativeSamples(samples.getInputSamples());
        verifyNonNegativeSamples(samples.getOutputSamples());
    }

    private void verifySamplesShape(WeightSamplesCollection samples) {
        Assertions.assertEquals(data.getInputCount(), samples.getInputSamples().columnCount());
        Assertions.assertEquals(data.getOutputCount(), samples.getOutputSamples().columnCount());
        Assertions.assertEquals(sampling.getNumberOfSamples(), samples.getInputSamples().rowCount());
        Assertions.assertEquals(sampling.getNumberOfSamples(), samples.getOutputSamples().rowCount());
    }

    private void verifySamplesSumToOne(Table samples) {
        samples = samples.transpose();
        for (var sample : samples.columns().stream().map(x -> (DoubleColumn) x).toList()) {
            Assertions.assertEquals(1.0, Arrays.stream(sample.asDoubleArray()).sum(), 1e-6);
        }
    }

    private void verifyNonNegativeSamples(Table inputSamples) {
        Assertions.assertTrue(
                inputSamples.columns().stream().map(c -> ((DoubleColumn) c).asDoubleArray())
                        .allMatch(row -> Arrays.stream(row).allMatch(value -> value >= 0))
        );
    }

    @Test
    public void checkSamplesWithWeightConstraints() {
        addWeightConstraints();
        var samples = sampling.generateWeightSamples(data);
        verifySizeAndBasicConstraints(samples);
        verifyWeightConstrains(samples);
    }

    private void verifyWeightConstrains(WeightSamplesCollection samples) {
        for (var sample : samples.getInputSamples()) {
            Assertions.assertTrue(sample.getDouble(0) >= 3 * sample.getDouble(2));
            Assertions.assertTrue(sample.getDouble(0) >= 5 * sample.getDouble(3));
            Assertions.assertTrue(sample.getDouble(1) >= 2 * sample.getDouble(2));
            Assertions.assertTrue(sample.getDouble(1) >= 5 * sample.getDouble(3));
        }
        for (var sample : samples.getOutputSamples()) {
            Assertions.assertTrue(sample.getDouble(0) >= 5 * sample.getDouble(1));
        }
    }

}
