package main.java;

import main.java.exceptions.InsufficientFundsException;
import main.java.resources.ChangeCalculationService;
import main.java.resources.RegisterContents;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RegisterIO {
    private CashRegisterModel register;
    private ChangeCalculationService changeService;
    private Scanner in;
    private String[] DOLLAR_KEYS;



    public RegisterIO() {
        register = new CashRegisterModel();

        DOLLAR_KEYS = new String[5];
        DOLLAR_KEYS[0] = "TWENTY";
        DOLLAR_KEYS[1] = "TEN";
        DOLLAR_KEYS[2] = "FIVE";
        DOLLAR_KEYS[3] = "TWO";
        DOLLAR_KEYS[4] = "ONE";



        try {
            startApplication();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void startApplication() throws IOException {
        String inputLine;
        String[] inputs;

        boolean keepAppRunning = true;
        in = new Scanner(System.in);

        System.out.println("This is a cash register. \nYou have a few commands available to you.");
        printHelpMenu();
        System.out.println("Ready for input... \n");

        while (keepAppRunning) {
            inputLine = in.nextLine();
            inputs = inputLine.split(" ");

            switch (inputs[0]) {
                case "quit": keepAppRunning = false;
                    break;
                case "help": printHelpMenu();
                    break;
                case "show": showRegisterContents();
                    break;
                case "put": depositBills(inputs);
                    break;
                case "take": withdrawBills(inputs);
                    break;
                case "change": makeChange(inputs);
                    break;
                case "default": printHelpMenu();
                    break;
            }
        }

        in.close();
    }

    public void printHelpMenu() {
        System.out.println("Commands: \n");
        System.out.println("> show \t\t\t\t\t\t| Display register value and inventory");
        System.out.println("> put \t20s 10s 5s 2s 1s \t| Deposit bills, a number for each denomination is required");
        System.out.println("> take \t20s 10s 5s 2s 1s \t| Withdraw bills, a number for each denomination is required");
        System.out.println("> change \t\t\t\t\t| Display change in available denominations and withdraw");
        System.out.println("> help \t\t\t\t\t\t| Display these commands");
        System.out.println("> quit \t\t\t\t\t\t| Exit the program");
    }

    public void showRegisterContents() {
        System.out.print(String.format("\n$%s ", register.getBalance()));
        Map<String, Long> contents = register.getContents();

        for (int i = 0; i < DOLLAR_KEYS.length; i++) {
            System.out.print(contents.get(DOLLAR_KEYS[i]) + " ");
        }

        System.out.print("\n\n");
    }

    private void depositBills(String[] inputs) {
        Map<String, Long> depositValues = new HashMap<>();

        if (inputs.length != 6) {
            System.out.println("Command must be the word \"put\" followed by five, space-delimited integers: \"put 3 0 5 2 1\"");
        } else {
            depositValues = mapInput(inputs);
        }

        register.deposit(depositValues);
        System.out.print("\n");
    }

    private void withdrawBills(String[] inputs) {
        Map<String, Long> withdrawalValues = new HashMap<>();

        if (inputs.length !=6) {
            System.out.println("Command must be the word \"put\" followed by five, space-delimited integers: \"put 3 0 5 2 1\"");
        } else {
            withdrawalValues = mapInput(inputs);
        }

        try {
            register.withdraw(withdrawalValues);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("\n");
    }

    private void makeChange(String[] inputs) {
        long value = Long.parseLong(inputs[1]);

        try {
            changeService.getChange(value, register.getContents());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("\n");
    }

    private Map<String, Long> mapInput(String[] input) {
        Map<String, Long> inputMap;

        long twenties = Long.parseLong(input[1]);
        long tens = Long.parseLong(input[2]);
        long fives = Long.parseLong(input[3]);
        long twos = Long.parseLong(input[4]);
        long ones = Long.parseLong(input[5]);

        inputMap = new RegisterContents(twenties, tens, fives, twos, ones).getContents();

        return inputMap;
    }
}
