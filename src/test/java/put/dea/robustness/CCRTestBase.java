package put.dea.robustness;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CCRTestBase {


    protected static ProblemData data;

    @BeforeAll
    public static void initialize() {
        var inputs = new double[][]{
                new double[]{10.5, 36, 129.4, 7},
                new double[]{3.1, 19, 31.6, 7.9},
                new double[]{3.6, 32, 57.4, 10.5},
                new double[]{1.5, 12, 18, 3},
                new double[]{1.5, 10, 24, 4},
                new double[]{0.6, 12, 24, 3.9},
                new double[]{1.0, 15, 42.9, 2.5},
                new double[]{0.7, 10, 25.7, 1.9},
                new double[]{0.3, 6, 3.4, 1.2},
                new double[]{0.6, 6, 11.3, 2.7},
                new double[]{0.1, 10, 63.4, 3}
        };

        var outputs = new double[][]{
                new double[]{9.5, 129.7},
                new double[]{2.9, 31.3},
                new double[]{2.4, 21.1},
                new double[]{1.5, 18.8},
                new double[]{1.3, 16.2},
                new double[]{0.3, 4.2},
                new double[]{2, 23.6},
                new double[]{0.3, 4.2},
                new double[]{0.3, 6.2},
                new double[]{0.3, 3.5},
                new double[]{0.005, 0.61}
        };
        data = new ProblemData(inputs, outputs, List.of("i1", "i2", "i3", "i4"), List.of("o1", "o2"));
    }

    @BeforeEach
    public void removeWeightConstraints() {
        data.getWeightConstraints().clear();
    }

    protected void addWeightConstraints() {
        data.getWeightConstraints().clear();
        for (Constraint constraint : Arrays.asList(
                new Constraint(ConstraintOperator.GEQ, 0,
                        Map.of("i1", 1.0, "i3", -3.0)),
                new Constraint(ConstraintOperator.LEQ, 0,
                        Map.of("i1", -1.0, "i4", 5.0)),
                new Constraint(ConstraintOperator.LEQ, 0,
                        Map.of("i2", -1.0, "i3", 2.0)),
                new Constraint(ConstraintOperator.LEQ, 0,
                        Map.of("i2", -1.0, "i4", 5.0)),
                new Constraint(ConstraintOperator.GEQ, 0,
                        Map.of("o1", 1.0, "o2", -5.0)))) {
            data.addWeightConstraint(constraint);
        }
    }


}
