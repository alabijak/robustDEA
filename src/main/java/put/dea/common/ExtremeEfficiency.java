package put.dea.common;

import java.util.List;
import java.util.stream.IntStream;

public interface ExtremeEfficiency<T extends ProblemData> {
    default List<Double> maxEfficiencyForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> maxEfficiency(data, idx))
                .toList();
    }

    double maxEfficiency(T data, int subjectDmuIdx);

    default List<Double> minEfficiencyForAll(T data) {
        return IntStream.range(0, data.getDmuCount())
                .boxed()
                .map(idx -> minEfficiency(data, idx))
                .toList();
    }

    double minEfficiency(T data, int subjectDmuIdx);

}
