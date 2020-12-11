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

public class UserFlow {

    static Scanner scanner = new Scanner(System.in);
    private static int loggedInClientId = 0;

    public static void userLoginWorkFlow(int clientId) {
        loggedInClientId = clientId;
        System.out.println("\t\t\tLogged in successfully!");
        System.out.println("\tPlease choose one of the following action now:");
        System.out.println("\t > Order Taxi \t\t\t > Account Actions");
        processAction(0);
    }

    private static void processAction(int retry) {
        String option = scanner.nextLine();
        if ("Order Taxi".equalsIgnoreCase(option)) {
            OderTaxi.orderTaxiWorkFlow(loggedInClientId);
        } else if ("Account Actions".equalsIgnoreCase(option)) {
            AccountAction.accountActionWorkFlow(loggedInClientId);
        } else {
            if (retry == 0) {
                System.out.println("Invalid action selected, please enter correct action:");
                processAction(1);
            } else {
                System.err.println("Invalid action selected");
                MainView.getConfirmationForMain();
            }
        }
    }
}
