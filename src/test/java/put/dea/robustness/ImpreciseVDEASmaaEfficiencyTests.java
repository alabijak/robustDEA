package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ImpreciseVDEASmaaEfficiencyTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyEfficiencyDistribution() {
        var expectedAverageValues = new double[]{0.38679, 0.589705, 0.415168, 0.196747, 0.614029, 0.405883,
                0.355846, 0.397876, 0.571603, 0.537343, 0.619873, 0.622237


        };

        var expectedDistribution = new double[][]{
                new double[]{0, 0.6, 0.4, 0, 0.0},
                new double[]{0, 0, 0.6, 0.4, 0.0},
                new double[]{0, 0.4, 0.6, 0, 0.0},
                new double[]{0.6, 0.4, 0, 0, 0.0},
                new double[]{0, 0, 0.4, 0.6, 0.0},
                new double[]{0, 0.6, 0.4, 0, 0.0},
                new double[]{0, 0.8, 0.2, 0, 0.0},
                new double[]{0, 0.8, 0.2, 0, 0.0},
                new double[]{0, 0, 1, 0, 0.0},
                new double[]{0, 0, 1, 0, 0.0},
                new double[]{0, 0, 0.4, 0.6, 0.0},
                new double[]{0, 0, 0.2, 0.8, 0.0}
        };
        addWeightConstraints();
        ImpreciseVDEASmaaEfficiency smaaDistance = new ImpreciseVDEASmaaEfficiency(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
        var distribution = smaaDistance.efficiencyDistribution(data);
        Assertions.assertArrayEquals(expectedAverageValues,
                distribution.expectedValues().stream().mapToDouble(x -> x).toArray(),
                1e-6);
        IntStream.range(0, data.getDmuCount())
                .forEach(idx -> Assertions.assertArrayEquals(expectedDistribution[idx],
                        TestUtils.tranformTableToArray(distribution.distribution())[idx],
                        1e-6));
    }
}
