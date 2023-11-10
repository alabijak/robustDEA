package put.dea.robustness;

import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VDEAProblemData extends ProblemData {
    private Map<String, Double> lowerBounds = new HashMap<>();
    private Map<String, Double> upperBounds = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> functionShapes = new HashMap<>();

    public VDEAProblemData(double[][] inputData, double[][] outputData) {
        super(inputData, outputData);
    }

    public VDEAProblemData(double[][] inputData, double[][] outputData,
                           List<String> inputNames,
                           List<String> outputNames) {
        super(inputData, outputData, inputNames, outputNames);
    }

    public Map<String, Double> getLowerBounds() {
        return lowerBounds;
    }

    public void setLowerBounds(Map<String, Double> lowerBounds) {
        this.lowerBounds = lowerBounds;
    }

    public Map<String, Double> getUpperBounds() {
        return upperBounds;
    }

    public void setUpperBounds(Map<String, Double> upperBounds) {
        this.upperBounds = upperBounds;
    }

    public void setFunctionShape(String column, List<Pair<Double, Double>> shape) {
        this.functionShapes.put(column, shape);
    }

    public List<Pair<Double, Double>> getFunctionShape(String column) {
        return getFunctionShape(this.functionShapes, column);
    }

    protected List<Pair<Double, Double>> getFunctionShape(Map<String, List<Pair<Double, Double>>> shapes,
                                                          String column) {
        if (shapes.containsKey(column))
            return shapes.get(column);
        if (this.getInputData().columns().contains(column))
            return List.of(
                    new Pair<>(getLowerBound(column), 1.0),
                    new Pair<>(getUpperBound(column), 0.0)
            );
        if (this.getOutputData().columns().contains(column))
            return List.of(
                    new Pair<>(getLowerBound(column), 0.0),
                    new Pair<>(getUpperBound(column), 1.0)
            );
        throw new IllegalArgumentException("Column with given name does not exist");
    }

    public Double getLowerBound(String column) {
        if (this.lowerBounds.containsKey(column))
            return this.lowerBounds.get(column);
        if (this.getInputData().columns().contains(column))
            return this.getInputData().col(column).stream().min(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        if (this.getOutputData().columns().contains(column))
            return this.getOutputData().col(column).stream().min(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        throw new IllegalArgumentException("Column with given name does not exist");
    }

    public Double getUpperBound(String column) {
        if (this.upperBounds.containsKey(column))
            return this.upperBounds.get(column);
        if (this.getInputData().columns().contains(column))
            return this.getInputData().col(column).stream().max(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        if (this.getOutputData().columns().contains(column))
            return this.getOutputData().col(column).stream().max(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        throw new IllegalArgumentException("Column with given name does not exist");
    }

    public Map<String, List<Pair<Double, Double>>> getFunctionShapes() {
        return functionShapes;
    }

    public void setFunctionShapes(Map<String, List<Pair<Double, Double>>> functionShapes) {
        this.functionShapes = functionShapes;
    }
}
