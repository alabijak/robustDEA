package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VDEASmaaPreferenceRelationsTests extends VDEATestBase {

    @Test
    public void efficiencyDistributionTest() {
        var peoi = new double[][]{
                new double[]{1, 0.4, 0.8, 0.2, 0, 0, 0, 0.2, 0, 0.2, 0, 0.2, 0, 0.2, 0, 0.2, 0, 0, 0, 0},
                new double[]{0.6, 1, 0.8, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.4, 0, 0.2, 0, 0.2, 0, 0, 0, 0},
                new double[]{0.2, 0.2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0.8, 1, 1, 1, 0, 0, 0.4, 0.4, 0, 0.8, 0, 0.6, 0, 0.8, 0, 0.4, 0.4, 0, 0, 0},
                new double[]{1, 1, 1, 1, 1, 0.6, 1, 1, 1, 1, 0.8, 1, 1, 1, 1, 0.8, 1, 1, 1, 0.4},
                new double[]{1, 1, 1, 1, 0.4, 1, 1, 1, 1, 1, 0.8, 1, 1, 1, 1, 0.6, 1, 1, 1, 0},
                new double[]{1, 1, 1, 0.6, 0, 0, 1, 0.4, 0.2, 0.8, 0, 1, 0.2, 0.8, 0, 0.4, 0.6, 0.2, 0.2, 0},
                new double[]{0.8, 1, 1, 0.6, 0, 0, 0.6, 1, 0.2, 1, 0, 0.6, 0, 1, 0, 0.4, 0.6, 0.2, 0, 0},
                new double[]{1, 1, 1, 1, 0, 0, 0.8, 0.8, 1, 1, 0, 0.8, 0.4, 1, 0, 0.4, 0.8, 0.2, 0.2, 0},
                new double[]{0.8, 0.8, 1, 0.2, 0, 0, 0.2, 0, 0, 1, 0, 0.6, 0, 0.4, 0, 0.4, 0, 0, 0, 0},
                new double[]{1, 1, 1, 1, 0.2, 0.2, 1, 1, 1, 1, 1, 1, 0.6, 1, 0.8, 0.8, 1, 1, 0.8, 0.2},
                new double[]{0.8, 0.6, 1, 0.4, 0, 0, 0, 0.4, 0.2, 0.4, 0, 1, 0.2, 0.6, 0, 0, 0.2, 0.2, 0.2, 0},
                new double[]{1, 1, 1, 1, 0, 0, 0.8, 1, 0.6, 1, 0.4, 0.8, 1, 1, 0.4, 0.6, 0.8, 0.6, 0.6, 0},
                new double[]{0.8, 0.8, 1, 0.2, 0, 0, 0.2, 0, 0, 0.6, 0, 0.4, 0, 1, 0, 0.4, 0.4, 0, 0, 0},
                new double[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0.2, 1, 0.6, 1, 1, 0.6, 1, 1, 0.8, 0},
                new double[]{0.8, 0.8, 1, 0.6, 0.2, 0.4, 0.6, 0.6, 0.6, 0.6, 0.2, 1, 0.4, 0.6, 0.4, 1, 0.6, 0.4, 0.4, 0.2},
                new double[]{1, 1, 1, 0.6, 0, 0, 0.4, 0.4, 0.2, 1, 0, 0.8, 0.2, 0.6, 0, 0.4, 1, 0.2, 0.4, 0},
                new double[]{1, 1, 1, 1, 0, 0, 0.8, 0.8, 0.8, 1, 0, 0.8, 0.4, 1, 0, 0.6, 0.8, 1, 0.2, 0},
                new double[]{1, 1, 1, 1, 0, 0, 0.8, 1, 0.8, 1, 0.2, 0.8, 0.4, 1, 0.2, 0.6, 0.6, 0.8, 1, 0},
                new double[]{1, 1, 1, 1, 0.6, 1, 1, 1, 1, 1, 0.8, 1, 1, 1, 1, 0.8, 1, 1, 1, 1}
        };
        var preferenceRelations = new VDEASmaaPreferenceRelations(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.getRandom());
        var result = TestUtils.tranformTableToArray(preferenceRelations.peoi(data));
        Assertions.assertArrayEquals(peoi, result);
    }
}
