package com.taxi.service;

import java.util.Scanner;

public class TaxiApplication {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\t\t\tWelcome to MyTaxi!\n");
        System.out.println("\tPlease choose one of the following actions:");
        System.out.println("\t> Create an account\t\t\t > User Login");
        System.out.println("\t\t\t\t > Admin Login");
        System.out.print("\t\t\t\t : ");
        processAction(0);
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
            if(retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1);
            } else {
                System.out.println("Invalid action selected, closing application");
            }
        }
    }

    /**
        Handle Admin Login flow
     */
    private static void adminLogin() {
    }

    /**
        Handle User Login flow
     */
    private static void userLogin() {
    }


}
