package put.dea.robustness;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing custom weight constraint
 */
public class Constraint {
    private final ConstraintOperator operator;
    private final double rhs;

    private Map<String, Double> elements = new HashMap<>();

    /**
     * Creates the constraint with given operator, RHS value and elements
     *
     * @param operator constraint operator
     * @param rhs      RHS value
     * @param elements {@link Map} with factor names as keys and constraint coefficients as values
     */
    public Constraint(ConstraintOperator operator, double rhs, Map<String, Double> elements) {
        this(operator, rhs);
        this.elements = elements;
    }

    /**
     * Creates the constraint with given operator and RHS value
     *
     * @param operator constraint operator
     * @param rhs      RHS value
     */
    public Constraint(ConstraintOperator operator, double rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    /**
     * gets get constraint operator
     *
     * @return operator
     */
    public ConstraintOperator getOperator() {
        return operator;
    }

    /**
     * get the constraint's RHS
     *
     * @return RHS value
     */
    public double getRhs() {
        return rhs;
    }

    /**
     * gets the factors coefficients
     *
     * @return {@link Map} with factor names as keys and constraint coefficients as values
     */
    public Map<String, Double> getElements() {
        return elements;
    }

}
