package main.java.features;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.resources.DollarValueConstants;
import main.java.resources.RegisterContentsFactory;
import main.java.services.ChangeCalculationService;

import java.util.Map;

public class CashRegisterModel {
    private Map<String, Long> contents;
    private static DollarValueConstants dollars;
    private ChangeCalculationService changeService;

    public CashRegisterModel() {
        contents = new RegisterContentsFactory().getContents();
        dollars = new DollarValueConstants();
        changeService = new ChangeCalculationService();
    }

    public Map<String, Long> getContents() {
        return contents;
    }

    public long getBalance() {
        long value = 0;

        for(String key: contents.keySet()) {
            value += computeDenominationValue(key);
        }

        return value;
    }

    private long computeDenominationValue(String bill) {
        return contents.get(bill) * dollars.get(bill);
    }

    public void deposit(Map<String, Long> deposit) {
        deposit.forEach((key, value) -> contents.merge(key, value, Long::sum));
    }

    public void withdraw(Map<String, Long> withdrawal) throws InsufficientFundsException {
        boolean insufficient = contents.keySet().stream().anyMatch((key) -> withdrawal.get(key) > contents.get(key));

        if (insufficient) {
            throw new InsufficientFundsException("");
        }

        withdrawal.forEach((key, value) -> contents.merge(key, value, (current, withdraw) -> current - withdraw));
    }

    public Map<String, Long> makeChange (Long changeRequested) throws InsufficientFundsException {
        Map<String, Long> change;

        if (changeRequested > getBalance()) {
            throw new InsufficientFundsException("");
        }

        try {
            change = changeService.getChange(changeRequested, contents);
            withdraw(change);

            return change;
        } catch (InsufficientFundsException e) {
            throw new InsufficientFundsException("change");
        }
    }
}


