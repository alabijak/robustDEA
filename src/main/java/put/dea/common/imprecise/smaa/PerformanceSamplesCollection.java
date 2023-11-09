package put.dea.common.imprecise.smaa;

import joinery.DataFrame;

import java.util.ArrayList;
import java.util.List;

public class PerformanceSamplesCollection {
    private List<DataFrame<Double>> inputPerformances;
    private List<DataFrame<Double>> outputPerformances;

    public PerformanceSamplesCollection() {
        this.inputPerformances = new ArrayList<>();
        this.outputPerformances = new ArrayList<>();
    }

    public List<DataFrame<Double>> getInputPerformances() {
        return inputPerformances;
    }

    public void setInputPerformances(List<DataFrame<Double>> inputPerformances) {
        this.inputPerformances = inputPerformances;
    }

    public List<DataFrame<Double>> getOutputPerformances() {
        return outputPerformances;
    }

    public void setOutputPerformances(List<DataFrame<Double>> outputPerformances) {
        this.outputPerformances = outputPerformances;
    }
}
