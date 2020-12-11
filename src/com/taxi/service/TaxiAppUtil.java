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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxiAppUtil {

    final static Scanner scanner = new Scanner(System.in);

    public static String validateUserEntriesWithRetry(String paramValue, String paramName, List<String> validValues) {
        int i = 0;
        while (i < 1 && (paramValue == null || paramValue.trim().equals(""))) {
            i++;
            paramValue = validateParam(paramValue, paramName, validValues);
        }
        if((paramValue == null || paramValue.trim().equals(""))){
            System.out.println("Invalid value entered, so closing the application");
            System.exit(0);
        }
        return paramValue;
    }

    public static String validateParam(String paramValue, String paramName, List<String> validValue) {
        if(paramValue == null || paramValue.trim().equals("")
                || (validValue != null && !validValue.contains(paramName))) {
            System.out.print("Invalid Value, please enter correct value for " + paramName + " : ");
            paramValue =  scanner.nextLine();
        }

        return paramValue;
    }
}
