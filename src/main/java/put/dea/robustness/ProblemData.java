package put.dea.robustness;

import joinery.DataFrame;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Class representing data set for problems with precise information for CCR efficiency model
 */
public class ProblemData {
    private final DataFrame<Double> inputData;
    private final DataFrame<Double> outputData;

    private final Map<String, Integer> columnIndices = new HashMap<>();
    private List<Constraint> weightConstraints = new ArrayList<>();

    /**
     * Constructor creating {@link ProblemData} object from input and output performances
     *
     * @param inputData  DMUs' inputs performances
     * @param outputData DMUs' outputs performances
     */
    public ProblemData(double[][] inputData, double[][] outputData) {
        this(inputData, outputData, null, null);
    }

    /**
     * Constructor creating {@link ProblemData} object from input and output performances
     * with specified factor names
     *
     * @param inputData   DMUs' inputs performances
     * @param outputData  DMUs' outputs performances
     * @param inputNames  {@link List} of names for inputs
     * @param outputNames {@link List} of names for outputs
     */
    public ProblemData(double[][] inputData, double[][] outputData,
                       List<String> inputNames,
                       List<String> outputNames) {
        this(inputData, outputData, inputNames, outputNames, new ArrayList<>());
    }

    /**
     * Constructor creating {@link ProblemData} object from input and output performances
     * with specified factor names and custom weight constraints
     *
     * @param inputData         DMUs' inputs performances
     * @param outputData        DMUs' outputs performances
     * @param inputNames        {@link List} of names for inputs
     * @param outputNames       {@link List} of names for outputs
     * @param weightConstraints {@link List} of {@link Constraint constraints} representing custom weight constraints
     */
    public ProblemData(double[][] inputData, double[][] outputData,
                       List<String> inputNames,
                       List<String> outputNames,
                       List<Constraint> weightConstraints) {
        if (inputNames == null)
            inputNames = generateColumnNames(inputData[0].length, "in_");
        if (outputNames == null)
            outputNames = generateColumnNames(outputData[0].length, "out_");

        this.inputData = convertArrayToDataFrame(inputData, inputNames);
        this.outputData = convertArrayToDataFrame(outputData, outputNames);
        initializeColumnIndices(inputNames, outputNames);
        this.weightConstraints = weightConstraints;
    }

    protected List<String> generateColumnNames(int count, String prefix) {
        return IntStream.range(0, count).boxed().map(idx -> prefix + idx).toList();
    }

    protected DataFrame<Double> convertArrayToDataFrame(double[][] arr, List<String> columnNames) {

        var table = new DataFrame<Double>(columnNames);
        for (double[] doubles : arr) {
            table.append(Arrays.stream(doubles).boxed().toList());
        }
        return table;
    }

    private void initializeColumnIndices(List<String> inputNames, List<String> outputNames) {
        IntStream.range(0, inputNames.size())
                .forEach(idx -> columnIndices.put(inputNames.get(idx), idx));
        IntStream.range(0, outputNames.size())
                .forEach(idx -> columnIndices.put(outputNames.get(idx), inputNames.size() + idx));
    }

    /**
     * Constructor creating {@link ProblemData} object from input and output performances
     * provided as {@link DataFrame} objects
     *
     * @param inputData  DMUs' inputs performances
     * @param outputData DMUs' outputs performances
     */
    public ProblemData(DataFrame<Double> inputData, DataFrame<Double> outputData) {
        this.inputData = inputData;
        this.outputData = outputData;
        initializeColumnIndices(getInputNames(), getOutputNames());
    }

    /**
     * returns list of input factor names
     *
     * @return {@link List} of input factor names
     */
    public List<String> getInputNames() {
        return getColumnNames(inputData);
    }

    /**
     * returns list of output factor names
     *
     * @return {@link List} of output factor names
     */
    public List<String> getOutputNames() {
        return getColumnNames(outputData);
    }

    protected List<String> getColumnNames(DataFrame<Double> dataFrame) {
        return dataFrame.columns().stream().map(Object::toString).toList();
    }

    /**
     * returns map with indices assigned to individual factors
     *
     * @return {@link Map} with column names as keys and column indices as values
     */
    public Map<String, Integer> getColumnIndices() {
        return columnIndices;
    }

    /**
     * returns number of inputs in the analysed data set
     *
     * @return number of inputs in the data set
     */
    public int getInputCount() {
        return inputData.size();
    }

    /**
     * returns number of outputs in the analysed data set
     *
     * @return number of outputs in the data set
     */
    public int getOutputCount() {
        return outputData.size();
    }

    /**
     * returns number of DMUs in the analysed data set
     *
     * @return number of DMUs in the data set
     */
    public int getDmuCount() {
        return inputData.length();
    }

    /**
     * returns DMUs' input performances
     *
     * @return DMUs' input performances
     */
    public DataFrame<Double> getInputData() {
        return inputData;
    }

    /**
     * returns DMUs' output performances
     *
     * @return DMUs' output performances
     */
    public DataFrame<Double> getOutputData() {
        return outputData;
    }

    /**
     * returns custom weight constraints in the data set
     *
     * @return {@link List} of custom weight {@link Constraint constraints}
     */
    public List<Constraint> getWeightConstraints() {
        return weightConstraints;
    }

    /**
     * sets custom weight constraints to the problem
     *
     * @param weightConstraints {@link List} of custom weight {@link Constraint constraints}
     */
    public void setWeightConstraints(List<Constraint> weightConstraints) {
        this.weightConstraints = weightConstraints;
    }

    /**
     * adds one custom weight constraint to the problem
     *
     * @param constraint {@link Constraint} to be added to the problem
     */
    public void addWeightConstraint(Constraint constraint) {
        this.weightConstraints.add(constraint);
    }
}
