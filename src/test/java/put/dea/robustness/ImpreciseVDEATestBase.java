package put.dea.robustness;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.Map;

import static put.dea.robustness.TestUtils.transposeArray;

public class ImpreciseVDEATestBase {
    protected static ImpreciseVDEAProblemData data;

    @BeforeAll
    public static void initializeData() {
        double[] minI1 = new double[]{24, 17, 23, 45, 15, 60, 35, 31, 28, 47, 50, 35};
        double[] maxI1 = new double[]{24, 19, 25, 51, 17, 65, 42, 31, 30, 50, 53, 38};

        double[] i2 = new double[]{8, 2, 7, 9, 1, 7, 8, 7, 3, 5, 6, 4};

        var minI3 = new double[]{154, 124, 142, 170, 147, 252, 232, 205, 231, 258, 301, 213};
        var maxI3 = new double[]{161, 131, 150, 178, 155, 255, 235, 206, 244, 268, 306, 250};

        var minI4 = new double[]{98, 72, 85, 135, 58, 85, 98, 85, 72, 72, 78, 60};
        var maxI4 = new double[]{100, 76, 90, 148, 62, 95, 100, 85, 76, 75, 80, 65};

        var minO1 = new double[]{90, 170, 172, 120, 96, 218, 190, 130, 195, 240, 280, 250};
        var maxO1 = new double[]{95, 182, 180, 140, 102, 255, 200, 140, 215, 250, 292, 255};

        var minO2 = new double[]{85, 80, 60, 48, 69, 85, 83, 72, 105, 97, 142, 113};
        var maxO2 = new double[]{88, 85, 63, 50, 73, 90, 88, 75, 110, 100, 147, 120};

        var minInputs = transposeArray(new double[][]{minI1, i2, minI3, minI4});
        var maxInputs = transposeArray(new double[][]{maxI1, i2, maxI3, maxI4});
        var minOutputs = transposeArray(new double[][]{minO1, minO2});
        var maxOutputs = transposeArray(new double[][]{maxO1, maxO2});

        data = new ImpreciseVDEAProblemData(minInputs, minOutputs, maxInputs, maxOutputs,
                List.of("i1", "i2", "i3", "i4"),
                List.of("o1", "o2")
        );
        data.getImpreciseInformation().getOrdinalFactors().add("i2");
        addFunctionShapes();
    }

    private static void addFunctionShapes() {
        data.setLowerFunctionShape("i1", List.of(
                new Pair<>(10.0, 1.0),
                new Pair<>(25.0, 0.4),
                new Pair<>(40.0, 0.1),
                new Pair<>(70.0, 0.0)
        ));

        data.setUpperFunctionShape("i1", List.of(
                new Pair<>(10.0, 1.0),
                new Pair<>(25.0, 0.5),
                new Pair<>(40.0, 0.25),
                new Pair<>(70.0, 0.0)
        ));


        data.setLowerFunctionShape("i3", List.of(
                new Pair<>(100.0, 1.0),
                new Pair<>(150.0, 0.9),
                new Pair<>(300.0, 0.8),
                new Pair<>(400.0, 0.0)
        ));


        data.setUpperFunctionShape("i3", List.of(
                new Pair<>(100.0, 1.0),
                new Pair<>(150.0, 0.95),
                new Pair<>(300.0, 0.9),
                new Pair<>(400.0, 0.0)
        ));

        data.setLowerFunctionShape("i4", List.of(
                new Pair<>(50.0, 1.0),
                new Pair<>(100.0, 0.5),
                new Pair<>(125.0, 0.25),
                new Pair<>(150.0, 0.0)
        ));

        data.setUpperFunctionShape("i4", List.of(
                new Pair<>(50.0, 1.0),
                new Pair<>(100.0, 0.6),
                new Pair<>(125.0, 0.4),
                new Pair<>(150.0, 0.0)
        ));

        data.setLowerFunctionShape("o1", List.of(
                new Pair<>(70.0, 0.0),
                new Pair<>(180.0, 0.084615385),
                new Pair<>(200.0, 0.1),
                new Pair<>(320.0, 1.0)
        ));

        data.setUpperFunctionShape("o1", List.of(
                new Pair<>(70.0, 0.0),
                new Pair<>(180.0, 0.1),
                new Pair<>(200.0, 0.228571429),
                new Pair<>(320.0, 1.0)
        ));

        data.setLowerFunctionShape("o2", List.of(
                new Pair<>(40.0, 0.0),
                new Pair<>(60.0, 0.0),
                new Pair<>(100.0, 0.3),
                new Pair<>(180.0, 1.0)
        ));

        data.setUpperFunctionShape("o2", List.of(
                new Pair<>(40.0, 0.0),
                new Pair<>(60.0, 0.1),
                new Pair<>(100.0, 0.3),
                new Pair<>(180.0, 1.0)
        ));
    }

    public void addWeightConstraints() {
        for (int i = 1; i < 5; i++) {
            data.getWeightConstraints().add(new Constraint(
                    ConstraintOperator.GEQ, 0.0833, Map.of("i" + i, 1.0)
            ));
            data.getWeightConstraints().add(new Constraint(
                    ConstraintOperator.LEQ, 0.25, Map.of("i" + i, 1.0)
            ));
        }
        data.getWeightConstraints().add(new Constraint(
                ConstraintOperator.GEQ, 0.0833, Map.of("o1", 1.0)
        ));
        data.getWeightConstraints().add(new Constraint(
                ConstraintOperator.GEQ, 0.0833, Map.of("o2", 1.0)
        ));

        data.getWeightConstraints().add(new Constraint(
                ConstraintOperator.LEQ, 0.25, Map.of("o1", 1.0)
        ));
        data.getWeightConstraints().add(new Constraint(
                ConstraintOperator.LEQ, 0.25, Map.of("o2", 1.0)
        ));
    }
}
