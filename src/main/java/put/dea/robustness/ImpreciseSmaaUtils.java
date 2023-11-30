package put.dea.robustness;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class ImpreciseSmaaUtils {

    private final ImpreciseCommonUtils impreciseUtils;
    private final SmaaBase smaa;

    public ImpreciseSmaaUtils(SmaaBase smaa) {
        this.impreciseUtils = new ImpreciseCommonUtils(1.0, 0.0);
        this.smaa = smaa;
    }

    public PerformanceSamplesCollection generatePerformanceSamples(ImpreciseInformation impreciseInformation,
                                                                   boolean reverseOrdinalInputs) {
        var data = impreciseInformation.getData();
        var samplesNo = smaa.getNumberOfSamples() * data.getDmuCount() * data.getColumnIndices().size();
        var samples = generateSamplesBetween0And1(samplesNo);
        return getPerformancesBasedOnSamples(samples, impreciseInformation, reverseOrdinalInputs);
    }

    private List<Double> generateSamplesBetween0And1(int numberOfSamples) {
        var lhs = new double[]{1};
        var constraints = new ConstraintsSet(List.of(lhs, lhs),
                List.of(">=", "<="),
                List.of(0.0, 1.0));
        var samples = smaa.generateSamples(smaa.convertConstraintsToConstraintsSystem(constraints), numberOfSamples);
        return Arrays.stream(samples).map(x -> x[0]).toList();
    }

    private PerformanceSamplesCollection getPerformancesBasedOnSamples(List<Double> samples,
                                                                       ImpreciseInformation impreciseInformation,
                                                                       boolean reverseOrdinalInputs) {
        var result = new PerformanceSamplesCollection();
        var data = impreciseInformation.getData();
        int variablesPerSample = (data.getDmuCount() * (data.getInputCount() + data.getOutputCount()));
        int samplesCount = samples.size() / variablesPerSample;
        var allFactors = new ArrayList<>(data.getInputData().columnNames());
        allFactors.addAll(data.getOutputData().columnNames());
        for (int i = 0; i < samplesCount; i++) {
            var sample = getValuesForCurrentSamples(samples, variablesPerSample, i);
            var inputTable = Table.create();
            var outputTable = Table.create();
            for (var factor : allFactors) {
                var factorIdx = data.getColumnIndices().get(factor);
                boolean input = factorIdx < data.getInputCount();
                var table = input ? inputTable : outputTable;
                var factorSample = getFactorSample(sample, factorIdx, data.getDmuCount());

                List<Double> performances;
                if (impreciseInformation.getOrdinalFactors().contains(factor)) {
                    performances = handleOrdinalSamples(data, factor, factorSample,
                            reverseOrdinalInputs && input);
                } else {
                    performances = handleImpreciseFactorSample(impreciseInformation, factor,
                            factorSample, input);
                }
                table.addColumns(DoubleColumn.create(factor, performances));
            }
            result.getInputPerformances().add(inputTable);
            result.getOutputPerformances().add(outputTable);
        }
        return result;
    }

    private List<Double> getValuesForCurrentSamples(List<Double> samples, int variablesPerSample, int sampleIdx) {
        return samples.stream()
                .skip((long) sampleIdx * variablesPerSample)
                .limit(variablesPerSample)
                .toList();
    }

    private List<Double> getFactorSample(List<Double> sample, int factorIdx, int dmuCount) {
        return sample.stream()
                .skip((long) factorIdx * dmuCount)
                .limit(dmuCount)
                .toList();
    }

    private List<Double> handleOrdinalSamples(ProblemData data,
                                              String factor,
                                              List<Double> factorSample,
                                              boolean descending) {
        var sortedIndices = impreciseUtils.sortIndicesByValues(data, factor, descending);
        var sortedSample = factorSample.stream().sorted().toList();

        var factorPerformances = impreciseUtils.getInputOrOutputTable(data, factor).doubleColumn(factor);
        var precisePerformances = new ArrayList<>(Collections.nCopies(data.getDmuCount(), 0.0));
        precisePerformances.set(sortedIndices.get(0), sortedSample.get(0));
        for (int i = 1; i < data.getDmuCount(); i++) {
            if (factorPerformances.get(sortedIndices.get(i))
                    .equals(factorPerformances.get(sortedIndices.get(i - 1)))) {
                precisePerformances.set(sortedIndices.get(i), precisePerformances.get(sortedIndices.get(i - 1)));
            } else {
                precisePerformances.set(sortedIndices.get(i), sortedSample.get(i));
            }
        }

        return precisePerformances;
    }

    private List<Double> handleImpreciseFactorSample(ImpreciseInformation impreciseInformation,
                                                     String factor,
                                                     List<Double> factorSample,
                                                     boolean input) {
        Table minTable, maxTable;
        if (input) {
            minTable = impreciseInformation.getData().getInputData();
            maxTable = impreciseInformation.getMaxInputs();
        } else {
            minTable = impreciseInformation.getData().getOutputData();
            maxTable = impreciseInformation.getMaxOutputs();
        }
        var minPerformances = minTable.doubleColumn(factor);
        var maxPerformances = maxTable.doubleColumn(factor);
        return calculatePreciseValueFromRange(minPerformances, maxPerformances, factorSample);
    }

    private List<Double> calculatePreciseValueFromRange(DoubleColumn lowerValues,
                                                        DoubleColumn upperValues,
                                                        List<Double> ratioSample) {
        var result = new ArrayList<Double>();
        for (int i = 0; i < ratioSample.size(); i++) {
            if (lowerValues.get(i).equals(upperValues.get(i)))
                result.add(lowerValues.get(i));
            else {
                var ratio = ratioSample.get(i);
                var performance = lowerValues.get(i) +
                        ratio * (upperValues.get(i) - lowerValues.get(i));
                result.add(performance);
            }
        }
        return result;
    }

    public Table calculateEfficiencyMatrixForSamples(WeightSamplesCollection weightSamples,
                                                     PerformanceSamplesCollection performanceSamples,
                                                     int dmuCount) {
        var efficiencies = Table.create();
        for (int dmu = 0; dmu < dmuCount; dmu++) {
            var dmuIdx = dmu;
            efficiencies.addColumns(
                    DoubleColumn.create(dmu + "",
                            IntStream.range(0, smaa.getNumberOfSamples())
                                    .mapToDouble(idx -> smaa.calculateEfficiency(
                                            performanceSamples.getInputPerformances().get(idx),
                                            performanceSamples.getOutputPerformances().get(idx),
                                            weightSamples.getInputSamples().row(idx),
                                            weightSamples.getOutputSamples().row(idx),
                                            dmuIdx))
                                    .toArray()
                    )
            );
        }

        return efficiencies.transpose();
    }

    public PerformanceSamplesCollection generateValueFunctionSamples(PerformanceSamplesCollection performanceSamples,
                                                                     ImpreciseVDEAProblemData data) {
        int variablesPerSample = data.getDmuCount() * (data.getInputCount() + data.getOutputCount()
                - data.getImpreciseInformation().getOrdinalFactors().size());
        var samplesNo = smaa.getNumberOfSamples() * variablesPerSample;
        var samples = generateSamplesBetween0And1(samplesNo);
        return getFunctionValuesBasedOnSamples(samples, data, performanceSamples, variablesPerSample);
    }

    private PerformanceSamplesCollection getFunctionValuesBasedOnSamples(List<Double> samples,
                                                                         ImpreciseVDEAProblemData data,
                                                                         PerformanceSamplesCollection performanceSamples,
                                                                         int variablesPerSample) {

        var result = new PerformanceSamplesCollection();
        int samplesCount = samples.size() / variablesPerSample;
        var allFactors = new ArrayList<>(data.getInputData().columnNames());
        allFactors.addAll(data.getOutputData().columnNames());
        for (int i = 0; i < samplesCount; i++) {
            var sample = getValuesForCurrentSamples(samples, variablesPerSample, i);

            var inputTable = Table.create();
            var outputTable = Table.create();
            var factorIdx = 0;

            for (var factor : allFactors) {
                boolean input = data.getInputData().containsColumn(factor);
                var table = input ? inputTable : outputTable;
                var performanceSampleTable = input ? performanceSamples.getInputPerformances().get(i)
                        : performanceSamples.getOutputPerformances().get(i);

                if (data.getImpreciseInformation().getOrdinalFactors().contains(factor)) {
                    table.addColumns(DoubleColumn.create(factor,
                            performanceSampleTable.doubleColumn(factor).asDoubleArray()));
                } else {
                    var factorSample = getFactorSample(sample, factorIdx, data.getDmuCount());
                    var performances = handleValueFunctionSamples(data,
                            performanceSampleTable,
                            factor,
                            factorSample);
                    table.addColumns(DoubleColumn.create(factor, performances));
                    factorIdx++;
                }
            }
            result.getInputPerformances().add(inputTable);
            result.getOutputPerformances().add(outputTable);
        }
        return result;
    }

    private List<Double> handleValueFunctionSamples(ImpreciseVDEAProblemData data,
                                                    Table performanceSample,
                                                    String factor,
                                                    List<Double> functionValuesSample) {
        PerformanceToValueConverter converter = new PerformanceToValueConverter();
        var lowerValues = converter.transformColumnToUtilities(
                performanceSample,
                factor,
                data.getLowerFunctionShape(factor));

        var upperValues = converter.transformColumnToUtilities(
                performanceSample,
                factor,
                data.getUpperFunctionShape(factor));

        return calculatePreciseValueFromRange(lowerValues, upperValues, functionValuesSample);
    }

}
