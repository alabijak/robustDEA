package put.dea.robustness;

import tech.tablesaw.api.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the imprecise input/output data
 */
public class ImpreciseInformation {
    private final double tolerance;
    private final ProblemData data;
    private Table maxInputs;
    private Table maxOutputs;
    private Set<String> ordinalFactors = new HashSet<>();

    /**
     * Creates new object with minimal and maximal performances
     *
     * @param data       data set specification (with minimal performances)
     * @param maxInputs  maximal input performances
     * @param maxOutputs maximal output performances
     */
    public ImpreciseInformation(ProblemData data, Table maxInputs, Table maxOutputs) {
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
    public Table getMaxInputs() {
        return maxInputs;
    }

    /**
     * returns maximal output performances
     *
     * @return maximal output performances
     */
    public Table getMaxOutputs() {
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
     * assigns the set of ordinal factor names to the data set specification
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
