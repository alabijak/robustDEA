package put.dea.robustness;

import java.util.List;

/**
 * Verifies the presence of the necessary and possible efficiency preference relations
 * for problems with hierarchical structure of inputs and outputs and VDEA model
 */
public class HierarchicalVDEAPreferenceRelations
        implements PreferenceRelations<HierarchicalVDEAProblemData> {

    /**
     * verifies if DMU with index subjectDmuIdx is necessarily preferred to
     * DMU with index relativeDmuIdx
     * at the root level of input/output hierarchy
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNecessarilyPreferred(HierarchicalVDEAProblemData data, int subjectDmuIdx, int relativeDmuIdx) {
        return isNecessarilyPreferred(data, subjectDmuIdx, relativeDmuIdx, data.getHierarchy().getName());
    }

    /**
     * verifies if DMU with index subjectDmuIdx is necessarily preferred to
     * DMU with index relativeDmuIdx
     * at the specified level of input/output hierarchy
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @param hierarchyLevel level of the input/output hierarchy
     * @return boolean value - true if subjectDmuIdx is necessarily preferred to relativeDmuIdx; false otherwise
     */
    public boolean isNecessarilyPreferred(HierarchicalVDEAProblemData data,
                                          int subjectDmuIdx,
                                          int relativeDmuIdx,
                                          String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.isNecessarilyPreferred(dataForModel, subjectDmuIdx, relativeDmuIdx);
    }

    /**
     * verifies if DMU with index subjectDmuIdx is possibly preferred to
     * DMU with index relativeDmuIdx
     * at the root level of input/output hierarchy
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @return {@inheritDoc}
     */
    @Override
    public boolean isPossiblyPreferred(HierarchicalVDEAProblemData data,
                                       int subjectDmuIdx,
                                       int relativeDmuIdx) {
        return isPossiblyPreferred(data, subjectDmuIdx, relativeDmuIdx, data.getHierarchy().getName());
    }

    /**
     * verifies if DMU with index subjectDmuIdx is possibly preferred to
     * DMU with index relativeDmuIdx
     * at the specified level of input/output hierarchy
     *
     * @param data           data set specification
     * @param subjectDmuIdx  index of the subject DMU
     * @param relativeDmuIdx index of the relative DMU
     * @param hierarchyLevel level of the input/output hierarchy
     * @return boolean value - true if subjectDmuIdx is possibly preferred to relativeDmuIdx; false otherwise
     */
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

    /**
     * verifies the presence of the possible efficiency preference relations
     * for all pairs of DMUs
     * at the specified level of the input/output hierarchy
     *
     * @param data           data set specification
     * @param hierarchyLevel level of the input/output hierarchy
     * @return {@link List} of {@link List lists} of boolean indicators representing the presence of the possible efficiency preference
     */
    public List<List<Boolean>> checkPossiblePreferenceForAll(HierarchicalVDEAProblemData data,
                                                             String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.checkPossiblePreferenceForAll(dataForModel);
    }

    /**
     * verifies the presence of the necessary efficiency preference relations
     * for all pairs of DMUs
     * at the specified level of the input/output hierarchy
     *
     * @param data           data set specification
     * @param hierarchyLevel level of the input/output hierarchy
     * @return {@link List} of {@link List lists} of boolean indicators representing the presence of the necessary efficiency preference
     */
    public List<List<Boolean>> checkNecessaryPreferenceForAll(HierarchicalVDEAProblemData data,
                                                              String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var preferenceRelations = new VDEAPreferenceRelations();
        return preferenceRelations.checkNecessaryPreferenceForAll(dataForModel);
    }
}
