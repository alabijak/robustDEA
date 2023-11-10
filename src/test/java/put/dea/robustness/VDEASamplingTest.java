package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VDEASamplingTest extends VDEATestBase {
    private static VDEASmaaBase sampling;

    @BeforeAll
    public static void initializeSampling() {
        sampling = new VDEASmaaEfficiency(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
    }

    @Test
    public void checkSamplesWithoutWeightConstraints() {
        var samples = sampling.generateWeightSamples(data);
        verifySizeAndBasicConstraints(samples);
    }


    private void verifySizeAndBasicConstraints(WeightSamplesCollection samples) {
        SmaaTestUtils.verifySamplesShape(data, samples, sampling.getNumberOfSamples());
        SmaaTestUtils.verifySamplesSumToOne(samples.getInputSamples().join(samples.getOutputSamples()));
        SmaaTestUtils.verifyNonNegativeSamples(samples.getInputSamples());
        SmaaTestUtils.verifyNonNegativeSamples(samples.getOutputSamples());
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
            Assertions.assertTrue(2 * sample.get(0) >= sample.get(1));
            Assertions.assertTrue(sample.get(0) <= .5);
            Assertions.assertTrue(sample.stream().reduce(0.0, Double::sum) <= 0.8);
        }

    }
}
