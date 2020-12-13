package com.taxi.service;

import java.util.Scanner;

public class UserFlow {

    static Scanner scanner = new Scanner(System.in);
    private static int loggedInClientId = 0;

    public static void userLoginWorkFlow(int clientId) {
        loggedInClientId = clientId;
        System.out.println("\t\t\tLogged in successfully!");
        System.out.println("\tPlease choose one of the following action now:");
        System.out.println("\t > 1. Order Taxi \t\t\t > 2. Account Actions");
        processAction(0);
    }

    private static void processAction(int retry) {
        String option = scanner.nextLine();
        if ("Order Taxi".equalsIgnoreCase(option) || "1".equalsIgnoreCase(option)) {
            OderTaxi.orderTaxiWorkFlow(loggedInClientId);
        } else if ("Account Actions".equalsIgnoreCase(option) || "2".equalsIgnoreCase(option)) {
            AccountAction.accountActionWorkFlow(loggedInClientId);
        } else {
            if (retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1);
            } else {
                System.err.println("Invalid action selected");
                MainView.getConfirmationForMain();
            }
        }
    }
}
