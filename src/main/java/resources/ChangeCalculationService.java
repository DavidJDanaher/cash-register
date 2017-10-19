package main.java.resources;

import main.java.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class ChangeCalculationService {
    private static String ONE = "ONE";
    private static String TWO = "TWO";
    private static String FIVE = "FIVE";
    private static String TEN = "TEN";
    private static String TWENTY = "TWENTY";
    private static DollarValueConstants dollars;

    public ChangeCalculationService() {

        dollars = new DollarValueConstants();
    }

    public Map<String, Integer> getChange(int changeDue, Map<String, Integer> contents) throws InsufficientFundsException {
        Map<String, Integer> change = new RegisterContents().getContents();
        Map<String, Integer> contentsCopy = new HashMap<>();
        contentsCopy.putAll(contents);
//TODO replace functionality
//        if (changeDue > getBalance()) {
//            throw new InsufficientFundsException("");
//        }

        if (changeDue % 2 != 0 && contentsCopy.get(FIVE) == 0 && contentsCopy.get(ONE) == 0) {
            throw new InsufficientFundsException("change");
        }

        while (changeDue >= dollars.TWENTY && contentsCopy.get(TWENTY) > 0) {
            changeDue -= dollars.TWENTY;
            change.merge(TWENTY, 1, Integer::sum);
            contentsCopy.merge(TWENTY, -1, Integer::sum);
        }

        while (changeDue >= dollars.TEN && contentsCopy.get(TEN) > 0) {
            if (changeDue >= dollars.TEN + dollars.FIVE || changeDue % 2 == 0 || contentsCopy.get(ONE) > 0) {
                changeDue -= dollars.TEN;
                change.merge(TEN, 1, Integer::sum);
                contentsCopy.merge(TEN, -1, Integer::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.FIVE && contentsCopy.get(FIVE) > 0) {
            if (changeDue % 2 == 1 || contentsCopy.get(ONE) > 0 || contentsCopy.get(FIVE) > 1) {
                changeDue -= dollars.FIVE;
                change.merge(FIVE, 1, Integer::sum);
                contentsCopy.merge(FIVE, -1, Integer::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.TWO && contentsCopy.get(TWO) > 0) {
            changeDue -= dollars.TWO;
            change.merge(TWO, 1, Integer::sum);
            contentsCopy.merge(TWO, -1, Integer::sum);
        }

        while (changeDue >= dollars.ONE && contentsCopy.get(ONE) > 0) {
            changeDue -= dollars.ONE;
            change.merge(ONE, 1, Integer::sum);
            contentsCopy.merge(ONE, -1, Integer::sum);
        }

        if (changeDue != 0) {
            change.clear();
            throw new InsufficientFundsException("change");
        }

        return change;
    }
}
