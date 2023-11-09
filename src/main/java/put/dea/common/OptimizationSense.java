package put.dea.common;

public enum OptimizationSense {
    MINIMIZE(false),
    MAXIMIZE(true);

    private final boolean maximize;

    OptimizationSense(boolean maximize) {
        this.maximize = maximize;
    }

    public boolean isMaximize() {
        return maximize;
    }
}
