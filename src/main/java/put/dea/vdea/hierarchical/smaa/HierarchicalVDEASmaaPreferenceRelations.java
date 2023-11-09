package put.dea.vdea.hierarchical.smaa;

import joinery.DataFrame;
import put.dea.common.smaa.SmaaPreferenceRelations;
import put.dea.vdea.hierarchical.HierarchicalVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;
import put.dea.vdea.smaa.VDEASmaaPreferenceRelations;

import java.util.Random;

public class HierarchicalVDEASmaaPreferenceRelations extends VDEASmaaBase
        implements SmaaPreferenceRelations<HierarchicalVDEAProblemData> {
    public HierarchicalVDEASmaaPreferenceRelations(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public HierarchicalVDEASmaaPreferenceRelations(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DataFrame<Double> peoi(HierarchicalVDEAProblemData data) {
        return peoi(data, data.getHierarchy().getName());
    }

    public DataFrame<Double> peoi(HierarchicalVDEAProblemData data, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaPreferenceRelations = new VDEASmaaPreferenceRelations(numberOfSamples, random);
        return smaaPreferenceRelations.peoi(dataForModel);
    }
}
