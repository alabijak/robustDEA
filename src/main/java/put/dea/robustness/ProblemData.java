package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Class representing data set for problems with precise information for CCR efficiency model
 */
public class ProblemData {
    private final Table inputData;
    private final Table outputData;

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

        this.inputData = convertArrayToTable(inputData, inputNames);
        this.outputData = convertArrayToTable(outputData, outputNames);
        initializeColumnIndices(inputNames, outputNames);
        this.weightConstraints = weightConstraints;
    }

    protected List<String> generateColumnNames(int count, String prefix) {
        return IntStream.range(0, count).boxed().map(idx -> prefix + idx).toList();
    }

    protected Table convertArrayToTable(double[][] arr, List<String> columnNames) {

        var table = Table.create();
        table.addColumns(StringColumn.create("names", columnNames));
        for (double[] values : arr) {
            table.addColumns(DoubleColumn.create("c" + table.columnCount(), values));
        }
        return table.transpose(false, true);
    }

    private void initializeColumnIndices(List<String> inputNames, List<String> outputNames) {
        IntStream.range(0, inputNames.size())
                .forEach(idx -> columnIndices.put(inputNames.get(idx), idx));
        IntStream.range(0, outputNames.size())
                .forEach(idx -> columnIndices.put(outputNames.get(idx), inputNames.size() + idx));
    }

    /**
     * Constructor creating {@link ProblemData} object from input and output performances
     * provided as {@link Table} objects
     *
     * @param inputData  DMUs' inputs performances
     * @param outputData DMUs' outputs performances
     */
    public ProblemData(Table inputData, Table outputData) {
        this.inputData = inputData;
        this.outputData = outputData;
        initializeColumnIndices(inputData.columnNames(), outputData.columnNames());
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
        return inputData.columnCount();
    }

    /**
     * returns number of outputs in the analysed data set
     *
     * @return number of outputs in the data set
     */
    public int getOutputCount() {
        return outputData.columnCount();
    }

    /**
     * returns number of DMUs in the analysed data set
     *
     * @return number of DMUs in the data set
     */
    public int getDmuCount() {
        return inputData.rowCount();
    }

    /**
     * returns DMUs' input performances
     *
     * @return DMUs' input performances
     */
    public Table getInputData() {
        return inputData;
    }

    /**
     * returns DMUs' output performances
     *
     * @return DMUs' output performances
     */
    public Table getOutputData() {
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
