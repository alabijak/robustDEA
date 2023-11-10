package put.dea.ccr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CCRExtremeEfficienciesTest extends CCRTestBase {
    private static CCRExtremeEfficiencies extremeEfficiencies;

    @BeforeAll
    public static void initializeExtremeEfficiencies() {
        extremeEfficiencies = new CCRExtremeEfficiencies();
    }

    @Test
    public void maxEfficiencyWithoutConstraintsTest() {
        var expected = new double[]{1.0, 1.0, 0.591209, 1.0, 0.799801,
                0.300036, 1.0, 0.270787, 1.0, 0.409183, 0.258475};
        var actual = extremeEfficiencies.maxEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithoutConstraintsTest() {
        var expected = new double[]{
                0.452381, 0.213833, 0.108455, 0.338216, 0.218581, 0.056680,
                0.301677, 0.089620, 0.184211, 0.069962, 0.000859};
        var actual = extremeEfficiencies.minEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithWeightConstraintsTest() {
        var expected = new double[]{
                1.0, 0.962982, 0.554985, 0.922027, 0.779551, 0.282738,
                1.0, 0.260742, 0.954653, 0.383013, 0.188988};

        addWeightConstraints();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithWeightConstraintsTest() {
        var expected = new double[]{
                0.452381, 0.439708, 0.210144, 0.445260, 0.433333,
                0.094737, 0.455079, 0.113684, 0.189474, 0.169300, 0.001461};

        addWeightConstraints();
        var actual = extremeEfficiencies.minEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void superEfficiencyWithoutWeightTest() {
        var expected = new double[]{
                2.277949, 1.123783, 0.591209, 1.0399456, 0.799801, 0.300036,
                2.0, 0.270787, 1.745932, 0.409183, 0.258475};

        var actual = extremeEfficiencies.superEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }
}
