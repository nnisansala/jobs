package com.taxi.service;

import java.util.List;
import java.util.Scanner;

public class TaxiAppUtil {

    static Scanner scanner = new Scanner(System.in);

    public static String validateUserEntriesWithRetry(String paramValue, String paramName, List<String> validValues) {
        int i = 0;
        while (i < 1 && (paramValue == null || paramValue.trim().equals(""))) {
            i++;
            paramValue = validateParam(paramValue, paramName, validValues);
        }
        if ((paramValue == null || paramValue.trim().equals(""))) {
            System.out.println("Invalid value entered, so closing the application");
            System.exit(0);
        }
        return paramValue;
    }

    public static String validateParam(String paramValue, String paramName, List<String> validValue) {
        if (paramValue == null || paramValue.trim().equals("")
                || (validValue != null && !validValue.contains(paramName))) {
            System.out.print("Invalid Value, please enter correct value for " + paramName + " : ");
            paramValue = scanner.nextLine();
        }

        return paramValue;
    }
}
