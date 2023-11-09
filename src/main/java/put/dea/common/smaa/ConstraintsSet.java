package put.dea.common.smaa;

import java.util.ArrayList;
import java.util.List;

public record ConstraintsSet(List<double[]> lhs, List<String> dir, List<Double> rhs) {
    public ConstraintsSet() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void merge(ConstraintsSet other) {
        lhs.addAll(other.lhs);
        dir.addAll(other.dir);
        rhs.addAll(other.rhs);
    }

    public void addConstraint(double[] lhs, String dir, double rhs) {
        this.lhs.add(lhs);
        this.dir.add(dir);
        this.rhs.add(rhs);
    }
}
