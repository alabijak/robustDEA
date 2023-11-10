package put.dea.robustness;

import java.util.List;

/**
 * Extension of standard {@link ProblemData} class for imprecise problems (and CCR model)
 */
public class CCRImpreciseProblemData extends ProblemData {

    private final ImpreciseInformation impreciseInformation;

    /**
     * Creates {@link CCRImpreciseProblemData} object based on input and output performances and tolerance
     *
     * @param inputData  input performances
     * @param outputData output performances
     * @param tolerance  tolerance value, inputs and outputs may vary between (1-tolerance)*value and (1+tolerance)*value
     */
    public CCRImpreciseProblemData(double[][] inputData, double[][] outputData, double tolerance) {
        this(inputData, outputData, null, null, tolerance);
    }

    /**
     * Creates {@link CCRImpreciseProblemData} object based on input and output performances
     * factors' names and tolerance
     *
     * @param inputData   input performances
     * @param outputData  output performances
     * @param inputNames  {@link List} of names for inputs
     * @param outputNames {@link List} of names for outputs
     * @param tolerance   tolerance value, inputs and outputs may vary between (1-tolerance)*value and (1+tolerance)*value
     */
    public CCRImpreciseProblemData(double[][] inputData, double[][] outputData,
                                   List<String> inputNames,
                                   List<String> outputNames,
                                   double tolerance) {
        super(inputData, outputData, inputNames, outputNames);
        impreciseInformation = new ImpreciseInformation(this, tolerance);
    }

    /**
     * creates the {@link CCRImpreciseProblemData} based on input and output perofmance ranges
     *
     * @param minInputData  minimal values for inputs
     * @param minOutputData minimal values for outputs
     * @param maxInputData  maximal values for inputs
     * @param maxOutputData maximal values for outputs
     */
    public CCRImpreciseProblemData(double[][] minInputData, double[][] minOutputData,
                                   double[][] maxInputData, double[][] maxOutputData) {
        this(minInputData, minOutputData, maxInputData, maxOutputData, null, null);
    }

    /**
     * creates the {@link CCRImpreciseProblemData} based on input and output performance ranges
     * with provided input and output names
     *
     * @param minInputData  minimal values for inputs
     * @param minOutputData minimal values for outputs
     * @param maxInputData  maximal values for inputs
     * @param maxOutputData maximal values for outputs
     * @param inputNames    {@link List} of names for inputs
     * @param outputNames   {@link List} of names for outputs
     */
    public CCRImpreciseProblemData(double[][] minInputData, double[][] minOutputData,
                                   double[][] maxInputData, double[][] maxOutputData,
                                   List<String> inputNames,
                                   List<String> outputNames) {
        super(minInputData, minOutputData, inputNames, outputNames);

        var maxInputs = convertArrayToDataFrame(maxInputData, getInputNames());
        var maxOutputs = convertArrayToDataFrame(maxOutputData, getOutputNames());
        impreciseInformation = new ImpreciseInformation(this, maxInputs, maxOutputs);
    }

    /**
     * gets the additional imprecise information about the data set
     *
     * @return imprecise dataset information
     */
    public ImpreciseInformation getImpreciseInformation() {
        return impreciseInformation;
    }
}
