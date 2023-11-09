package put.dea.vdea.hierarchical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import put.dea.common.hierarchical.HierarchyNode;

import java.util.Set;

public class HierarchyStructureTests {
    private static HierarchyNode hierarchy;

    @BeforeAll
    public static void initializeHierarchy() {
        var i1Node = new HierarchyNode("i1");
        var i2Node = new HierarchyNode("i2");
        var o1Node = new HierarchyNode("o1");
        var o2Node = new HierarchyNode("o2");

        hierarchy = new HierarchyNode("root");
        var c1 = new HierarchyNode("c1");
        hierarchy.addChild(c1);

        c1.addChild(i1Node);
        c1.addChild(o1Node);

        var c2 = new HierarchyNode("c2");
        hierarchy.addChild(c2);
        c2.addChild(i2Node);
        c2.addChild(o2Node);


    }

    @Test
    public void verifyFactorsSearch() {
        var rootFactors = Set.of("i1", "i2", "o1", "o2");
        var c1Factors = Set.of("i1", "o1");
        var c2Factors = Set.of("i2", "o2");

        var c1 = hierarchy.findNodeByName("c1");
        var c2 = hierarchy.findNodeByName("c2");
        var i1Node = hierarchy.findNodeByName("i1");
        Assertions.assertTrue(compareSets(rootFactors, hierarchy.findAllChildFactors()));
        Assertions.assertTrue(compareSets(c1Factors, c1.findAllChildFactors()));
        Assertions.assertTrue(compareSets(c2Factors, c2.findAllChildFactors()));
        Assertions.assertTrue(compareSets(Set.of("i1"), i1Node.findAllChildFactors()));
    }

    private boolean compareSets(Set<String> expected, Set<String> actual) {
        return expected.size() == actual.size()
                && actual.containsAll(expected);
    }
}
