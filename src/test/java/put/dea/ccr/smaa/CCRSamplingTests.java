package put.dea.ccr.smaa;

import joinery.DataFrame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import put.dea.ccr.CCRTestBase;
import put.dea.common.smaa.WeightSamplesCollection;

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
        Assertions.assertEquals(data.getInputCount(), samples.getInputSamples().size());
        Assertions.assertEquals(data.getOutputCount(), samples.getOutputSamples().size());
        Assertions.assertEquals(sampling.getNumberOfSamples(), samples.getInputSamples().length());
        Assertions.assertEquals(sampling.getNumberOfSamples(), samples.getOutputSamples().length());
    }

    private void verifySamplesSumToOne(DataFrame<Double> samples) {
        for (var sample : samples) {
            Assertions.assertEquals(1.0, sample.stream().mapToDouble(x -> x).sum(), 1e-6);
        }
    }

    private void verifyNonNegativeSamples(DataFrame<Double> inputSamples) {
        Assertions.assertTrue(
                Arrays.stream(inputSamples.toArray(new Double[0][]))
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
            Assertions.assertTrue(sample.get(0) >= 3 * sample.get(2));
            Assertions.assertTrue(sample.get(0) >= 5 * sample.get(3));
            Assertions.assertTrue(sample.get(1) >= 2 * sample.get(2));
            Assertions.assertTrue(sample.get(1) >= 5 * sample.get(3));
        }
        for (var sample : samples.getOutputSamples()) {
            Assertions.assertTrue(sample.get(0) >= 5 * sample.get(1));
        }
    }

}
