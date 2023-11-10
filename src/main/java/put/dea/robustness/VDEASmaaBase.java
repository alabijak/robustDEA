package put.dea.robustness;

import joinery.DataFrame;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

abstract class VDEASmaaBase extends SmaaBase {

    protected final PerformanceToValueConverter performanceToValueConverter = new PerformanceToValueConverter();

    protected VDEASmaaBase(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    protected DataFrame<Double> calculateEfficiencyMatrix(ProblemData data, DataFrame<Double> inputValues, DataFrame<Double> outputValues) {
        var samples = generateWeightSamples(data);
        return calculateEfficiencyMatrixForSamples(inputValues, outputValues, samples);
    }

    @Override
    protected double calculateEfficiency(DataFrame<Double> inputs, DataFrame<Double> outputs,
                                         List<Double> inputsSample, List<Double> outputsSample, int dmuIdx) {
        return calculateWeightedSum(Stream.concat(inputs.row(dmuIdx).stream(), outputs.row(dmuIdx).stream()).toList(),
                Stream.concat(inputsSample.stream(), outputsSample.stream()).toList());
    }

    @Override
    protected ConstraintsSet createModelSpecificConstraints(ProblemData data) {
        return new ConstraintsSet(
                List.of(Collections
                        .nCopies(data.getInputCount() + data.getOutputCount(), 1.0)
                        .stream()
                        .mapToDouble(x -> x)
                        .toArray()),
                List.of("="),
                List.of(1.0)
        );
    }

    protected DataFrame<Double> calculateDistanceMatrix(DataFrame<Double> efficiencyMatrix) {
        var best = efficiencyMatrix.max().row(0);
        var distanceMatrix = new DataFrame<Double>(efficiencyMatrix.columns());
        efficiencyMatrix
                .iterrows()
                .forEachRemaining(row -> distanceMatrix.append(calculateDistancesForRow(row, best)));
        return distanceMatrix;
    }

    private List<Double> calculateDistancesForRow(List<Double> row, List<Double> best) {
        return IntStream.range(0, row.size())
                .mapToDouble(idx -> best.get(idx) - row.get(idx))
                .boxed()
                .toList();
    }
}
