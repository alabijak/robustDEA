package put.dea.vdea.imprecise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImpreciseVDEADistanceTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyMinDistance() {
        addWeightConstraints();
        var distances = new ImpreciseVDEAExtremeDistances(1.0001, 1e-5, 1);
        var result = distances.minDistanceForAll(data);
        var expectedResult = new double[]{0.015555, 0, 0.012475, 0.128628, 0, 0.014525, 0.035138,
                0.041083, 0, 0.011641, 0, 0

        };
        Assertions.assertArrayEquals(expectedResult, result.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }

    @Test
    public void verifyMaxDistance() {
        addWeightConstraints();
        var distances = new ImpreciseVDEAExtremeDistances(1.0001, 1e-8, 1);
        var result = distances.maxDistanceForAll(data);
        var expectedResult = new double[]{0.582077, 0.343349, 0.607564, 0.702186, 0.305709, 0.558406,
                0.589504, 0.599717, 0.421504, 0.45888, 0.422185, 0.402557
        };
        Assertions.assertArrayEquals(expectedResult, result.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }
}
