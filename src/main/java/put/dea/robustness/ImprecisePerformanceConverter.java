package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

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
                    subjectDmuIdx);
        else
            return convertToPrecise(
                    dataWithTolerance.getInputData(),
                    dataWithTolerance.getImpreciseInformation().getMaxOutputs(),
                    dataWithTolerance.getImpreciseInformation().getMaxInputs().row(subjectDmuIdx),
                    dataWithTolerance.getOutputData().row(subjectDmuIdx),
                    subjectDmuIdx);
    }

    private CCRImpreciseProblemData applyTolerance(ImpreciseInformation impreciseInformation) {
        var tolerance = impreciseInformation.getTolerance();
        var minInputs = applyToleranceToTable(impreciseInformation.getData().getInputData(), tolerance, true);
        var minOutputs = applyToleranceToTable(impreciseInformation.getData().getOutputData(), tolerance, true);

        Table maxInputs;
        if (impreciseInformation.getMaxInputs() != null)
            maxInputs = applyToleranceToTable(impreciseInformation.getMaxInputs(), tolerance, false);
        else
            maxInputs = applyToleranceToTable(impreciseInformation.getData().getInputData(), tolerance, false);

        Table maxOutputs;
        if (impreciseInformation.getMaxInputs() != null)
            maxOutputs = applyToleranceToTable(impreciseInformation.getMaxOutputs(), tolerance, false);
        else
            maxOutputs = applyToleranceToTable(impreciseInformation.getData().getOutputData(), tolerance, false);

        return new CCRImpreciseProblemData(
                minInputs,
                minOutputs,
                maxInputs,
                maxOutputs);
    }

    private ProblemData convertToPrecise(Table inputs,
                                         Table outputs,
                                         Row subjectInputs,
                                         Row subjectOutputs,
                                         int subjectDmuIdx) {
        var preciseInputs = inputs.copy();
        var preciseOutputs = outputs.copy();
        var subjectInputsRow = preciseInputs.row(subjectDmuIdx);
        for (int i = 0; i < subjectInputsRow.columnCount(); i++) {
            subjectInputsRow.setDouble(i, subjectInputs.getDouble(i));
        }
        var subjectOutputRow = preciseOutputs.row(subjectDmuIdx);
        for (int i = 0; i < subjectOutputRow.columnCount(); i++) {
            subjectOutputRow.setDouble(i, subjectOutputs.getDouble(i));
        }
        return new ProblemData(preciseInputs, preciseOutputs);
    }

    private Table applyToleranceToTable(Table table, double tolerance, boolean minimum) {
        var newTable = Table.create();
        table.columns()
                .stream()
                .map(c -> (DoubleColumn) c)
                .map(column -> column.map(value -> applyToleranceToValue(value, tolerance, minimum)))
                .forEach(newTable::addColumns);
        return newTable;
    }

    private double applyToleranceToValue(double value, double tolerance, boolean minimum) {
        if (minimum)
            return value * (1 - tolerance);
        return value * (1 + tolerance);
    }


}
