package put.dea.vdea.imprecise.smaa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.vdea.imprecise.ImpreciseVDEATestBase;

import java.util.stream.IntStream;

public class ImpreciseVDEASmaaDistanceTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyDistanceDistribution() {
        var expectedAverageValues = new double[]{0.261319, 0.058403, 0.23294, 0.451362, 0.03408, 0.242226,
                0.292262, 0.250232, 0.076505, 0.110765, 0.028236, 0.025872
        };

        var expectedDistribution = new double[][]{
                new double[]{0, 1, 0, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0},
                new double[]{0.2, 0.8, 0, 0, 0.0},
                new double[]{0, 0.2, 0.8, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0},
                new double[]{0.4, 0.6, 0, 0, 0.0},
                new double[]{0, 1, 0, 0, 0.0},
                new double[]{0.2, 0.8, 0, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0},
                new double[]{1, 0, 0, 0, 0.0}
        };
        addWeightConstraints();
        ImpreciseVDEASmaaDistance smaaDistance = new ImpreciseVDEASmaaDistance(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
        var distribution = smaaDistance.distanceDistribution(data);
        Assertions.assertArrayEquals(expectedAverageValues,
                distribution.expectedValues().stream().mapToDouble(x -> x).toArray(),
                1e-6);
        IntStream.range(0, data.getDmuCount())
                .forEach(idx -> Assertions.assertArrayEquals(expectedDistribution[idx],
                        distribution.distribution().row(idx).stream().mapToDouble(x -> x).toArray(), 1e-6));
    }
}
