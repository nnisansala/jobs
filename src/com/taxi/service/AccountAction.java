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

public class AccountAction {
    final static Scanner scanner = new Scanner(System.in);

    public static void accountActionWorkFlow() {
        System.out.println("Please choose the action");
        System.out.println("\t> Delete Account \t\t> Go Back");
        processAction(0);
    }

    private static void processAction(int retry) {
        String option = scanner.nextLine();
        if("Delete Account".equalsIgnoreCase(option)) {
            System.out.println("Your account was deleted successfully.");
        } else if("Go Back".equalsIgnoreCase(option)) {
            UserFlow.userLoginWorkFlow(1);
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
