package put.dea.common;

import java.util.List;
import java.util.stream.IntStream;

public interface ExtremeRanks<T extends ProblemData> {
    default List<Integer> minRankForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .map(idx -> minRank(data, idx))
                .boxed()
                .toList();
    }

    int minRank(T data, int subjectDmuIdx);

    default List<Integer> maxRankForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .map(idx -> maxRank(data, idx))
                .boxed()
                .toList();
    }

    int maxRank(T data, int subjectDmuIdx);
}
