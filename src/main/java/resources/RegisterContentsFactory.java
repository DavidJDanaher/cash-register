package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class RegisterContentsFactory {
    private Map<Integer, Integer> contents;

    public RegisterContentsFactory(int[] denominations) {
        contents = new HashMap<>();

        for (int value : denominations) {
            contents.put(value, 0);
        }
    }

    public RegisterContentsFactory(int[] denominations, int[] initialValues) {
        contents = new HashMap<>();

        for (int i = 0; i < denominations.length; i++) {
            contents.put(denominations[i], initialValues[i]);
        }
    }

    public Map<Integer, Integer> getContents() {
        return contents;
    }
}
