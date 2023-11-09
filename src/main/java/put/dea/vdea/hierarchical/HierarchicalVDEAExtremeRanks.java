package put.dea.vdea.hierarchical;

import put.dea.common.ExtremeRanks;
import put.dea.vdea.VDEAExtremeEfficiencies;
import put.dea.vdea.VDEAExtremeRanks;

import java.util.List;
import java.util.stream.IntStream;

public class HierarchicalVDEAExtremeRanks
        implements ExtremeRanks<HierarchicalVDEAProblemData> {
    public double minEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx,
                                String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        VDEAExtremeEfficiencies vdeaExtremeEfficiencies = new VDEAExtremeEfficiencies();
        return vdeaExtremeEfficiencies.minEfficiency(dataForModel, subjectDmuIdx);
    }

    public List<Integer> minRanksForAll(HierarchicalVDEAProblemData data,
                                        String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minRank(data, dmu, hierarchyNode))
                .toList();
    }

    public int minRank(HierarchicalVDEAProblemData data, int subjectDmuIdx, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeRanks = new VDEAExtremeRanks();
        return vdeaExtremeRanks.minRank(dataForModel, subjectDmuIdx);
    }

    public List<Integer> maxRankForAll(HierarchicalVDEAProblemData data,
                                       String hierarchyNode) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxRank(data, dmu, hierarchyNode))
                .toList();
    }

    public int maxRank(HierarchicalVDEAProblemData data,
                       int subjectDmuIdx,
                       String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeRanks = new VDEAExtremeRanks();
        return vdeaExtremeRanks.maxRank(dataForModel, subjectDmuIdx);
    }

    @Override
    public int minRank(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minRank(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    @Override
    public int maxRank(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxRank(data, subjectDmuIdx, data.getHierarchy().getName());
    }
}
