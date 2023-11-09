package put.dea.ccr.smaa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import put.dea.SmaaTestUtils;
import put.dea.ccr.CCRTestBase;
import put.dea.common.smaa.WeightSamplesCollection;

import java.util.stream.IntStream;

public class CCRSmaaBaseTests extends CCRTestBase {
    private static CCRSmaaEfficiency efficiency;

    @BeforeAll
    public static void init() {
        efficiency = new CCRSmaaEfficiency(5, 10, SmaaTestUtils.getRandom());
    }

    @Test
    public void verifyEfficiencyCalculation() {
        var samplesArray = new double[][]{
                new double[]{0.359, 0.336, 0.224, 0.081, 0.375, 0.625},
                new double[]{0.246, 0.335, 0.149, 0.27, 0.416, 0.584},
                new double[]{0.319, 0.11, 0.331, 0.24, 0.788, 0.212},
                new double[]{0.296, 0.402, 0.212, 0.09, 0.067, 0.933},
                new double[]{0.258, 0.303, 0.272, 0.167, 0.425, 0.575}
        };

        var efficiencies = new double[][]{
                new double[]{1.863244, 2.225322, 0.675064, 2.665187, 1.572841},
                new double[]{1.357195, 1.394917, 0.577978, 1.841261, 1.167485},
                new double[]{0.547034, 0.579336, 0.243029, 0.733898, 0.469881},
                new double[]{1.391951, 1.4723, 0.609638, 1.88592, 1.215234},
                new double[]{1.105642, 1.194221, 0.425357, 1.533666, 0.929756},
                new double[]{0.275422, 0.293022, 0.108436, 0.377248, 0.231766},
                new double[]{1.018993, 1.184494, 0.392345, 1.415894, 0.853795},
                new double[]{0.287492, 0.327751, 0.109547, 0.400006, 0.241722},
                new double[]{1.336966, 1.285205, 0.714951, 1.743363, 1.222439},
                new double[]{0.461727, 0.474542, 0.186728, 0.628438, 0.389282},
                new double[]{0.021475, 0.026287, 0.005835, 0.032064, 0.016964},
        };
        var samples = new WeightSamplesCollection(samplesArray, data.getInputCount());
        var actualEfficiencies = efficiency.calculateEfficiencyMatrixForSamples(
                data.getInputData(),
                data.getOutputData(),
                samples);
        assert2DDoubleArrayEquals(efficiencies, actualEfficiencies.toModelMatrix(0));
    }

    private void assert2DDoubleArrayEquals(double[][] expected, double[][] actual) {
        Assertions.assertEquals(expected.length, actual.length);
        IntStream.range(0, expected.length)
                .forEach(idx -> Assertions.assertArrayEquals(expected[idx], actual[idx], 1e-6));
    }

    @Test
    public void verifyEfficiencyCalculationWithSampling() {
        var expected = new double[][]{
                new double[]{1.030892, 0.154119, 2.348693, 1.366934, 1.147123},
                new double[]{0.512864, 0.178977, 1.979985, 1.252951, 1.04031},
                new double[]{0.219692, 0.081957, 0.799163, 0.486904, 0.396115},
                new double[]{0.478594, 0.168445, 2.212611, 1.347347, 1.100432},
                new double[]{0.482107, 0.112121, 1.487426, 0.896018, 0.740866},
                new double[]{0.103693, 0.027049, 0.400241, 0.235224, 0.189773},
                new double[]{0.483035, 0.099527, 1.373984, 0.773451, 0.625508},
                new double[]{0.124255, 0.02548, 0.399398, 0.226515, 0.18348},
                new double[]{0.294085, 0.186245, 3.22046, 2.067498, 1.585099},
                new double[]{0.178116, 0.054276, 0.654676, 0.40315, 0.329965},
                new double[]{0.013735, 0.000417, 0.024912, 0.013558, 0.01116}
        };
        var actualEfficiencies = efficiency.calculateEfficiencyMatrix(data)
                .toModelMatrix(0);
        assert2DDoubleArrayEquals(expected, actualEfficiencies);
    }

}
