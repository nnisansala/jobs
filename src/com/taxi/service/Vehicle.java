package com.taxi.service;

import java.util.List;
import java.util.Scanner;

public class Vehicle {

    static Scanner scanner = new Scanner(System.in);

    public static void showAvailableVehicles() {
        showVehicleList();
        System.out.println("\n");
        System.out.print(" Enter 1 to go back to previous menu : ");
        String option = scanner.nextLine();
        if ("1".equals(option)) {
            AdminFlow.adminLoginWorkFlow();
        } else {
            System.exit(0);
        }
    }

    public static List<String> showVehicleList() {
        List<String> vehicles = DbManager.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No active vehicles found");
        } else {
            int i = 0;
            for (String vehicleName : vehicles) {
                i++;
                System.out.println(i + ": " + vehicleName);
            }
        }
        return vehicles;
    }

    public static void addVehicle() {
        System.out.print("Enter the name of the vehicle here: ");
        String vehicleName = scanner.nextLine();
        vehicleName = TaxiAppUtil.validateUserEntriesWithRetry(vehicleName, "Vehicle Name", null);
        int recordId = DbManager.addVehicle(vehicleName);
        if (recordId > 0) {
            System.out.println("New Vehicle Added");
        } else {
            System.out.println("System Error Occurred, Please try again");
        }
    }

    public static void removeVehicle() {
        System.out.print("Enter the name of the vehicle here: ");
        String vehicleName = scanner.nextLine();
        vehicleName = TaxiAppUtil.validateUserEntriesWithRetry(vehicleName, "Vehicle Name", null);
        boolean isDeleted = DbManager.deleteVehicle(vehicleName);
        if (!isDeleted) {
            System.out.println("Invalid Vehicle Name.");
        } else {
            System.out.println("Vehicle is deleted from the system");
        }
    }
}
