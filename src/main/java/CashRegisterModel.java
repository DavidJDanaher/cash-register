package main.java;

import main.java.exceptions.InsufficientFundsException;
import main.java.resources.DollarValueConstants;
import main.java.resources.RegisterContents;

import java.util.Map;

public class CashRegisterModel {
    private Map<String, Integer> contents;
    private static DollarValueConstants dollars;

    public CashRegisterModel() {
        contents = new RegisterContents().getContents();
        dollars = new DollarValueConstants();
    }

    public Map<String, Integer> getContents() {
        return contents;
    }

    public int getBalance() {
        int value = 0;

        for(String key: contents.keySet()) {
            value += computeDenominationValue(key);
        }

        return value;
    }

    private int computeDenominationValue(String bill) {
        return contents.get(bill) * dollars.get(bill);
    }

    public void deposit(Map<String, Integer> deposit) {
        deposit.forEach((key, value) -> contents.merge(key, value, Integer::sum));
    }

    public void withdraw(Map<String, Integer> withdrawal) throws InsufficientFundsException {
        boolean insufficient = contents.keySet().stream().anyMatch((key) -> withdrawal.get(key) > contents.get(key));

        if (insufficient) {
            throw new InsufficientFundsException("");
        }

        withdrawal.forEach((key, value) -> contents.merge(key, value, (current, withdraw) -> current - withdraw));
    }
}


