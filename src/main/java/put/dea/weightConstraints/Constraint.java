package put.dea.weightConstraints;

import java.util.HashMap;
import java.util.Map;

public class Constraint {
    private final ConstraintOperator operator;
    private final double rhs;

    private Map<String, Double> elements = new HashMap<>();

    public Constraint(ConstraintOperator operator, double rhs, Map<String, Double> elements) {
        this(operator, rhs);
        this.elements = elements;
    }

    public Constraint(ConstraintOperator operator, double rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    public ConstraintOperator getOperator() {
        return operator;
    }

    public double getRhs() {
        return rhs;
    }

    public Map<String, Double> getElements() {
        return elements;
    }

}
