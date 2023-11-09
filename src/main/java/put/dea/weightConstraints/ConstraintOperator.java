package put.dea.weightConstraints;

public enum ConstraintOperator {
    EQ("="),
    LEQ("<="),
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
