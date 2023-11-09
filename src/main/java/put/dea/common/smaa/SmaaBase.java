package put.dea.common.smaa;

import joinery.DataFrame;
import polyrun.PolytopeRunner;
import polyrun.constraints.ConstraintsSystem;
import polyrun.exceptions.InfeasibleSystemException;
import polyrun.exceptions.UnboundedSystemException;
import polyrun.sampling.HitAndRun;
import polyrun.thinning.NCubedThinningFunction;
import put.dea.common.ProblemData;
import put.dea.weightConstraints.Constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class SmaaBase {
    protected int numberOfSamples;
    protected Random random;

    public SmaaBase(int numberOfSamples, Random random) {
        this.numberOfSamples = numberOfSamples;
        this.random = random;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    protected List<Double> calculateExpectedValues(DataFrame<Double> distribution) {
        return distribution.transpose().mean().row(0);
    }

    public DataFrame<Double> calculateEfficiencyMatrix(ProblemData data) {
        var samples = generateWeightSamples(data);
        return calculateEfficiencyMatrixForSamples(data.getInputData(), data.getOutputData(), samples);
    }

    public WeightSamplesCollection generateWeightSamples(ProblemData data) {
        var constraints = prepareConstraintsSystem(data);
        var samples = generateSamples(constraints);
        return new WeightSamplesCollection(samples, data.getInputCount());
    }

    public DataFrame<Double> calculateEfficiencyMatrixForSamples(DataFrame<Double> inputs,
                                                                 DataFrame<Double> outputs,
                                                                 WeightSamplesCollection samples) {
        var efficiencies = new DataFrame<Double>(IntStream.range(0, numberOfSamples).mapToObj(x -> "sample" + x).toList());
        for (int dmu = 0; dmu < inputs.length(); dmu++) {
            var dmuIdx = dmu;
            efficiencies.append(IntStream.range(0, numberOfSamples)
                    .mapToObj(idx -> calculateEfficiency(inputs,
                            outputs,
                            samples.getInputSamples().row(idx),
                            samples.getOutputSamples().row(idx),
                            dmuIdx))
                    .toList());
        }

        return efficiencies;
    }

    private ConstraintsSystem prepareConstraintsSystem(ProblemData data) {
        var constraints = new ConstraintsSet(
                new ArrayList<>(createNonNegativeConstrains(data)),
                new ArrayList<>(Collections.nCopies(data.getInputCount() + data.getOutputCount(), ">=")),
                new ArrayList<>(Collections.nCopies(data.getInputCount() + data.getOutputCount(), 0.0))
        );

        constraints.merge(createModelSpecificConstraints(data));
        constraints.merge(parseCustomWeightConstraints(data));

        return convertConstraintsToConstraintsSystem(constraints);
    }

    public double[][] generateSamples(ConstraintsSystem constraints) {
        return generateSamples(constraints, this.numberOfSamples);
    }

    public abstract double calculateEfficiency(DataFrame<Double> inputs,
                                               DataFrame<Double> outputs,
                                               List<Double> inputsSample,
                                               List<Double> outputsSample,
                                               int dmuIdx);

    private List<double[]> createNonNegativeConstrains(ProblemData data) {
        int variablesCount = data.getInputCount() + data.getOutputCount();
        return IntStream.range(0, variablesCount)
                .boxed()
                .map(idx -> createNonNegativeConstraint(idx, variablesCount))
                .toList();
    }

    protected abstract ConstraintsSet createModelSpecificConstraints(ProblemData data);

    private ConstraintsSet parseCustomWeightConstraints(ProblemData data) {
        return new ConstraintsSet(
                data.getWeightConstraints().stream().map(c -> parseCustomWeightConstraint(data, c)).toList(),
                data.getWeightConstraints().stream().map(x -> x.getOperator().toString()).toList(),
                data.getWeightConstraints().stream().map(Constraint::getRhs).toList());

    }

    public ConstraintsSystem convertConstraintsToConstraintsSystem(ConstraintsSet constraints) {
        return new ConstraintsSystem(
                constraints.lhs().toArray(new double[0][]),
                constraints.dir().toArray(new String[0]),
                constraints.rhs().stream().mapToDouble(x -> x).toArray());
    }

    public double[][] generateSamples(ConstraintsSystem constraints, int numberOfSamples) {
        var runner = new PolytopeRunner(constraints);
        try {
            runner.setAnyStartPoint();
        } catch (UnboundedSystemException | InfeasibleSystemException e) {
            throw new RuntimeException(e);
        }
        return runner.chain(new HitAndRun(random),
                new NCubedThinningFunction(1.0),
                numberOfSamples);
    }

    private double[] createNonNegativeConstraint(int varIdx, int variablesCount) {
        var result = new double[variablesCount];
        result[varIdx] = 1;
        return result;
    }

    private double[] parseCustomWeightConstraint(ProblemData data, Constraint constraint) {
        var lhs = new double[data.getInputCount() + data.getOutputCount()];
        for (var element : constraint.getElements().entrySet()) {
            var index = data.getColumnIndices().get(element.getKey());
            lhs[index] = element.getValue();
        }
        return lhs;
    }


    protected double calculateWeightedSum(List<Double> performances, List<Double> weights) {
        return IntStream.range(0, performances.size())
                .mapToDouble(idx -> performances.get(idx) * weights.get(idx))
                .sum();
    }
}
