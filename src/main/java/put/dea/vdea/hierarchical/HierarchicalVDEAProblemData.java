package put.dea.vdea.hierarchical;

import put.dea.common.hierarchical.HierarchyNode;
import put.dea.vdea.VDEAProblemData;
import put.dea.weightConstraints.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HierarchicalVDEAProblemData extends VDEAProblemData {
    private HierarchyNode hierarchy;

    public HierarchicalVDEAProblemData(double[][] inputData, double[][] outputData,
                                       HierarchyNode hierarchy) {
        super(inputData, outputData);
        this.hierarchy = hierarchy;
    }

    public HierarchicalVDEAProblemData(double[][] inputData, double[][] outputData,
                                       List<String> inputNames,
                                       List<String> outputNames,
                                       HierarchyNode hierarchy) {
        super(inputData, outputData, inputNames, outputNames);
        this.hierarchy = hierarchy;
    }

    public HierarchyNode getHierarchy() {
        return hierarchy;
    }

    public VDEAProblemData prepareDataForModel(String hierarchyLevel) {
        if (hierarchy.findNodeByName(hierarchyLevel) == null)
            throw new IllegalArgumentException("Given hierarchy level does not exist in the hierarchy");

        var node = hierarchy.findNodeByName(hierarchyLevel);
        var factors = node.findAllChildFactors();
        var inputs = getInputNames().stream().filter(factors::contains).toList();
        var outputs = getOutputNames().stream().filter(factors::contains).toList();
        var inputsTable = getInputData().retain(inputs.toArray());
        var outputsTable = getOutputData().retain(outputs.toArray());
        var dataForModel = new VDEAProblemData(inputsTable.toModelMatrix(0),
                outputsTable.toModelMatrix(0),
                inputs,
                outputs);
        var constraints = parseWeightConstraints(node);
        dataForModel.setWeightConstraints(constraints);
        dataForModel.setFunctionShapes(getFunctionShapes());
        dataForModel.setLowerBounds(getLowerBounds());
        dataForModel.setUpperBounds(getUpperBounds());
        return dataForModel;
    }


    private List<Constraint> parseWeightConstraints(HierarchyNode node) {
        List<Constraint> transformedConstraints = new ArrayList<>();
        for (var constraint : getWeightConstraints()) {
            var transformedConstraint = parseConstraint(constraint, node);
            if (transformedConstraint != null)
                transformedConstraints.add(transformedConstraint);
        }
        return transformedConstraints;
    }

    private Constraint parseConstraint(Constraint constraint, HierarchyNode node) {
        HierarchyNode parent = null;
        Set<String> parentFactors = null;
        var transformedConstraint = new Constraint(constraint.getOperator(), 0);
        for (var item : constraint.getElements().entrySet()) {
            var currentNode = hierarchy.findNodeByName(item.getKey());
            var currentParent = currentNode.getParent();
            if (parent == null) {
                parent = currentParent;
                parentFactors = parent.findAllChildFactors();
                if (node.findNodeByName(parent.getName()) == null)
                    return null;
            } else if (currentParent != parent)
                throw new IllegalArgumentException("The elements within one constraint must have common parent");
            var currentFactors = currentNode.findAllChildFactors();
            for (var factor : currentFactors) {
                transformedConstraint.getElements().put(factor, item.getValue());
            }
        }

        assert parentFactors != null;
        for (var factor : parentFactors) {
            var currentValue = transformedConstraint.getElements().getOrDefault(factor, 0.0);
            transformedConstraint.getElements().put(factor, currentValue - constraint.getRhs());
        }
        return transformedConstraint;
    }
}
