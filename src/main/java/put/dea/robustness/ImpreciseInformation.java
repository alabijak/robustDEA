package put.dea.robustness;

import joinery.DataFrame;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the imprecise input/output data
 */
public class ImpreciseInformation {
    private final double tolerance;
    private final ProblemData data;
    private DataFrame<Double> maxInputs;
    private DataFrame<Double> maxOutputs;
    private Set<String> ordinalFactors = new HashSet<>();

    /**
     * Creates new object with minimal and maximal performances
     *
     * @param data       data set specification (with minimal performances)
     * @param maxInputs  maximal input performances
     * @param maxOutputs maximal output performances
     */
    public ImpreciseInformation(ProblemData data, DataFrame<Double> maxInputs, DataFrame<Double> maxOutputs) {
        this(data, 0.0);
        this.maxInputs = maxInputs;
        this.maxOutputs = maxOutputs;
    }

    /**
     * Creates new object with tolerance
     *
     * @param data      data set specification (with precise performances)
     * @param tolerance tolerance (between 0 and 1)
     */
    public ImpreciseInformation(ProblemData data, double tolerance) {
        this.data = data;
        this.tolerance = tolerance;
    }

    /**
     * returns maximal input performances
     *
     * @return maximal input performances
     */
    public DataFrame<Double> getMaxInputs() {
        return maxInputs;
    }

    /**
     * returns maximal output performances
     *
     * @return maximal output performances
     */
    public DataFrame<Double> getMaxOutputs() {
        return maxOutputs;
    }

    /**
     * returns input/output tolerance
     *
     * @return tolerance
     */
    public double getTolerance() {
        return tolerance;
    }

    /**
     * returns set of ordinal factors
     *
     * @return set of the ordinal factors' names
     */
    public Set<String> getOrdinalFactors() {
        return ordinalFactors;
    }

    /**
     * sets the ordinal factors
     *
     * @param ordinalFactors ordinal factors names
     */
    public void setOrdinalFactors(Set<String> ordinalFactors) {
        this.ordinalFactors = ordinalFactors;
    }

    /**
     * returns the data set specification
     *
     * @return data set specification
     */
    public ProblemData getData() {
        return data;
    }


}
