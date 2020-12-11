package com.taxi.service;

import java.util.Map;
import java.util.Scanner;

public class UserLogin {

    static Scanner scanner = new Scanner(System.in);

    public static void userLoginWorkflow() {
        System.out.println("Please enter the following account details:");
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        userName = TaxiAppUtil.validateParam(userName, "Username", null);
        System.out.print("Password: ");
        String password = scanner.nextLine();
        password = TaxiAppUtil.validateParam(password, "Password", null);
        Map<String, Object> userDetail = DbManager.getUser(userName, password);
        if (userDetail == null || userDetail.size() == 0) {
            System.err.println("Invalid Login Credentials");
            MainView.getConfirmationForMain();
        } else {
            String userNameFromDb = (String) userDetail.get("USER_NAME");
            String passwordFromDb = (String) userDetail.get("PASSWORD");
            if (!userNameFromDb.equals(userName) || !passwordFromDb.equals(password)) {
                System.out.println("Invalid Login Credentials");
                MainView.getConfirmationForMain();
            } else {
                int clientId = (int) userDetail.get("CLIENT_ID");
                int companyId = (int) userDetail.get("COMPANY_ID");
                if (clientId > 0) {
                    UserFlow.userLoginWorkFlow(clientId);
                } else if (companyId > 0) {
                    AdminFlow.adminLoginWorkFlow();
                } else {
                    System.err.println("System error, please try again later");
                    MainView.getConfirmationForMain();
                }
            }
        }
    }
}
