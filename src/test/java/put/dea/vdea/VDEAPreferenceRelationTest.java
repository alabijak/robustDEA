package put.dea.vdea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class VDEAPreferenceRelationTest extends VDEATestBase {
    private static VDEAPreferenceRelations preferenceRelations;

    @BeforeAll
    public static void initializeRelations() {
        preferenceRelations = new VDEAPreferenceRelations();
    }

    @Test
    public void necessaryRelationsWithLinearFunctions() {
        var actual = preferenceRelations.checkNecessaryPreferenceForAll(data);
        verifyNecessaryRelationsWithoutConstraints(actual);
    }

    private void verifyNecessaryRelationsWithoutConstraints(List<List<Boolean>> actualPreferences) {
        var expectedRow0 = List.of(true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        var expectedRow10 = List.of(false, true, true, true, false, false, true, true, true, false, true, true, false, true, false, false, false, false, false, false);
        var expectedRow19 = List.of(false, true, true, true, false, false, true, true, true, true, false, true, false, true, true, false, false, false, true, true);

        verifyTrueRelationsCount(actualPreferences, 65);
        verifyPreferenceRelationWithItself(actualPreferences);
        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow10, actualPreferences.get(10));
        Assertions.assertIterableEquals(expectedRow19, actualPreferences.get(19));
    }

    private void verifyTrueRelationsCount(List<List<Boolean>> actualRelations, int expectedCount) {
        var count = actualRelations.stream().flatMap(Collection::stream).filter(x -> x).count();
        Assertions.assertEquals(expectedCount, count);
    }

    private void verifyPreferenceRelationWithItself(List<List<Boolean>> actualRelations) {
        Assertions.assertTrue(IntStream.range(0, actualRelations.size())
                .boxed().allMatch(idx -> actualRelations.get(idx).get(idx)));
    }

    @Test
    public void necessaryRelationsWithLinearFunctionsAndBoundsProvided() {
        addInputOutputBoundaries();
        var actual = preferenceRelations.checkNecessaryPreferenceForAll(data);
        verifyNecessaryRelationsWithoutConstraints(actual);
    }

    @Test
    public void possibleRelationsWithLinearFunctions() {
        var actual = preferenceRelations.checkPossiblePreferenceForAll(data);
        verifyPossibleRelationsWithoutConstraints(actual);
    }

    private void verifyPossibleRelationsWithoutConstraints(List<List<Boolean>> actualPreferences) {
        var expectedRow0 = List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        var expectedRow2 = List.of(true, true, true, true, false, true, false, true, true, true, false, false, true, true, false, true, false, true, false, false);
        var expectedRow13 = List.of(true, true, true, true, false, false, true, true, false, true, false, true, true, true, false, true, true, true, true, false);
        verifyTrueRelationsCount(actualPreferences, 356);
        verifyPreferenceRelationWithItself(actualPreferences);
        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow2, actualPreferences.get(2));
        Assertions.assertIterableEquals(expectedRow13, actualPreferences.get(13));
    }

    @Test
    public void possibleRelationsWithLinearFunctionsAndBoundsProvided() {
        addInputOutputBoundaries();
        var actual = preferenceRelations.checkPossiblePreferenceForAll(data);
        verifyPossibleRelationsWithoutConstraints(actual);
    }

    @Test
    public void necessaryRelationsWithFunctionShapes() {
        addFunctionShapes();
        var actual = preferenceRelations.checkNecessaryPreferenceForAll(data);
        verifyNecessaryRelationsWithoutConstraints(actual);
    }

    @Test
    public void possibleRelationsWithFunctionShapes() {
        addFunctionShapes();
        var actual = preferenceRelations.checkPossiblePreferenceForAll(data);
        verifyPossibleRelationsWithoutConstraints(actual);
    }


    @Test
    public void necessaryRelationsWithFunctionShapesAndWeightConstraints() {
        addFunctionShapes();
        addWeightConstraints();
        var actualPreferences = preferenceRelations.checkNecessaryPreferenceForAll(data);

        verifyPreferenceRelationWithItself(actualPreferences);
        verifyTrueRelationsCount(actualPreferences, 114);
        var expectedRow0 = List.of(true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        var expectedRow9 = List.of(false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false);
        var expectedRow19 = List.of(false, true, true, true, false, false, true, true, true, true, false, true, false, true, true, false, true, false, true, true);

        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow9, actualPreferences.get(9));
        Assertions.assertIterableEquals(expectedRow19, actualPreferences.get(19));
    }


    @Test
    public void possibleRelationsWithFunctionShapesAndWeightConstraints() {
        addFunctionShapes();
        addWeightConstraints();
        var actualPreferences = preferenceRelations.checkPossiblePreferenceForAll(data);

        verifyPreferenceRelationWithItself(actualPreferences);
        verifyTrueRelationsCount(actualPreferences, 307);
        var expectedRow0 = List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        var expectedRow9 = List.of(true, true, true, false, false, false, false, false, false, true, false, true, false, true, false, false, true, false, false, false);
        var expectedRow19 = List.of(true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true);

        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow9, actualPreferences.get(9));
        Assertions.assertIterableEquals(expectedRow19, actualPreferences.get(19));
    }

    @Test
    public void necessaryRelationsWithLinearFunctionsAndWeightConstraints() {
        addWeightConstraints();
        var actualPreferences = preferenceRelations.checkNecessaryPreferenceForAll(data);

        verifyPreferenceRelationWithItself(actualPreferences);
        verifyTrueRelationsCount(actualPreferences, 89);
        var expectedRow0 = List.of(true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        var expectedRow9 = List.of(false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false);
        var expectedRow19 = List.of(false, true, true, true, false, false, true, true, true, true, false, true, false, true, true, false, true, false, true, true);

        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow9, actualPreferences.get(9));
        Assertions.assertIterableEquals(expectedRow19, actualPreferences.get(19));
    }


    @Test
    public void possibleRelationsWithLinearFunctionsAndWeightConstraints() {
        addWeightConstraints();
        var actualPreferences = preferenceRelations.checkPossiblePreferenceForAll(data);

        verifyPreferenceRelationWithItself(actualPreferences);
        verifyTrueRelationsCount(actualPreferences, 332);
        var expectedRow0 = List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        var expectedRow9 = List.of(true, true, true, false, false, false, true, false, false, true, false, true, false, true, false, true, true, false, false, false);
        var expectedRow19 = List.of(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);

        Assertions.assertIterableEquals(expectedRow0, actualPreferences.get(0));
        Assertions.assertIterableEquals(expectedRow9, actualPreferences.get(9));
        Assertions.assertIterableEquals(expectedRow19, actualPreferences.get(19));
    }
}
