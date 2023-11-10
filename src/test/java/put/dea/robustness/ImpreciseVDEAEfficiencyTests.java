package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImpreciseVDEAEfficiencyTests extends ImpreciseVDEATestBase {
    @Test
    public void verifyMinEfficiency() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAExtremeEfficiencies(1.0001, 1e-8, 1);
        var result = efficiencies.minEfficiencyForAll(data);
        var expectedResult = new double[]{0.203426, 0.249884, 0.177925, 0.086898, 0.229919, 0.185595,
                0.172696, 0.183396, 0.259099, 0.250294, 0.360373, 0.318274


        };
        Assertions.assertArrayEquals(expectedResult, result.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }

    @Test
    public void verifyMaxEfficiency() {
        addWeightConstraints();
        var efficiencies = new ImpreciseVDEAExtremeEfficiencies(1.0001, 1e-8, 1);
        var result = efficiencies.maxEfficiencyForAll(data);
        var expectedResult = new double[]{0.707357, 0.792059, 0.73452, 0.572474, 0.807918, 0.735167, 0.701324,
                0.716005, 0.783581, 0.771226, 0.815474, 0.82806

        };
        Assertions.assertArrayEquals(expectedResult, result.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }
}
