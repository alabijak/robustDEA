package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class VDEATransformationToUtilitiesTest extends VDEATestBase {

    private static VDEAExtremeEfficiencies efficiencies;

    @BeforeAll
    public static void initializeEfficiencies() {
        efficiencies = new VDEAExtremeEfficiencies();
    }

    @Test
    public void transformToUtilitiesLinearTest() {
        var expectedInputs = new double[][]{
                new double[]{0.325342, 0.0, 0.0},
                new double[]{0.401826, 0.199159, 0.318777},
                new double[]{0.100456, 0.224382, 0.604803},
                new double[]{0.487442, 0.492380, 0.565502},
                new double[]{0.913242, 1.0, 0.945414},
                new double[]{0.976027, 0.751445, 0.593886},
                new double[]{0.465753, 0.464004, 0.707423},
                new double[]{0.692922, 0.541250, 0.528384},
                new double[]{0.688356, 0.437729, 0.574235},
                new double[]{0.378995, 0.657908, 0.218340},
                new double[]{0.752283, 0.600105, 0.716157},
                new double[]{0.162100, 0.471361, 0.681222},
                new double[]{0.772831, 0.642143, 0.292576},
                new double[]{0.646118, 0.337887, 0.397379},
                new double[]{0.818493, 0.636889, 0.694323},
                new double[]{0.0, 0.642143, 1.0},
                new double[]{0.398401, 0.531791, 0.792576},
                new double[]{0.578767, 0.614818, 0.430131},
                new double[]{0.849315, 0.668943, 0.696506},
                new double[]{1.0, 0.820809, 0.768558}
        };

        var expectedOutputs = new double[][]{
                new double[]{1.0},
                new double[]{0.589473},
                new double[]{0.0},
                new double[]{0.494736},
                new double[]{0.494736},
                new double[]{0.768421},
                new double[]{0.536842},
                new double[]{0.357894},
                new double[]{0.663157},
                new double[]{0.178947},
                new double[]{0.8},
                new double[]{0.589473},
                new double[]{0.778947},
                new double[]{0.389473},
                new double[]{0.547368},
                new double[]{0.726315},
                new double[]{0.452631},
                new double[]{0.757894},
                new double[]{0.336842},
                new double[]{0.673684}

        };

        var inputValues = TestUtils.tranformTableToArray(efficiencies.transformInputsToUtilities(data));
        var outputValues = TestUtils.tranformTableToArray(efficiencies.transformOutputsToUtilities(data));

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

    private double[] flattenArray(double[][] arr) {
        return Arrays.stream(arr).flatMapToDouble(Arrays::stream).toArray();
    }

    @Test
    public void transformToUtilitiesLinearWithBoundariesTest() {
        var expectedInputs = new double[][]{
                new double[]{0.249473, 0.080000, 0.08},
                new double[]{0.284736, 0.206333, 0.226},
                new double[]{0.145789, 0.222333, 0.357},
                new double[]{0.324210, 0.392333, 0.339},
                new double[]{0.520526, 0.714333, 0.513},
                new double[]{0.549473, 0.556666, 0.352},
                new double[]{0.314210, 0.374333, 0.404},
                new double[]{0.418947, 0.423333, 0.322},
                new double[]{0.416842, 0.357666, 0.343},
                new double[]{0.274210, 0.497333, 0.18},
                new double[]{0.446315, 0.460666, 0.408},
                new double[]{0.174210, 0.379, 0.392},
                new double[]{0.455789, 0.487333, 0.214},
                new double[]{0.397368, 0.294333, 0.262},
                new double[]{0.476842, 0.484, 0.398},
                new double[]{0.099473, 0.487333, 0.538},
                new double[]{0.283157, 0.417333, 0.443},
                new double[]{0.366315, 0.47, 0.277},
                new double[]{0.491052, 0.504333, 0.399},
                new double[]{0.560526, 0.600666, 0.432}
        };

        var expectedOutputs = new double[][]{
                new double[]{1.0},
                new double[]{0.645454},
                new double[]{0.136363},
                new double[]{0.563636},
                new double[]{0.563636},
                new double[]{0.8},
                new double[]{0.6},
                new double[]{0.445454},
                new double[]{0.709090},
                new double[]{0.290909},
                new double[]{0.827272},
                new double[]{0.645454},
                new double[]{0.809090},
                new double[]{0.472727},
                new double[]{0.609090},
                new double[]{0.763636},
                new double[]{0.527272},
                new double[]{0.790909},
                new double[]{0.427272},
                new double[]{0.718181}
        };

        addInputOutputBoundaries();
        var inputValues = TestUtils.tranformTableToArray(efficiencies.transformInputsToUtilities(data));
        var outputValues = TestUtils.tranformTableToArray(efficiencies.transformOutputsToUtilities(data));

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

    @Test
    public void transformToUtilitiesWithFunctionShapes() {
        var expectedInputs = new double[][]{
                new double[]{0.0474, 0.18, 0.013333},
                new double[]{0.082086, 0.46425, 0.037666},
                new double[]{0.0277, 0.50025, 0.082571},
                new double[]{0.140782, 0.772125, 0.072285},
                new double[]{0.432695, 0.892875, 0.171714},
                new double[]{0.475739, 0.83375, 0.079714},
                new double[]{0.125913, 0.765375, 0.109428},
                new double[]{0.281652, 0.78375, 0.062571},
                new double[]{0.278521, 0.759125, 0.074571},
                new double[]{0.066434, 0.8115, 0.03},
                new double[]{0.322347, 0.79775, 0.111714},
                new double[]{0.0331, 0.767125, 0.102571},
                new double[]{0.336434, 0.80775, 0.035666},
                new double[]{0.249565, 0.66225, 0.043666},
                new double[]{0.367739, 0.8065, 0.106},
                new double[]{0.0189, 0.80775, 0.186},
                new double[]{0.079739, 0.7815, 0.131714},
                new double[]{0.203391, 0.80125, 0.046166},
                new double[]{0.388869, 0.814125, 0.106571},
                new double[]{0.492173, 0.85025, 0.125428}
        };

        var expectedOutputs = new double[][]{
                new double[]{1.0},
                new double[]{0.155},
                new double[]{0.025},
                new double[]{0.11},
                new double[]{0.11},
                new double[]{0.413333},
                new double[]{0.13},
                new double[]{0.081666},
                new double[]{0.19},
                new double[]{0.053333},
                new double[]{0.493333},
                new double[]{0.155},
                new double[]{0.44},
                new double[]{0.086666},
                new double[]{0.135},
                new double[]{0.306666},
                new double[]{0.096666},
                new double[]{0.386666},
                new double[]{0.078333},
                new double[]{0.195}
        };

        addFunctionShapes();
        var inputValues = TestUtils.tranformTableToArray(efficiencies.transformInputsToUtilities(data));
        var outputValues = TestUtils.tranformTableToArray(efficiencies.transformOutputsToUtilities(data));

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

}
