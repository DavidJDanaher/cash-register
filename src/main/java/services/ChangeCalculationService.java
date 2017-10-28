package main.java.services;

import main.java.resources.RegisterContentsFactory;
import main.java.resources.exceptions.InsufficientFundsException;

import java.util.*;

public class ChangeCalculationService {

    public ChangeCalculationService() {
    }

    public Map<Integer, Integer> getChange(int changeDue, Map<Integer, Integer> contents) throws InsufficientFundsException {
        //This is done to avoid needing to cast 1 and -1 as a int in each loop.
        int positiveOne = 1;
        int negativeOne = -1;
        int arrSize = contents.size();
        int[] keys = new int[arrSize];

        Integer[] arr = contents.keySet().toArray(new Integer[arrSize]);

        for(int i = 0; i < arrSize; i++) {
            keys[i] = arr[i].intValue();
        }

        Map<Integer, Integer> change = new RegisterContentsFactory(keys).getContents();
        Map<Integer, Integer> contentsCopy = new RegisterContentsFactory(keys).getContents();
        contentsCopy.putAll(contents);

        if (changeDue % 2 != 0 && contentsCopy.get(5) == 0 && contentsCopy.get(1) == 0) {
            throw new InsufficientFundsException("change");
        }

        while (changeDue >= 20 && contentsCopy.get(20) > 0) {
            changeDue -= 20;
            change.merge(20, positiveOne, Integer::sum);
            contentsCopy.merge(20, negativeOne, Integer::sum);
        }

        while (changeDue >= 10 && contentsCopy.get(10) > 0) {
            if (changeDue >= 10 + 5 || changeDue % 2 == 0 || contentsCopy.get(1) > 0) {
                changeDue -= 10;
                change.merge(10, positiveOne, Integer::sum);
                contentsCopy.merge(10, negativeOne, Integer::sum);
            } else {
                break;
            }
        }

        while (changeDue >= 5 && contentsCopy.get(5) > 0) {
            if (changeDue % 2 == 1 || contentsCopy.get(1) > 0 || contentsCopy.get(5) > 1) {
                changeDue -= 5;
                change.merge(5, positiveOne, Integer::sum);
                contentsCopy.merge(5, negativeOne, Integer::sum);
            } else {
                break;
            }
        }

        while (changeDue >= 2 && contentsCopy.get(2) > 0) {
            changeDue -= 2;
            change.merge(2, positiveOne, Integer::sum);
            contentsCopy.merge(2, negativeOne, Integer::sum);
        }

        while (changeDue >= 1 && contentsCopy.get(1) > 0) {
            changeDue -= 1;
            change.merge(1, positiveOne, Integer::sum);
            contentsCopy.merge(1, negativeOne, Integer::sum);
        }

        if (changeDue != 0) {
            change.clear();
            throw new InsufficientFundsException("change");
        }

        return change;
    }

    public Map<Integer, Integer> makeChange(int changeDue, Map<Integer, Integer> contents) {
        int arrSize = contents.size();
        int[] keys = new int[arrSize];

        Integer[] arr = contents.keySet().toArray(new Integer[arrSize]);

        Arrays.sort(arr);


        for(int i = 0; i < arrSize; i++) {
            keys[i] = arr[i].intValue();
        }

        Map<Integer, Integer> change = new RegisterContentsFactory(keys).getContents();





        return change;
    }

    public Map<Integer, ArrayList<Map<Integer, Integer>>> generatePossibleChangeCombinations(int change, int[] denominations, Map<Integer, Integer> contents) {
        Map<Integer, ArrayList<Map<Integer, Integer>>> allCombinations = new HashMap<>();
        ArrayList<Map<Integer, Integer>> combinationsOfValue;
        Map<Integer, Integer> emptyMap = new RegisterContentsFactory(denominations).getContents();
        Map<Integer, Integer> singlePermutation;

        int denominationIndex = 0;

        for (int i = 1; i <= change; i++) {
            combinationsOfValue = new ArrayList<>();

            if (i == denominations[denominationIndex]) {
                singlePermutation = new HashMap<>(emptyMap);
                singlePermutation.put(i, 1);

                combinationsOfValue.add(singlePermutation);
                denominationIndex++;
            }

            for (int j = i - 1; j >= i - j; j--) {
                combinationsOfValue = mergeAll(allCombinations.get(j), allCombinations.get(i - j), combinationsOfValue, contents);
            }

            Collections.sort(combinationsOfValue, new DescendingDenomValue());

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
        int[] standardDenominations = new int[] { 1, 2, 5, 10, 20 };
        int difference = 0;

        public int compare(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
            for (int i = standardDenominations[standardDenominations.length - 1]; i >= 0; i--) {
                if (!(map1.get(i) == map2.get(i))) {
                    difference = map2.get(i) - map1.get(i);
                    break;
                }
            }

            return difference;
        }
    }

    private boolean insufficientFundsCheck(Map<Integer, Integer> changeCandidate, Map<Integer, Integer> contents) {
        return contents.keySet().stream().anyMatch((key) -> changeCandidate.get(key) > contents.get(key));
    }
}
