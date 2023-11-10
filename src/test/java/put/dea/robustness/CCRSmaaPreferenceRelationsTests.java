package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CCRSmaaPreferenceRelationsTests extends CCRTestBase {
    private static CCRSmaaPreferenceRelations preferenceRelations;

    @BeforeAll
    public static void initializeEfficiency() {

        preferenceRelations = new CCRSmaaPreferenceRelations(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.getRandom());
    }

    @Test
    public void efficiencyDistributionTest() {
        var peoi = new double[][]{new double[]{1, 0.8, 1, 0.8, 1, 1, 1, 1, 0.2, 1, 1},
                new double[]{0.2, 1, 1, 0.4, 1, 1, 1, 1, 0.2, 1, 1},
                new double[]{0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1},
                new double[]{0.2, 0.6, 1, 1, 0.8, 1, 0.8, 1, 0.2, 1, 1},
                new double[]{0, 0, 1, 0.2, 1, 1, 0.8, 1, 0.2, 1, 1},
                new double[]{0, 0, 0, 0, 0, 1, 0, 0.8, 0, 0, 1},
                new double[]{0, 0, 1, 0.2, 0.2, 1, 1, 1, 0.2, 1, 1},
                new double[]{0, 0, 0, 0, 0, 0.2, 0, 1, 0, 0, 1},
                new double[]{0.8, 0.8, 1, 0.8, 0.8, 1, 0.8, 1, 1, 1, 1},
                new double[]{0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        var result = preferenceRelations.peoi(data).toModelMatrix(0);
        Assertions.assertArrayEquals(peoi, result);
    }
}
