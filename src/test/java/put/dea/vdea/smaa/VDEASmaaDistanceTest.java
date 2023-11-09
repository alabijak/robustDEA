package put.dea.vdea.smaa;

import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.vdea.VDEATestBase;

public class VDEASmaaDistanceTest extends VDEATestBase {
    @Test
    public void verifyEfficiencyDistribution() {
        var expected = new double[]{
                0.529778, 0.486855, 0.645596, 0.351151, 0.012405,
                0.072658, 0.327671, 0.322336, 0.272074, 0.473803,
                0.145879, 0.398207, 0.215217, 0.413561, 0.18249,
                0.294928, 0.328919, 0.253108, 0.215063, 0.034412};
        var distribution = new double[][]{
                new double[]{0, 0.2, 0.2, 0.6, 0},
                new double[]{0, 0.2, 0.8, 0, 0},
                new double[]{0, 0, 0.4, 0.6, 0},
                new double[]{0, 0.8, 0.2, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0},
                new double[]{0.2, 0.6, 0.2, 0, 0},
                new double[]{0, 1, 0, 0, 0},
                new double[]{0.2, 0.8, 0, 0, 0},
                new double[]{0, 0.2, 0.8, 0, 0},
                new double[]{0.8, 0.2, 0, 0, 0},
                new double[]{0.2, 0.2, 0.6, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{0, 0.6, 0.4, 0, 0},
                new double[]{0.6, 0.4, 0, 0, 0},
                new double[]{0.4, 0.2, 0.4, 0, 0},
                new double[]{0.2, 0.4, 0.4, 0, 0},
                new double[]{0.2, 0.8, 0, 0, 0},
                new double[]{0.4, 0.6, 0, 0, 0},
                new double[]{1, 0, 0, 0, 0}
        };
        var smaaDistance = new VDEASmaaDistance(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());

        var actual = smaaDistance.distanceDistribution(data);

        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expected, actual);
    }
}
