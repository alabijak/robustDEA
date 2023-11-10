package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Calculates the extreme efficiency scores
 * for problems with hierarchical structure of inputs and outputs and VDEA model
 */
public class HierarchicalVDEAExtremeEfficiencies
        implements ExtremeEfficiency<HierarchicalVDEAProblemData> {
    /**
     * returns the maximal efficiency score of the specified DMU
     * at the root level of the hierarchy of factors
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of a DMU under consideration
     * @return maximal efficiency score
     */
    @Override
    public double maxEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxEfficiency(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the maximal efficiency score of the specified DMU
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of a DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return maximal efficiency score
     */
    public double maxEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx,
                                String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        VDEAExtremeEfficiencies vdeaExtremeEfficiencies = new VDEAExtremeEfficiencies();
        return vdeaExtremeEfficiencies.maxEfficiency(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the minimal efficiency score of the specified DMU
     * at the root level of the hierarchy of factors
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of a DMU under consideration
     * @return minimal efficiency score
     */
    @Override
    public double minEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minEfficiency(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the minimal efficiency score of the specified DMU
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of a DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return minimal efficiency score
     */
    public double minEfficiency(HierarchicalVDEAProblemData data, int subjectDmuIdx,
                                String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        VDEAExtremeEfficiencies vdeaExtremeEfficiencies = new VDEAExtremeEfficiencies();
        return vdeaExtremeEfficiencies.minEfficiency(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the minimal efficiency score of each DMU
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of minimal efficiency scores for each DMU in the considered data set
     */
    public List<Double> minEfficiencyForAll(HierarchicalVDEAProblemData data,
                                            String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minEfficiency(data, dmu, hierarchyLevel))
                .toList();
    }

    /**
     * returns the maximal efficiency score of each DMU
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of maximal efficiency scores for each DMU in the considered data set
     */
    public List<Double> maxEfficiencyForAll(HierarchicalVDEAProblemData data,
                                            String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxEfficiency(data, dmu, hierarchyLevel))
                .toList();
    }
}
