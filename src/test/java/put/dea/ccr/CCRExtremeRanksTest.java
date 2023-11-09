package put.dea.ccr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CCRExtremeRanksTest extends CCRTestBase {

    private static CCRExtremeRanks extremeRanks;

    @BeforeAll
    public static void initializeExtremeEfficiencies() {
        extremeRanks = new CCRExtremeRanks();
    }

    @Test
    public void maxRanksWithoutConstraintsTest() {
        var expected = List.of(5, 6, 10, 5, 6, 10, 6, 10, 8, 11, 11);

        var actual = extremeRanks.maxRankForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void minRanksWithoutConstraintsTest() {
        var expected = List.of(1, 1, 6, 1, 3, 7, 1, 7, 1, 7, 8);
        var actual = extremeRanks.minRankForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void maxRanksWithWeightConstraintsTest() {
        var expected = List.of(5, 6, 8, 5, 6, 10, 6, 10, 8, 9, 11);

        addWeightConstraints();
        var actual = extremeRanks.maxRankForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void minRanksWithWeightConstraintsTest() {
        var expected = List.of(1, 2, 6, 2, 3, 7, 1, 9, 2, 7, 11);

        addWeightConstraints();
        var actual = extremeRanks.minRankForAll(data);
        assertIterableEquals(expected, actual);
    }
}
