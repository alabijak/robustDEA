package put.dea.ccr;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import put.dea.common.ExtremeEfficiency;
import put.dea.common.OptimizationSense;
import put.dea.common.ProblemData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CCRExtremeEfficiencies
        extends CCRRobustnessBase
        implements ExtremeEfficiency<ProblemData> {
    @Override
    public double maxEfficiency(ProblemData data, int subjectDmuIdx) {
        return findMaxOrSuperEfficiency(data, subjectDmuIdx, false);
    }

    @Override
    public double minEfficiency(ProblemData data, int subjectDmuIdx) {

        var model = makeModel(OptimizationSense.MINIMIZE);

        var inputWeights = createWeightVariablesWithEqualToOneConstraint(model,
                data.getInputData(), subjectDmuIdx);

        var outputWeights = makeWeightVariables(model, data.getOutputData(), MPSolver.infinity());
        var binVariables = model.makeBoolVarArray(data.getDmuCount());

        var objective = model.objective();
        for (int i = 0; i < data.getOutputCount(); i++)
            objective.setCoefficient(outputWeights.get(i), data.getOutputData().get(subjectDmuIdx, i));

        for (int k = 0; k < data.getDmuCount(); k++) {
            var constraint = model.makeConstraint(-MPSolver.infinity(), C);
            constraint.setCoefficient(binVariables[k], C);
            for (int i = 0; i < inputWeights.size(); i++)
                constraint.setCoefficient(inputWeights.get(i), data.getInputData().get(k, i));
            for (int i = 0; i < outputWeights.size(); i++)
                constraint.setCoefficient(outputWeights.get(i), -data.getOutputData().get(k, i));
        }

        var constraint = model.makeConstraint(1, MPSolver.infinity());
        Arrays.stream(binVariables).forEach(b -> constraint.setCoefficient(b, 1));

        addCustomWeightConstraints(data, model);
        return getModelResult(model);
    }

    private double findMaxOrSuperEfficiency(ProblemData data, int subjectDmuIdx, boolean superEfficiency) {
        return createMaxOrSuperEfficiencyModel(data, subjectDmuIdx, superEfficiency).objective().value();
    }

    private MPSolver createMaxOrSuperEfficiencyModel(ProblemData data, int subjectDmuIdx, boolean superEfficiency) {

        var model = makeModel(OptimizationSense.MAXIMIZE);
        var inputVariables = createWeightVariablesWithEqualToOneConstraint(model, data.getInputData(), subjectDmuIdx);
        var outputVariables = makeWeightVariables(model, data.getOutputData());

        for (int i = 0; i < outputVariables.size(); i++)
            model.objective().setCoefficient(outputVariables.get(i), data.getOutputData().get(subjectDmuIdx, i));

        var constraint = model.makeConstraint(1, 1);
        setConstraintCoefficients(constraint, inputVariables, data.getInputData().row(subjectDmuIdx), false);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (!superEfficiency || k != subjectDmuIdx) {
                constraint = model.makeConstraint(0, MPSolver.infinity());
                setConstraintCoefficients(constraint, inputVariables, data.getInputData().row(k), false);
                setConstraintCoefficients(constraint, outputVariables, data.getOutputData().row(k), true);
            }
        }
        addCustomWeightConstraints(data, model);
        solveModel(model);
        return model;
    }

    private void setConstraintCoefficients(MPConstraint constraint, List<MPVariable> variables,
                                           List<Double> coefficients,
                                           boolean negative) {
        int sign = negative ? -1 : 1;
        IntStream.range(0, variables.size()).forEach(idx ->
                constraint.setCoefficient(variables.get(idx), sign * coefficients.get(idx))
        );
    }

    public List<Double> superEfficiencyForAll(ProblemData data) {
        return IntStream.range(0, data.getDmuCount())
                .mapToDouble(idx -> superEfficiency(data, idx))
                .boxed().toList();
    }

    public double superEfficiency(ProblemData data, int subjectDmuIdx) {
        return findMaxOrSuperEfficiency(data, subjectDmuIdx, true);
    }

}
