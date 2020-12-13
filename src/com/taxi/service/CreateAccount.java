package com.taxi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CreateAccount {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Handle Create Account flow
     */
    public static void createClientAccountWorkFlow() {
        System.out.println("Please fill the following information about your self: ");
        System.out.print("Name : ");
        String name = scanner.nextLine();
        name = TaxiAppUtil.validateUserEntriesWithRetry(name, "Name", null);
        System.out.print("Birth Date (dd/mm/yyyy): ");
        String birthDate = scanner.nextLine();
        birthDate = TaxiAppUtil.getDateParam(birthDate, "Birth Date");
        System.out.print("Email Address : ");
        String email = scanner.nextLine();
        email = TaxiAppUtil.validateUserEntriesWithRetry(email, "Email Address", null);
        System.out.print("Address : ");
        String address = scanner.nextLine();
        address = TaxiAppUtil.validateUserEntriesWithRetry(address, "Address", null);
        System.out.print("Postal Code : ");
        String postalCode = scanner.nextLine();
        postalCode = TaxiAppUtil.validateUserEntriesWithRetry(postalCode, "Postal Code", null);
        System.out.print("Location : ");
        String location = scanner.nextLine();
        location = TaxiAppUtil.validateUserEntriesWithRetry(location, "Location", null);
        System.out.print("Username : ");
        String username = scanner.nextLine();
        username = TaxiAppUtil.validateUserEntriesWithRetry(username, "Username", null);
        System.out.print("password : ");
        String password = scanner.nextLine();
        password = TaxiAppUtil.validateUserEntriesWithRetry(password, "Password", null);

        Account account = new Account();
        account.setAddress(address);
        try {
            account.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(birthDate));
        } catch (ParseException e) {

        }
        account.setEmail(email);
        account.setLocation(location);
        account.setName(name);
        account.setPostalCode(postalCode);
        account.setUserName(username);
        account.setPassword(password);
        int accountId = DbManager.createAccount(account);
        if (accountId > 0) {
            System.out.println("Account is created successfully");
            System.out.println("Please login to the system");
            UserLogin.userLoginWorkflow(false);
        } else {
            System.out.println("Account is not created due to system error. ");
            MainView.getConfirmationForMain();
        }

    }


}
