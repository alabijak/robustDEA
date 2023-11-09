package put.dea.vdea.imprecise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ImpreciseVDEAPreferenceRelationsTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyNecessaryPreferences() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAPreferenceRelations(1.0001, 1e-8, 1);
        var result = efficiencies.checkNecessaryPreferenceForAll(data);

        var expected = List.of(
                List.of(true, false, false, true, false, false, false, false, false, false, false, false),
                List.of(true, true, true, true, false, false, false, true, false, false, false, false),
                List.of(false, false, true, true, false, false, false, false, false, false, false, false),
                List.of(false, false, false, true, false, false, false, false, false, false, false, false),
                List.of(false, false, false, true, true, false, false, true, false, false, false, false),
                List.of(false, false, false, false, false, true, false, false, false, false, false, false),
                List.of(false, false, false, true, false, false, true, false, false, false, false, false),
                List.of(false, false, false, true, false, false, false, true, false, false, false, false),
                List.of(false, false, false, true, false, false, false, false, true, false, false, false),
                List.of(false, false, false, true, false, false, false, false, false, true, false, false),
                List.of(false, false, false, true, false, true, false, false, false, false, true, false),
                List.of(false, false, false, true, false, true, false, false, false, true, false, true)
        );

        Assertions.assertIterableEquals(expected, result);
    }

    @Test
    public void verifyPossiblePreferences() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAPreferenceRelations(1.0001, 1e-8, 1);
        var result = efficiencies.checkPossiblePreferenceForAll(data);

        var expected = List.of(
                List.of(true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, true, true, true, true, true, true, true, true),
                List.of(false, false, false, true, false, true, false, false, false, false, false, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, false, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, false, true, true, false, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, false),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true),
                List.of(true, true, true, true, true, true, true, true, true, true, true, true)
        );

        Assertions.assertIterableEquals(expected, result);
    }
}
