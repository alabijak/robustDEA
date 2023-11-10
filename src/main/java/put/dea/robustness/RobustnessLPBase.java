package put.dea.robustness;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import joinery.DataFrame;

import java.util.List;

abstract class RobustnessLPBase {
    public RobustnessLPBase() {
        Loader.loadNativeLibraries();
    }

    protected MPSolver makeModel(OptimizationSense sense) {
        MPSolver model = MPSolver.createSolver("SCIP");
        if (model == null)
            throw new RuntimeException("Cannot create solver");
        model.objective().setOptimizationDirection(sense.isMaximize());
        return model;
    }

    protected double getModelResult(MPSolver model) {
        solveModel(model);
        return model.objective().value();
    }

    protected void solveModel(MPSolver model) {
        var result = model.solve();
        if (result != MPSolver.ResultStatus.OPTIMAL)
            throw new RuntimeException("Cannot find optimal solution. Model is " + result.name());
    }

    protected void addCustomWeightConstraints(ProblemData data,
                                              MPSolver model) {
        for (var weightConstraint : data.getWeightConstraints()) {
            var modelConstraint = model.makeConstraint();
            setWeightConstraintBounds(weightConstraint, modelConstraint);
            for (var element : weightConstraint.getElements().entrySet()) {
                MPVariable variable = model.lookupVariableOrNull(element.getKey());
                if (variable == null)
                    throw new IllegalArgumentException("Given input or output name does not exist");
                modelConstraint.setCoefficient(variable, element.getValue());
            }
        }
    }

    protected void setWeightConstraintBounds(Constraint weightConstraint, MPConstraint constraint) {
        switch (weightConstraint.getOperator()) {
            case EQ:
                constraint.setBounds(weightConstraint.getRhs(), weightConstraint.getRhs());
                break;
            case GEQ:
                constraint.setLb(weightConstraint.getRhs());
                break;
            case LEQ:
                constraint.setUb(weightConstraint.getRhs());
                break;
        }
    }

    protected List<MPVariable> makeWeightVariables(MPSolver model, DataFrame<Double> data, double ub) {
        return data.columns().stream()
                .map(col -> model.makeNumVar(0, ub, col.toString()))
                .toList();
    }
}
