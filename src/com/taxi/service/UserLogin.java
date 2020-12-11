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

import java.util.Map;
import java.util.Scanner;

public class UserLogin {

    final static Scanner scanner = new Scanner(System.in);

    public static void userLoginWorkflow() {
        System.out.println("Please enter the following account details:");
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        userName = TaxiAppUtil.validateParam(userName, "Username", null);
        System.out.print("Password: ");
        String password = scanner.nextLine();
        password = TaxiAppUtil.validateParam(password, "Password", null);
        Map<String, Object> userDetail = DbManager.getUser(userName, password);
        if(userDetail == null || userDetail.size() == 0) {
            System.out.println("Invalid Login Credentials");
        } else {
            String roleName = (String) userDetail.get("ROLE_NAME");
            if ("USER".equalsIgnoreCase(roleName)) {
                int clientId = (int) userDetail.get("CLIENT_ID");
                UserFlow.userLoginWorkFlow(clientId);
            } else if ("ADMIN".equalsIgnoreCase(roleName)) {
                AdminFlow.adminLoginWorkFlow();
            } else {
                System.out.println("System error, please try again later");
            }
        }
    }
}
