package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ImpreciseCCRPreferenceRelationsTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyPossiblePreferences() {
        var expectedNotPossiblyPreffered = List.of(
                Set.of(26),
                Set.of(26),
                Set.of(1, 25, 26),
                Set.of(0, 1, 6, 8, 9, 14, 16, 18, 21, 24, 26),
                Set.of(0, 1, 15, 25, 26),
                Set.of(9),
                Set.of(1, 26),
                Set.of(1, 14, 26),
                Set.of(1, 26),
                Set.of(),
                Set.of(26),
                Set.of(1, 16, 26),
                Set.of(0, 1, 2, 10, 13, 14, 15, 25, 26),
                Set.of(1, 14, 26),
                Set.of(26),
                Set.of(26),
                Set.of(),
                Set.of(0, 1, 10, 13, 14, 15, 26),
                Set.of(26),
                Set.of(),
                Set.of(26),
                Set.of(1, 6, 9, 14, 16, 24, 26),
                Set.of(0, 1, 2, 10, 13, 14, 15, 18, 20, 25, 26),
                Set.of(1, 14, 18, 26),
                Set.of(),
                Set.of(26),
                Set.of()
        );
        addWeightConstraints();
        ImpreciseCCRPreferenceRelations preferenceRelations =
                new ImpreciseCCRPreferenceRelations(1.1, 0.01);
        var actualRelations = preferenceRelations.checkPossiblePreferenceForAll(data);

        verifyRelationsCount(actualRelations, 650);
        for (int row = 0; row < actualRelations.size(); row++) {
            for (int col = 0; col < actualRelations.size(); col++) {
                var value = actualRelations.get(row).get(col);
                if (expectedNotPossiblyPreffered.get(row).contains(col))
                    Assertions.assertFalse(value);
                else
                    Assertions.assertTrue(value);
            }
        }
    }

    private void verifyRelationsCount(List<List<Boolean>> relations, int expectedCount) {
        var relationsCount = relations.stream().map(x -> x.stream().map(y -> y ? 1 : 0)
                        .reduce(Integer::sum))
                .map(Optional::orElseThrow)
                .reduce(Integer::sum)
                .orElseThrow();
        Assertions.assertEquals(expectedCount, relationsCount);
    }

    @Test
    public void verifyNecessaryPreferences() {
        var expectedNecessaryRelations = List.of(
                Set.of(0, 3, 4, 12, 17, 22),
                Set.of(1, 2, 3, 4, 6, 7, 8, 11, 12, 13, 17, 21, 22, 23),
                Set.of(2, 12, 22),
                Set.of(3),
                Set.of(4),
                Set.of(5),
                Set.of(3, 6, 21),
                Set.of(7),
                Set.of(3, 8),
                Set.of(3, 5, 9, 21),
                Set.of(10, 12, 17, 22),
                Set.of(11),
                Set.of(12),
                Set.of(12, 13, 17, 22),
                Set.of(3, 7, 12, 13, 14, 17, 21, 22, 23),
                Set.of(4, 12, 15, 17, 22),
                Set.of(3, 11, 16, 21),
                Set.of(17),
                Set.of(3, 18, 22, 23),
                Set.of(19),
                Set.of(20, 22),
                Set.of(3, 21),
                Set.of(22),
                Set.of(23),
                Set.of(3, 21, 24),
                Set.of(2, 4, 12, 22, 25),
                Set.of(0, 1, 2, 3, 4, 6, 7, 8, 10, 11, 12, 13, 14, 15, 17, 18, 20, 21, 22, 23, 25, 26)
        );
        addWeightConstraints();
        ImpreciseCCRPreferenceRelations preferenceRelations =
                new ImpreciseCCRPreferenceRelations(1.1, 0.01);
        var actualRelations = preferenceRelations.checkNecessaryPreferenceForAll(data);

        verifyRelationsCount(actualRelations, 106);

        for (int row = 0; row < actualRelations.size(); row++) {
            for (int col = 0; col < actualRelations.size(); col++) {
                var value = actualRelations.get(row).get(col);
                if (expectedNecessaryRelations.get(row).contains(col))
                    Assertions.assertTrue(value);
                else
                    Assertions.assertFalse(value);
            }
        }
    }
}
