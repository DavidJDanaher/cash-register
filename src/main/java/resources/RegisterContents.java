package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class RegisterContents {
    private static String ONE = "ONE";
    private static String TWO = "TWO";
    private static String FIVE = "FIVE";
    private static String TEN = "TEN";
    private static String TWENTY = "TWENTY";
    private Map<String,Integer> contents;

    public RegisterContents () {
        contents = new HashMap<>();

        contents.put(ONE, 0);
        contents.put(TWO, 0);
        contents.put(FIVE, 0);
        contents.put(TEN, 0);
        contents.put(TWENTY, 0);
    }

    public RegisterContents(int ones, int twos, int fives, int tens, int twenties) {
        contents = new HashMap<>();

        contents.put(ONE, ones);
        contents.put(TWO, twos);
        contents.put(FIVE, fives);
        contents.put(TEN, tens);
        contents.put(TWENTY, twenties);
    }

    public Map<String, Integer> getContents() {
        return contents;
    }
}
