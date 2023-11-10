package put.dea.vdea;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import put.dea.weightConstraints.Constraint;
import put.dea.weightConstraints.ConstraintOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VDEATestBase {
    protected static VDEAProblemData data;

    @BeforeAll
    public static void initialize() {
        var inputs = new double[][]{
                new double[]{2.026, 2.76, 0.92},
                new double[]{1.959, 2.381, 0.774},
                new double[]{2.223, 2.333, 0.643},
                new double[]{1.884, 1.823, 0.661},
                new double[]{1.511, 0.857, 0.487},
                new double[]{1.456, 1.33, 0.648},
                new double[]{1.903, 1.877, 0.596},
                new double[]{1.704, 1.73, 0.678},
                new double[]{1.708, 1.927, 0.657},
                new double[]{1.979, 1.508, 0.82},
                new double[]{1.652, 1.618, 0.592},
                new double[]{2.169, 1.863, 0.608},
                new double[]{1.634, 1.538, 0.786},
                new double[]{1.745, 2.117, 0.738},
                new double[]{1.594, 1.548, 0.602},
                new double[]{2.311, 1.538, 0.462},
                new double[]{1.962, 1.748, 0.557},
                new double[]{1.804, 1.59, 0.723},
                new double[]{1.567, 1.487, 0.601},
                new double[]{1.435, 1.198, 0.568}
        };
        var outputs = new double[][]{
                new double[]{1.0},
                new double[]{0.961},
                new double[]{0.905},
                new double[]{0.952},
                new double[]{0.952},
                new double[]{0.978},
                new double[]{0.956},
                new double[]{0.939},
                new double[]{0.968},
                new double[]{0.922},
                new double[]{0.981},
                new double[]{0.961},
                new double[]{0.979},
                new double[]{0.942},
                new double[]{0.957},
                new double[]{0.974},
                new double[]{0.948},
                new double[]{0.977},
                new double[]{0.937},
                new double[]{0.969},
        };

        data = new VDEAProblemData(inputs, outputs, List.of("i1", "i2", "i3"), List.of("o1"));
    }

    @BeforeEach
    public void clearBoundaries() {
        data.getUpperBounds().clear();
        data.getLowerBounds().clear();
    }

    @BeforeEach
    public void clearShapes() {
        data.setFunctionShapes(new HashMap<>());
    }

    protected void addInputOutputBoundaries() {
        data.getLowerBounds().put("i1", 0.6);
        data.getLowerBounds().put("i2", 0.0);
        data.getLowerBounds().put("i3", 0.0);
        data.getLowerBounds().put("o1", 0.89);

        data.getUpperBounds().put("i1", 2.5);
        data.getUpperBounds().put("i2", 3.0);
        data.getUpperBounds().put("i3", 1.0);
        data.getUpperBounds().put("o1", 1.0);
    }

    protected void addFunctionShapes() {
        data.setFunctionShape("i1",
                List.of(new Pair<>(0.6, 1.0),
                        new Pair<>(0.85, 0.95),
                        new Pair<>(2.0, 0.05),
                        new Pair<>(2.5, 0.0)));

        data.setFunctionShape("i2",
                List.of(new Pair<>(0.0, 1.0),
                        new Pair<>(2.0, 0.75),
                        new Pair<>(3.0, 0.0)));

        data.setFunctionShape("i3",
                List.of(new Pair<>(0.0, 1.0),
                        new Pair<>(0.35, 0.25),
                        new Pair<>(0.7, 0.05),
                        new Pair<>(1.0, 0.0)));

        data.setFunctionShape("o1",
                List.of(new Pair<>(0.89, 0.0),
                        new Pair<>(0.95, 0.1),
                        new Pair<>(0.97, 0.2),
                        new Pair<>(1.0, 1.0)));
    }

    @BeforeEach
    public void clearWeightConstraints() {
        data.getWeightConstraints().clear();
    }

    protected void addWeightConstraints() {
        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ, 0, Map.of("i1", 2.0, "i2", -1.0)
        ));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.LEQ, 0.5, Map.of("i1", 1.0)
        ));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.LEQ, 0.8, Map.of("i1", 1.0, "i2", 1.0, "i3", 1.0)
        ));
    }
}
