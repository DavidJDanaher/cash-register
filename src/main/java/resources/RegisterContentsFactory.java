package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class RegisterContentsFactory {
    private Map<Integer, Integer> contents;
    private int[] currencyDenominations;

    public RegisterContentsFactory(int[] denominations) {
        contents = new HashMap<>();
        currencyDenominations = denominations;

        for (int value : denominations) {
            contents.put(value, 0);
        }
    }

    public Map<Integer, Integer> getContents() {
        return contents;
    }

    public Map<Integer, Integer> getContents(int[] initialValues) {
        Map<Integer, Integer> customContents = new HashMap<>();

        for (int i = 0; i < initialValues.length; i++) {
            customContents.put(currencyDenominations[i], initialValues[i]);
        }
        return customContents;
    }

    public int[] getDenominations() {
        return currencyDenominations;
    }
}
