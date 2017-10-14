package main.java;

import main.java.excpetions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class CashRegister {
    private Map<String, Integer> contents;
    private static Map<String, Integer> billValues;

    public CashRegister() {
        initializeRegister();
        initializeBillValues();
    }

    private void initializeRegister() {
        contents = new HashMap<>();

        contents.put("ONE", 0);
        contents.put("TWO", 0);
        contents.put("FIVE", 0);
        contents.put("TEN", 0);
        contents.put("TWENTY", 0);
    }

    private void initializeBillValues() {
        billValues = new HashMap<>();

        billValues.put("ONE", 1);
        billValues.put("TWO", 2);
        billValues.put("FIVE", 5);
        billValues.put("TEN", 10);
        billValues.put("TWENTY", 20);
    }

    public Map<String, Integer> getContents() {
        return contents;
    }

    public int getTotalValue() {
        int value = 0;

        for(String key: contents.keySet()) {
            value += computeDenominationValue(key);
        }

        return value;
    }

    private int computeDenominationValue(String bill) {
        return contents.get(bill) * billValues.get(bill);
    }

    public void addToContents(int ones, int twos, int fives, int tens, int twenties) {
        if (ones > 0) {
            contents.put("ONE", contents.get("ONE") + ones);
        }

        if (twos > 0) {
            contents.put("TWO", contents.get("TWO") + twos);
        }

        if (fives > 0) {
            contents.put("FIVE", contents.get("FIVE") + fives);
        }

        if (tens > 0) {
            contents.put("TEN", contents.get("TEN") + tens);
        }

        if (twenties > 0) {
            contents.put("TWENTY", contents.get("TWENTY") + twenties);
        }
    }

    public void removeContents(int ones, int twos, int fives, int tens, int twenties) throws InsufficientFundsException {
        if (ones > contents.get("ONE")) {
            throw new InsufficientFundsException("one");
        }

        if (twos > contents.get("TWO")) {
            throw new InsufficientFundsException("two");
        }

        if (fives > contents.get("FIVE")) {
            throw new InsufficientFundsException("five");
        }

        if (tens > contents.get("TEN")) {
            throw new InsufficientFundsException("ten");
        }

        if (twenties > contents.get("TWENTY")) {
            throw new InsufficientFundsException("twenty");
        }

        contents.put("ONE", contents.get("ONE") - ones);
        contents.put("TWO", contents.get("TWO") - twos);
        contents.put("FIVE", contents.get("FIVE") - fives);
        contents.put("TEN", contents.get("TEN") - tens);
        contents.put("TWENTY", contents.get("TWENTY") - twenties);
    }
}


