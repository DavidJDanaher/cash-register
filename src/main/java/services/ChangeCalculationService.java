package main.java.services;

import main.java.resources.CurrencyComparator;
import main.java.resources.CurrencyFactory;
import main.java.resources.exceptions.InsufficientFundsException;

import java.util.*;

public class ChangeCalculationService {
    private int[] currencyDenominations;
    private CurrencyFactory currency;
    private CurrencyComparator descendingCurrencyValue;

    public ChangeCalculationService(CurrencyFactory contentsFactory) {
        currency = contentsFactory;
        currencyDenominations = currency.getAsArray();
        descendingCurrencyValue = new CurrencyComparator(currencyDenominations);
    }

    public Map<Integer, Integer> makeChange(int changeDue, Map<Integer, Integer> contents) throws InsufficientFundsException {
        Map<Integer, Integer> optimalChangeCandidate;

        Map<Integer, ArrayList<Map<Integer, Integer>>> masterPermutationCollection = generatePossibleChangePermutations(changeDue, contents);
        ArrayList<Map<Integer, Integer>> permutationsOfChange = masterPermutationCollection.get(changeDue);

        if (permutationsOfChange.isEmpty()) {
            throw new InsufficientFundsException("change");
        }

        optimalChangeCandidate = permutationsOfChange.get(0);

        return optimalChangeCandidate;
    }

    public Map<Integer, ArrayList<Map<Integer, Integer>>> generatePossibleChangePermutations(int change,  Map<Integer, Integer> contents) {
        Map<Integer, ArrayList<Map<Integer, Integer>>> allPermutations = new HashMap<>();
        ArrayList<Map<Integer, Integer>> permutationsOfValue;
        Map<Integer, Integer> singlePermutation;

        int[] currencySmallToLarge = currencyDenominations.clone();
        int denominationIndex = 0;

        Arrays.sort(currencySmallToLarge);

        for (int i = 1; i <= change; i++) {
            permutationsOfValue = new ArrayList<>();

            if (denominationIndex < currencySmallToLarge.length && i == currencySmallToLarge[denominationIndex]) {
                singlePermutation = currency.getAsMap();
                singlePermutation.put(i, 1);

                permutationsOfValue.add(singlePermutation);
                denominationIndex++;
            }

            for (int j = i - 1; j >= i - j; j--) {
                permutationsOfValue = mergeAll(allPermutations.get(j), allPermutations.get(i - j), permutationsOfValue, contents);
            }

            Collections.sort(permutationsOfValue, descendingCurrencyValue);

            allPermutations.put(i, permutationsOfValue);
        }

        return allPermutations;
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

    private boolean insufficientFundsCheck(Map<Integer, Integer> changeCandidate, Map<Integer, Integer> contents) {
        return contents.keySet().stream().anyMatch((key) -> changeCandidate.get(key) > contents.get(key));
    }
}
