package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VDEAExtremeRanksTest extends VDEATestBase {
    private static VDEAExtremeRanks extremeRanks;

    @BeforeAll
    public static void initializeRanks() {
        extremeRanks = new VDEAExtremeRanks();
    }

    @Test
    public void minRankWithLinearFunctions() {
        var expected = List.of(1, 9, 10, 11, 1, 1, 6, 7, 6, 5, 1, 6, 2, 10, 4, 1, 3, 4, 3, 1);
        var actual = extremeRanks.minRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRankWithLinearFunctions() {
        var expected = List.of(20, 19, 20, 16, 14, 11, 16, 18, 16, 20, 10, 19, 18, 19, 11, 20, 18, 15, 18, 7);
        var actual = extremeRanks.maxRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void minRankWithLinearFunctionsAndBoundsProvided() {
        var expected = List.of(1, 9, 10, 11, 1, 1, 6, 7, 6, 5, 1, 6, 2, 10, 4, 1, 3, 4, 3, 1);
        addInputOutputBoundaries();
        var actual = extremeRanks.minRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRankWithLinearFunctionsAndBoundsProvided() {
        var expected = List.of(20, 19, 20, 16, 14, 11, 16, 18, 16, 20, 10, 19, 18, 19, 11, 20, 18, 15, 18, 7);
        addInputOutputBoundaries();
        var actual = extremeRanks.maxRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void minRankWithFunctionShapes() {
        var expected = List.of(1, 9, 10, 11, 1, 1, 6, 8, 7, 5, 1, 8, 2, 10, 4, 1, 3, 4, 3, 1);
        addFunctionShapes();
        var actual = extremeRanks.minRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRankWithFunctionShapes() {
        var expected = List.of(20, 20, 20, 18, 14, 11, 16, 18, 16, 20, 10, 19, 18, 19, 13, 20, 18, 17, 18, 8);
        addFunctionShapes();
        var actual = extremeRanks.maxRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void minRankWithFunctionShapesAndWeightConstraints() {
        var expected = List.of(1, 9, 16, 12, 3, 1, 10, 10, 7, 14, 1, 9, 3, 11, 6, 1, 7, 4, 6, 2);
        addFunctionShapes();
        addWeightConstraints();
        var actual = extremeRanks.minRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRankWithFunctionShapesAndWeightConstraints() {
        var expected = List.of(18, 20, 20, 18, 14, 5, 15, 18, 14, 20, 4, 18, 8, 19, 13, 15, 18, 10, 18, 8);
        addFunctionShapes();
        addWeightConstraints();
        var actual = extremeRanks.maxRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void minRankWithLinearFunctionsAndWeightConstraints() {
        var expected = List.of(1, 9, 15, 11, 1, 1, 6, 9, 6, 12, 1, 6, 3, 11, 5, 1, 5, 5, 6, 1);
        addWeightConstraints();
        var actual = extremeRanks.minRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRankWithLinearFunctionsAndWeightConstraints() {
        var expected = List.of(20, 19, 20, 15, 14, 9, 15, 18, 11, 20, 5, 19, 17, 18, 11, 19, 18, 14, 18, 7);
        addWeightConstraints();
        var actual = extremeRanks.maxRankForAll(data);
        Assertions.assertIterableEquals(expected, actual);
    }
}
