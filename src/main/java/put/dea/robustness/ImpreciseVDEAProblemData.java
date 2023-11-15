package put.dea.robustness;

import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class extendign VDEAProblemData representing the data set specification for
 * problems with VDEA efficiency model and imprecise information
 */
public class ImpreciseVDEAProblemData extends VDEAProblemData {

    private final ImpreciseInformation impreciseInformation;

    private Map<String, List<Pair<Double, Double>>> lowerFunctionShapes = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> upperFunctionShapes = new HashMap<>();

    /**
     * Creates new object based on imput and output performances and their tolerance
     * inputs and outputs may vary between (1-tolerance)*value and (1+tolerance*value)
     * tolerance should be berween 0 and 1
     *
     * @param inputData  input performances
     * @param outputData output performances
     * @param tolerance  tolerance (value between 0 and 1)
     */
    public ImpreciseVDEAProblemData(double[][] inputData, double[][] outputData, double tolerance) {
        super(inputData, outputData);
        impreciseInformation = new ImpreciseInformation(this, tolerance);
    }

    /**
     * Creates new object based on minimal and maximal performances for DMUs
     *
     * @param minInputData  minimal input performances
     * @param minOutputData minimal output performances
     * @param maxInputData  maximal input performances
     * @param maxOutputData maximal output performances
     */
    public ImpreciseVDEAProblemData(double[][] minInputData, double[][] minOutputData,
                                    double[][] maxInputData, double[][] maxOutputData) {
        super(minInputData, minOutputData);
        impreciseInformation = new ImpreciseInformation(this,
                convertArrayToDataFrame(maxInputData, getInputNames()),
                convertArrayToDataFrame(maxOutputData, getOutputNames()));
    }

    /**
     * Creates new object based on minimal and maximal performances for DMUs
     * and factors names
     *
     * @param minInputData  minimal input performances
     * @param minOutputData minimal output performances
     * @param maxInputData  maximal input performances
     * @param maxOutputData maximal output performances
     * @param inputNames    input names
     * @param outputNames   output names
     */
    public ImpreciseVDEAProblemData(double[][] minInputData, double[][] minOutputData,
                                    double[][] maxInputData, double[][] maxOutputData,
                                    List<String> inputNames, List<String> outputNames) {
        super(minInputData, minOutputData, inputNames, outputNames);
        impreciseInformation = new ImpreciseInformation(this,
                convertArrayToDataFrame(maxInputData, getInputNames()),
                convertArrayToDataFrame(maxOutputData, getOutputNames()));
    }

    /**
     * gets the shape of lower marginal value function for given factor (input or output)
     *
     * @param column factor (input or output) name
     * @return shape of the lower marginal function
     */
    public List<Pair<Double, Double>> getLowerFunctionShape(String column) {
        if (this.lowerFunctionShapes != null && this.lowerFunctionShapes.containsKey(column))
            return getFunctionShape(this.lowerFunctionShapes, column);
        return getFunctionShape(column);
    }

    /**
     * gets the shape of upper marginal value function for given factor (input or output)
     *
     * @param column factor (input or output) name
     * @return shape of the upper marginal function
     */
    public List<Pair<Double, Double>> getUpperFunctionShape(String column) {
        if (this.upperFunctionShapes != null && this.upperFunctionShapes.containsKey(column))
            return getFunctionShape(this.upperFunctionShapes, column);
        return getFunctionShape(column);
    }

    /**
     * sets the lower and upper function shapes for given input or output
     *
     * @param column        name of the factor
     * @param lowerFunction the shape of the lower function
     * @param upperFunction the shape of the upper function
     */
    public void setColumnFunctionShapes(String column,
                                        List<Pair<Double, Double>> lowerFunction,
                                        List<Pair<Double, Double>> upperFunction) {
        setLowerFunctionShape(column, lowerFunction);
        setUpperFunctionShape(column, upperFunction);
    }

    /**
     * sets the shape of the lower marginal function for given input or output
     *
     * @param column name of the factor (input or output)
     * @param shape  the shape of the lower marginal function
     */
    public void setLowerFunctionShape(String column, List<Pair<Double, Double>> shape) {
        if (this.lowerFunctionShapes == null)
            this.lowerFunctionShapes = new HashMap<>();
        this.lowerFunctionShapes.put(column, shape);
    }

    /**
     * sets the shape of the upper marginal function for given input or output
     *
     * @param column name of the factor (input or output)
     * @param shape  the shape of the upper marginal function
     */
    public void setUpperFunctionShape(String column, List<Pair<Double, Double>> shape) {
        if (this.upperFunctionShapes == null)
            this.upperFunctionShapes = new HashMap<>();
        this.upperFunctionShapes.put(column, shape);
    }

    @Override
    public Double getUpperBound(String column) {
        if (this.getUpperBounds().containsKey(column))
            return this.getUpperBounds().get(column);
        if (this.getInputNames().contains(column))
            return this.getImpreciseInformation()
                    .getMaxInputs()
                    .col(column)
                    .stream()
                    .max(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        if (this.getOutputNames().contains(column))
            return this.getImpreciseInformation()
                    .getMaxOutputs()
                    .col(column)
                    .stream()
                    .max(Comparator.comparing(x -> x))
                    .orElseThrow(IllegalArgumentException::new);
        throw new IllegalArgumentException("Column with given name does not exist");
    }

    /**
     * gets the object with specification of imprecise information in the data set
     *
     * @return specification of imprecise information in the data set
     */
    public ImpreciseInformation getImpreciseInformation() {
        return impreciseInformation;
    }
}
