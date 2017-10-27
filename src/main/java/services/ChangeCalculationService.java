package main.java.services;

import main.java.resources.RegisterContentsFactory;
import main.java.resources.exceptions.InsufficientFundsException;

import java.util.Map;

public class ChangeCalculationService {

    public ChangeCalculationService() {
    }

    public Map<Integer, Long> getChange(long changeDue, Map<Integer, Long> contents) throws InsufficientFundsException {
        //This is done to avoid needing to cast 1 and -1 as a long in each loop.
        long positiveOne = 1;
        long negativeOne = -1;
        int arrSize = contents.size();
        int[] keys = new int[arrSize];

        Integer[] arr = contents.keySet().toArray(new Integer[arrSize]);

        for(int i = 0; i < arrSize; i++) {
            keys[i] = arr[i].intValue();
        }

        Map<Integer, Long> change = new RegisterContentsFactory(keys).getContents();
        Map<Integer, Long> contentsCopy = new RegisterContentsFactory(keys).getContents();
        contentsCopy.putAll(contents);

        if (changeDue % 2 != 0 && contentsCopy.get(5) == 0 && contentsCopy.get(1) == 0) {
            throw new InsufficientFundsException("change");
        }

        while (changeDue >= 20 && contentsCopy.get(20) > 0) {
            changeDue -= 20;
            change.merge(20, positiveOne, Long::sum);
            contentsCopy.merge(20, negativeOne, Long::sum);
        }

        while (changeDue >= 10 && contentsCopy.get(10) > 0) {
            if (changeDue >= 10 + 5 || changeDue % 2 == 0 || contentsCopy.get(1) > 0) {
                changeDue -= 10;
                change.merge(10, positiveOne, Long::sum);
                contentsCopy.merge(10, negativeOne, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= 5 && contentsCopy.get(5) > 0) {
            if (changeDue % 2 == 1 || contentsCopy.get(1) > 0 || contentsCopy.get(5) > 1) {
                changeDue -= 5;
                change.merge(5, positiveOne, Long::sum);
                contentsCopy.merge(5, negativeOne, Long::sum);
            } else {
                break;
            }
        }

        while (changeDue >= 2 && contentsCopy.get(2) > 0) {
            changeDue -= 2;
            change.merge(2, positiveOne, Long::sum);
            contentsCopy.merge(2, negativeOne, Long::sum);
        }

        while (changeDue >= 1 && contentsCopy.get(1) > 0) {
            changeDue -= 1;
            change.merge(1, positiveOne, Long::sum);
            contentsCopy.merge(1, negativeOne, Long::sum);
        }

        if (changeDue != 0) {
            change.clear();
            throw new InsufficientFundsException("change");
        }

        return change;
    }
}
