package put.dea.vdea.imprecise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ImpreciseVDEARanksTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyMinRank() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAExtremeRanks(1.0001, 1e-8, 1);
        var result = efficiencies.minRankForAll(data);
        var expectedResult = List.of(3, 1, 3, 11, 1, 3, 3, 3, 1, 2, 1, 1);
        Assertions.assertIterableEquals(expectedResult, result);
    }

    @Test
    public void verifyMaxRank() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAExtremeRanks(1.0001, 1e-8, 1);
        var result = efficiencies.maxRankForAll(data);
        var expectedResult = List.of(11, 8, 11, 12, 10, 12, 11, 11, 11, 11, 10, 9);
        Assertions.assertIterableEquals(expectedResult, result);
    }
}
