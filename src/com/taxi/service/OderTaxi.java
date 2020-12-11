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

public class OderTaxi {

    final static Scanner scanner = new Scanner(System.in);

    public static void orderTaxiWorkFlow(int clientId) {
        System.out.println("Choose one of the following vehicles:");
        System.out.println("1. Mercedes Benz S class");
        System.out.println("2. Mercedes Benz C class");
        System.out.println("3. Toyota Prius");
        System.out.println("4. Tesla");
        String option = scanner.nextLine();
        List<String> validValue = new ArrayList<String>();
        validValue.add("1");
        validValue.add("2");
        validValue.add("3");
        validValue.add("4");
        option = TaxiAppUtil.validateUserEntriesWithRetry(option, "Vehicle Type", validValue);
        //Insert to DB
        System.out.println("Your Taxi is on its way to you!");
    }

}
