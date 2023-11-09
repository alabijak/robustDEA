package put.dea.common.smaa;

import put.dea.common.ProblemData;

public interface SmaaEfficiency<T extends ProblemData> {
    int getNumberOfIntervals();

    void setNumberOfIntervals(int numberOfIntervals);

    DistributionResult efficiencyDistribution(T data);
}
