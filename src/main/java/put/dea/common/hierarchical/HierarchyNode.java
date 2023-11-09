package put.dea.common.hierarchical;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HierarchyNode {
    private final String name;
    private final Map<String, HierarchyNode> children = new HashMap<>();

    private HierarchyNode parent;

    public HierarchyNode(String name) {
        this.name = name;
        this.parent = null;
    }

    public HierarchyNode(String name, HierarchyNode parent) {
        this.name = name;
        parent.addChild(this);
    }

    public void addChild(HierarchyNode child) {
        this.children.put(child.name, child);
        child.parent = this;
    }

    public String getName() {
        return name;
    }

    public HierarchyNode getParent() {
        return parent;
    }

    public HierarchyNode findNodeByName(String name) {
        if (this.name.equals(name))
            return this;
        if (children.containsKey(name))
            return children.get(name);
        for (var child : children.values()) {
            var node = child.findNodeByName(name);
            if (node != null)
                return node;
        }
        return null;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public List<HierarchyNode> getChildren() {
        return children.values().stream().toList();
    }

    public Set<String> findAllChildFactors() {
        if (isLeaf())
            return Set.of(this.name);

        return this.children.values()
                .stream()
                .flatMap(child -> child.findAllChildFactors().stream())
                .collect(Collectors.toSet());
    }

}
