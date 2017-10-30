package test.java;

import main.java.resources.CurrencyFactory;
import main.java.services.ChangeCalculationService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeGenerationTest {

    @Nested
    @DisplayName("Standard Denominations, 20, 10, 5, 2, 1")
    class StandardDenomination{
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsActual;
        Map<Integer, Integer> singlePermutation;

        int[] standardDenominations = new int[] { 20, 10, 5, 2, 1 };
        CurrencyFactory currency = new CurrencyFactory(standardDenominations);
        ChangeCalculationService service = new ChangeCalculationService(currency);
        Map<Integer, Integer> sufficientRegister = currency.getAsMap(new int[] { 9, 9, 9, 9, 9 });;
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsExpected = new HashMap<>();

        @AfterEach
        void tearDown() {
            allCombinationsActual.clear();
        }

        @Test
        @DisplayName("One")
        void testOne() {
            ArrayList<Map<Integer,Integer>> combosForOne = new ArrayList<>();
            singlePermutation = currency.getAsMap();

            singlePermutation.put(1, 1);
            combosForOne.add(singlePermutation);
            allCombinationsExpected.put(1, combosForOne);

            allCombinationsActual = service.generatePossibleChangePermutations(1, sufficientRegister);

            assertEquals(allCombinationsActual.get(1), allCombinationsExpected.get(1));
        }

        @Test
        @DisplayName("Two")
        void testTwo() {
            ArrayList<Map<Integer,Integer>> permutationsTwo = new ArrayList<>();
            Map<Integer, Integer> permSingleTwo = currency.getAsMap();
            Map<Integer, Integer> permDoubleOne = currency.getAsMap();

            permSingleTwo.put(2, 1);
            permDoubleOne.put(1, 2);
            permutationsTwo.add(permSingleTwo);
            permutationsTwo.add(permDoubleOne);

            allCombinationsExpected.put(2, permutationsTwo);

            allCombinationsActual = service.generatePossibleChangePermutations(2, sufficientRegister);

            assertEquals(allCombinationsExpected.get(2), allCombinationsActual.get(2));
        }

        @Test
        @DisplayName("Three")
        void testThree() {
            ArrayList<Map<Integer,Integer>> permutationsThree = new ArrayList<>();
            Map<Integer, Integer> permTwoOne = currency.getAsMap();
            Map<Integer, Integer> permTripleOne = currency.getAsMap();

            permTwoOne.put(2, 1);
            permTwoOne.put(1,1);
            permTripleOne.put(1, 3);
            permutationsThree.add(permTwoOne);
            permutationsThree.add(permTripleOne);

            allCombinationsExpected.put(3, permutationsThree);

            allCombinationsActual = service.generatePossibleChangePermutations(3, sufficientRegister);

            assertEquals(allCombinationsExpected.get(3), allCombinationsActual.get(3));
        }


        @Test
        @DisplayName("Four")
        void testFour() {
            ArrayList<Map<Integer,Integer>> permutationsFour = new ArrayList<>();
            Map<Integer, Integer> permDoubleTwo = currency.getAsMap();
            Map<Integer, Integer> permSingleTwoDoubleOne = currency.getAsMap();
            Map<Integer, Integer> permQuadOne = currency.getAsMap();

            permDoubleTwo.put(2, 2);
            permutationsFour.add(permDoubleTwo);

            permSingleTwoDoubleOne.put(1, 2);
            permSingleTwoDoubleOne.put(2, 1);
            permutationsFour.add(permSingleTwoDoubleOne);

            permQuadOne.put(1, 4);
            permutationsFour.add(permQuadOne);

            allCombinationsExpected.put(4, permutationsFour);

            allCombinationsActual = service.generatePossibleChangePermutations(4, sufficientRegister);

            assertEquals(allCombinationsExpected.get(4), allCombinationsActual.get(4));
        }

        @Test
        @DisplayName("Five")
        void testFive() {
            ArrayList<Map<Integer, Integer>> permutationsFive = new ArrayList<>();
            Map<Integer, Integer> perm_0_0_1_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_1_2_0_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_3_1_0_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_5_0_0_0_0 = currency.getAsMap();

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

            allCombinationsActual = service.generatePossibleChangePermutations(5, sufficientRegister);

            assertEquals(allCombinationsExpected.get(5), allCombinationsActual.get(5));
        }

        @Test
        @DisplayName("Six")
        void testSix() {
            ArrayList<Map<Integer, Integer>> permutationsSix = new ArrayList<>();
            Map<Integer, Integer> perm_1_0_1_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_0_3_0_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_2_2_0_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_4_1_0_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_6_0_0_0_0 = currency.getAsMap();

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

            allCombinationsActual = service.generatePossibleChangePermutations(6, sufficientRegister);

            assertEquals(allCombinationsExpected.get(6), allCombinationsActual.get(6));
        }
    }



    @Nested
    @DisplayName("Other Denominations")
    class OtherCurrencyDenominations {
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsActual;
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinationsExpected = new HashMap<>();

        @AfterEach
        void tearDown() {
            allCombinationsActual.clear();
        }

        @Test
        @DisplayName("Combinations of 23, 7, and 3")
        void test_23_7_3() {
            int[] oddDenominations = new int[] { 23, 7, 3 };
            CurrencyFactory currency = new CurrencyFactory(oddDenominations);
            ChangeCalculationService service = new ChangeCalculationService(currency);
            Map<Integer, Integer> sufficientRegister = currency.getAsMap(new int[] { 9, 9, 9 });

            ArrayList<Map<Integer,Integer>> permutationsTwentyThree = new ArrayList<>();
            Map<Integer, Integer> perm_1_0_0 = currency.getAsMap();
            Map<Integer, Integer> perm_0_2_3 = currency.getAsMap();

            perm_1_0_0.put(23, 1);
            permutationsTwentyThree.add(perm_1_0_0);

            perm_0_2_3.put(7, 2);
            perm_0_2_3.put(3, 3);
            permutationsTwentyThree.add(perm_0_2_3);

            allCombinationsExpected.put(23, permutationsTwentyThree);

            allCombinationsActual = service.generatePossibleChangePermutations(23, sufficientRegister);

            assertEquals(allCombinationsExpected.get(23), allCombinationsActual.get(23));
            assertEquals(currency.getAsMap(new int[] { 0, 1, 5 }), allCombinationsActual.get(22).get(0));
            assertEquals(currency.getAsMap(new int[] { 0, 3, 0 }), allCombinationsActual.get(21).get(0));
            assertEquals(currency.getAsMap(new int[] { 0, 2, 2 }), allCombinationsActual.get(20).get(0));
            assertEquals(currency.getAsMap(new int[] { 0, 2, 1 }), allCombinationsActual.get(17).get(0));
            assertEquals(currency.getAsMap(new int[] { 0, 1, 3 }), allCombinationsActual.get(16).get(0));
            assertEquals(currency.getAsMap(new int[] { 0, 0, 5 }), allCombinationsActual.get(15).get(0));
            assertEquals(0, allCombinationsActual.get(11).size());
            assertEquals(0, allCombinationsActual.get(8).size());
            assertEquals(0, allCombinationsActual.get(5).size());
        }
    }
}
