package test.java;

import main.java.services.ChangeCalculationService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChangeGenerationTest {

    ChangeCalculationService service = new ChangeCalculationService();
    static int[] standardDenominations = new int[] { 1, 2, 5, 10, 20 };
    static Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsExpected = new HashMap<>();
    Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsActual;

    Map<Integer, Integer> singlePermutation;
    static Map<Integer, Integer> emptyMap;



    @BeforeAll
    static void setup() {
        emptyMap = new HashMap<>();
        allCombinationsExpected = new HashMap<>();

        for (int key : standardDenominations) {
            emptyMap.put(key, 0);
        }
    }

    @AfterEach
    void tearDown() {
        allCombinationsActual.clear();
    }

    @Test
    @DisplayName("One")
    void testOne() {
        ArrayList<Map<Integer,Integer>> combosForOne = new ArrayList<>();
        singlePermutation = new HashMap<>(emptyMap);

        singlePermutation.put(1, 1);
        combosForOne.add(singlePermutation);
        allCombinationsExpected.put(1, combosForOne);

        allCombinationsActual = service.generatePossibleChangeCombinations(1, standardDenominations);
        
        assertEquals(allCombinationsActual.get(1), allCombinationsExpected.get(1));
    }

    @Test
    @DisplayName("Two")
    void testTwo() {
        ArrayList<Map<Integer,Integer>> permutationsTwo = new ArrayList<>();
        Map<Integer, Integer> permSingleTwo = new HashMap<>(emptyMap);
        Map<Integer, Integer> permDoubleOne = new HashMap<>(emptyMap);
        
        permSingleTwo.put(2, 1);
        permDoubleOne.put(1, 2);
        permutationsTwo.add(permSingleTwo);
        permutationsTwo.add(permDoubleOne);

        allCombinationsExpected.put(2, permutationsTwo);

        allCombinationsActual = service.generatePossibleChangeCombinations(2, standardDenominations);

        assertEquals(allCombinationsExpected.get(2), allCombinationsActual.get(2));
    }

    @Test
    @DisplayName("Three")
    void testThree() {
        ArrayList<Map<Integer,Integer>> permutationsThree = new ArrayList<>();
        Map<Integer, Integer> permTwoOne = new HashMap<>(emptyMap);
        Map<Integer, Integer> permTripleOne = new HashMap<>(emptyMap);

        permTwoOne.put(2, 1);
        permTwoOne.put(1,1);
        permTripleOne.put(1, 3);
        permutationsThree.add(permTwoOne);
        permutationsThree.add(permTripleOne);

        allCombinationsExpected.put(3, permutationsThree);

        allCombinationsActual = service.generatePossibleChangeCombinations(3, standardDenominations);

        assertEquals(allCombinationsExpected.get(3), allCombinationsActual.get(3));
    }


    @Test
    @DisplayName("Four")
    void testFour() {
        ArrayList<Map<Integer,Integer>> permutationsFour = new ArrayList<>();
        Map<Integer, Integer> permDoubleTwo = new HashMap<>(emptyMap);
        Map<Integer, Integer> permSingleTwoDoubleOne = new HashMap<>(emptyMap);
        Map<Integer, Integer> permQuadOne = new HashMap<>(emptyMap);

        permDoubleTwo.put(2, 2);
        permutationsFour.add(permDoubleTwo);

        permSingleTwoDoubleOne.put(1, 2);
        permSingleTwoDoubleOne.put(2, 1);
        permutationsFour.add(permSingleTwoDoubleOne);

        permQuadOne.put(1, 4);
        permutationsFour.add(permQuadOne);

        allCombinationsExpected.put(4, permutationsFour);

        allCombinationsActual = service.generatePossibleChangeCombinations(4, standardDenominations);

        assertEquals(allCombinationsExpected.get(4), allCombinationsActual.get(4));
    }

    @Test
    @DisplayName("Five")
    void testFive() {
        ArrayList<Map<Integer, Integer>> permutationsFive = new ArrayList<>();
        Map<Integer, Integer> perm_0_0_1_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_1_2_0_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_3_1_0_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_5_0_0_0_0 = new HashMap<>(emptyMap);

        perm_0_0_1_0_0.put(5, 1);
        permutationsFive.add(perm_0_0_1_0_0);
        
        perm_1_2_0_0_0.put(2, 2);
        perm_1_2_0_0_0.put(1, 1);
        permutationsFive.add(perm_1_2_0_0_0);

        perm_3_1_0_0_0.put(1, 3);
        perm_3_1_0_0_0.put(2, 1);
        permutationsFive.add(perm_3_1_0_0_0);

        perm_5_0_0_0_0.put(1, 5);
        permutationsFive.add(perm_5_0_0_0_0);

        allCombinationsExpected.put(5, permutationsFive);

        allCombinationsActual = service.generatePossibleChangeCombinations(5, standardDenominations);

        assertEquals(allCombinationsExpected.get(5), allCombinationsActual.get(5));
    }

    @Test
    void testJava() {
    Map<Integer, Integer> test = new HashMap<>();
        test.put(2, 5);

        assertFalse(test.containsKey(1));
        assertTrue(test.containsKey(2));
    }
}
