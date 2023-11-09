package put.dea.common.smaa;

import put.dea.common.ProblemData;

public interface SmaaRanks<T extends ProblemData> {
    DistributionResult rankDistribution(T data);
}
