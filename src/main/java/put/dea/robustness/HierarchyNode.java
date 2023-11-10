package put.dea.robustness;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class representing the node in the input/output hierarchy tree
 */
public class HierarchyNode {
    private final String name;
    private final Map<String, HierarchyNode> children = new HashMap<>();

    private HierarchyNode parent;

    /**
     * Creates new node with given name
     *
     * @param name name
     */
    public HierarchyNode(String name) {
        this.name = name;
        this.parent = null;
    }

    /**
     * Creates new node with given name and parent node
     *
     * @param name   new node name
     * @param parent parent node
     */
    public HierarchyNode(String name, HierarchyNode parent) {
        this.name = name;
        parent.addChild(this);
    }

    /**
     * adds child to current node
     *
     * @param child child node
     */
    public void addChild(HierarchyNode child) {
        this.children.put(child.name, child);
        child.parent = this;
    }

    /**
     * returns name of the node
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * returns parent of this node
     *
     * @return parent node
     */
    public HierarchyNode getParent() {
        return parent;
    }

    /**
     * finds node with given name in the hierarchy below current node
     *
     * @param name node to be found
     * @return node with given name of null if not found
     */
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

    /**
     * returns information if current node is leaf
     *
     * @return boolean flag - true if this is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    /**
     * finds all last level factors (leafs) being children of the current node
     *
     * @return set of names of factors
     */
    public Set<String> findAllChildFactors() {
        if (isLeaf())
            return Set.of(this.name);

        return this.children.values()
                .stream()
                .flatMap(child -> child.findAllChildFactors().stream())
                .collect(Collectors.toSet());
    }

}
