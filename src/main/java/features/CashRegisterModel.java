package main.java.features;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.resources.RegisterContentsFactory;
import main.java.services.ChangeCalculationService;

import java.util.Map;

public class CashRegisterModel {
    private Map<Integer, Long> contents;
    private ChangeCalculationService changeService;

    public CashRegisterModel(int[] denominations) {
        contents = new RegisterContentsFactory(denominations).getContents();
        changeService = new ChangeCalculationService();
    }

    public Map<Integer, Long> getContents() {
        return contents;
    }

    public long getBalance() {
        long value = 0;

        for(Integer key: contents.keySet()) {
            value += computeDenominationValue(key);
        }

        return value;
    }

    private long computeDenominationValue(int bill) {
        return contents.get(bill) * bill;
    }

    public void deposit(Map<Integer, Long> deposit) {
        deposit.forEach((key, value) -> contents.merge(key, value, Long::sum));
    }

    public void withdraw(Map<Integer, Long> withdrawal) throws InsufficientFundsException {
        boolean insufficient = contents.keySet().stream().anyMatch((key) -> withdrawal.get(key) > contents.get(key));

        if (insufficient) {
            throw new InsufficientFundsException("");
        }

        withdrawal.forEach((key, value) -> contents.merge(key, value, (current, withdraw) -> current - withdraw));
    }

    public Map<Integer, Long> makeChange (Long changeRequested) throws InsufficientFundsException {
        Map<Integer, Long> change;

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


