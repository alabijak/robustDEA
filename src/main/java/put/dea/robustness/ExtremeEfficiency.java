package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Interface providing methods for finding the extreme (minimal and maximal) efficiency scores
 * of analysed DMUs
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface ExtremeEfficiency<T extends ProblemData> {
    /**
     * returns the maximal efficiency score of each DMU
     *
     * @param data data set specification
     * @return list of maximal efficiency scores for each DMU in the considered data set
     */
    default List<Double> maxEfficiencyForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> maxEfficiency(data, idx))
                .toList();
    }

    /**
     * returns the maximal efficiency score of DMU with index subjectDmuIdx
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of a DMU under consideration
     * @return the maximal efficiency score of the analyzed DMU
     */
    double maxEfficiency(T data, int subjectDmuIdx);

    /**
     * returns the minimal efficiency score of each DMU to the best one
     *
     * @param data data set specification
     * @return list of minimal efficiency scores for each DMU in the considered data set
     */
    default List<Double> minEfficiencyForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> minEfficiency(data, idx))
                .toList();
    }

    /**
     * returns the minimal efficiency score of DMU with index subjectDmuIdx
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of a DMU under consideration
     * @return the minimal efficiency score of the analyzed DMU
     */
    double minEfficiency(T data, int subjectDmuIdx);

}
