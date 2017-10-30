package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class CurrencyFactory {
    Map<Integer, Integer> contents;
    private int[] currencyDenominations;

    public CurrencyFactory(int[] denominations) {
        currencyDenominations = denominations;
    }

    public Map<Integer, Integer> getAsMap() {
        contents = new HashMap<>();

        for (int value : currencyDenominations) {
            contents.put(value, 0);
        }

        return contents;
    }

    public Map<Integer, Integer> getAsMap(int[] initialValues) {
        contents = new HashMap<>();

        for (int i = 0; i < initialValues.length; i++) {
            contents.put(currencyDenominations[i], initialValues[i]);
        }
        return contents;
    }

    public int[] getAsArray() {
        return currencyDenominations;
    }
}
