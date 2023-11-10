package put.dea.robustness;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Calculates the extreme efficiency distances to the best DMU and super-distance measure
 * for problems with hierarchical structure of inputs and outputs and VDEA model
 */
public class HierarchicalVDEAExtremeDistances
        implements ExtremeDistances<HierarchicalVDEAProblemData> {
    /**
     * returns minimal distance of each DMU to the best one
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of minimal distances for each DMU in the considered data set
     */
    public List<Double> minDistanceForAll(HierarchicalVDEAProblemData data,
                                          String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> minDistance(data, dmu, hierarchyLevel))
                .toList();
    }

    /**
     * returns minimal distance of given to the best one
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of the DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return minimal distance of DMU with index subjectDmuIdx to the best unit
     */
    public double minDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeDistances = new VDEAExtremeDistances();
        return vdeaExtremeDistances.minDistance(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the maximal distance of each DMU to the best one
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy of factors
     * @return {@link List} of the maximal distances for each DMU in the considered data set
     */
    public List<Double> maxDistanceForAll(HierarchicalVDEAProblemData data,
                                          String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToObj(dmu -> maxDistance(data, dmu, hierarchyLevel))
                .toList();
    }

    /**
     * returns the maximal distance of a specific to the best one
     * at given level of the hierarchy of factors
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of the DMU under consideration
     * @param hierarchyLevel level of the hierarchy of factors
     * @return maximal distance of DMU with index subjectDmuIdx to the best unit
     */
    public double maxDistance(HierarchicalVDEAProblemData data,
                              int subjectDmuIdx,
                              String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeEfficiencies = new VDEAExtremeDistances();
        return vdeaExtremeEfficiencies.maxDistance(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the minimal distance of the specified DMU to the best one
     * when considering the root level of the factors' hierarchy
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of the DMU under consideration
     * @return minimal distance of the specified DMU to the best one
     */
    @Override
    public double minDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return minDistance(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the maximal distance of the specified DMU to the best one
     * when considering the root level of the factors' hierarchy
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of the DMU under consideration
     * @return maximal distance of the specified DMU to the best one
     */
    @Override
    public double maxDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return maxDistance(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the super-distance measure of the specified DMU to the best one
     * when considering the root level of the factors' hierarchy
     *
     * @param data          object containing information about the analyzed data set
     * @param subjectDmuIdx index of the DMU under consideration
     * @return super-distance of the specified DMU to the best one
     */
    @Override
    public double superDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx) {
        return superDistance(data, subjectDmuIdx, data.getHierarchy().getName());
    }

    /**
     * returns the super-distance measure of the specified DMU to the best one
     * at specified level of the factors' hierarchy
     *
     * @param data           object containing information about the analyzed data set
     * @param subjectDmuIdx  index of the DMU under consideration
     * @param hierarchyLevel level of the hierarchy
     * @return super-distance of the specified DMU to the best one
     */
    public double superDistance(HierarchicalVDEAProblemData data, int subjectDmuIdx, String hierarchyLevel) {
        var dataForModel = data.prepareDataForModel(hierarchyLevel);
        var vdeaExtremeDistances = new VDEAExtremeDistances();
        return vdeaExtremeDistances.superDistance(dataForModel, subjectDmuIdx);
    }

    /**
     * returns the super-distance measure of all DMUs to the best one
     * at specified level of the factors' hierarchy
     *
     * @param data           object containing information about the analyzed data set
     * @param hierarchyLevel level of the hierarchy
     * @return {@link List} of super-distance values of DMUs to the best one
     */
    public List<Double> superDistanceForAll(HierarchicalVDEAProblemData data, String hierarchyLevel) {
        return IntStream.range(0, data.getDmuCount())
                .mapToDouble(idx -> superDistance(data, idx, hierarchyLevel))
                .boxed()
                .toList();
    }
}
