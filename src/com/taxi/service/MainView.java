package com.taxi.service;

import java.util.Scanner;

public class MainView {

    private static Scanner scanner = new Scanner(System.in);

    public static void mainView() {
        System.out.println("\t\t\tWelcome to MyTaxi!\n");
        System.out.println("\tPlease choose one of the following actions:");
        System.out.println("\t> Create an account\t\t\t > User Login");
        System.out.println("\t\t\t\t > Admin Login");
        System.out.print("\t: ");
        processAction(0);
    }

    public static void getConfirmationForMain() {
        System.out.println("Do you want to return to main menu (y/n) : ");
        String mainMenu = scanner.nextLine();
        if (mainMenu == null || !mainMenu.equalsIgnoreCase("y")){
            System.out.println("Closing the application");
            System.exit(0);
        } else {
            mainView();
        }
    }

    private static void processAction(int retry) {
        String option = scanner.nextLine();
        if ("Create an account".equalsIgnoreCase(option)) {
            CreateAccount.createClientAccountWorkFlow();
        } else if ("User Login".equalsIgnoreCase(option)) {
            UserLogin.userLoginWorkflow();
        } else if ("Admin Login".equalsIgnoreCase(option)) {
            UserLogin.userLoginWorkflow();
        } else {
            if (retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1);
            } else {
                System.err.println("Invalid action selected, closing application");
                System.exit(0);
            }
        }
    }
}
