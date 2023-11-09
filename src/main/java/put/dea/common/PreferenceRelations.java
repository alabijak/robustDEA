package put.dea.common;

import java.util.List;
import java.util.stream.IntStream;

public interface PreferenceRelations<T extends ProblemData> {
    default List<List<Boolean>> checkNecessaryPreferenceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(subjectDmu -> IntStream.range(0, data.getDmuCount())
                        .boxed()
                        .map(relative -> isNecessarilyPreferred(data, subjectDmu, relative))
                        .toList())
                .toList();
    }

    boolean isNecessarilyPreferred(T data, int subjectDmuIdx, int relativeDmuIdx);

    default List<List<Boolean>> checkPossiblePreferenceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(subjectDmu -> IntStream.range(0, data.getDmuCount())
                        .boxed()
                        .map(relative -> isPossiblyPreferred(data, subjectDmu, relative))
                        .toList())
                .toList();
    }

    boolean isPossiblyPreferred(T data, int subjectDmuIdx, int relativeDmuIdx);

}
