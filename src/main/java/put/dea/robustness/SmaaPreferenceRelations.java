package put.dea.robustness;

import joinery.DataFrame;

/**
 * Finds the pairwise efficiency outranking indices for all pairs of DMUs
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface SmaaPreferenceRelations<T extends ProblemData> {
    /**
     * calculates the matrix of pairwise efficiency outranking indices (PEOIs) for all pairs of DMUs
     *
     * @param data data set specification
     * @return matrix of PEOIs
     */
    DataFrame<Double> peoi(T data);
}
