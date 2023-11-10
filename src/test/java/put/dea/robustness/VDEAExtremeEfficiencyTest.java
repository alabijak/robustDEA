package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VDEAExtremeEfficiencyTest extends VDEATestBase {
    private static VDEAExtremeEfficiencies extremeEfficiencies;

    @BeforeAll
    public static void initEfficiencies() {
        extremeEfficiencies = new VDEAExtremeEfficiencies();
    }

    @Test
    public void minEfficiencyWithLinearFunctions() {
        var expected = new double[]{0, 0.1991592222805, 0, 0.4874429223745, 0.4947368421055,
                0.593886462882, 0.4640042038885, 0.357894736842, 0.4377299001575, 0.178947368421,
                0.600105097215, 0.162100456621, 0.292576419214, 0.33788754598, 0.547368421053,
                0, 0.398401826484, 0.430131004367, 0.336842105263, 0.673684210526};
        var actual = extremeEfficiencies.minEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithLinearFunctions() {
        var expected = new double[]{1, 0.589473684211, 0.60480349345, 0.565502183406, 1,
                0.97602739726, 0.707423580786, 0.692922374429, 0.688356164384, 0.657908565423,
                0.8, 0.681222707424, 0.778947368421, 0.646118721461, 0.818493150685,
                1, 0.792576419214, 0.757894736842, 0.849315068493, 1};
        var actual = extremeEfficiencies.maxEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithLinearFunctionsAndBoundsProvided() {
        var expected = new double[]{0.08, 0.20633333333325, 0.13636363636375, 0.324210526316, 0.513,
                0.352, 0.314210526316, 0.322, 0.343, 0.18,
                0.408, 0.17421052631575, 0.214, 0.262, 0.398,
                0.0994736842105, 0.283157894737, 0.277, 0.399, 0.432};
        addInputOutputBoundaries();
        var actual = extremeEfficiencies.minEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithLinearFunctionsAndBoundsProvided() {
        var expected = new double[]{1, 0.645454545455, 0.357, 0.563636363636, 0.714333333333, 0.8,
                0.6, 0.4454545454545, 0.709090909091, 0.4973333333335, 0.827272727273,
                0.645454545455, 0.809090909091, 0.4727272727275, 0.609090909091, 0.763636363636,
                0.527272727273, 0.790909090909, 0.504333333333, 0.718181818182};
        addInputOutputBoundaries();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithFunctionShapes() {
        var expected = new double[]{0.0133333333333281, 0.0376666666666875, 0.025, 0.07228571428575, 0.11,
                0.07971428571425, 0.109428571428625, 0.062571428571375, 0.074571428571375, 0.03,
                0.11171428571425, 0.0331, 0.0356666666666875, 0.0436666666666875, 0.106,
                0.0189, 0.07973913043475, 0.0461666666666875, 0.078333333333375, 0.1254285714285};
        addFunctionShapes();
        var actual = extremeEfficiencies.minEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithFunctionShapes() {
        var expected = new double[]{1, 0.46425, 0.50025, 0.772125, 0.892875,
                0.83375, 0.765375, 0.78375, 0.759125, 0.8115,
                0.79775, 0.767125, 0.80775, 0.66225, 0.8065,
                0.80775, 0.7815, 0.80125, 0.814125, 0.85025};
        addFunctionShapes();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }


    @Test
    public void minEfficiencyWithFunctionShapesAndWeightConstraints() {
        var expected = new double[]{0.210666666666662, 0.06113333333335, 0.025, 0.0798285714286, 0.11,
                0.1464380952381, 0.1135428571429, 0.066390476190425, 0.0976571428571, 0.0346666666666625,
                0.1880380952381, 0.0783214285714125, 0.11653333333335, 0.052266666666675, 0.1118,
                0.1265833333333, 0.0882028985506875, 0.11426666666665, 0.078333333333375, 0.1393428571428};
        addFunctionShapes();
        addWeightConstraints();
        var actual = extremeEfficiencies.minEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithFunctionShapesAndWeightConstraints() {
        var expected = new double[]{1, 0.300489855072467, 0.279186666666667, 0.471342028985533, 0.6135855072464,
                0.654197101449367, 0.4677768115942, 0.509440579710125, 0.5171391304348, 0.461182608695663,
                0.610092753623233, 0.44896, 0.608515942028933, 0.437084057970992, 0.555197101449333,
                0.4971733333333, 0.457397101449258, 0.558904347826033, 0.553565217391342, 0.623713043478267};
        addFunctionShapes();
        addWeightConstraints();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithLinearFunctionsAndWeightConstraints() {
        var expected = new double[]{0.2, 0.3312667178, 0, 0.4903829652, 0.4947368421,
                0.6287933808, 0.479038243, 0.3578947368, 0.5496491695, 0.1789473684,
                0.6806648799, 0.3403532678, 0.3898506091, 0.3894736842, 0.5473684211,
                0.3379063528, 0.4255167027, 0.4956837509, 0.3368421053, 0.6736842105};
        addWeightConstraints();
        var actual = extremeEfficiencies.minEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithLinearFunctionsAndWeightConstraints() {
        var expected = new double[]{1, 0.5894736842, 0.4838427948, 0.5513491151, 0.8758119042,
                0.8722242249, 0.6733072857, 0.5804153316, 0.6757570296, 0.4877394909,
                0.8, 0.6628729028, 0.7789473684, 0.5201680714, 0.7270172028,
                0.9452631579, 0.7245874512, 0.7578947368, 0.7009779204, 0.8809796167};
        addWeightConstraints();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }
}
