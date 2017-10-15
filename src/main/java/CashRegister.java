package main.java;

import main.java.excpetions.InsufficientFundsException;
import main.java.resources.DollarValueConstants;
import main.java.resources.RegisterContents;

import java.util.HashMap;
import java.util.Map;

public class CashRegister {
    private Map<String, Integer> contents;
    private static DollarValueConstants dollars;
    private static RegisterContents count;

    private static String ONE = "ONE";
    private static String TWO = "TWO";
    private static String FIVE = "FIVE";
    private static String TEN = "TEN";
    private static String TWENTY = "TWENTY";

    public CashRegister() {
        contents = initializeEmptyRegister();
        count = new RegisterContents();
        dollars = new DollarValueConstants();
    }

    private Map<String,Integer> initializeEmptyRegister() {
        Map<String,Integer> register = new HashMap<>();

        register.put(ONE, 0);
        register.put(TWO, 0);
        register.put(FIVE, 0);
        register.put(TEN, 0);
        register.put(TWENTY, 0);

        return register;
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
        return contents.get(bill) * dollars.get(bill);
    }

    public Map<String, Integer> getChange(int changeDue) throws InsufficientFundsException {
        Map<String, Integer> change = initializeEmptyRegister();

        if (changeDue > getTotalValue()) {
            throw new InsufficientFundsException("");
        }

        if (changeDue % 2 != 0 && contents.get(FIVE) == 0 && contents.get(ONE) == 0) {
            throw new InsufficientFundsException("change");
        }

        while (changeDue >= dollars.TWENTY && contents.get(TWENTY) > 0) {
            changeDue -= dollars.TWENTY;
            contents.put(TWENTY, contents.get(TWENTY) - 1);
            change.put(TWENTY, change.get(TWENTY) + 1);
        }

        while (changeDue >= dollars.TEN && contents.get(TEN) > 0) {
            changeDue -= dollars.TEN;
            contents.put(TEN, contents.get(TEN) - 1);
            change.put(TEN, change.get(TEN) + 1);
        }

        while (changeDue >= dollars.FIVE && contents.get(FIVE) > 0) {
            if (changeDue % 2 == 1 || contents.get(ONE) > 0) {
                changeDue -= dollars.FIVE;
                contents.put(FIVE, contents.get(FIVE) - 1);
                change.put(FIVE, change.get(FIVE) + 1);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.TWO && contents.get(TWO) > 0) {
            changeDue -= dollars.TWO;
            contents.put(TWO, contents.get(TWO) - 1);
            change.put(TWO, change.get(TWO) + 1);
        }

        while (changeDue >= dollars.ONE && contents.get(ONE) > 0) {
            changeDue -= dollars.ONE;
            contents.put(ONE, contents.get(ONE) - 1);
            change.put(ONE, change.get(ONE) + 1);
        }

        if (changeDue != 0) {
            throw new InsufficientFundsException("change");
        }

        return change;
    }

    public void addToContents(int ones, int twos, int fives, int tens, int twenties) {
        if (ones > 0) {
            contents.put(ONE, contents.get(ONE) + ones);
        }

        if (twos > 0) {
            contents.put(TWO, contents.get(TWO) + twos);
        }

        if (fives > 0) {
            contents.put(FIVE, contents.get(FIVE) + fives);
        }

        if (tens > 0) {
            contents.put(TEN, contents.get(TEN) + tens);
        }

        if (twenties > 0) {
            contents.put(TWENTY, contents.get(TWENTY) + twenties);
        }
    }

    public void removeContents(int ones, int twos, int fives, int tens, int twenties) throws InsufficientFundsException {
        if (ones > contents.get(ONE)) {
            throw new InsufficientFundsException("one");
        }

        if (twos > contents.get(TWO)) {
            throw new InsufficientFundsException("two");
        }

        if (fives > contents.get(FIVE)) {
            throw new InsufficientFundsException("five");
        }

        if (tens > contents.get(TEN)) {
            throw new InsufficientFundsException("ten");
        }

        if (twenties > contents.get(TWENTY)) {
            throw new InsufficientFundsException("twenty");
        }

        contents.put(ONE, contents.get(ONE) - ones);
        contents.put(TWO, contents.get(TWO) - twos);
        contents.put(FIVE, contents.get(FIVE) - fives);
        contents.put(TEN, contents.get(TEN) - tens);
        contents.put(TWENTY, contents.get(TWENTY) - twenties);
    }
}


