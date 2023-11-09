package put.dea.common;

import joinery.DataFrame;
import put.dea.weightConstraints.Constraint;

import java.util.*;
import java.util.stream.IntStream;

public class ProblemData {
    private final DataFrame<Double> inputData;
    private final DataFrame<Double> outputData;

    private final Map<String, Integer> columnIndices = new HashMap<>();
    private List<Constraint> weightConstraints = new ArrayList<>();

    public ProblemData(double[][] inputData, double[][] outputData) {
        this(inputData, outputData, null, null);
    }

    public ProblemData(double[][] inputData, double[][] outputData,
                       List<String> inputNames,
                       List<String> outputNames) {
        this(inputData, outputData, inputNames, outputNames, new ArrayList<>());
    }

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

    public ProblemData(DataFrame<Double> inputData, DataFrame<Double> outputData) {
        this.inputData = inputData;
        this.outputData = outputData;
        initializeColumnIndices(getInputNames(), getOutputNames());
    }

    public List<String> getInputNames() {
        return getColumnNames(inputData);
    }

    public List<String> getOutputNames() {
        return getColumnNames(outputData);
    }

    protected List<String> getColumnNames(DataFrame<Double> dataFrame) {
        return dataFrame.columns().stream().map(Object::toString).toList();
    }

    public Map<String, Integer> getColumnIndices() {
        return columnIndices;
    }

    public int getInputCount() {
        return inputData.size();
    }

    public int getOutputCount() {
        return outputData.size();
    }

    public int getDmuCount() {
        return inputData.length();
    }

    public DataFrame<Double> getInputData() {
        return inputData;
    }

    public DataFrame<Double> getOutputData() {
        return outputData;
    }

    public List<Constraint> getWeightConstraints() {
        return weightConstraints;
    }

    public void setWeightConstraints(List<Constraint> weightConstraints) {
        this.weightConstraints = weightConstraints;
    }

    public void addWeightConstraint(Constraint constraint) {
        this.weightConstraints.add(constraint);
    }
}
