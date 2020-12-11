package com.taxi.service;

import java.util.Scanner;

public class AccountAction {
    static Scanner scanner = new Scanner(System.in);

    public static void accountActionWorkFlow(int clientId) {
        System.out.println("Please choose the action");
        System.out.println("\t> Delete Account \t\t> Go Back");
        processAction(0, clientId);
    }

    private static void processAction(int retry, int clientId) {
        String option = scanner.nextLine();
        if ("Delete Account".equalsIgnoreCase(option)) {
            boolean isDeleted = DbManager.deleteClient(clientId);
            if (isDeleted) {
                System.out.println("Your account was deleted successfully.");
            } else {
                System.err.println("System Error Occurred");
            }
        } else if ("Go Back".equalsIgnoreCase(option)) {
            UserFlow.userLoginWorkFlow(clientId);
        } else {
            if (retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1, clientId);
            } else {
                System.err.println("Invalid action selected");
            }
        }
        MainView.getConfirmationForMain();
    }
}
