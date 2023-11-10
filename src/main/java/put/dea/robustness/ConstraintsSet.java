package put.dea.robustness;

import java.util.List;

record ConstraintsSet(List<double[]> lhs, List<String> dir, List<Double> rhs) {

    public void merge(ConstraintsSet other) {
        lhs.addAll(other.lhs);
        dir.addAll(other.dir);
        rhs.addAll(other.rhs);
    }

}
