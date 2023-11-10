package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HierarchicalVDEAExtremeEfficiencyTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyMaxEfficienciesAtIndexLevel() {
        var expectedMaxEfficiency = new double[]{0.690204, 0.830347, 0.588746, 0.486805,
                0.814148, 0.832732, 0.634884, 0.585405, 0.606351, 0.476959,
                0.452445, 0.557267, 0.560952, 0.618889, 0.523134, 0.565685
        };
        verifyEfficiencies("index", expectedMaxEfficiency, false);
    }

    private void verifyEfficiencies(String level, double[] expectedValues, boolean minimal) {
        HierarchicalVDEAExtremeEfficiencies extremeEfficiencies = new HierarchicalVDEAExtremeEfficiencies();
        List<Double> actual;
        if (minimal)
            actual = extremeEfficiencies.minEfficiencyForAll(data, level);
        else
            actual = extremeEfficiencies.maxEfficiencyForAll(data, level);
        Assertions.assertArrayEquals(expectedValues,
                actual.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }

    @Test
    public void verifyMaxEfficienciesAtHealthLevel() {
        var expectedMaxEfficiency = new double[]{0.886067, 0.833354, 0.572783, 0.454238,
                0.841814, 0.968238, 0.510014, 0.639219, 0.536158, 0.581955,
                0.241941, 0.480286, 0.72375, 0.48165, 0.392164, 0.391187
        };
        verifyEfficiencies("health_improvement", expectedMaxEfficiency, false);
    }

    @Test
    public void verifyMaxEfficienciesAtFinancesLevel() {
        var expectedMaxEfficiency = new double[]{0.559846, 0.876785, 0.623249, 0.613, 0.868662,
                0.741923, 0.657, 0.553515, 0.44875, 0.385017, 0.440911,
                0.634977, 0.135083, 0.601753, 0.606083, 0.451502
        };
        verifyEfficiencies("finances", expectedMaxEfficiency, false);
    }

    @Test
    public void verifyMaxEfficienciesAtSatisfactionLevel() {
        var expectedMaxEfficiency = new double[]{0.232971, 0.731455, 0.551666, 0.299548,
                0.649789, 0.517025, 0.737636, 0.455854, 0.755345, 0.253912, 0.668717,
                0.555811, 0.498424, 0.764697, 0.571155, 0.797274
        };
        verifyEfficiencies("satisfaction", expectedMaxEfficiency, false);
    }

    @Test
    public void verifyMinEfficienciesAtIndexLevel() {
        var expectedMinEfficiency = new double[]{0.445065, 0.637293, 0.421332, 0.288404,
                0.606763, 0.664607, 0.426093, 0.424429, 0.403585, 0.257895,
                0.258468, 0.368746, 0.341989, 0.410381, 0.334974, 0.316486
        };
        verifyEfficiencies("index", expectedMinEfficiency, true);

    }

    @Test
    public void verifyMinEfficienciesAtHealthLevel() {
        var expectedMinEfficiency = new double[]{0.821567, 0.772827, 0.396807, 0.305694,
                0.711126, 0.950287, 0.376786, 0.525244, 0.351583, 0.335736,
                0.167591, 0.330286, 0.6015, 0.37515, 0.212036, 0.22133
        };
        verifyEfficiencies("health_improvement", expectedMinEfficiency, true);
    }

    @Test
    public void verifyMinEfficienciesAtFinancesLevel() {
        var expectedMinEfficiency = new double[]{0.406038, 0.733154, 0.475429, 0.43,
                0.778098, 0.592269, 0.461, 0.384096, 0.3223, 0.269017,
                0.349744, 0.447131, 0.061933, 0.426291, 0.587533, 0.234985
        };
        verifyEfficiencies("finances", expectedMinEfficiency, true);
    }

    @Test
    public void verifyMinEfficienciesAtSatisfactionLevel() {
        var expectedMinEfficiency = new double[]{0.107589, 0.453829, 0.440812, 0.200316,
                0.416731, 0.451265, 0.539109, 0.363946, 0.670158, 0.174493,
                0.439825, 0.405744, 0.383077, 0.500164, 0.451229, 0.683454

        };
        verifyEfficiencies("satisfaction", expectedMinEfficiency, true);
    }
}
