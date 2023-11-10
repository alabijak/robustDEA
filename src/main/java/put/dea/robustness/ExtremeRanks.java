package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Interface providing methods for finding the extreme (minimal and maximal) ranks
 * of analysed DMUs
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface ExtremeRanks<T extends ProblemData> {
    /**
     * returns the minimal efficiency rank of each DMU
     *
     * @param data data set specification
     * @return list of minimal efficiency ranks for each DMU in the considered data set
     */
    default List<Integer> minRankForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .map(idx -> minRank(data, idx))
                .boxed()
                .toList();
    }

    /**
     * returns the best (minimal) efficiency rank for a DMU with index subjectDmuIdx
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of a DMU under consideration
     * @return the best rank for the analyzed DMU
     */
    int minRank(T data, int subjectDmuIdx);

    default List<Integer> maxRankForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .map(idx -> maxRank(data, idx))
                .boxed()
                .toList();
    }

    /**
     * returns the worst (maximal) efficiency rank for a DMU with index subjectDmuIdx
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of a DMU under consideration
     * @return the worst rank for the analyzed DMU
     */
    int maxRank(T data, int subjectDmuIdx);
}
