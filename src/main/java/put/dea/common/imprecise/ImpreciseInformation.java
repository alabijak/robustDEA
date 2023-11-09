package put.dea.common.imprecise;

import joinery.DataFrame;
import put.dea.common.ProblemData;

import java.util.HashSet;
import java.util.Set;

public class ImpreciseInformation {
    private DataFrame<Double> maxInputs;
    private DataFrame<Double> maxOutputs;

    private final double tolerance;

    private Set<String> ordinalFactors = new HashSet<>();

    private final ProblemData data;

    public ImpreciseInformation(ProblemData data, double tolerance) {
        this.data = data;
        this.tolerance = tolerance;
    }

    public ImpreciseInformation(ProblemData data, DataFrame<Double> maxInputs, DataFrame<Double> maxOutputs) {
        this(data, 0.0);
        this.maxInputs = maxInputs;
        this.maxOutputs = maxOutputs;
    }

    public DataFrame<Double> getMaxInputs() {
        return maxInputs;
    }

    public DataFrame<Double> getMaxOutputs() {
        return maxOutputs;
    }

    public double getTolerance() {
        return tolerance;
    }

    public Set<String> getOrdinalFactors() {
        return ordinalFactors;
    }

    public void setOrdinalFactors(Set<String> ordinalFactors) {
        this.ordinalFactors = ordinalFactors;
    }

    public ProblemData getData() {
        return data;
    }
}
