package put.dea.vdea.hierarchical.smaa;

import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.vdea.hierarchical.HierarchicalVDEATestBase;

public class HierarchicalVDEASmaaEfficiencyTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyEfficiencyDistributionAtIndexLevel() {
        var expectedEfficiencies = new double[]{
                0.554393, 0.727225, 0.484158, 0.372406, 0.700102, 0.732287,
                0.510458, 0.496764, 0.494605, 0.327761, 0.356156, 0.467856,
                0.428177, 0.53659, 0.463857, 0.443395};
        var distribution = new double[][]{
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 0, 1, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 1, 0, 0, 0},
                new double[]{0, 0, 0, 1, 0},
                new double[]{0, 0, 0, 1, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 1, 0, 0, 0},
                new double[]{0, 1, 0, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0.2, 0.8, 0, 0}
        };
        verifyEfficiencyDistribution("index", distribution, expectedEfficiencies);
    }

    private void verifyEfficiencyDistribution(String hierarchyLevel, double[][] distribution, double[] expectedEfficiencies) {
        var smaa = new HierarchicalVDEASmaaEfficiency(
                SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
        var actual = smaa.efficiencyDistribution(data, hierarchyLevel);

        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expectedEfficiencies, actual);
    }

    @Test
    public void verifyEfficiencyDistributionAtHealthLevel() {
        var expectedEfficiencies = new double[]{
                0.864658, 0.792388, 0.456114, 0.348396, 0.757909, 0.955476,
                0.417354, 0.591643, 0.425803, 0.422597, 0.198922, 0.432362,
                0.681957, 0.45186, 0.327407, 0.34064
        };
        var distribution = new double[][]{
                new double[]{0.0, 0.0, 0.0, 0.0, 1.0},
                new double[]{0.0, 0.0, 0.0, 0.8, 0.2},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.8, 0.2, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 0.0, 1.0},
                new double[]{0.0, 0.2, 0.8, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.6, 0.4, 0.0},
                new double[]{0.0, 0.4, 0.6, 0.0, 0.0},
                new double[]{0.0, 0.2, 0.8, 0.0, 0.0},
                new double[]{0.4, 0.6, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.2, 0.8, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0}
        };
        verifyEfficiencyDistribution("health_improvement", distribution, expectedEfficiencies);
    }

    @Test
    public void verifyEfficiencyDistributionAtFinancesLevel() {
        var expectedEfficiencies = new double[]{
                0.457938, 0.81452, 0.529636, 0.496525, 0.804738, 0.646021,
                0.538902, 0.433023, 0.366125, 0.302062, 0.414393, 0.507139,
                0.091051, 0.480033, 0.593725, 0.319808
        };
        var distribution = new double[][]{
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 0.4, 0.6},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 0.6, 0.4},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.2, 0.8, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.2, 0.8, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{1.0, 0.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0}
        };
        verifyEfficiencyDistribution("finances", distribution, expectedEfficiencies);
    }

    @Test
    public void verifyEfficiencyDistributionAtSatisfactionLevel() {
        var expectedEfficiencies = new double[]{
                0.191787, 0.54574, 0.476604, 0.268975, 0.537291, 0.496998,
                0.601935, 0.401088, 0.729888, 0.199547, 0.506289, 0.478486,
                0.427053, 0.664245, 0.498272, 0.740298
        };
        var distribution = new double[][]{
                new double[]{0.8, 0.2, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.8, 0.2, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 1.0, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.4, 0.6, 0.0},
                new double[]{0.0, 0.4, 0.6, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0},
                new double[]{0.6, 0.4, 0.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.2, 0.8, 0.0},
                new double[]{0.0, 0.0, 1.0, 0.0, 0.0},
                new double[]{0.0, 0.0, 0.0, 1.0, 0.0}
        };
        verifyEfficiencyDistribution("satisfaction", distribution, expectedEfficiencies);
    }
}
