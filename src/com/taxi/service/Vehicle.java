// Persistent Systems
//
// All Rights Reserved.
//
// This document or any part thereof may not, without the written
// consent of AePONA Limited, be copied, reprinted or reproduced in
// any material form including but not limited to photocopying,
// transcribing, transmitting or storing it in any medium or
// translating it into any language, in any form or by any means,
// be it electronic, mechanical, xerographic, optical,
// magnetic or otherwise.
//
//nn40238
package com.taxi.service;

import java.util.List;
import java.util.Scanner;

public class Vehicle {

    final static Scanner scanner = new Scanner(System.in);

    public static void showAvailableVehicles() {
        List<String> vehicles = DbManager.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No active vehicles found");
        } else {
            for (String vehicleName: vehicles) {
                System.out.println(vehicleName);
            }
        }
        System.out.println("\n");
        System.out.print(" Enter 1 to go back to previous menu : ");
        String option =  scanner.nextLine();
        if("1".equals(option)) {
            AdminFlow.adminLoginWorkFlow();
        } else {
            System.exit(0);
        }
    }

    public static void addVehicle() {
        System.out.print("Enter the name of the vehicle here:");
    }

    public static void removeVehicle() {
        System.out.print("Enter the name of the vehicle here: ");
        String vehicleName = scanner.nextLine();
        vehicleName = TaxiAppUtil.validateUserEntriesWithRetry(vehicleName, "Vehicle Name", null);
        DbManager.deleteVehicle(vehicleName);
    }
}
