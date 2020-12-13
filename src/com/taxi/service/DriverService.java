package com.taxi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class DriverService {

    static Scanner scanner = new Scanner(System.in);

    public static void showAvailableDrivers() {
        List<String> drivers = DbManager.getDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No active drivers found");
        } else {
            int i = 0;
            for (String driverName : drivers) {
                i++;
                System.out.println( i + ": " + driverName);
            }
        }
        adminMenuView();

    }

    private static void adminMenuView() {
        System.out.print(" Enter 1 to go back to previous menu : ");
        String option = scanner.nextLine();
        if ("1".equals(option)) {
            AdminFlow.adminLoginWorkFlow();
        } else {
            System.exit(0);
        }
    }

    public static void addDriver() {
        System.out.println("Enter the following details: ");
        System.out.print("First Name : ");
        String firstName = scanner.nextLine();
        firstName = TaxiAppUtil.validateUserEntriesWithRetry(firstName, "First Name", null);
        System.out.print("Last Name : ");
        String lastName = scanner.nextLine();
        lastName = TaxiAppUtil.validateUserEntriesWithRetry(lastName, "Last Name", null);
        System.out.print("Birth Date (dd/mm/yyyy): ");
        String birthDate = scanner.nextLine();
        birthDate = TaxiAppUtil.getDateParam(birthDate, "Birth Date");
        System.out.print("Licence Number: ");
        String licenceNumber = scanner.nextLine();
        licenceNumber = TaxiAppUtil.validateUserEntriesWithRetry(licenceNumber, "Licence Number", null);
        System.out.print("Expiry Date (dd/mm/yyyy): ");
        String expiryDate = scanner.nextLine();
        expiryDate = TaxiAppUtil.getDateParam(expiryDate, "Expiry Date");
        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        try {
            driver.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(birthDate));
            driver.setExpiryDate(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate));
        } catch (ParseException e) {

        }
        driver.setLicenceNumber(licenceNumber);
        driver.setWorking(true);
        int accountId = DbManager.addDriver(driver);
        if (accountId > 0) {
            System.out.println("Driver is added successfully");
        } else {
            System.out.println("Driver is not created due to system error. ");

        }

        adminMenuView();


    }



    public static void removeDriver() {
        System.out.print("Enter the name of the driver here: ");
        String driverName = scanner.nextLine();
        driverName = TaxiAppUtil.validateUserEntriesWithRetry(driverName, "Driver Name", null);
        boolean isDeleted = DbManager.deleteDriverByDriverName(driverName);
        if (!isDeleted) {
            System.out.println("Invalid Driver Name.");
        } else {
            System.out.println("Driver is deleted from the system");
        }
        adminMenuView();
    }
}
