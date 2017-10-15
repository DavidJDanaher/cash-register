package main.java.resources;

import java.util.HashMap;
import java.util.Map;

public class DollarValueConstants {
    public final int ONE = 1;
    public final int TWO = 2;
    public final int FIVE = 5;
    public final int TEN = 10;
    public final int TWENTY = 20;
    private static Map<String, Integer> values;

    public DollarValueConstants() {
        values = new HashMap<>();

        values.put("ONE", ONE);
        values.put("TWO", TWO);
        values.put("FIVE", FIVE);
        values.put("TEN", TEN);
        values.put("TWENTY", TWENTY);
    }

    public int get(String num) {
       return values.get(num);
    }
}
