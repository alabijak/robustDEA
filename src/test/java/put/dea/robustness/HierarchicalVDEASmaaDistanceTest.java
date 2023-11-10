package put.dea.robustness;

import org.junit.jupiter.api.Test;

public class HierarchicalVDEASmaaDistanceTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyEfficiencyDistributionAtIndexLevel() {
        var expectedDistance = new double[]{
                0.181519, 0.008687, 0.251754, 0.363507, 0.03581, 0.003625,
                0.225454, 0.239148, 0.241307, 0.408151, 0.379756, 0.268056,
                0.307735, 0.199322, 0.272055, 0.292518,
        };
        var distribution = new double[][]{
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.2, 0.8, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.2, 0.8, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.8, 0.2, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.6, 0.4, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0}
        };

        verifyDistanceDistribution("index", distribution, expectedDistance);
    }

    private void verifyDistanceDistribution(String hierarchyLevel, double[][] distribution, double[] expectedEfficiencies) {
        var smaaDistance = new HierarchicalVDEASmaaDistance(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
        var actual = smaaDistance.distanceDistribution(data, hierarchyLevel);

        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expectedEfficiencies, actual);
    }

    @Test
    public void verifyEfficiencyDistributionAtHealthLevel() {
        var expectedDistance = new double[]{
                0.090818, 0.163088, 0.499362, 0.607081, 0.197567,
                0.0, 0.538122, 0.363833, 0.529673, 0.532879, 0.756554,
                0.523114, 0.273519, 0.503616, 0.62807, 0.614836
        };
        var distribution = new double[][]{
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.2, 0.8, 0.0},
                new double[]{0.4, 0.6, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.2, 0.8, 0.0},
                new double[]{0.0, 0.0, 0.6, 0.4, 0.0}

        };

        verifyDistanceDistribution("health_improvement", distribution, expectedDistance);
    }

    @Test
    public void verifyEfficiencyDistributionAtFinancesLevel() {
        var expectedDistance = new double[]{
                0.361688, 0.005106, 0.289991, 0.323101, 0.014889, 0.173605,
                0.280724, 0.386603, 0.453502, 0.517564, 0.405234, 0.312487,
                0.728575, 0.339593, 0.225902, 0.499819
        };
        var distribution = new double[][]{
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.8, 0.2, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.4, 0.6, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.2, 0.8, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
        };

        verifyDistanceDistribution("finances", distribution, expectedDistance);
    }

    @Test
    public void verifyEfficiencyDistributionAtSatisfactionLevel() {
        var expectedDistance = new double[]{
                0.551643, 0.19769, 0.266826, 0.474456, 0.206139, 0.246432,
                0.141495, 0.342342, 0.013543, 0.543883, 0.237141, 0.264944,
                0.316377, 0.079185, 0.245158, 0.003132
        };
        var distribution = new double[][]{
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.6, 0.4, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.4, 0.6, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.2, 0.8, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.2, 0.8, 0.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0}
        };

        verifyDistanceDistribution("satisfaction", distribution, expectedDistance);
    }
}
