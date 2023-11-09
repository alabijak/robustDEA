package put.dea.vdea.smaa;

import joinery.DataFrame;
import put.dea.common.smaa.SmaaPreferenceRelations;
import put.dea.common.smaa.SmaaPreferenceRelationsBase;
import put.dea.vdea.VDEAProblemData;

import java.util.Random;

public class VDEASmaaPreferenceRelations extends VDEASmaaBase implements SmaaPreferenceRelations<VDEAProblemData> {

    public VDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public VDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DataFrame<Double> peoi(VDEAProblemData data) {
        var inputs = performanceToValueConverter.transformInputsToUtilities(data);
        var outputs = performanceToValueConverter.transformOutputsToUtilities(data);
        var efficiencyMatrix = calculateEfficiencyMatrix(data, inputs, outputs);
        var preferenceRelationsBase = new SmaaPreferenceRelationsBase(numberOfSamples);
        return preferenceRelationsBase.calculatePeois(efficiencyMatrix);
    }
}
