package main.java.resources;

import java.util.Comparator;
import java.util.Map;

public class CurrencyComparator implements Comparator<Map<Integer, Integer>> {
    int[] currencyDenominations;
    int result = 0;

    public CurrencyComparator(int[] denominations) {
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
