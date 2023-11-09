package put.dea.vdea.hierarchical;

import put.dea.common.ExtremeEfficiency;
import put.dea.vdea.VDEAExtremeEfficiencies;

import java.util.List;
import java.util.stream.IntStream;

public class HierarchicalVDEAExtremeEfficiencies
        implements ExtremeEfficiency<HierarchicalVDEAProblemData> {
    @Override
    public double maxEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxEfficiency(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    public double maxEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx,
                                String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        VDEAExtremeEfficiencies vdeaExtremeEfficiencies = new VDEAExtremeEfficiencies();
        return vdeaExtremeEfficiencies.maxEfficiency(dataForModel, subjectDmuIdx);
    }

    @Override
    public double minEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minEfficiency(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    public double minEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx,
                                String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        VDEAExtremeEfficiencies vdeaExtremeEfficiencies = new VDEAExtremeEfficiencies();
        return vdeaExtremeEfficiencies.minEfficiency(dataForModel, subjectDmuIdx);
    }

    public List<Double> minEfficiencyForAll(HierarchicalVDEAProblemData data,
                                            String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minEfficiency(data, dmu, hierarchyNode))
                .toList();
    }

    public List<Double> maxEfficiencyForAll(HierarchicalVDEAProblemData data,
                                            String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxEfficiency(data, dmu, hierarchyNode))
                .toList();
    }
}
