package com.taxi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class VehicleService {

    static Scanner scanner = new Scanner(System.in);

    public static void showAvailableVehicles() {
        showVehicleList();
        adminMenuView();
    }

    public static List<String> showVehicleList() {
        List<String> vehicles = DbManager.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No active vehicles found.");
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
        System.out.println("Please enter the following details: ");
        System.out.print("Model Name : ");
        String modelName = scanner.nextLine();
        modelName = TaxiAppUtil.validateUserEntriesWithRetry(modelName, "Model Name", null);
        System.out.print("Model Description : ");
        String modelDescription = scanner.nextLine();
        modelDescription = TaxiAppUtil.validateUserEntriesWithRetry(modelDescription, "Model Description", null);
        System.out.print("Registered Date (dd/mm/yyyy): ");
        String registeredDate = scanner.nextLine();
        registeredDate = TaxiAppUtil.getDateParam(registeredDate, "Registered Date");
        System.out.print("Licence Plate Number : ");
        String licencePlateNumber = scanner.nextLine();
        licencePlateNumber = TaxiAppUtil.validateUserEntriesWithRetry(licencePlateNumber, "Licence Plate Number", null);
        System.out.print("Driver Name/Id : ");
        String driverId = scanner.nextLine();
        driverId = TaxiAppUtil.validateUserEntriesWithRetry(driverId, "Driver Id", null);
        int dbDriverId = DbManager.getDriverByIdOrName(driverId);
        if(dbDriverId <= 0) {
            System.out.print("Driver is not a registered driver. Enter registered driver name/id : ");
            driverId = scanner.nextLine();
            driverId = TaxiAppUtil.validateUserEntriesWithRetry(driverId, "Driver Id", null);
            dbDriverId = DbManager.getDriverByIdOrName(driverId);
            if(dbDriverId <= 0) {
                System.out.println("Driver is not a registered driver.");

            }
        }
        if(dbDriverId > 0) {
            Vehicle vehicle = new Vehicle();
            vehicle.setActive(true);
            vehicle.setModelDescription(modelDescription);
            vehicle.setModelName(modelName);
            vehicle.setLicencePlateNumber(licencePlateNumber);
            vehicle.setDriverId(dbDriverId);
            try {
                vehicle.setRegistrationDate(new SimpleDateFormat("dd/MM/yyyy").parse(registeredDate));
            } catch (ParseException e) {

            }
            int recordId = DbManager.addVehicle(vehicle);
            if (recordId > 0) {
                System.out.println("New Vehicle Added.");
            } else {
                System.out.println("System Error Occurred, Please try again.");
            }
        }
        adminMenuView();
    }

    public static void removeVehicle() {
        System.out.print("Enter the name of the vehicle here: ");
        String vehicleName = scanner.nextLine();
        vehicleName = TaxiAppUtil.validateUserEntriesWithRetry(vehicleName, "Vehicle Name", null);
        boolean isDeleted = DbManager.deleteVehicleByVehicleName(vehicleName);
        if (!isDeleted) {
            System.out.println("Invalid Vehicle Name.");
        } else {
            System.out.println("Vehicle is deleted from the system.");
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
}
