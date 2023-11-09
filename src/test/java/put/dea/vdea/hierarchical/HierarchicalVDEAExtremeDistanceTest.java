package put.dea.vdea.hierarchical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HierarchicalVDEAExtremeDistanceTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyMaxDistancesAtIndexLevel() {
        var expected = new double[]{0.342457, 0.099699, 0.375136, 0.491428,
                0.145904, 0.116575, 0.396156, 0.358531, 0.415285, 0.512969,
                0.561902, 0.43815, 0.475095, 0.401693, 0.483603, 0.479041
        };
        verifyDistances("index", expected, false);
    }

    private void verifyDistances(String level, double[] expectedValues, boolean minimal) {
        var extremeDistances = new HierarchicalVDEAExtremeDistances();
        List<Double> actual;
        if (minimal)
            actual = extremeDistances.minDistanceForAll(data, level);
        else
            actual = extremeDistances.maxDistanceForAll(data, level);
        Assertions.assertArrayEquals(expectedValues,
                actual.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }

    @Test
    public void verifyMaxDistancesAtHealthLevel() {
        var expected = new double[]{0.146431, 0.180288, 0.559944, 0.645073,
                0.245625, 0, 0.573981, 0.427799, 0.616655, 0.621014,
                0.782697, 0.637952, 0.354831, 0.592848, 0.743995, 0.746668
        };
        verifyDistances("health_improvement", expected, false);
    }

    @Test
    public void verifyMaxDistancesAtFinancesLevel() {
        var expected = new double[]{0.391715, 0.088278, 0.321891, 0.434631,
                0.065956, 0.205051, 0.415785, 0.428224, 0.542331, 0.563064,
                0.519085, 0.402185, 0.814851, 0.410736, 0.277097, 0.562336
        };
        verifyDistances("finances", expected, false);
    }

    @Test
    public void verifyMaxDistancesAtSatisfactionLevel() {
        var expected = new double[]{0.669932, 0.306781, 0.31703, 0.555477,
                0.31277, 0.311668, 0.216236, 0.433328, 0.080679, 0.622781,
                0.318825, 0.330867, 0.38162, 0.284786, 0.346045, 0.056594
        };
        verifyDistances("satisfaction", expected, false);
    }

    @Test
    public void verifyMinDistancesAtIndexLevel() {
        var expected = new double[]{0.129988, 0, 0.17465, 0.248708,
                0, 0, 0.103395, 0.175149, 0.105747, 0.311586, 0.280002,
                0.178544, 0.191118, 0.096383, 0.16905, 0.164768
        };
        verifyDistances("index", expected, true);
    }

    @Test
    public void verifyMinDistancesAtHealthLevel() {
        var expected = new double[]{0.064701, 0.12995, 0.383248, 0.51376,
                0.114216, 0, 0.457983, 0.323508, 0.41413, 0.374076, 0.726297,
                0.470001, 0.232851, 0.469117, 0.564586, 0.55958
        };
        verifyDistances("health_improvement", expected, true);
    }

    @Test
    public void verifyMinDistancesAtFinancesLevel() {
        var expected = new double[]{0.305797, 0, 0.243761, 0.213654,
                0, 0.124755, 0.140321, 0.309616, 0.378759, 0.481357,
                0.350395, 0.223162, 0.662237, 0.26691, 0.193126, 0.419539
        };
        verifyDistances("finances", expected, true);
    }

    @Test
    public void verifyMinDistancesAtSatisfactionLevel() {
        var expectedMinEfficiency = new double[]{0.513052, 0, 0.170254, 0.454065,
                0.147485, 0.226751, 0, 0.24957, 0, 0.448859,
                0.049435, 0.241463, 0.277499, 0, 0.136657, 0
        };
        verifyDistances("satisfaction", expectedMinEfficiency, true);
    }
}
