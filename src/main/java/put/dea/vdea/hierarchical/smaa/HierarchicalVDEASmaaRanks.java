package put.dea.vdea.hierarchical.smaa;

import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaRanks;
import put.dea.vdea.hierarchical.HierarchicalVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;
import put.dea.vdea.smaa.VDEASmaaRanks;

import java.util.Random;

public class HierarchicalVDEASmaaRanks extends VDEASmaaBase
        implements SmaaRanks<HierarchicalVDEAProblemData> {
    public HierarchicalVDEASmaaRanks(int numberOfSamples) {
        this(numberOfSamples, new Random());
    }

    public HierarchicalVDEASmaaRanks(int numberOfSamples, Random random) {
        super(numberOfSamples, random);
    }

    @Override
    public DistributionResult rankDistribution(HierarchicalVDEAProblemData data) {
        return rankDistribution(data, data.getHierarchy().getName());
    }

    public DistributionResult rankDistribution(HierarchicalVDEAProblemData data,
                                               String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaRanks = new VDEASmaaRanks(numberOfSamples, random);
        return smaaRanks.rankDistribution(dataForModel);
    }
}
