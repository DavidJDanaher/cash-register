package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class RegisterContentsFactory {
    private Map<Integer, Long> contents;

    public RegisterContentsFactory(int[] denominations) {
        contents = new HashMap<>();

        for (int value : denominations) {
            contents.put(value, (long) 0);
        }
    }

    public RegisterContentsFactory(int[] denominations, long[] initialValues) {
        contents = new HashMap<>();

        for (int i = 0; i < denominations.length; i++) {
            contents.put(denominations[i], initialValues[i]);
        }
    }

    public Map<Integer, Long> getContents() {
        return contents;
    }
}
