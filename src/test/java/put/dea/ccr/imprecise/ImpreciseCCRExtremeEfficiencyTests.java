package put.dea.ccr.imprecise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImpreciseCCRExtremeEfficiencyTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyMaxEfficiency() {
        var extremeEfficiency = new ImpreciseCCRExtremeEfficiency(1.1, 0.01);
        var maxEfficiency = extremeEfficiency.maxEfficiencyForAll(data);
        var expectedValues = new double[]{
                0.487463, 0.767216, 0.284882, 0.188482, 0.114476, 0.89074, 0.749684,
                0.359152, 0.479496, 1, 0.32883, 0.085284, 0.215702, 0.367308, 0.603594,
                0.305116, 1, 0.22474, 0.333356, 1, 0.319469, 0.425223, 0.128088, 0.250636, 1,
                0.346613, 1
        };
        Assertions.assertArrayEquals(expectedValues, maxEfficiency.stream().mapToDouble(x -> x).toArray(), 1e-6);
    }

    @Test
    public void verifyMaxEfficiencyWithWeightConstraints() {
        addWeightConstraints();
        var extremeEfficiency = new ImpreciseCCRExtremeEfficiency(1.1, 0.01);
        var maxEfficiency = extremeEfficiency.maxEfficiencyForAll(data);
        var expectedValues = new double[]{
                0.23322, 0.39074, 0.252716, 0.035156, 0.112771, 0.779398,
                0.215203, 0.202671, 0.106397, 1, 0.305657, 0.058544, 0.15684,
                0.246263, 0.370208, 0.279226, 1, 0.090419, 0.333356, 1,
                0.319469, 0.082285, 0.128088, 0.157855, 1, 0.346613, 1
        };
        Assertions.assertArrayEquals(expectedValues,
                maxEfficiency.stream().mapToDouble(x -> x).toArray(),
                1e-6);
    }

    @Test
    public void verifyMinEfficiency() {
        ImpreciseCCRExtremeEfficiency extremeEfficiency = new ImpreciseCCRExtremeEfficiency(1.1, 0.01);
        var minEff = extremeEfficiency.minEfficiencyForAll(data);
        var expectedMinEff = new double[]{
                0.00366, 0.004392, 0.002928, 0.000073, 0.00025, 0.000556,
                0.000293, 0.000732, 0.000659, 0.002778, 0.00183, 0.000732,
                0.000586, 0.001464, 0.002928, 0.005, 0.023232, 0.000659,
                0.000732, 0.000556, 0.00183, 0.000059, 0.000146, 0.000073,
                0.004444, 0.004758, 0.00375
        };
        Assertions.assertArrayEquals(expectedMinEff,
                minEff.stream().mapToDouble(x -> x).toArray(),
                1e-6);
    }

    @Test
    public void verifyMinEfficiencyWithWeightConstraints() {
        addWeightConstraints();
        ImpreciseCCRExtremeEfficiency extremeEfficiency = new ImpreciseCCRExtremeEfficiency(1.1, 0.01);
        var minEff = extremeEfficiency.minEfficiencyForAll(data);
        var expectedMinEff = new double[]{
                0.004085, 0.004715, 0.003336, 0.00031, 0.003186, 0.000893, 0.000648,
                0.00108, 0.001044, 0.003571, 0.002123, 0.000774, 0.001008, 0.001871,
                0.003239, 0.005758, 0.045685, 0.001008, 0.000828, 0.001786, 0.002411,
                0.000417, 0.000324, 0.000252, 0.004643, 0.005128, 0.041927
        };
        Assertions.assertArrayEquals(expectedMinEff,
                minEff.stream().mapToDouble(x -> x).toArray(),
                1e-6);
    }
}
