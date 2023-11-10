package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ImpreciseCCRExtremeRanksTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyMinRankWithWeightConstraints() {
        addWeightConstraints();
        var impreciseRanks = new ImpreciseCCRExtremeRanks(1.1, 0.01);
        var actualMinRanks = impreciseRanks.minRankForAll(data);
        var expectedMinRanks = List.of(3, 2, 6, 15, 9, 2, 4, 5, 6, 1, 3, 6, 12, 4, 2, 3, 1, 14,
                3, 1, 3, 10, 14, 6, 1, 2, 1);
        Assertions.assertIterableEquals(expectedMinRanks, actualMinRanks);
    }

    @Test
    public void verifyMaxRankWithWeightConstraints() {
        addWeightConstraints();
        var impreciseRanks = new ImpreciseCCRExtremeRanks(1.1, 0.01);
        var actualMaxRanks = impreciseRanks.maxRankForAll(data);
        var expectedMaxRanks = List.of(16, 7, 24, 27, 25, 25, 20, 24, 25, 20, 23, 26, 26,
                23, 12, 20, 24, 26, 22, 21, 26, 26, 27, 26, 20, 22, 6);
        Assertions.assertIterableEquals(expectedMaxRanks, actualMaxRanks);
    }
}
