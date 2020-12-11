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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount {

    final static Scanner scanner = new Scanner(System.in);

    /**
     Handle Create Account flow
     */
    public static void createClientAccountWorkFlow() {
        System.out.println("Please fill the following information about your self: ");
        System.out.print("Name : ");
        String name =  scanner.nextLine();
        name = TaxiAppUtil.validateUserEntriesWithRetry(name, "Name", null);
        System.out.print("Birth Date (dd/mm/yyyy): ");
        String birthDate =  scanner.nextLine();
        birthDate = TaxiAppUtil.validateUserEntriesWithRetry(birthDate, "Birth Date", null);
        String dateRegex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(birthDate);
        boolean bool = matcher.matches();
        if(!bool) {
           System.out.println("Invalid date format");
           System.exit(0);
        }
        System.out.print("Email Address : ");
        String email =  scanner.nextLine();
        email = TaxiAppUtil.validateUserEntriesWithRetry(email, "Email Address", null);
        System.out.print("Address : ");
        String address =  scanner.nextLine();
        address = TaxiAppUtil.validateUserEntriesWithRetry(address, "Address", null);
        System.out.print("Postal Code : ");
        String postalCode =  scanner.nextLine();
        postalCode = TaxiAppUtil.validateUserEntriesWithRetry(postalCode, "Postal Code", null);
        System.out.print("Location : ");
        String location =  scanner.nextLine();
        location = TaxiAppUtil.validateUserEntriesWithRetry(location, "Location", null);
        System.out.print("Username : ");
        String username =  scanner.nextLine();
        username = TaxiAppUtil.validateUserEntriesWithRetry(username, "Username", null);
        System.out.print("password : ");
        String password =  scanner.nextLine();
        password = TaxiAppUtil.validateUserEntriesWithRetry(password, "Password", null);
        // TODO Call to DB
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
        int clientId = DbManager.createAccount(account);
        UserFlow.userLoginWorkFlow(clientId);

    }




}
