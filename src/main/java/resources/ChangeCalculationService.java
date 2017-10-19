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

    public Map<String, Long> getChange(long changeDue, Map<String, Long> contents) throws InsufficientFundsException {
        Map<String, Long> change = new RegisterContents().getContents();
        Map<String, Long> contentsCopy = new HashMap<>();
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
            change.merge(TWENTY, (long) 1, Long::sum);
            contentsCopy.merge(TWENTY, (long) -1, Long::sum);
        }

        while (changeDue >= dollars.TEN && contentsCopy.get(TEN) > 0) {
            if (changeDue >= dollars.TEN + dollars.FIVE || changeDue % 2 == 0 || contentsCopy.get(ONE) > 0) {
                changeDue -= dollars.TEN;
                change.merge(TEN, (long) 1, Long::sum);
                contentsCopy.merge(TEN, (long) -1, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.FIVE && contentsCopy.get(FIVE) > 0) {
            if (changeDue % 2 == 1 || contentsCopy.get(ONE) > 0 || contentsCopy.get(FIVE) > 1) {
                changeDue -= dollars.FIVE;
                change.merge(FIVE, (long) 1, Long::sum);
                contentsCopy.merge(FIVE, (long) -1, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.TWO && contentsCopy.get(TWO) > 0) {
            changeDue -= dollars.TWO;
            change.merge(TWO, (long) 1, Long::sum);
            contentsCopy.merge(TWO, (long) -1, Long::sum);
        }

        while (changeDue >= dollars.ONE && contentsCopy.get(ONE) > 0) {
            changeDue -= dollars.ONE;
            change.merge(ONE, (long) 1, Long::sum);
            contentsCopy.merge(ONE, (long) -1, Long::sum);
        }

        if (changeDue != 0) {
            change.clear();
            throw new InsufficientFundsException("change");
        }

        return change;
    }
}
