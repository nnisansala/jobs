package com.taxi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OderTaxi {

    static Scanner scanner = new Scanner(System.in);

    public static void orderTaxiWorkFlow(int clientId) {
        System.out.println("Choose one of the following vehicles:");
        List<String> vehicles = VehicleService.showVehicleList();
        userMenuView(clientId);
        String option = scanner.nextLine();
        List<String> validValue = new ArrayList<String>();
        for (int i = 0; i < vehicles.size(); i++) {
            validValue.add("" + i++);
        }
        option = TaxiAppUtil.validateUserEntriesWithRetry(option, "Vehicle Type", validValue);
        int bookingId = DbManager.addBooking(option, clientId);
        if (bookingId > 0) {
            System.out.println("Your Taxi is on its way to you!");
            System.exit(0);
        } else {
            System.err.println("System error occurred, Please try again later");
            MainView.getConfirmationForMain();
        }
    }

    private static void userMenuView(int clientId) {
        System.out.print(" Enter 1 to go back to previous menu : ");
        String option = scanner.nextLine();
        if ("1".equals(option)) {
            UserFlow.userLoginWorkFlow(clientId);
        } else {
            System.exit(0);
        }
    }

}
