package put.dea.robustness;

import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

class PerformanceSamplesCollection {
    private final List<Table> inputPerformances;
    private final List<Table> outputPerformances;

    public PerformanceSamplesCollection() {
        this.inputPerformances = new ArrayList<>();
        this.outputPerformances = new ArrayList<>();
    }

    public List<Table> getInputPerformances() {
        return inputPerformances;
    }

    public List<Table> getOutputPerformances() {
        return outputPerformances;
    }

}
