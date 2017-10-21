package main.java.services;

import main.java.resources.DollarValueConstants;
import main.java.resources.RegisterContentsFactory;
import main.java.resources.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class ChangeCalculationService {


    private static DollarValueConstants dollars;

    public ChangeCalculationService() {
        dollars = new DollarValueConstants();
    }

    public Map<String, Long> getChange(long changeDue, Map<String, Long> contents) throws InsufficientFundsException {
        String ONE = "ONE";
        String TWO = "TWO";
        String FIVE = "FIVE";
        String TEN = "TEN";
        String TWENTY = "TWENTY";
        //This is done to avoid needing to cast 1 and -1 as a long in each loop.
        long positiveOne = 1;
        long negativeOne = -1;

        Map<String, Long> change = new RegisterContentsFactory().getContents();
        Map<String, Long> contentsCopy = new RegisterContentsFactory().getContents();
        contentsCopy.putAll(contents);

        if (changeDue % 2 != 0 && contentsCopy.get(FIVE) == 0 && contentsCopy.get(ONE) == 0) {
            throw new InsufficientFundsException("change");
        }

        while (changeDue >= dollars.TWENTY && contentsCopy.get(TWENTY) > 0) {
            changeDue -= dollars.TWENTY;
            change.merge(TWENTY, positiveOne, Long::sum);
            contentsCopy.merge(TWENTY, negativeOne, Long::sum);
        }

        while (changeDue >= dollars.TEN && contentsCopy.get(TEN) > 0) {
            if (changeDue >= dollars.TEN + dollars.FIVE || changeDue % 2 == 0 || contentsCopy.get(ONE) > 0) {
                changeDue -= dollars.TEN;
                change.merge(TEN, positiveOne, Long::sum);
                contentsCopy.merge(TEN, negativeOne, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.FIVE && contentsCopy.get(FIVE) > 0) {
            if (changeDue % 2 == 1 || contentsCopy.get(ONE) > 0 || contentsCopy.get(FIVE) > 1) {
                changeDue -= dollars.FIVE;
                change.merge(FIVE, positiveOne, Long::sum);
                contentsCopy.merge(FIVE, negativeOne, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= dollars.TWO && contentsCopy.get(TWO) > 0) {
            changeDue -= dollars.TWO;
            change.merge(TWO, positiveOne, Long::sum);
            contentsCopy.merge(TWO, negativeOne, Long::sum);
        }

        while (changeDue >= dollars.ONE && contentsCopy.get(ONE) > 0) {
            changeDue -= dollars.ONE;
            change.merge(ONE, positiveOne, Long::sum);
            contentsCopy.merge(ONE, negativeOne, Long::sum);
        }

        if (changeDue != 0) {
            change.clear();
            throw new InsufficientFundsException("change");
        }

        return change;
    }
}
