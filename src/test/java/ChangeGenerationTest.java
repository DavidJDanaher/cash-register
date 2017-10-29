package test.java;

import main.java.resources.RegisterContentsFactory;
import main.java.services.ChangeCalculationService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeGenerationTest {


    static int[] standardDenominations = new int[] { 20, 10, 5, 2, 1 };
    static RegisterContentsFactory factory = new RegisterContentsFactory(standardDenominations);
    static ChangeCalculationService service = new ChangeCalculationService(factory);

    static Map<Integer, Integer> sufficientRegister = factory.getContents(new int[] { 9, 9, 9, 9, 9 });;
    static Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsExpected = new HashMap<>();
    static Map<Integer, Integer> emptyMap;
    Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsActual;
    Map<Integer, Integer> singlePermutation;

    @BeforeAll
    static void setup() {
        emptyMap = new HashMap<>();
        allCombinationsExpected = new HashMap<>();

//        factory = new RegisterContentsFactory(standardDenominations);
//        service = new ChangeCalculationService(factory);
//        sufficientRegister = factory.getContents(new int[] { 9, 9, 9, 9, 9 });

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

        allCombinationsActual = service.generatePossibleChangeCombinations(1, sufficientRegister);
        
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

        allCombinationsActual = service.generatePossibleChangeCombinations(2, sufficientRegister);

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

        allCombinationsActual = service.generatePossibleChangeCombinations(3, sufficientRegister);

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

        allCombinationsActual = service.generatePossibleChangeCombinations(4, sufficientRegister);

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

        allCombinationsActual = service.generatePossibleChangeCombinations(5, sufficientRegister);

        assertEquals(allCombinationsExpected.get(5), allCombinationsActual.get(5));
    }

    @Test
    @DisplayName("Six")
    void testSix() {
        ArrayList<Map<Integer, Integer>> permutationsSix = new ArrayList<>();
        Map<Integer, Integer> perm_1_0_1_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_0_3_0_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_2_2_0_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_4_1_0_0_0 = new HashMap<>(emptyMap);
        Map<Integer, Integer> perm_6_0_0_0_0 = new HashMap<>(emptyMap);

        perm_1_0_1_0_0.put(5, 1);
        perm_1_0_1_0_0.put(1, 1);
        permutationsSix.add(perm_1_0_1_0_0);

        perm_0_3_0_0_0.put(2, 3);
        permutationsSix.add(perm_0_3_0_0_0);

        perm_2_2_0_0_0.put(1, 2);
        perm_2_2_0_0_0.put(2, 2);
        permutationsSix.add(perm_2_2_0_0_0);

        perm_4_1_0_0_0.put(1, 4);
        perm_4_1_0_0_0.put(2, 1);
        permutationsSix.add(perm_4_1_0_0_0);

        perm_6_0_0_0_0.put(1, 6);
        permutationsSix.add(perm_6_0_0_0_0);

        allCombinationsExpected.put(6, permutationsSix);

        allCombinationsActual = service.generatePossibleChangeCombinations(6, sufficientRegister);

        assertEquals(allCombinationsExpected.get(6), allCombinationsActual.get(6));
    }

//    @Test
//    @DisplayName("A giant test for twenty")
//    void testTwenty() {
//        ArrayList<Map<Integer, Integer>> permutationsTwenty = new ArrayList<>();
//        Map<Integer, Integer> perm_0_0_0_0_1 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_0_0_0_2_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_0_0_2_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_1_2_1_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_3_1_1_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_5_0_1_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_0_5_0_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_2_4_0_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_4_3_0_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_6_2_0_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_8_1_0_1_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_10_0_0_1_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_0_0_4_0_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_1_2_3_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_3_1_3_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_5_0_3_0_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_0_5_2_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_2_4_2_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_4_3_2_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_6_2_2_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_8_1_2_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_10_0_2_0_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_1_7_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_3_6_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_5_5_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_7_4_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_9_3_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_11_2_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_13_1_1_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_15_0_1_0_0 = new HashMap<>(emptyMap);
//
//        Map<Integer, Integer> perm_0_10_0_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_2_9_0_0_0 = new HashMap<>(emptyMap);
//        Map<Integer, Integer> perm_4_8_0_0_0 = new HashMap<>(emptyMap);
//
//        perm_0_0_0_0_1.put(20, 1);
//        permutationsTwenty.add(perm_0_0_0_0_1);
//
//        perm_0_0_0_2_0.put(10, 2);
//        permutationsTwenty.add(perm_0_0_0_2_0);
//
//        perm_0_0_2_1_0.put(5, 2);
//        perm_0_0_2_1_0.put(10, 1);
//        permutationsTwenty.add(perm_0_0_2_1_0);
//
//        perm_1_2_1_1_0.put(10, 1);
//        perm_1_2_1_1_0.put(5, 1);
//        perm_1_2_1_1_0.put(2, 2);
//        perm_1_2_1_1_0.put(1, 1);
//        permutationsTwenty.add(perm_1_2_1_1_0);
//
//        perm_0_0_4_0_0.put(5, 4);
//        permutationsTwenty.add(perm_0_0_4_0_0);
//
//        perm_1_2_3_0_0.put(5, 3);
//        perm_1_2_3_0_0.put(2, 2);
//        perm_1_2_3_0_0.put(1, 1);
//        permutationsTwenty.add(perm_1_2_3_0_0);
//
//        allCombinationsExpected.put(6, permutationsTwenty);
//
//        allCombinationsActual = service.generatePossibleChangeCombinations(6, standardDenominations, sufficientRegister);
//
//        assertEquals(allCombinationsExpected.get(6), allCombinationsActual.get(6));
//    }

}
