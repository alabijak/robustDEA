package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Calculates the extreme efficiency ranks
 * for problems with hierarchical structure of inputs and outputs and VDEA model
 */
public class HierarchicalVDEAExtremeRanks
        implements ExtremeRanks<HierarchicalVDEAProblemData> {

    /**
     * returns the best (minimal) efficiency rank of each DMU
     * at the given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of minimal efficiency ranks for each DMU in the considered data set
     */
    public List<Integer> minRanksForAll(HierarchicalVDEAProblemData data,
                                        String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minRank(data, dmu, hierarchyLevel))
                .toList();
    }

    /**
     * returns the best (minimal) efficiency rank for a specified DMU
     * at the given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of a DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return minimal efficiency rank for a specified DMU
     */
    public int minRank(HierarchicalVDEAProblemData data, int subjectDmuIdx, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeRanks = new VDEAExtremeRanks();
        return vdeaExtremeRanks.minRank(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the worst (maximal) efficiency rank of each DMU
     * at the given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of maximal efficiency ranks for each DMU in the considered data set
     */
    public List<Integer> maxRankForAll(HierarchicalVDEAProblemData data,
                                       String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxRank(data, dmu, hierarchyLevel))
                .toList();
    }

    /**
     * returns the worst (maximal) efficiency rank for a specified DMU
     * at the given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of a DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return maximal efficiency rank for a specified DMU
     */
    public int maxRank(HierarchicalVDEAProblemData data,
                       int subjectDmuIdx,
                       String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeRanks = new VDEAExtremeRanks();
        return vdeaExtremeRanks.maxRank(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the best (minimal) efficiency rank for a specified DMU
     * at the root level of the hierarchy of factors
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of a DMU under consideration
     * @return minimal efficiency rank for a specified DMU
     */
    @Override
    public int minRank(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minRank(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the worst (maximal) efficiency rank for a specified DMU
     * at the root level of the hierarchy of factors
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of a DMU under consideration
     * @return maximal efficiency rank for a specified DMU
     */
    @Override
    public int maxRank(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxRank(data, subjectDmuIdx, data.getHierarchy().getName());
    }
}
