package put.dea.robustness;

/**
 * Interface for methods for calculation the distribution of efficiency scores
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface SmaaEfficiency<T extends ProblemData> {
    /**
     * gets the number of intervals (buckets) in the distribution
     *
     * @return number of intervals
     */
    int getNumberOfIntervals();

    /**
     * calculates the distribution and expected values of efficiency scores for all DMUs
     *
     * @param data data set specification
     * @return distribution of efficiency scores and their expected values
     */
    DistributionResult efficiencyDistribution(T data);
}
