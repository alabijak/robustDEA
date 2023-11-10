package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * interface for extreme efficiencies and super-efficiency scores for CCR model
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface ExtremeEfficiencyCCR<T extends ProblemData> extends ExtremeEfficiency<T> {
    /**
     * calculates the super-efficiency score for all DMUs
     *
     * @param data data set specification
     * @return {@link List} of super-efficiency scores for all DMUs
     */
    default List<Double> superEfficiencyForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .mapToDouble(idx -> superEfficiency(data, idx))
                .boxed().toList();
    }

    /**
     * calculates the super-efficiency score for a specific DMU
     *
     * @param data          data set specification
     * @param subjectDmuIdx index of DMU under consideration
     * @return super-efficiency scores for analyzed DMUs
     */
    double superEfficiency(T data, int subjectDmuIdx);
}
