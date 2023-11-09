package put.dea.ccr.smaa;

import joinery.DataFrame;
import put.dea.common.ProblemData;
import put.dea.common.smaa.SmaaPreferenceRelations;
import put.dea.common.smaa.SmaaPreferenceRelationsBase;

import java.util.Random;

public class CCRSmaaPreferenceRelations extends CCRSmaaBase implements SmaaPreferenceRelations {

    public CCRSmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public CCRSmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DataFrame<Double> peoi(ProblemData data) {
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        var efficiencies = calculateEfficiencyMatrix(data);
        return preferenceRelationsBase.calculatePeois(efficiencies);
    }
}
