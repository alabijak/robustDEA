package put.dea.common.smaa;

import java.util.ArrayList;
import java.util.List;

public record ConstraintsSet(List<double[]> lhs, List<String> dir, List<Double> rhs) {

    public void merge(ConstraintsSet other) {
        lhs.addAll(other.lhs);
        dir.addAll(other.dir);
        rhs.addAll(other.rhs);
    }

}
