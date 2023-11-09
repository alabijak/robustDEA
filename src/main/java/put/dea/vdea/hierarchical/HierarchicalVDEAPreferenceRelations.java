package put.dea.vdea.hierarchical;

import put.dea.common.PreferenceRelations;
import put.dea.vdea.VDEAPreferenceRelations;

import java.util.List;

public class HierarchicalVDEAPreferenceRelations
        implements PreferenceRelations<HierarchicalVDEAProblemData> {

    @Override
    public boolean isNecessarilyPreferred(HierarchicalVDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return isNecessarilyPreferred(data, subjectDmuIdx, relativeDmuIdx, data.getHierarchy().getName());
    }

    public boolean isNecessarilyPreferred(HierarchicalVDEAProblemData data,
                                          int subjectDmuIdx,
                                          int relativeDmuIdx,
                                          String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.isNecessarilyPreferred(dataForModel, subjectDmuIdx, relativeDmuIdx);
    }

    @Override
    public boolean isPossiblyPreferred(HierarchicalVDEAProblemData data,
                                       int subjectDmuIdx,
                                       int relativeDmuIdx) {
        return isPossiblyPreferred(data, subjectDmuIdx, relativeDmuIdx, data.getHierarchy().getName());
    }

    public boolean isPossiblyPreferred(HierarchicalVDEAProblemData data,
                                       int subjectDmuIdx,
                                       int relativeDmuIdx,
                                       String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.isPossiblyPreferred(dataForModel,
                subjectDmuIdx,
                relativeDmuIdx);
    }

    public List<List<Boolean>> checkPossiblePreferenceForAll(HierarchicalVDEAProblemData data,
                                                             String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.checkPossiblePreferenceForAll(dataForModel);
    }

    public List<List<Boolean>> checkNecessaryPreferenceForAll(HierarchicalVDEAProblemData data,
                                                              String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.checkNecessaryPreferenceForAll(dataForModel);
    }
}
