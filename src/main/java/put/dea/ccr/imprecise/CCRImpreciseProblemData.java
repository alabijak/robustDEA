package put.dea.ccr.imprecise;

import put.dea.common.ProblemData;
import put.dea.common.imprecise.ImpreciseInformation;

import java.util.List;

public class CCRImpreciseProblemData extends ProblemData {

    private final ImpreciseInformation impreciseInformation;

    public CCRImpreciseProblemData(double[][] inputData, double[][] outputData, double tolerance) {
        this(inputData, outputData, null, null, tolerance);
    }

    public CCRImpreciseProblemData(double[][] inputData, double[][] outputData,
                                   List<String> inputNames,
                                   List<String> outputNames,
                                   double tolerance) {
        super(inputData, outputData, inputNames, outputNames);
        impreciseInformation = new ImpreciseInformation(this, tolerance);
    }

    public CCRImpreciseProblemData(double[][] minInputData, double[][] minOutputData,
                                   double[][] maxInputData, double[][] maxOutputData) {
        this(minInputData, minOutputData, maxInputData, maxOutputData, null, null);
    }

    public CCRImpreciseProblemData(double[][] minInputData, double[][] minOutputData,
                                   double[][] maxInputData, double[][] maxOutputData,
                                   List<String> inputNames,
                                   List<String> outputNames) {
        super(minInputData, minOutputData, inputNames, outputNames);

        var maxInputs = convertArrayToDataFrame(maxInputData, getInputNames());
        var maxOutputs = convertArrayToDataFrame(maxOutputData, getOutputNames());
        impreciseInformation = new ImpreciseInformation(this, maxInputs, maxOutputs);
    }

    public ImpreciseInformation getImpreciseInformation() {
        return impreciseInformation;
    }
}
