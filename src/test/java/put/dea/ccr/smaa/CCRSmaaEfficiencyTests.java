package put.dea.ccr.smaa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.ccr.CCRTestBase;

public class CCRSmaaEfficiencyTests extends CCRTestBase {
    private static CCRSmaaEfficiency efficiency;

    @BeforeAll
    public static void initializeEfficiency() {
        efficiency = new CCRSmaaEfficiency(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
    }

    @Test
    public void efficiencyDistributionTest() {
        var distribution = new double[][]{
                new double[]{0, 0, 0, 0.6, 0.4},
                new double[]{0, 0, 0.2, 0.6, 0.2},
                new double[]{0, 0.8, 0.2, 0, 0},
                new double[]{0, 0, 0.2, 0.6, 0.2},
                new double[]{0, 0, 0.8, 0.2, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0, 0.4, 0.6, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0, 0.2, 0, 0, 0.8},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0}
        };

        var expectedEfficiency = new double[]{0.788331, 0.667123, 0.277342, 0.680329, 0.486462,
                0.120719, 0.439661, 0.121335, 0.857055, 0.214129, 0.007379};

        var result = efficiency.efficiencyDistribution(data);
        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expectedEfficiency, result);

    }

}
