package put.dea.robustness;

/**
 * Enum containing the available comparison operators for weight constraints
 */
public enum ConstraintOperator {
    /**
     * Equals
     */
    EQ("="),

    /**
     * Less than or equal to
     */
    LEQ("<="),

    /**
     * Greater than or equals to
     */
    GEQ(">=");

    private final String operator;

    ConstraintOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return this.operator;
    }
}
