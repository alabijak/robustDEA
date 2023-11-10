package put.dea.robustness;

/**
 * Interface for methods for calculation the distribution of efficiency distance to the best DMU
 * (Distance Acceptability Interval Indices)
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface SmaaDistance<T extends ProblemData> {
    /**
     * gets the number of intervals (buckets) in the distribution
     *
     * @return number of intervals
     */
    int getNumberOfIntervals();

    /**
     * calculates the distribution and expected values of distance to the best DMU for all DMUs
     *
     * @param data data set specification
     * @return distribution of the efficiency distance to the best DMU and distance expected values
     */
    DistributionResult distanceDistribution(T data);
}
