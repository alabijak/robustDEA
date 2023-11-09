package put.dea.vdea.hierarchical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class HierarchicalVDEAPreferenceRelationsTest extends HierarchicalVDEATestBase {
    @Test
    public void verifyPossiblePreferencesAtIndexLevel() {
        var expected = List.of(
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, false, false, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, false, false, false, false, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, false, false, false, false, true, true, true, true, false, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true)
        );
        verifyPreferences("index", expected, true);
    }

    private void verifyPreferences(String hierarchyLevel, List<List<Boolean>> expected, boolean possible) {
        var preferenceRelations = new HierarchicalVDEAPreferenceRelations();
        List<List<Boolean>> actual;
        List<Boolean> firstRow;
        if (possible) {
            actual = preferenceRelations.checkPossiblePreferenceForAll(data, hierarchyLevel);
            firstRow = IntStream.range(0, data.getDmuCount())
                    .mapToObj(dmuIdx ->
                            preferenceRelations.isPossiblyPreferred(data, 0, dmuIdx, hierarchyLevel))
                    .toList();
        } else {
            actual = preferenceRelations.checkNecessaryPreferenceForAll(data, hierarchyLevel);
            firstRow = IntStream.range(0, data.getDmuCount())
                    .mapToObj(dmuIdx -> preferenceRelations.isNecessarilyPreferred(data, 0, dmuIdx, hierarchyLevel))
                    .toList();
        }

        Assertions.assertEquals(actual.size(), expected.size());
        Assertions.assertIterableEquals(expected, actual);
        Assertions.assertIterableEquals(expected.get(0), firstRow);
    }

    @Test
    public void verifyPossiblePreferencesAtHealthLevel() {
        var expected = List.of(
                List.of(true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, true, false, false, true, true, true, true, true, true, false, true, true, true),
                List.of(false, false, false, true, false, false, false, false, true, true, true, true, false, true, true, true),
                List.of(true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, true, false, false, true, false, true, true, true, true, false, true, true, true),
                List.of(false, false, true, true, false, false, true, true, true, true, true, true, false, true, true, true),
                List.of(false, false, true, true, false, false, true, true, true, true, true, true, false, true, true, true),
                List.of(false, false, true, true, false, false, true, true, true, true, true, true, false, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true),
                List.of(false, false, true, true, false, false, true, false, true, true, true, true, false, true, true, true),
                List.of(false, false, true, true, true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, true, false, false, true, false, true, true, true, true, false, true, true, true),
                List.of(false, false, false, true, false, false, false, false, true, true, true, false, false, false, true, true),
                List.of(false, false, false, true, false, false, true, false, true, true, true, false, false, false, true, true)
        );
        verifyPreferences("health_improvement", expected, true);
    }

    @Test
    public void verifyPossiblePreferencesAtFinancesLevel() {
        var expected = List.of(
                List.of(true, false, false, true, false, false, true, true, true, true, true, true, true, true, false, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, false, true, false, false, true, true, true, true, true, false, true, false, false, true),
                List.of(true, false, false, false, false, false, false, true, true, true, true, false, true, false, false, true),
                List.of(false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, true),
                List.of(true, false, false, false, false, false, false, true, true, true, true, false, true, true, false, true),
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false),
                List.of(true, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true),
                List.of(true, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, true, true, true, false, true, false, false, true)
        );
        verifyPreferences("finances", expected, true);
    }

    @Test
    public void verifyPossiblePreferencesAtSatisfactionLevel() {
        var expected = List.of(
                List.of(true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, true, true, false, true, false, true, true, true, true, true, true, false),
                List.of(true, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, false),
                List.of(true, true, true, true, true, true, false, true, false, true, true, true, true, false, true, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, false, true, true, false, false, true, false, true, false, true, true, false, false, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(true, true, true, true, true, true, false, true, false, true, true, true, true, true, true, false),
                List.of(true, true, true, true, false, true, false, true, false, true, true, true, true, true, true, false),
                List.of(true, false, false, true, false, true, false, true, false, true, true, false, true, false, true, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, false, true, false, true, true, true, true, false, true, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
        );
        verifyPreferences("satisfaction", expected, true);
    }

    @Test
    public void verifyNecessaryPreferencesAtIndexLevel() {
        var expected = List.of(
                List.of(true, false, false, true, false, false, false, false, false, true, true, false, false, false, false, false),
                List.of(true, true, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, true, false, false, false, false, false, true, true, false, false, false, false, false),
                List.of(false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false),
                List.of(true, false, true, true, true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, false, true, false, false, true, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true)
        );
        verifyPreferences("index", expected, false);
    }

    @Test
    public void verifyNecessaryPreferencesAtHealthLevel() {
        var expected = List.of(
                List.of(true, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, true, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, true, false, false, false, false, false, false, true, false, false, false, true, true),
                List.of(false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false),
                List.of(false, false, true, true, true, false, true, true, true, true, true, true, false, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, false, true, false, false, false, true, false, false, false, true, false),
                List.of(false, false, false, true, false, false, true, true, false, false, true, true, false, true, true, true),
                List.of(false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, true),
                List.of(false, false, true, true, false, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true)
        );
        verifyPreferences("health_improvement", expected, false);
    }

    @Test
    public void verifyNecessaryPreferencesAtFinancesLevel() {
        var expected = List.of(
                List.of(true, false, false, false, false, false, false, false, false, true, false, false, true, false, false, true),
                List.of(true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, false, false, false, false, true, true, true, true, false, true, false, false, true),
                List.of(false, false, false, true, false, false, false, false, true, true, true, false, true, false, false, true),
                List.of(true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, true, false, true, true, true, true, true, true, true, false, true),
                List.of(false, false, false, false, false, false, true, false, true, true, true, false, true, false, false, true),
                List.of(false, false, false, false, false, false, false, true, false, true, false, false, true, false, false, true),
                List.of(false, false, false, false, false, false, false, false, true, true, false, false, true, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false),
                List.of(false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, true),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false),
                List.of(false, false, false, false, false, false, false, true, true, true, false, false, true, true, false, true),
                List.of(true, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true)
        );
        verifyPreferences("finances", expected, false);
    }

    @Test
    public void verifyNecessaryPreferencesAtSatisfactionLevel() {
        var expected = List.of(
                List.of(true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                List.of(true, true, true, true, false, false, false, true, false, true, false, false, true, false, false, false),
                List.of(true, false, true, true, false, false, false, true, false, true, false, false, true, false, false, false),
                List.of(true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false),
                List.of(true, false, false, true, true, false, false, false, false, true, false, true, true, false, false, false),
                List.of(true, false, false, true, false, true, false, true, false, true, false, false, false, false, false, false),
                List.of(true, false, true, true, false, true, true, true, false, true, true, true, true, false, true, false),
                List.of(true, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false),
                List.of(true, false, true, true, true, true, false, true, true, true, true, true, true, false, true, false),
                List.of(false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(true, false, false, true, false, false, false, true, false, true, true, false, false, false, false, false),
                List.of(true, false, false, true, false, false, false, false, false, true, false, true, true, false, false, false),
                List.of(true, false, false, true, false, false, false, false, false, true, false, false, true, false, false, false),
                List.of(true, false, false, true, false, true, false, true, false, true, false, false, true, true, true, false),
                List.of(true, false, false, true, false, false, false, true, false, true, false, false, false, false, true, false),
                List.of(true, false, true, true, true, true, false, true, false, true, true, true, true, false, true, true)
        );
        verifyPreferences("satisfaction", expected, false);
    }
}
