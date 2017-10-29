package main.java.services;

import main.java.resources.RegisterContentsFactory;
import main.java.resources.exceptions.InsufficientFundsException;

import java.util.*;

public class ChangeCalculationService {
    private int[] currencyDenominations;
    private RegisterContentsFactory factory;
    public ChangeCalculationService(RegisterContentsFactory contentsFactory) {
        factory = contentsFactory;
        currencyDenominations = factory.getDenominations();
    }

    public Map<Integer, Integer> makeChange(int changeDue, Map<Integer, Integer> contents) throws InsufficientFundsException {
        Map<Integer, Integer> optimalChangeCandidate;

        Map<Integer, ArrayList<Map<Integer, Integer>>> allPossibleCombos = generatePossibleChangeCombinations(changeDue, contents);
        ArrayList<Map<Integer, Integer>> possibleChangePermutations = allPossibleCombos.get(changeDue);

        if (possibleChangePermutations.isEmpty()) {
            throw new InsufficientFundsException("change");
        }

        optimalChangeCandidate = possibleChangePermutations.get(0);

        return optimalChangeCandidate;
    }

    public Map<Integer, ArrayList<Map<Integer, Integer>>> generatePossibleChangeCombinations(int change,  Map<Integer, Integer> contents) {
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinations = new HashMap<>();
        ArrayList<Map<Integer, Integer>> combinationsOfValue;
        Map<Integer, Integer> emptyMap = factory.getContents();
        Map<Integer, Integer> singlePermutation;

        int denominationIndex = 0;
        int[] currencySmallToLarge = currencyDenominations.clone();

        Arrays.sort(currencySmallToLarge);


        for (int i = 1; i <= change; i++) {
            combinationsOfValue = new ArrayList<>();

            if (denominationIndex < currencySmallToLarge.length && i == currencySmallToLarge[denominationIndex]) {
                singlePermutation = new HashMap<>(emptyMap);
                singlePermutation.put(i, 1);

                combinationsOfValue.add(singlePermutation);
                denominationIndex++;
            }

            for (int j = i - 1; j >= i - j; j--) {
                combinationsOfValue = mergeAll(allCombinations.get(j), allCombinations.get(i - j), combinationsOfValue, contents);
            }

            Collections.sort(combinationsOfValue, new DescendingDenomValue(currencyDenominations));

            allCombinations.put(i, combinationsOfValue);
        }

        return allCombinations;
    }

    private ArrayList<Map<Integer, Integer>> mergeAll(ArrayList<Map<Integer, Integer>> list1, ArrayList<Map<Integer, Integer>> list2, ArrayList<Map<Integer, Integer>> masterList, Map<Integer, Integer> registerContents) {

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                Map<Integer, Integer> mergedMap = new HashMap<>();
                mergedMap.putAll(list1.get(i));

                list2.get(j).forEach((key, value) -> mergedMap.merge(key, value, Integer::sum));

                if (!masterList.contains(mergedMap) && !insufficientFundsCheck(mergedMap, registerContents)) {
                    masterList.add(mergedMap);
                }
            }
        }

        return masterList;
    }

    class DescendingDenomValue implements Comparator<Map<Integer, Integer>> {
        int[] currencyDenominations;
        int result = 0;

        public DescendingDenomValue(int[] denominations) {
            currencyDenominations = denominations;
        }

        public int compare(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
            for (int i = 0; i <  currencyDenominations.length - 1; i++) {
                if (map1.get(currencyDenominations[i]) > map2.get(currencyDenominations[i])) {
                    result = -1;
                    break;
                } else if (map1.get(currencyDenominations[i]) < map2.get(currencyDenominations[i])) {
                    result = 1;
                    break;
                } else {
                    result = 0;
                }
            }

            return result;
        }
    }

    private boolean insufficientFundsCheck(Map<Integer, Integer> changeCandidate, Map<Integer, Integer> contents) {
        return contents.keySet().stream().anyMatch((key) -> changeCandidate.get(key) > contents.get(key));
    }
}
