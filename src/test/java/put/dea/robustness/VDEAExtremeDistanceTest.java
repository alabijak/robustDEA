package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VDEAExtremeDistanceTest extends VDEATestBase {
    private static VDEAExtremeDistances distances;

    @BeforeAll
    public static void initializeDistances() {
        distances = new VDEAExtremeDistances();
    }

    @Test
    public void minDistanceWithLinearFunctions() {
        var expected = new double[]{0, 0.25137408, 0.36724182, 0.22880515, 0, 0,
                0.15286383, 0.27917125, 0.12247429, 0.32957326, 0,
                0.16842994, 0.0177024, 0.32153126, 0.10903285, 0,
                0.1554005, 0.04023273, 0.12480617, 0};
        var actual = distances.minDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxDistanceWithLinearFunctions() {
        var expected = new double[]{1.0, 0.8008407777195, 1, 0.512557077625128, 0.5052631578945,
                0.406113537118, 0.5359957961115, 0.642105263158, 0.5622700998425, 0.821052631579,
                0.399894902785, 0.837899543379233, 0.707423580786, 0.66211245402, 0.452631578947,
                1, 0.601598173515739, 0.569868995633, 0.663157894737, 0.326315789474};
        var actual = distances.maxDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minDistanceWithLinearFunctionsAndBoundsProvided() {
        var expected = new double[]{0, 0.176122412835215, 0.168259587020633, 0.131917049154222, 0,
                0, 0.0863465015178212, 0.131139625924033, 0.0746716162193226, 0.2169999999995,
                0, 0.103520614661877, 0.0140886699507124, 0.1631578947365, 0.0615881793338494,
                0, 0.0793805309734254, 0.0320197044336488, 0.0574162679422314, 0};
        addInputOutputBoundaries();
        var actual = distances.minDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxDistanceWithLinearFunctionsAndBoundsProvided() {
        var expected = new double[]{0.634333333333, 0.50799999999975, 0.86363636363625, 0.436363636364, 0.436363636364,
                0.2, 0.4, 0.5545454545455, 0.3566666666665, 0.709090909091,
                0.2536666666665, 0.386315789472831, 0.324, 0.5272727272725, 0.390909090909,
                0.461052631579195, 0.472727272727, 0.261, 0.5727272727275, 0.281818181818};
        addInputOutputBoundaries();
        var actual = distances.maxDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minDistanceWithFunctionShapes() {
        var expected = new double[]{0, 0.1285866777428, 0.0996833364528859, 0.0921383085646106, 0,
                0, 0.0704449406639697, 0.0943562209499, 0.0766997806475418, 0.0773445223588973,
                0, 0.0755109199289492, 0.00965517241384485, 0.118007383525165, 0.0510710247362586,
                0, 0.0502568708460793, 0.0306896551725175, 0.0548002874148655, 0};
        addFunctionShapes();
        var actual = distances.minDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxDistanceWithFunctionShapes() {
        var expected = new double[]{0.712875, 0.845, 0.975, 0.89, 0.89,
                0.5866666666665, 0.87, 0.918333333333375, 0.81, 0.946666666666687,
                0.5066666666665, 0.845, 0.56, 0.913333333333375, 0.865,
                0.6933333333335, 0.903333333333375, 0.6133333333335, 0.921666666666625, 0.805};
        addFunctionShapes();
        var actual = distances.maxDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minDistanceWithLinearFunctionsAndWeightConstraints() {
        var expected = new double[]{0, 0.2513740784, 0.4153369207, 0.229830689, 0, 0,
                0.1528638282, 0.2791712473, 0.1224742936, 0.3757866533, 0,
                0.1740941649, 0.0263350556, 0.3215312576, 0.1090328467, 0,
                0.1554004963, 0.0602414452, 0.156537833, 0};
        addWeightConstraints();
        var actual = distances.minDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxDistanceWithLinearFunctionsAndWeightConstraints() {
        var expected = new double[]{0.7452631579, 0.572346587, 1, 0.5052631579, 0.5052631579,
                0.3164697771, 0.4631578947, 0.6421052632, 0.3532429327, 0.8210526316,
                0.2123373937, 0.5406263488, 0.5554125488, 0.6105263158, 0.452631579,
                0.5430732638, 0.5473684211, 0.449579407, 0.6631578948, 0.3263157894};
        addWeightConstraints();
        var actual = distances.maxDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minDistanceWithFunctionShapesAndWeightConstraints() {
        var expected = new double[]{0, 0.142438261784678, 0.138121507235812, 0.113207611514087, 0.0130568181818545,
                0, 0.0855941411263648, 0.111456648113856, 0.0907805488064536, 0.151205967841705,
                0, 0.0890797516043226, 0.019970104633916, 0.136378258432647, 0.0647704994192644,
                0, 0.0779575358839588, 0.0628098639456432, 0.0710334494773877, 0.0136652729385353};
        addFunctionShapes();
        addWeightConstraints();
        var actual = distances.minDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxDistanceWithFunctionShapesAndWeightConstraints() {
        var expected = new double[]{0.345557101449367, 0.845, 0.975, 0.89, 0.89,
                0.5866666666665, 0.87, 0.918333333333375, 0.81, 0.946666666666688,
                0.5066666666665, 0.845, 0.56, 0.913333333333375, 0.865,
                0.6933333333335, 0.903333333333375, 0.6133333333335, 0.921666666666625, 0.805};
        addFunctionShapes();
        addWeightConstraints();
        var actual = distances.maxDistanceForAll(data).stream().mapToDouble(x -> x).toArray();
        Assertions.assertArrayEquals(expected, actual, 1e-6);
    }
}
