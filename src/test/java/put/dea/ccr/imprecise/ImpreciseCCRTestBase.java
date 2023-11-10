package put.dea.ccr.imprecise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import put.dea.weightConstraints.Constraint;
import put.dea.weightConstraints.ConstraintOperator;

import java.util.List;
import java.util.Map;

import static put.dea.TestUtils.transposeArray;

public abstract class ImpreciseCCRTestBase {

    protected static CCRImpreciseProblemData data;

    @BeforeAll
    public static void initializeData() {
        var cost = new double[]{7.2, 4.8, 5, 7.2, 9.6, 1.07, 1.76, 3.2, 6.72, 2.4, 2.88, 6.9, 3.2, 4, 3.68, 6.88, 8, 6.3, 0.94, 0.16, 2.81, 3.8, 1.25, 1.37, 3.63, 5.3, 4};
        var reputation = new double[]{15, 7, 23, 16, 24, 3, 8, 17, 9, 2, 18, 10, 25, 19, 11, 20, 1, 21, 12, 5, 26, 13, 27, 14, 4, 22, 6};
        var capacityMin = new double[]{50, 60, 40, 1, 45, 1, 4, 10, 9, 5, 25, 10, 8, 20, 40, 75, 10, 9, 10, 1, 25, 0.8, 2, 1, 8, 65, 190};
        var capacityMax = new double[]{65, 70, 50, 3, 55, 2, 5, 20, 12, 8, 35, 15, 12, 35, 55, 85, 18, 15, 13, 4, 30, 1.2, 4, 5, 12, 80, 220};
        var velocity = new double[]{1.35, 1.1, 1.27, 0.66, 0.05, 0.3, 1, 1, 1.1, 1, 0.9, 0.15, 1.2, 1.2, 1, 1, 2, 1, 0.3, 0.8, 1.7, 1, 0.5, 0.5, 1, 1.25, 0.75};

        var minInputs = transposeArray(new double[][]{cost, reputation});
        var maxInputs = transposeArray(new double[][]{cost, reputation});
        var minOutputs = transposeArray(new double[][]{capacityMin, velocity});
        var maxOutputs = transposeArray(new double[][]{capacityMax, velocity});

        data = new CCRImpreciseProblemData(minInputs, minOutputs, maxInputs, maxOutputs,
                List.of("cost", "reputation"),
                List.of("capacity", "velocity"));
        data.getImpreciseInformation().getOrdinalFactors().add("reputation");
    }


    protected void addWeightConstraints() {
        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ,
                0,
                Map.of("velocity", 1.0, "capacity", -5.0)));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.LEQ,
                0,
                Map.of("velocity", 1.0, "capacity", -15.0)));
    }

    @BeforeEach
    public void removeWeightConstraints() {
        data.getWeightConstraints().clear();
    }

}
