package put.dea.vdea.hierarchical;

import put.dea.common.ExtremeDistances;
import put.dea.vdea.VDEAExtremeDistances;

import java.util.List;
import java.util.stream.IntStream;

public class HierarchicalVDEAExtremeDistances
        implements ExtremeDistances<HierarchicalVDEAProblemData> {
    public List<Double> minDistanceForAll(HierarchicalVDEAProblemData data,
                                          String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minDistance(data, dmu, hierarchyNode))
                .toList();
    }

    public double minDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeDistances = new VDEAExtremeDistances();
        return vdeaExtremeDistances.minDistance(dataForModel, subjectDmuIdx);
    }

    public List<Double> maxDistanceForAll(HierarchicalVDEAProblemData data,
                                          String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxDistance(data, dmu, hierarchyNode))
                .toList();
    }

    public double maxDistance(HierarchicalVDEAProblemData data,
                              int subjectDmuIdx,
                              String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeEfficiencies = new VDEAExtremeDistances();
        return vdeaExtremeEfficiencies.maxDistance(dataForModel, subjectDmuIdx);
    }

    @Override
    public double minDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minDistance(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    @Override
    public double maxDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxDistance(data, subjectDmuIdx, data.getHierarchy().getName());
    }
}
