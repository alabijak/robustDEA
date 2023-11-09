package put.dea.vdea.smaa;

import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.vdea.VDEATestBase;

public class VDEASmaaRankTest extends VDEATestBase {
    @Test
    public void verifyRankDistribution() {
        var expectedEfficiencies = new double[]{
                17.6, 17.6, 19.6, 13.4, 2.4, 3.2, 11.6, 12, 9.6, 15.6,
                4.4, 14.8, 7.4, 15.2, 5.8, 9.6, 11.8, 8.8, 7.8, 1.8};
        var distribution = new double[][]{
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.2, 0, 0, 0.4, 0.2},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0.2, 0.4, 0.2, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.8},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.2, 0.4, 0.2, 0, 0, 0, 0, 0},
                new double[]{0.4, 0.2, 0.2, 0, 0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0.2, 0.4, 0.4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0.2, 0, 0.4, 0, 0, 0.2, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0.4, 0, 0, 0, 0, 0.2, 0.2, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0.4, 0.2, 0, 0.2, 0.2, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.4, 0.2, 0, 0, 0.2, 0},
                new double[]{0.2, 0, 0, 0, 0.6, 0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0.2, 0, 0, 0, 0, 0.2, 0, 0.2, 0.2, 0},
                new double[]{0, 0, 0, 0.4, 0, 0, 0.2, 0, 0.2, 0, 0, 0, 0.2, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.2, 0, 0.2, 0, 0, 0, 0.4, 0.2, 0, 0},
                new double[]{0, 0, 0, 0, 0.2, 0.8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0.2, 0.2, 0, 0, 0, 0, 0, 0.2, 0, 0, 0, 0, 0, 0, 0, 0.4, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0.2, 0, 0, 0, 0, 0.4, 0, 0.4, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0, 0, 0, 0.2, 0.4, 0, 0.2, 0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0, 0, 0, 0.2, 0, 0, 0.4, 0.2, 0, 0, 0, 0, 0.2, 0, 0, 0, 0, 0, 0, 0},
                new double[]{0.4, 0.4, 0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        var smaaRanks = new VDEASmaaRanks(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.getRandom());

        var actual = smaaRanks.rankDistribution(data);

        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expectedEfficiencies, actual);
    }
}
