package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Interface providing methods for finding the extreme (minimal and maximal) efficiency distance
 * of analysed DMU to the best one
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface ExtremeDistances<T extends VDEAProblemData> {
    /**
     * returns minimal distance of each DMU to the best one
     *
     * @param data data set specification
     * @return list of minimal distances for each DMU in the considered data set
     */
    default List<Double> minDistanceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> minDistance(data, idx))
                .toList();
    }

    /**
     * returns the best (minimal) distance of a specific DMU to the best one
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of the DMU under consideration
     * @return the best (minimal) possible distance of DMU with index subjectDmuIdx to the best DMU
     */
    double minDistance(T data, int subjectDmuIdx);

    /**
     * returns maximal distance of each DMU to the best one
     *
     * @param data data set specification
     * @return list of maximal distances for each DMU in the considered data set
     */
    default List<Double> maxDistanceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> maxDistance(data, idx))
                .toList();
    }

    /**
     * returns the worst (maximal) distance of a specific DMU to the best one
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of the DMU under consideration
     * @return the worst (maximal) possible distance of DMU with index subjectDmuIdx to the best DMU
     */
    double maxDistance(T data, int subjectDmuIdx);

    /**
     * Calculates the super-distance to the best unit for all DMUs
     *
     * @param data data set specification
     * @return {@link List} of super-distance values for all DMUs
     */
    default List<Double> superDistanceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .mapToDouble(idx -> superDistance(data, idx))
                .boxed()
                .toList();
    }

    /**
     * Calculates the super-distance to the best unit for a specified unit DMUs
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of a DMU under consideration
     * @return value of super-distance for specified DMU
     */
    double superDistance(T data, int subjectDmuIdx);
}
