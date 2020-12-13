package com.taxi.service;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaxiAppUtil {

    static Scanner scanner = new Scanner(System.in);

    public static String getDateParam(String paramValue, String paramName) {
        paramValue = TaxiAppUtil.validateUserEntriesWithRetry(paramValue, paramName, null);
        if (!TaxiAppUtil.validateDate(paramValue)) {
            System.out.print("Invalid date format, please re-enter value: ");
            paramValue = scanner.nextLine();
            paramValue = TaxiAppUtil.validateUserEntriesWithRetry(paramValue, paramName, null);
            if (!TaxiAppUtil.validateDate(paramValue)) {
                System.out.println("Invalid date format");
                MainView.mainView();
            }
        }
        return paramValue;
    }

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

    public static boolean validateDate(String birthDate) {
        String dateRegex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(birthDate);
        boolean bool = matcher.matches();
        return bool;
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
