package put.dea.robustness;

import joinery.DataFrame;

import java.util.Random;

public class ImpreciseVDEASmaaPreferenceRelations extends VDEASmaaBase implements SmaaPreferenceRelations<ImpreciseVDEAProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    public ImpreciseVDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public ImpreciseVDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DataFrame<Double> peoi(ImpreciseVDEAProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), true);
        var valueFunctionsSamples = impreciseSmaaUtils.generateValueFunctionSamples(performanceSamples, data);

        var efficiencyMatrix = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                valueFunctionsSamples,
                data.getDmuCount());
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        return preferenceRelationsBase.calculatePeois(efficiencyMatrix);
    }
}
