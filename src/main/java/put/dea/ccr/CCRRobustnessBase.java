package put.dea.ccr;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import joinery.DataFrame;
import put.dea.common.RobustnessLPBase;

import java.util.List;

public abstract class CCRRobustnessBase extends RobustnessLPBase {

    public static final Double C = 1_000_000.0;

    protected List<MPVariable> createWeightVariablesWithEqualToOneConstraint(MPSolver model,
                                                                             DataFrame<Double> data,
                                                                             int subjectDmuIdx) {
        var weights = makeWeightVariables(model, data);
        var constraint = model.makeConstraint(1, 1);
        for (int i = 0; i < data.size(); i++)
            constraint.setCoefficient(weights.get(i), data.get(subjectDmuIdx, i));
        return weights;
    }

    protected List<MPVariable> makeWeightVariables(MPSolver model, DataFrame<Double> data) {
        return makeWeightVariables(model, data, MPSolver.infinity());
    }


}
