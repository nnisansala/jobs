package com.taxi.service;

import java.util.Scanner;

public class AccountAction {
    static Scanner scanner = new Scanner(System.in);

    public static void accountActionWorkFlow(int clientId) {
        System.out.println("Please choose the action");
        System.out.println("\t> 1. Delete Account \t\t> 2. Go Back");
        processAction(0, clientId);
    }

    private static void processAction(int retry, int clientId) {
        String option = scanner.nextLine();
        if ("Delete Account".equalsIgnoreCase(option) || "1".equals(option)) {
            boolean isDeleted = DbManager.deleteClientByClientId(clientId);
            if (isDeleted) {
                System.out.println("Your account was deleted successfully.");
            } else {
                System.out.println("System Error Occurred");
            }
        } else if ("Go Back".equalsIgnoreCase(option) || "2".equals(option)) {
            UserFlow.userLoginWorkFlow(clientId);
        } else {
            if (retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1, clientId);
            } else {
                System.out.println("Invalid action selected");
            }
        }
        MainView.getConfirmationForMain();
    }
}
