package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

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
        verifySamplesSumToOne(samples);
        SmaaTestUtils.verifyNonNegativeSamples(samples.getInputSamples());
        SmaaTestUtils.verifyNonNegativeSamples(samples.getOutputSamples());
    }

    private void verifySamplesSumToOne(WeightSamplesCollection samples) {
        var inputSamples = samples.getInputSamples().transpose();
        var outputSamples = samples.getOutputSamples().transpose();
        for (int i = 0; i < inputSamples.columnCount(); i++) {
            Assertions.assertEquals(1.0, inputSamples.doubleColumn(i).sum() + outputSamples.doubleColumn(i).sum(), 1e-6);
        }
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
            Assertions.assertTrue(2 * sample.getDouble(0) >= sample.getDouble(1));
            Assertions.assertTrue(sample.getDouble(0) <= .5);
            Assertions.assertTrue(IntStream.range(0, sample.columnCount()).mapToDouble(sample::getDouble)
                    .reduce(0.0, Double::sum) <= 0.8);
        }

    }
}
