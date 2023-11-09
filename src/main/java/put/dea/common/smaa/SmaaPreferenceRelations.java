package put.dea.common.smaa;

import joinery.DataFrame;
import put.dea.common.ProblemData;

public interface SmaaPreferenceRelations<T extends ProblemData> {
    DataFrame<Double> peoi(T data);
}
