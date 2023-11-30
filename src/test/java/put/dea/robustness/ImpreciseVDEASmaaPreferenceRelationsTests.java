package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ImpreciseVDEASmaaPreferenceRelationsTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyRankDistribution() {
        var expectedDistribution = new double[][]{
                new double[]{1, 0, 0, 1, 0, 0.2, 1, 0.4, 0, 0, 0, 0.0},
                new double[]{1, 1, 1, 1, 0, 1, 1, 1, 0.6, 1, 0.6, 0.2},
                new double[]{1, 0, 1, 1, 0, 0.6, 1, 0.8, 0, 0, 0, 0.0},
                new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0.0},
                new double[]{1, 1, 1, 1, 1, 1, 1, 1, 0.8, 1, 0.6, 0.6},
                new double[]{0.8, 0, 0.4, 1, 0, 1, 1, 0.4, 0, 0, 0, 0.0},
                new double[]{0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0.0},
                new double[]{0.6, 0, 0.2, 1, 0, 0.6, 1, 1, 0, 0, 0, 0.0},
                new double[]{1, 0.4, 1, 1, 0.2, 1, 1, 1, 1, 1, 0, 0.0},
                new double[]{1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0.0},
                new double[]{1, 0.4, 1, 1, 0.4, 1, 1, 1, 1, 1, 1, 0.2},
                new double[]{1, 0.8, 1, 1, 0.4, 1, 1, 1, 1, 1, 0.8, 1.0}
        };
        addWeightConstraints();
        var smaaPreferences = new ImpreciseVDEASmaaPreferenceRelations(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.getRandom());
        var distribution = smaaPreferences.peoi(data);
        IntStream.range(0, data.getDmuCount())
                .forEach(idx -> Assertions.assertArrayEquals(expectedDistribution[idx],
                        TestUtils.tranformTableToArray(distribution)[idx],
                        1e-6));
    }
}
