package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Interface providing methods for verification of presence of the necessary and possible efficiency preference relations
 *
 * @param <T> type of object containing data describing the analyzed problem
 */
public interface PreferenceRelations<T extends ProblemData> {
    /**
     * verifies the presence of necessary efficiency preference relations for all pairs of DMUs
     *
     * @param data data set specification
     * @return {@link List} of {@link List lists} of boolean indicators representing the presence of necessary efficiency preference
     */
    default List<List<Boolean>> checkNecessaryPreferenceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(subjectDmu -> IntStream.range(0, data.getDmuCount())
                        .boxed()
                        .map(relative -> isNecessarilyPreferred(data, subjectDmu, relative))
                        .toList())
                .toList();
    }

    /**
     * verifies if DMU with index subjectDmuIdx is necessarily preferred to DMU with index relativeDmuIdx
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @return boolean value - true if subjectDmuIdx is necessarily preferred to relativeDmuIdx; false otherwise
     */
    boolean isNecessarilyPreferred(T data, int subjectDmuIdx, int relativeDmuIdx);

    /**
     * verifies the presence of the possible efficiency preference relations for all pairs of DMUs
     *
     * @param data data set specification
     * @return {@link List} of {@link List lists} of boolean indicators representing the presence of the possible efficiency preference
     */
    default List<List<Boolean>> checkPossiblePreferenceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(subjectDmu -> IntStream.range(0, data.getDmuCount())
                        .boxed()
                        .map(relative -> isPossiblyPreferred(data, subjectDmu, relative))
                        .toList())
                .toList();
    }

    /**
     * verifies if DMU with index subjectDmuIdx is possibly preferred to DMU with index relativeDmuIdx
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @return boolean value - true if subjectDmuIdx is possibly preferred to relativeDmuIdx; false otherwise
     */
    boolean isPossiblyPreferred(T data, int subjectDmuIdx, int relativeDmuIdx);

}
