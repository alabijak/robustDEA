package put.dea.common;

import put.dea.vdea.VDEAProblemData;

import java.util.List;
import java.util.stream.IntStream;

public interface ExtremeDistances<T extends VDEAProblemData> {
    default List<Double> minDistanceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> minDistance(data, idx))
                .toList();
    }

    double minDistance(T data, int subjectDmuIdx);

    default List<Double> maxDistanceForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> maxDistance(data, idx))
                .toList();
    }

    double maxDistance(T data, int subjectDmuIdx);
}
