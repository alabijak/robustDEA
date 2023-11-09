package put.dea.vdea.imprecise;

import org.apache.commons.math3.util.Pair;
import put.dea.common.imprecise.ImpreciseInformation;
import put.dea.vdea.VDEAProblemData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImpreciseVDEAProblemData extends VDEAProblemData {

    private final ImpreciseInformation impreciseInformation;

    private Map<String, List<Pair<Double, Double>>> lowerFunctionShapes = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> upperFunctionShapes = new HashMap<>();

    public ImpreciseVDEAProblemData(double[][] inputData, double[][] outputData, double tolerance) {
        super(inputData, outputData);
        impreciseInformation = new ImpreciseInformation(this, tolerance);
    }

    public ImpreciseVDEAProblemData(double[][] minInputData, double[][] minOutputData,
                                    double[][] maxInputData, double[][] maxOutputData) {
        super(minInputData, minOutputData);
        impreciseInformation = new ImpreciseInformation(this,
                convertArrayToDataFrame(maxInputData, getInputNames()),
                convertArrayToDataFrame(maxOutputData, getOutputNames()));
    }

    public ImpreciseVDEAProblemData(double[][] minInputData, double[][] minOutputData,
                                        double[][] maxInputData, double[][] maxOutputData,
                                        List<String> inputNames, List<String> outputNames) {
        super(minInputData, minOutputData, inputNames, outputNames);
        impreciseInformation = new ImpreciseInformation(this,
                convertArrayToDataFrame(maxInputData, getInputNames()),
                convertArrayToDataFrame(maxOutputData, getOutputNames()));
    }

    public List<Pair<Double, Double>> getLowerFunctionShape(String column) {
        if (this.lowerFunctionShapes != null && this.lowerFunctionShapes.containsKey(column))
            return getFunctionShape(this.lowerFunctionShapes, column);
        return getFunctionShape(column);
    }

    public List<Pair<Double, Double>> getUpperFunctionShape(String column) {
        if (this.upperFunctionShapes != null && this.upperFunctionShapes.containsKey(column))
            return getFunctionShape(this.upperFunctionShapes, column);
        return getFunctionShape(column);
    }

    public void setColumnFunctionShapes(String column,
                                        List<Pair<Double, Double>> lowerFunction,
                                        List<Pair<Double, Double>> upperFunction) {
        setLowerFunctionShape(column, lowerFunction);
        setUpperFunctionShape(column, upperFunction);
    }

    public void setLowerFunctionShape(String column, List<Pair<Double, Double>> shape) {
        if (this.lowerFunctionShapes == null)
            this.lowerFunctionShapes = new HashMap<>();
        this.lowerFunctionShapes.put(column, shape);
    }

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

    public ImpreciseInformation getImpreciseInformation() {
        return impreciseInformation;
    }
}
