package main.java.features;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.resources.exceptions.InvalidInputException;
import main.java.resources.RegisterContentsFactory;

import java.util.Map;
import java.util.Scanner;

public class RegisterIO {
    private CashRegisterModel register;
    private Scanner in;
    int[] currencyList;

    public RegisterIO() {
        currencyList = new int[] { 20, 10, 5, 2, 1};

        register = new CashRegisterModel(currencyList);

        startApplication();
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
        Map<Integer, Long> contents = register.getContents();

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
        Map<Integer, Long> withdrawalValues;

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

    private Map<Integer, Long> mapInput(String[] input) {
        String command = input[0];
        long[] currencyInputs;

        try {
            Map<Integer, Long> inputMap;

            long twenties = Long.parseLong(input[1]);
            long tens = Long.parseLong(input[2]);
            long fives = Long.parseLong(input[3]);
            long twos = Long.parseLong(input[4]);
            long ones = Long.parseLong(input[5]);
            currencyInputs = new long[] { twenties, tens, fives, twos, ones };

            inputMap = new RegisterContentsFactory(currencyList, currencyInputs).getContents();

            return inputMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidInputException(command);
        }
    }

    private void print(Exception e) {
        System.out.println(e.getMessage());
    }

    private void print(Map<Integer, Long> value) {
        for (Integer key : value.keySet()) {
            System.out.print(value.get(key) + " ");
        }

        System.out.print("\n");
    }
}
