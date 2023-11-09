package put.dea.vdea;

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
        var expectedInputs = new Double[][]{
                new Double[]{0.325342465753425, 0.0, 0.0},
                new Double[]{0.401826484018265, 0.19915922228061, 0.318777292576419},
                new Double[]{0.100456621004566, 0.224382553862322, 0.604803493449782},
                new Double[]{0.487442922374429, 0.492380451918024, 0.565502183406114},
                new Double[]{0.91324200913242, 1.0, 0.945414847161572},
                new Double[]{0.976027397260274, 0.751445086705202, 0.593886462882096},
                new Double[]{0.465753424657534, 0.464004203888597, 0.707423580786026},
                new Double[]{0.692922374429224, 0.541250656857593, 0.528384279475983},
                new Double[]{0.688356164383562, 0.437729900157646, 0.574235807860262},
                new Double[]{0.378995433789954, 0.657908565423016, 0.218340611353712},
                new Double[]{0.752283105022831, 0.600105097214924, 0.716157205240175},
                new Double[]{0.162100456621004, 0.471361008933263, 0.681222707423581},
                new Double[]{0.772831050228311, 0.642143983184446, 0.292576419213974},
                new Double[]{0.646118721461187, 0.337887545980031, 0.397379912663756},
                new Double[]{0.818493150684931, 0.636889122438255, 0.694323144104804},
                new Double[]{0.0, 0.642143983184446, 1.0},
                new Double[]{0.398401826484018, 0.531791907514451, 0.792576419213974},
                new Double[]{0.578767123287671, 0.614818707304256, 0.430131004366812},
                new Double[]{0.849315068493151, 0.668943772990016, 0.696506550218341},
                new Double[]{1.0, 0.820809248554913, 0.768558951965066}
        };

        var expectedOutputs = new Double[][]{
                new Double[]{1.0},
                new Double[]{0.589473684210526},
                new Double[]{0.0},
                new Double[]{0.494736842105263},
                new Double[]{0.494736842105263},
                new Double[]{0.768421052631579},
                new Double[]{0.536842105263157},
                new Double[]{0.357894736842104},
                new Double[]{0.663157894736842},
                new Double[]{0.178947368421053},
                new Double[]{0.8},
                new Double[]{0.589473684210526},
                new Double[]{0.778947368421052},
                new Double[]{0.389473684210526},
                new Double[]{0.547368421052631},
                new Double[]{0.726315789473684},
                new Double[]{0.452631578947368},
                new Double[]{0.757894736842105},
                new Double[]{0.336842105263158},
                new Double[]{0.673684210526315}

        };

        var inputValues = efficiencies.transformInputsToUtilities(data).toArray(new Double[0][]);
        var outputValues = efficiencies.transformOutputsToUtilities(data).toArray(new Double[0][]);

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

    private double[] flattenArray(Double[][] arr) {
        return Arrays.stream(arr).flatMapToDouble(row -> Arrays.stream(row).mapToDouble(v -> v)).toArray();
    }

    @Test
    public void transformToUtilitiesLinearWithBoundariesTest() {
        var expectedInputs = new Double[][]{
                new Double[]{0.249473684210526, 0.0800000000000001, 0.08},
                new Double[]{0.284736842105263, 0.206333333333333, 0.226},
                new Double[]{0.145789473684211, 0.222333333333333, 0.357},
                new Double[]{0.32421052631579, 0.392333333333333, 0.339},
                new Double[]{0.520526315789474, 0.714333333333333, 0.513},
                new Double[]{0.549473684210526, 0.556666666666667, 0.352},
                new Double[]{0.314210526315789, 0.374333333333333, 0.404},
                new Double[]{0.418947368421053, 0.423333333333333, 0.322},
                new Double[]{0.416842105263158, 0.357666666666667, 0.343},
                new Double[]{0.274210526315789, 0.497333333333333, 0.18},
                new Double[]{0.446315789473684, 0.460666666666667, 0.408},
                new Double[]{0.174210526315789, 0.379, 0.392},
                new Double[]{0.455789473684211, 0.487333333333333, 0.214},
                new Double[]{0.397368421052632, 0.294333333333333, 0.262},
                new Double[]{0.476842105263158, 0.484, 0.398},
                new Double[]{0.0994736842105264, 0.487333333333333, 0.538},
                new Double[]{0.283157894736842, 0.417333333333333, 0.443},
                new Double[]{0.366315789473684, 0.47, 0.277},
                new Double[]{0.491052631578947, 0.504333333333333, 0.399},
                new Double[]{0.560526315789474, 0.600666666666667, 0.432}
        };

        var expectedOutputs = new Double[][]{
                new Double[]{1.0},
                new Double[]{0.645454545454545},
                new Double[]{0.136363636363636},
                new Double[]{0.563636363636363},
                new Double[]{0.563636363636363},
                new Double[]{0.8},
                new Double[]{0.6},
                new Double[]{0.445454545454545},
                new Double[]{0.709090909090909},
                new Double[]{0.290909090909091},
                new Double[]{0.827272727272727},
                new Double[]{0.645454545454545},
                new Double[]{0.809090909090909},
                new Double[]{0.472727272727272},
                new Double[]{0.609090909090909},
                new Double[]{0.763636363636363},
                new Double[]{0.527272727272727},
                new Double[]{0.790909090909091},
                new Double[]{0.427272727272728},
                new Double[]{0.718181818181818}
        };

        addInputOutputBoundaries();
        var inputValues = efficiencies.transformInputsToUtilities(data).toArray(new Double[0][]);
        var outputValues = efficiencies.transformOutputsToUtilities(data).toArray(new Double[0][]);

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

    @Test
    public void transformToUtilitiesWithFunctionShapes() {
        var expectedInputs = new Double[][]{
                new Double[]{0.0474, 0.18, 0.0133333333333333},
                new Double[]{0.0820869565217391, 0.46425, 0.0376666666666667},
                new Double[]{0.0277, 0.50025, 0.0825714285714285},
                new Double[]{0.140782608695652, 0.772125, 0.0722857142857142},
                new Double[]{0.432695652173913, 0.892875, 0.171714285714286},
                new Double[]{0.475739130434783, 0.83375, 0.0797142857142857},
                new Double[]{0.125913043478261, 0.765375, 0.109428571428571},
                new Double[]{0.281652173913044, 0.78375, 0.0625714285714285},
                new Double[]{0.278521739130435, 0.759125, 0.0745714285714285},
                new Double[]{0.0664347826086956, 0.8115, 0.03},
                new Double[]{0.322347826086957, 0.79775, 0.111714285714286},
                new Double[]{0.0331, 0.767125, 0.102571428571429},
                new Double[]{0.336434782608696, 0.80775, 0.0356666666666667},
                new Double[]{0.249565217391304, 0.66225, 0.0436666666666667},
                new Double[]{0.367739130434783, 0.8065, 0.106},
                new Double[]{0.0189, 0.80775, 0.186},
                new Double[]{0.0797391304347825, 0.7815, 0.131714285714286},
                new Double[]{0.203391304347826, 0.80125, 0.0461666666666667},
                new Double[]{0.388869565217391, 0.814125, 0.106571428571429},
                new Double[]{0.492173913043478, 0.85025, 0.125428571428571}
        };

        var expectedOutputs = new Double[][]{
                new Double[]{1.0},
                new Double[]{0.155},
                new Double[]{0.025},
                new Double[]{0.11},
                new Double[]{0.11},
                new Double[]{0.413333333333333},
                new Double[]{0.13},
                new Double[]{0.0816666666666666},
                new Double[]{0.19},
                new Double[]{0.0533333333333334},
                new Double[]{0.493333333333333},
                new Double[]{0.155},
                new Double[]{0.44},
                new Double[]{0.0866666666666667},
                new Double[]{0.135},
                new Double[]{0.306666666666667},
                new Double[]{0.0966666666666667},
                new Double[]{0.386666666666667},
                new Double[]{0.0783333333333335},
                new Double[]{0.195}
        };

        addFunctionShapes();
        var inputValues = efficiencies.transformInputsToUtilities(data).toArray(new Double[0][]);
        var outputValues = efficiencies.transformOutputsToUtilities(data).toArray(new Double[0][]);

        Assertions.assertArrayEquals(flattenArray(expectedInputs), flattenArray(inputValues), 1e-6);
        Assertions.assertArrayEquals(flattenArray(expectedOutputs), flattenArray(outputValues), 1e-6);
    }

}
