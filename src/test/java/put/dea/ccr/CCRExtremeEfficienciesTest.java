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
        var expected = new double[]{
                1.0,
                1.0,
                0.591209131468945,
                1.0,
                0.799800988723909,
                0.3000362089255,
                1.0,
                0.270787290501618,
                1.0,
                0.409183376925312,
                0.258474576271186};
        var actual = extremeEfficiencies.maxEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithoutConstraintsTest() {
        var expected = new double[]{
                0.452380952380952,
                0.213833286161834,
                0.108455409920329,
                0.338216396812914,
                0.218581341557208,
                0.0566801619433198,
                0.30167681780585,
                0.0896196811848877,
                0.184210526315787,
                0.0699620206173791,
                0.000859349505055007};
        var actual = extremeEfficiencies.minEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void maxEfficiencyWithWeightConstraintsTest() {
        var expected = new double[]{
                1.0,
                0.962981823533741,
                0.554985241062643,
                0.922026971802207,
                0.779550830564784,
                0.282738095238095,
                1.0,
                0.260742298695236,
                0.954652739250888,
                0.383012675418742,
                0.188988095238095};

        addWeightConstraints();
        var actual = extremeEfficiencies.maxEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

    @Test
    public void minEfficiencyWithWeightConstraintsTest() {
        var expected = new double[]{
                0.452380952380952,
                0.439708141319492,
                0.210143905191874,
                0.445259593679673,
                0.43333333333259,
                0.0947368421052632,
                0.455079006771479,
                0.113684210526316,
                0.189473684210527,
                0.169300225733672,
                0.00146116173826793};

        addWeightConstraints();
        var actual = extremeEfficiencies.minEfficiencyForAll(data)
                .stream().mapToDouble(x -> x).toArray();
        assertArrayEquals(expected, actual, 1e-6);
    }

}
