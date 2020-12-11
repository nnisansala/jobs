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

import java.util.Scanner;

public class AdminFlow {

    final static Scanner scanner = new Scanner(System.in);

    public static void adminLoginWorkFlow() {
        System.out.println("\t\t\tLogged in successfully!");
        System.out.println("Please choose one of the following actions:");
        System.out.println("1. See list of available drives");
        System.out.println("2. See list of available vehicles");
        System.out.println("3. Add driver");
        System.out.println("4. Add vehicle");
        System.out.println("5. Remove driver");
        System.out.println("6. Remove vehicle");
        processAction(0);
    }

    private static void processAction(int retry) {
        String option = scanner.nextLine();
        if("See list of available drives".equalsIgnoreCase(option)
                || "1".equalsIgnoreCase(option)) {
            Driver.showAvailableDrivers();

        } else if("See list of available vehicles".equalsIgnoreCase(option)
                || "2".equalsIgnoreCase(option)) {

        } else if("Add driver".equalsIgnoreCase(option)
                || "3".equalsIgnoreCase(option)) {

        } else if("Add vehicle".equalsIgnoreCase(option)
                || "4".equalsIgnoreCase(option)) {

        } else if("Remove driver".equalsIgnoreCase(option)
                || "5".equalsIgnoreCase(option)) {

        } else if("Remove vehicle".equalsIgnoreCase(option)
                || "6".equalsIgnoreCase(option)) {

        } else {
            if(retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1);
            } else {
                System.out.println("Invalid action selected, closing application");
            }
        }
    }
}
