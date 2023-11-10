package put.dea.robustness;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

class ExtremeRanksBase {
    public MPVariable[] createBinaryVariablesAndObjective(MPSolver model, int dmuCount, int subjectDmuIdx) {
        var binVariables = model.makeBoolVarArray(dmuCount);
        var objective = model.objective();
        for (var b : binVariables)
            objective.setCoefficient(b, 1);
        objective.setCoefficient(binVariables[subjectDmuIdx], 0);
        objective.setOffset(1);
        return binVariables;
    }

}
