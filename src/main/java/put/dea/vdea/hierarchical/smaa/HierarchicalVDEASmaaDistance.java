package put.dea.vdea.hierarchical.smaa;

import put.dea.common.smaa.DistributionResult;
import put.dea.common.smaa.SmaaDistance;
import put.dea.vdea.hierarchical.HierarchicalVDEAProblemData;
import put.dea.vdea.smaa.VDEASmaaBase;
import put.dea.vdea.smaa.VDEASmaaDistance;

import java.util.Random;

public class HierarchicalVDEASmaaDistance extends VDEASmaaBase
        implements SmaaDistance<HierarchicalVDEAProblemData> {

    private int numberOfIntervals;

    public HierarchicalVDEASmaaDistance(int numberOfSamples) {
        this(numberOfSamples, 10);
    }

    public HierarchicalVDEASmaaDistance(int numberOfSamples, int numberOfIntervals) {
        this(numberOfSamples, numberOfIntervals, new Random());
    }

    public HierarchicalVDEASmaaDistance(int numberOfSamples, int numberOfIntervals, Random random) {
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
    public DistributionResult distanceDistribution(HierarchicalVDEAProblemData data) {
        return distanceDistribution(data, data.getHierarchy().getName());
    }

    public DistributionResult distanceDistribution(HierarchicalVDEAProblemData data,
                                                   String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var smaaDistance = new VDEASmaaDistance(numberOfSamples,
                numberOfIntervals, random);
        return smaaDistance.distanceDistribution(dataForModel);
    }
}
