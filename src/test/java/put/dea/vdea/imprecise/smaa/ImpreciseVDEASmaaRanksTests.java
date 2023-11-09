package put.dea.vdea.imprecise.smaa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.vdea.imprecise.ImpreciseVDEATestBase;

import java.util.stream.IntStream;

public class ImpreciseVDEASmaaRanksTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyRankDistribution() {
        var expectedAverageValues = new double[]{9.4, 3.6, 7.6, 12, 2, 8.4, 11,
                8.6, 4.4, 6, 3, 2};

        var expectedDistribution = new double[][]{
                new double[]{0, 0, 0, 0, 0, 0, 0, 0.2, 0.2, 0.6, 0, 0.0},
                new double[]{0, 0.2, 0.4, 0, 0.4, 0, 0, 0, 0, 0, 0, 0.0},
                new double[]{0, 0, 0, 0, 0, 0, 0.6, 0.2, 0.2, 0, 0, 0.0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0},
                new double[]{0.6, 0, 0.2, 0.2, 0, 0, 0, 0, 0, 0, 0, 0.0},
                new double[]{0, 0, 0, 0, 0, 0, 0.4, 0, 0.4, 0.2, 0, 0.0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0.0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0.6, 0.2, 0.2, 0, 0.0},
                new double[]{0, 0, 0.2, 0.2, 0.6, 0, 0, 0, 0, 0, 0, 0.0},
                new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.0},
                new double[]{0.2, 0.2, 0, 0.6, 0, 0, 0, 0, 0, 0, 0, 0.0},
                new double[]{0.2, 0.6, 0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0.0},
        };
        addWeightConstraints();
        var smaaRanks = new ImpreciseVDEASmaaRanks(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.getRandom());
        var distribution = smaaRanks.rankDistribution(data);
        Assertions.assertArrayEquals(expectedAverageValues,
                distribution.expectedValues().stream().mapToDouble(x -> x).toArray(),
                1e-6);
        IntStream.range(0, data.getDmuCount())
                .forEach(idx -> Assertions.assertArrayEquals(expectedDistribution[idx],
                        distribution.distribution().row(idx).stream().mapToDouble(x -> x).toArray(), 1e-6));
    }
}
