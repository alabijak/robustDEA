package put.dea.vdea.hierarchical.smaa;

import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaEfficiency;
import put.dea.vdea.hierarchical.HierarchicalVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;
import put.dea.vdea.smaa.VDEASmaaEfficiency;

import java.util.Random;

public class HierarchicalVDEASmaaEfficiency extends VDEASmaaBase
        implements SmaaEfficiency<HierarchicalVDEAProblemData> {

    private int numberOfIntervals;

    public HierarchicalVDEASmaaEfficiency(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    public HierarchicalVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public HierarchicalVDEASmaaEfficiency(int numberOfSamples, int numberOfIntervals, Random random) {
        super(numberOfSamples, random);
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

    @Override
    public void setNumberOfIntervals(int numberOfIntervals) {
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public DistributionResult efficiencyDistribution(HierarchicalVDEAProblemData data) {
        return efficiencyDistribution(data, data.getHierarchy().getName());
    }

    public DistributionResult efficiencyDistribution(HierarchicalVDEAProblemData data,
                                                     String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaEfficiency = new VDEASmaaEfficiency(numberOfSamples,
                numberOfIntervals, random);
        return smaaEfficiency.efficiencyDistribution(dataForModel);
    }
}
