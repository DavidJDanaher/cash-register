package main.java.features;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.resources.exceptions.InvalidInputException;
import main.java.resources.RegisterContentsFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RegisterIO {
    private CashRegisterModel register;
    private Scanner in;
    private final String[] DOLLAR_KEYS;

    public RegisterIO() {
        register = new CashRegisterModel();

        DOLLAR_KEYS = generateKeyArray();

        startApplication();
    }

    private String[] generateKeyArray() {
        String[] keys = new String[5];

        keys[0] = "TWENTY";
        keys[1] = "TEN";
        keys[2] = "FIVE";
        keys[3] = "TWO";
        keys[4] = "ONE";

        return keys;
    }

    private void startApplication() {
        String inputLine;
        String[] inputs;
        String command;

        System.out.println("This is a cash register. \nYou have a few commands available to you.");
        printHelpMenu();
        System.out.println("Ready for input... \n");

        boolean keepAppRunning = true;
        in = new Scanner(System.in);

        while (keepAppRunning) {
            inputLine = in.nextLine();
            inputs = inputLine.split(" ");
            command = inputs[0];

            switch (command) {
                case "quit":
                    keepAppRunning = false;
                    break;
                case "help":
                    printHelpMenu();
                    break;
                case "show":
                    showRegisterContents();
                    break;
                case "put":
                    depositBills(inputs);
                    break;
                case "take":
                    withdrawBills(inputs);
                    break;
                case "change":
                    makeChange(inputs);
                    break;
                default:
                    printWarning();
                    printHelpMenu();
                    break;
            }
            System.out.print("\n");
        }

        System.out.print("\nTransactions complete.\n");
        in.close();
    }


    private void printHelpMenu() {
        System.out.println("Commands: \n");
        System.out.println("> show \t\t\t\t\t\t| Display register value and inventory");
        System.out.println("> put \t20s 10s 5s 2s 1s \t| Deposit bills, a number for each denomination is required");
        System.out.println("> take \t20s 10s 5s 2s 1s \t| Withdraw bills, a number for each denomination is required");
        System.out.println("> change [amount]\t\t\t| Display change in available denominations and withdraw");
        System.out.println("> help \t\t\t\t\t\t| Display these commands");
        System.out.println("> quit \t\t\t\t\t\t| Exit the program");
    }

    private void printWarning() {
        System.out.println("\nInput not recognized\n");
    }

    private void showRegisterContents() {
        System.out.print(String.format("\n$%s ", register.getBalance()));
        Map<String, Long> contents = register.getContents();

        print(contents);
    }

    private void depositBills(String[] inputs) {
        try {
            register.deposit(mapInput(inputs));
        } catch (InvalidInputException e) {
            print(e);
        }


    }

    private void withdrawBills(String[] inputs) {
        Map<String, Long> withdrawalValues;

        try {
            withdrawalValues = mapInput(inputs);

            try {
                register.withdraw(withdrawalValues);
            } catch (InsufficientFundsException e) {
                print(e);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            print(e);
        }


    }

    private void makeChange(String[] inputs) {
        try {
            long value = Long.parseLong(inputs[1]);

            try {
                print(register.makeChange(value));
            } catch (InsufficientFundsException e) {
                print(e);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            print(new InvalidInputException("change"));
        }
    }

    private Map<String, Long> mapInput(String[] input) {
        String command = input[0];

        try {
            Map<String, Long> inputMap;

            long twenties = Long.parseLong(input[1]);
            long tens = Long.parseLong(input[2]);
            long fives = Long.parseLong(input[3]);
            long twos = Long.parseLong(input[4]);
            long ones = Long.parseLong(input[5]);

            inputMap = new RegisterContentsFactory(ones, twos, fives, tens, twenties).getContents();

            return inputMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidInputException(command);
        }
    }

    private void print(Exception e) {
        System.out.println(e.getMessage());
    }

    private void print(Map<String, Long> value) {
        for (String key : DOLLAR_KEYS) {
            System.out.print(value.get(key) + " ");
        }

        System.out.print("\n");
    }
}
