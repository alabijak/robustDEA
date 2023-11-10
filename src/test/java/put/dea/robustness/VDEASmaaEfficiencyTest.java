package put.dea.robustness;

import org.junit.jupiter.api.Test;

public class VDEASmaaEfficiencyTest extends VDEATestBase {
    @Test
    public void verifyEfficiencyDistribution() {
        var expectedEfficiencies = new double[]{
                0.327225, 0.370148, 0.211406, 0.505851, 0.844598,
                0.784345, 0.529331, 0.534666, 0.584928, 0.383199,
                0.711123, 0.458796, 0.641785, 0.443442, 0.674512,
                0.562074, 0.528084, 0.603894, 0.64194, 0.82259};
        var distribution = new double[][]{
                new double[]{0.2, 0.6, 0.2, 0, 0},
                new double[]{0, 0.8, 0.2, 0, 0},
                new double[]{0.6, 0.4, 0, 0, 0},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 0, 0.2, 0.8},
                new double[]{0, 0, 0, 0.6, 0.4},
                new double[]{0, 0, 1, 0, 0},
                new double[]{0, 0, 0.8, 0.2, 0},
                new double[]{0, 0, 0.4, 0.6, 0},
                new double[]{0, 0.6, 0.4, 0, 0},
                new double[]{0, 0, 0, 1, 0},
                new double[]{0, 0.4, 0.6, 0, 0},
                new double[]{0, 0, 0.2, 0.8, 0},
                new double[]{0, 0.2, 0.8, 0, 0},
                new double[]{0, 0, 0, 1, 0},
                new double[]{0, 0.4, 0.2, 0.4, 0},
                new double[]{0, 0, 0.8, 0.2, 0},
                new double[]{0, 0, 0.4, 0.6, 0},
                new double[]{0, 0, 0.2, 0.8, 0},
                new double[]{0, 0, 0, 0.4, 0.6}
        };
        var smaaEfficiency = new VDEASmaaEfficiency(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());

        var actual = smaaEfficiency.efficiencyDistribution(data);

        SmaaTestUtils.verifyExpectedValuesAndDistribution(distribution, expectedEfficiencies, actual);
    }
}
