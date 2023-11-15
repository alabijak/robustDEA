package put.dea.robustness;

import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class extending {@link ProblemData} representing specification of the data set
 * for VDEA efficiency model
 */
public class VDEAProblemData extends ProblemData {
    private Map<String, Double> lowerBounds = new HashMap<>();
    private Map<String, Double> upperBounds = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> functionShapes = new HashMap<>();

    /**
     * Constructor creating {@link VDEAProblemData} object from input and output performances
     *
     * @param inputData  DMUs' inputs performances
     * @param outputData DMUs' outputs performances
     */
    public VDEAProblemData(double[][] inputData, double[][] outputData) {
        super(inputData, outputData);
    }

    /**
     * Constructor creating {@link VDEAProblemData} object from input and output performances
     * with specified factor names
     *
     * @param inputData   DMUs' inputs performances
     * @param outputData  DMUs' outputs performances
     * @param inputNames  {@link List} of names for inputs
     * @param outputNames {@link List} of names for outputs
     */
    public VDEAProblemData(double[][] inputData, double[][] outputData,
                           List<String> inputNames,
                           List<String> outputNames) {
        super(inputData, outputData, inputNames, outputNames);
    }

    /**
     * gets the lower boundaries for all columns
     *
     * @return {@link Map} of factors' lower bounds with factor names as keys and lower bounds as values
     */
    public Map<String, Double> getLowerBounds() {
        return lowerBounds;
    }


    /**
     * sets the lower boundaries for all columns
     *
     * @param lowerBounds {@link Map} of factors' lower bounds with factor names as keys and lower bounds as values
     */
    public void setLowerBounds(Map<String, Double> lowerBounds) {
        this.lowerBounds = lowerBounds;
    }

    /**
     * gets the upper boundaries for all columns
     *
     * @return {@link Map} of factors' lower bounds with factor names as keys and lower bounds as values
     */
    public Map<String, Double> getUpperBounds() {
        return upperBounds;
    }

    /**
     * sets the upper boundaries for all columns
     *
     * @param upperBounds {@link Map} of factors' lower bounds with factor names as keys and lower bounds as values
     */
    public void setUpperBounds(Map<String, Double> upperBounds) {
        this.upperBounds = upperBounds;
    }

    /**
     * sets the shape of the marginal value function for given input or output
     *
     * @param column name of the factor
     * @param shape  shape of the marginal value function in a form {@link List}
     *               of {@link Pair} representing points
     */
    public void setFunctionShape(String column, List<Pair<Double, Double>> shape) {
        this.functionShapes.put(column, shape);
    }

    /**
     * gets the shape of the marginal value function for given input or output
     *
     * @param column name of the factor
     * @return the shape of the marginal value function in a form of {@link List}
     * of {@link Pair} representing points
     */
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

    /**
     * gets the lower bound (maximal possible value) for the provided column
     *
     * @param column factor (input or output) name
     * @return lower bound value
     */
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

    /**
     * gets the upper bound (maximal possible value) for the provided column
     *
     * @param column factor (input or output) name
     * @return upper bound value
     */
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

    /**
     * gets the marginal value function shapes for all inputs and outputs
     *
     * @return {@link Map} with factor names as keys and function shapes as values
     */
    public Map<String, List<Pair<Double, Double>>> getFunctionShapes() {
        return functionShapes;
    }

    /**
     * sets the marginal value function shapes for all inputs and outputs
     *
     * @param functionShapes {@link Map} with factor names as keys and function shapes as values
     */
    public void setFunctionShapes(Map<String, List<Pair<Double, Double>>> functionShapes) {
        this.functionShapes = functionShapes;
    }
}
