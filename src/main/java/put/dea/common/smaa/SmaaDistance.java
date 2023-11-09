package put.dea.common.smaa;

import put.dea.common.ProblemData;

public interface SmaaDistance<T extends ProblemData> {
    int getNumberOfIntervals();

    void setNumberOfIntervals(int numberOfIntervals);

    DistributionResult distanceDistribution(T data);
}
