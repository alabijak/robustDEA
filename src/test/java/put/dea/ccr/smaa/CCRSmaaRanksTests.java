package put.dea.ccr.smaa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.ccr.CCRTestBase;

public class CCRSmaaRanksTests extends CCRTestBase {
    private static CCRSmaaRanks ranks;

    @BeforeAll
    public static void initializeEfficiency() {
        ranks = new CCRSmaaRanks(SmaaTestUtils.NUMBER_OF_SAMPLES, SmaaTestUtils.getRandom());
    }

    @Test
    public void efficiencyDistributionTest() {
        var rankDistribution = new double[][]{
                new double[]{0.2, 0.6, 0, 0.2, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0.4, 0, 0.6, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                new double[]{0, 0, 0.8, 0, 0.2, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0.2, 0.8, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0.8, 0.2, 0},
                new double[]{0, 0, 0.2, 0, 0, 0.8, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0.8, 0},
                new double[]{0.8, 0, 0, 0, 0, 0.2, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        var expectedRanks = new double[]{2.2, 3.2, 7, 3.4, 4.8, 9.2, 5.4, 9.8, 2, 8, 11};

        var actual = ranks.rankDistribution(data);
        SmaaTestUtils.verifyExpectedValuesAndDistribution(rankDistribution, expectedRanks, actual);


    }
}
