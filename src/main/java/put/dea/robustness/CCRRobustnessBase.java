package put.dea.robustness;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import joinery.DataFrame;

import java.util.List;

abstract class CCRRobustnessBase extends RobustnessLPBase {

    protected static final Double C = 1_000_000.0;

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
