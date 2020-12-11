package com.taxi.service;

import java.util.List;
import java.util.Scanner;

public class Driver {

    static Scanner scanner = new Scanner(System.in);

    public static void showAvailableDrivers() {
        List<String> drivers = DbManager.getDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No active drivers found");
        } else {
            for (String driverName : drivers) {
                System.out.println(driverName);
            }
        }
        adminMenuView();

    }

    private static void adminMenuView() {
        System.out.println("\n");
        System.out.print(" Enter 1 to go back to previous menu : ");
        String option = scanner.nextLine();
        if ("1".equals(option)) {
            AdminFlow.adminLoginWorkFlow();
        } else {
            System.exit(0);
        }
    }

    public static void addDriver() {
        System.out.println("Enter the name of the driver here:");
        String driverName = scanner.nextLine();
        driverName = TaxiAppUtil.validateUserEntriesWithRetry(driverName, "Driver Name", null);
        int recordId = DbManager.addDriver(driverName);
        if (recordId > 0) {
            System.out.println("New Driver Added");
        } else {
            System.err.println("System Error Occurred, Please try again");
        }
        adminMenuView();
    }

    public static void removeDriver() {
        System.out.print("Enter the name of the driver here: ");
        String driverName = scanner.nextLine();
        driverName = TaxiAppUtil.validateUserEntriesWithRetry(driverName, "Driver Name", null);
        boolean isDeleted = DbManager.deleteDriver(driverName);
        if (!isDeleted) {
            System.err.println("Invalid Driver Name.");
        } else {
            System.out.println("Driver is deleted from the system");
        }
        adminMenuView();
    }
}
