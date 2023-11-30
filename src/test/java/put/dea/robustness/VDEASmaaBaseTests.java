package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class VDEASmaaBaseTests extends VDEATestBase {
    private static VDEASmaaBase smaaBase;

    @BeforeAll
    public static void init() {
        smaaBase = new VDEASmaaEfficiency(SmaaTestUtils.NUMBER_OF_SAMPLES,
                SmaaTestUtils.NUMBER_OF_INTERVALS,
                SmaaTestUtils.getRandom());
    }

    @Test
    public void verifyEfficiencyCalculation() {
        var samplesArray = new double[][]{
                new double[]{0.265, 0.055, 0.28, 0.4},
                new double[]{0.07, 0.267, 0.355, 0.308},
                new double[]{0.096, 0.008, 0.57, 0.326},
                new double[]{0.13, 0.168, 0.336, 0.366},
                new double[]{0.043, 0.015, 0.414, 0.528}
        };

        var efficiencies = new double[][]{
                new double[]{0.486216, 0.330774, 0.357233, 0.408295, 0.54199},
                new double[]{0.442485, 0.376027, 0.41404, 0.408553, 0.463482},
                new double[]{0.208307, 0.281647, 0.356177, 0.25397, 0.258074},
                new double[]{0.512489, 0.518719, 0.534354, 0.51717, 0.523685},
                new double[]{0.75962, 0.818928, 0.795842, 0.785455, 0.706892},
                new double[]{0.773633, 0.716461, 0.688731, 0.733914, 0.704836},
                new double[]{0.56176, 0.572975, 0.626666, 0.572679, 0.603313},
                new double[]{0.504499, 0.490826, 0.488703, 0.489537, 0.445634},
                new double[]{0.632539, 0.573165, 0.613088, 0.598684, 0.624046},
                new double[]{0.269333, 0.334818, 0.224438, 0.298655, 0.211043},
                new double[]{0.752885, 0.713524, 0.74603, 0.732043, 0.760239},
                new double[]{0.495413, 0.560592, 0.599798, 0.5449, 0.607309},
                new double[]{0.633618, 0.569331, 0.500034, 0.591749, 0.575275},
                new double[]{0.456861, 0.396472, 0.418205, 0.416828, 0.403009},
                new double[]{0.665287, 0.642418, 0.657877, 0.647031, 0.621209},
                new double[]{0.605844, 0.750158, 0.811916, 0.709712, 0.807127},
                new double[]{0.537799, 0.590652, 0.641827, 0.573102, 0.592224},
                new double[]{0.610783, 0.590798, 0.552729, 0.600443, 0.612352},
                new double[]{0.591619, 0.589067, 0.593705, 0.580104, 0.512761},
                new double[]{0.794815, 0.769489, 0.760266, 0.7727, 0.729201}
        };
        var samples = new WeightSamplesCollection(samplesArray, data.getInputCount());
        var inputs = smaaBase.performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = smaaBase.performanceToValueConverter.transformOutputsToUtilities(data);
        var actualEfficiencies = smaaBase.calculateEfficiencyMatrixForSamples(inputs, outputs, samples);
        assert2DDoubleArrayEquals(efficiencies, TestUtils.tranformTableToArray(actualEfficiencies));
    }

    private void assert2DDoubleArrayEquals(double[][] expected, double[][] actual) {
        Assertions.assertEquals(expected.length, actual.length);
        IntStream.range(0, expected.length)
                .forEach(idx -> Assertions.assertArrayEquals(expected[idx], actual[idx], 1e-6));
    }

    @Test
    public void verifyEfficiencyCalculationWithSampling() {
        var expected = new double[][]{
                new double[]{0.526127, 0.278447, 0.392428, 0.239471, 0.199649},
                new double[]{0.450057, 0.372211, 0.387887, 0.313674, 0.326909},
                new double[]{0.226018, 0.197549, 0.121647, 0.177984, 0.333832},
                new double[]{0.516978, 0.502256, 0.492273, 0.495189, 0.522558},
                new double[]{0.723058, 0.894965, 0.834636, 0.892322, 0.878007},
                new double[]{0.727878, 0.856185, 0.850923, 0.79293, 0.693806},
                new double[]{0.579829, 0.513261, 0.487854, 0.488314, 0.577398},
                new double[]{0.458811, 0.610512, 0.561627, 0.53993, 0.502451},
                new double[]{0.616147, 0.62667, 0.606606, 0.534311, 0.540906},
                new double[]{0.251925, 0.377878, 0.407206, 0.497556, 0.38143},
                new double[]{0.750643, 0.72617, 0.719057, 0.671196, 0.68855},
                new double[]{0.571968, 0.338621, 0.369589, 0.438378, 0.575423},
                new double[]{0.607534, 0.670316, 0.726575, 0.676213, 0.528288},
                new double[]{0.409568, 0.531417, 0.487017, 0.412577, 0.376628},
                new double[]{0.626697, 0.743976, 0.695237, 0.661196, 0.645455},
                new double[]{0.745408, 0.338711, 0.389012, 0.542162, 0.795079},
                new double[]{0.56592, 0.492021, 0.458799, 0.503001, 0.620677},
                new double[]{0.622732, 0.57513, 0.631072, 0.623587, 0.566951},
                new double[]{0.52808, 0.74841, 0.665535, 0.649643, 0.61803},
                new double[]{0.745942, 0.902533, 0.861506, 0.829543, 0.773425}
        };
        var inputs = smaaBase.performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = smaaBase.performanceToValueConverter.transformOutputsToUtilities(data);
        var actualEfficiencies = TestUtils.tranformTableToArray(smaaBase.calculateEfficiencyMatrix(data, inputs, outputs));
        assert2DDoubleArrayEquals(expected, actualEfficiencies);
    }

}
