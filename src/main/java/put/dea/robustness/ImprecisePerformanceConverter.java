package put.dea.robustness;

import joinery.DataFrame;

import java.util.List;

class ImprecisePerformanceConverter {
    public ProblemData convertPerformanceToPrecise(ImpreciseInformation impreciseInformation,
                                                   int subjectDmuIdx,
                                                   ResultType resultType) {
        var dataWithTolerance = applyTolerance(impreciseInformation);
        if (resultType == ResultType.OPTIMISTIC)
            return convertToPrecise(
                    dataWithTolerance.getImpreciseInformation().getMaxInputs(),
                    dataWithTolerance.getOutputData(),
                    dataWithTolerance.getInputData().row(subjectDmuIdx),
                    dataWithTolerance.getImpreciseInformation().getMaxOutputs().row(subjectDmuIdx),
                    subjectDmuIdx,
                    impreciseInformation.getData().getInputNames(),
                    dataWithTolerance.getOutputNames());
        else
            return convertToPrecise(
                    dataWithTolerance.getInputData(),
                    dataWithTolerance.getImpreciseInformation().getMaxOutputs(),
                    dataWithTolerance.getImpreciseInformation().getMaxInputs().row(subjectDmuIdx),
                    dataWithTolerance.getOutputData().row(subjectDmuIdx),
                    subjectDmuIdx,
                    impreciseInformation.getData().getInputNames(),
                    impreciseInformation.getData().getOutputNames());
    }

    private CCRImpreciseProblemData applyTolerance(ImpreciseInformation impreciseInformation) {
        var tolerance = impreciseInformation.getTolerance();
        var minInputs = impreciseInformation.getData()
                .getInputData()
                .apply(value -> applyToleranceToValue(value, tolerance, true));
        var minOutputs = impreciseInformation.getData()
                .getOutputData()
                .apply(value -> applyToleranceToValue(value, tolerance, true));

        DataFrame<Double> maxInputs;
        if (impreciseInformation.getMaxInputs() != null)
            maxInputs = impreciseInformation
                    .getMaxInputs()
                    .apply(value -> applyToleranceToValue(value, tolerance, false));
        else
            maxInputs = impreciseInformation.getData()
                    .getInputData()
                    .apply(value -> applyToleranceToValue(value, tolerance, false));


        DataFrame<Double> maxOutputs;
        if (impreciseInformation.getMaxInputs() != null)
            maxOutputs = impreciseInformation
                    .getMaxOutputs()
                    .apply(value -> applyToleranceToValue(value, tolerance, false));
        else
            maxOutputs = impreciseInformation.getData()
                    .getOutputData()
                    .apply(value -> applyToleranceToValue(value, tolerance, false));

        return new CCRImpreciseProblemData(
                minInputs.toModelMatrix(0),
                minOutputs.toModelMatrix(0),
                maxInputs.toModelMatrix(0),
                maxOutputs.toModelMatrix(0),
                impreciseInformation.getData().getInputNames(),
                impreciseInformation.getData().getOutputNames());
    }

    private ProblemData convertToPrecise(DataFrame<Double> inputs,
                                         DataFrame<Double> outputs,
                                         List<Double> subjectInputs,
                                         List<Double> subjectOutputs,
                                         int subjectDmuIdx,
                                         List<String> inputNames,
                                         List<String> outputNames) {
        var preciseInputs = inputs.toModelMatrix(0);
        var preciseOutputs = outputs.toModelMatrix(0);
        preciseInputs[subjectDmuIdx] = subjectInputs.stream().mapToDouble(x -> x).toArray();
        preciseOutputs[subjectDmuIdx] = subjectOutputs.stream().mapToDouble(x -> x).toArray();
        return new ProblemData(preciseInputs, preciseOutputs, inputNames, outputNames);
    }

    private double applyToleranceToValue(double value, double tolerance, boolean minimum) {
        if (minimum)
            return value * (1 - tolerance);
        return value * (1 + tolerance);
    }


}
