package put.dea.robustness;

/**
 * Finds the rank distribution (efficiency rank acceptability indices)
 * and expected ranks for all DMUs in the data set
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface SmaaRanks<T extends ProblemData> {
    /**
     * Finds the rank distribution (efficiency rank acceptability indices)
     * and expected ranks for all DMUs in the data set
     *
     * @param data data set specification
     * @return rank distribution and expected ranks
     */
    DistributionResult rankDistribution(T data);
}
