package com.taxi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OderTaxi {

    static Scanner scanner = new Scanner(System.in);

    public static void orderTaxiWorkFlow(int clientId) {
        System.out.println("Choose one of the following vehicles:");
        List<String> vehicles = Vehicle.showVehicleList();
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

}
