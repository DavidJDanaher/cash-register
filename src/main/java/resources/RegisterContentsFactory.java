package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class RegisterContentsFactory {
    private static String ONE = "ONE";
    private static String TWO = "TWO";
    private static String FIVE = "FIVE";
    private static String TEN = "TEN";
    private static String TWENTY = "TWENTY";
    private Map<String, Long> contents;

    public RegisterContentsFactory() {
        contents = new HashMap<>();

        contents.put(ONE, (long) 0);
        contents.put(TWO, (long) 0);
        contents.put(FIVE, (long) 0);
        contents.put(TEN, (long) 0);
        contents.put(TWENTY, (long) 0);
    }

    public RegisterContentsFactory(long ones, long twos, long fives, long tens, long twenties) {
        contents = new HashMap<>();

        contents.put(ONE, ones);
        contents.put(TWO, twos);
        contents.put(FIVE, fives);
        contents.put(TEN, tens);
        contents.put(TWENTY, twenties);
    }

    public Map<String, Long> getContents() {
        return contents;
    }
}
