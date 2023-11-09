package put.dea.ccr.imprecise.smaa;

import joinery.DataFrame;
import put.dea.ccr.imprecise.CCRImpreciseProblemData;
import put.dea.ccr.smaa.CCRSmaaBase;
import put.dea.common.imprecise.smaa.ImpreciseSmaaUtils;
import put.dea.common.smaa.SmaaPreferenceRelations;
import put.dea.common.smaa.SmaaPreferenceRelationsBase;

import java.util.Random;

public class ImpreciseCCRSmaaPreferenceRelations extends CCRSmaaBase implements SmaaPreferenceRelations<CCRImpreciseProblemData> {

    private final ImpreciseSmaaUtils impreciseSmaaUtils;

    public ImpreciseCCRSmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public ImpreciseCCRSmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
        this.impreciseSmaaUtils = new ImpreciseSmaaUtils(this);
    }

    @Override
    public DataFrame<Double> peoi(CCRImpreciseProblemData data) {
        var weightSamples = generateWeightSamples(data);
        var performanceSamples = impreciseSmaaUtils.generatePerformanceSamples(
                data.getImpreciseInformation(), false);
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        var efficiencies = impreciseSmaaUtils.calculateEfficiencyMatrixForSamples(weightSamples,
                performanceSamples,
                data.getDmuCount());
        return preferenceRelationsBase.calculatePeois(efficiencies);
    }
}
