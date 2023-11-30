package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ImpreciseSmaaEfficiencyTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyEfficiencyDistribution() {
        addWeightConstraints();
        var smaaEfficiency = new ImpreciseCCRSmaaEfficiency(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());

        var expectedDistribution = new double[][]{new double[]{1, 0, 0, 0, 0},
                new double[]{0, 1, 0, 0, 0},
                new double[]{0.6, 0.4, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0.2, 0.8, 0, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{0.2, 0.2, 0.2, 0.2, 0.2},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{0, 0, 0, 0, 1},
        };
        var expectedAverage = new double[]{0.160715, 0.282027, 0.169246, 0.016892, 0.090797, 0.062592,
                0.102097, 0.103189, 0.054576, 0.117516, 0.180449, 0.038864, 0.075805,
                0.146147, 0.244807, 0.202811, 0.078887, 0.052558, 0.178371, 0.508391,
                0.176187, 0.035434, 0.050387, 0.067186, 0.09607, 0.235624, 1};
        var distribution = smaaEfficiency.efficiencyDistribution(data);

        IntStream.range(0, expectedDistribution.length)
                .forEach(rowIdx -> Assertions.assertArrayEquals(expectedDistribution[rowIdx],
                        TestUtils.tranformTableToArray(distribution.distribution())[rowIdx], 1e-6));
        Assertions.assertArrayEquals(expectedAverage,
                distribution.expectedValues().stream().mapToDouble(x -> x).toArray(), 1e-6);

    }

}
