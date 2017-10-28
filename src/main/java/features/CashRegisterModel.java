package main.java.features;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.resources.RegisterContentsFactory;
import main.java.services.ChangeCalculationService;

import java.util.Map;

public class CashRegisterModel {
    private Map<Integer, Integer> contents;
    private ChangeCalculationService changeService;

    public CashRegisterModel(int[] denominations) {
        contents = new RegisterContentsFactory(denominations).getContents();
        changeService = new ChangeCalculationService();
    }

    public Map<Integer, Integer> getContents() {
        return contents;
    }

    public int getBalance() {
        int value = 0;

        for(Integer key: contents.keySet()) {
            value += computeDenominationValue(key);
        }

        return value;
    }

    private int computeDenominationValue(int bill) {
        return contents.get(bill) * bill;
    }

    public void deposit(Map<Integer, Integer> deposit) {
        deposit.forEach((key, value) -> contents.merge(key, value, Integer::sum));
    }

    public void withdraw(Map<Integer, Integer> withdrawal) throws InsufficientFundsException {
        boolean insufficient = contents.keySet().stream().anyMatch((key) -> withdrawal.get(key) > contents.get(key));

        if (insufficient) {
            throw new InsufficientFundsException("");
        }

        withdrawal.forEach((key, value) -> contents.merge(key, value, (current, withdraw) -> current - withdraw));
    }

    public Map<Integer, Integer> makeChange (Integer changeRequested) throws InsufficientFundsException {
        Map<Integer, Integer> change;

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


