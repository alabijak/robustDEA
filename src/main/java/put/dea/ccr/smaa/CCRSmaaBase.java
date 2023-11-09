package put.dea.ccr.smaa;

import joinery.DataFrame;
import put.dea.common.ProblemData;
import put.dea.common.smaa.ConstraintsSet;
import put.dea.common.smaa.SmaaBase;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class CCRSmaaBase extends SmaaBase {

    protected CCRSmaaBase(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public double calculateEfficiency(DataFrame<Double> inputs,
                                      DataFrame<Double> outputs,
                                      List<Double> inputsSample,
                                      List<Double> outputsSample,
                                      int dmuIdx) {
        var inputsValue = calculateWeightedSum(inputs.row(dmuIdx), inputsSample);
        var outputsValue = calculateWeightedSum(outputs.row(dmuIdx), outputsSample);
        return outputsValue / inputsValue;
    }

    @Override
    protected ConstraintsSet createModelSpecificConstraints(ProblemData data) {
        return new ConstraintsSet(
                List.of(createInputsSumConstraint(data), createOutputsSumConstraint(data)),
                List.of("=", "="),
                List.of(1.0, 1.0)
        );
    }

    private double[] createInputsSumConstraint(ProblemData data) {
        return createInputsOrOutputsSumConstraint(data, true);
    }

    private double[] createOutputsSumConstraint(ProblemData data) {
        return createInputsOrOutputsSumConstraint(data, false);
    }

    private double[] createInputsOrOutputsSumConstraint(ProblemData data, boolean inputs) {
        var value = inputs ? 1 : 0;
        return Stream.concat(Collections.nCopies(data.getInputCount(), value).stream(),
                        Collections.nCopies(data.getOutputCount(), 1 - value).stream())
                .mapToDouble(x -> x)
                .toArray();
    }

    protected DataFrame<Double> normalizeEfficiencies(DataFrame<Double> efficiencies) {
        var max = efficiencies.max().row(0);
        return efficiencies.transform(row -> List.of(normalizeRow(row, max)));
    }

    private List<Double> normalizeRow(List<Double> row, List<Double> max) {
        return IntStream.range(0, row.size())
                .mapToDouble(idx -> row.get(idx) / max.get(idx))
                .boxed()
                .toList();
    }


}
