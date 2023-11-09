package put.dea.ccr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CCRPreferenceRelationsTest extends CCRTestBase {
    private static CCRPreferenceRelations preferenceRelations;

    @BeforeAll
    public static void initializeExtremeEfficiencies() {
        preferenceRelations = new CCRPreferenceRelations();
    }

    @Test
    public void necessaryPreferenceWithoutWeightConstraintsTest() {
        var expected = List.of(
                List.of(true, false, true, false, true, true, false, true, false, true, true),
                List.of(false, true, true, false, false, true, false, true, false, true, true),
                List.of(false, false, true, false, false, false, false, false, false, true, false),
                List.of(false, false, true, true, false, true, false, true, false, true, true),
                List.of(false, false, true, false, true, true, false, true, false, true, true),
                List.of(false, false, false, false, false, true, false, false, false, false, true),
                List.of(false, false, true, false, false, true, true, true, false, true, true),
                List.of(false, false, false, false, false, false, false, true, false, false, false),
                List.of(false, false, false, false, false, true, false, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, true, false),
                List.of(false, false, false, false, false, false, false, false, false, false, true)
        );
        var actual = preferenceRelations.checkNecessaryPreferenceForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void necessaryPreferenceWithWeightConstraintsTest() {
        var expected = List.of(
                List.of(true, false, true, false, true, true, false, true, false, true, true),
                List.of(false, true, true, false, false, true, false, true, false, true, true),
                List.of(false, false, true, false, false, false, false, true, false, true, true),
                List.of(false, false, true, true, false, true, false, true, false, true, true),
                List.of(false, false, true, false, true, true, false, true, false, true, true),
                List.of(false, false, false, false, false, true, false, false, false, false, true),
                List.of(false, false, true, false, false, true, true, true, false, true, true),
                List.of(false, false, false, false, false, false, false, true, false, false, true),
                List.of(false, false, false, false, false, true, false, true, true, true, true),
                List.of(false, false, false, false, false, false, false, true, false, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, true)
        );
        addWeightConstraints();
        var actual = preferenceRelations.checkNecessaryPreferenceForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void possiblePreferenceWithoutWeightConstraintsTest() {
        var expected = List.of(
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, false, false, true, false, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, false, false, true, false, true, false, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, false, false, true, false, true, false, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, true, false, true, true, true, true),
                List.of(false, false, true, false, false, false, false, true, false, true, true)
        );
        var actual = preferenceRelations.checkPossiblePreferenceForAll(data);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void possiblePreferenceWithWeightConstraintsTest() {
        var expected = List.of(
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, false, false, true, false, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, true, false, false, true, false, true, false, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, true, false, true, false, false, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, false, false, true, false, true, true, true, true),
                List.of(false, false, false, false, false, false, false, false, false, false, true)
        );
        addWeightConstraints();
        var actual = preferenceRelations.checkPossiblePreferenceForAll(data);
        assertIterableEquals(expected, actual);
    }
}
