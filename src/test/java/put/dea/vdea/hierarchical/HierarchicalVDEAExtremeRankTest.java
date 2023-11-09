package put.dea.vdea.hierarchical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HierarchicalVDEAExtremeRankTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyMaxRanksAtIndexLevel() {
        var expected = new int[]{13, 3, 13, 16, 3, 3, 13, 13, 13, 16, 16, 16, 16, 13, 15, 16};
        verifyRanks("index", expected, false);
    }

    private void verifyRanks(String level, int[] expectedValues, boolean minimal) {
        var extremeRanks = new HierarchicalVDEAExtremeRanks();
        List<Integer> actual;
        if (minimal)
            actual = extremeRanks.minRanksForAll(data, level);
        else
            actual = extremeRanks.maxRankForAll(data, level);
        Assertions.assertIterableEquals(Arrays.stream(expectedValues).boxed().toList(), actual);
    }

    @Test
    public void verifyMaxRanksAtHealthLevel() {
        var expected = new int[]{4, 4, 10, 15, 5, 1, 13, 9, 14, 15, 16, 13, 5, 12, 15, 16};
        verifyRanks("health_improvement", expected, false);
    }

    @Test
    public void verifyMaxRanksAtFinancesLevel() {
        var expected = new int[]{12, 2, 9, 11, 2, 5, 11, 13, 14, 15, 15, 8, 16, 11, 7, 15};
        verifyRanks("finances", expected, false);
    }

    @Test
    public void verifyMaxRanksAtSatisfactionLevel() {
        var expected = new int[]{16, 10, 11, 15, 11, 12, 5, 13, 5, 16, 11, 12, 13, 9, 12, 3};
        verifyRanks("satisfaction", expected, false);
    }

    @Test
    public void verifyMinRanksAtIndexLevel() {
        var expected = new int[]{4, 1, 4, 8, 1, 1, 4, 4, 4, 10, 10, 4, 5, 4, 5, 4};
        verifyRanks("index", expected, true);
    }

    @Test
    public void verifyMinRanksAtHealthLevel() {
        var expected = new int[]{2, 2, 6, 9, 2, 1, 8, 6, 6, 6, 15, 8, 4, 7, 11, 11};
        verifyRanks("health_improvement", expected, true);
    }

    @Test
    public void verifyMinRanksAtFinancesLevel() {
        var expected = new int[]{6, 1, 4, 5, 1, 3, 3, 8, 10, 14, 9, 4, 16, 6, 4, 12};
        verifyRanks("finances", expected, true);
    }

    @Test
    public void verifyMinRanksAtSatisfactionLevel() {
        var expected = new int[]{15, 1, 7, 14, 3, 5, 1, 10, 1, 14, 5, 6, 9, 1, 5, 1};
        verifyRanks("satisfaction", expected, true);
    }
}
