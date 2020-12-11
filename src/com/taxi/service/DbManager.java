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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager {
    private static Connection conn = null;
    private static String connectionUrl = "jdbc:mysql://remotemysql.com:3306/eVArTTJYZH?useSSL=false";
    private static String connectionUser = "eVArTTJYZH";
    private static String connectionPassword = "wPPiA3SPnc";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            System.err.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public static Map<String, Object> getUser(String userName, String password) {
        Map<String, Object> userDetails = new HashMap<>();
        try {
            final String sql = "SELECT ROLE_NAME, CLIENT_ID from ROLES R, USERS U WHERE U.ROLE_ID = R.ROLE_ID AND U.USERNAME = ? AND U.PASSWORD = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userDetails.put("ROLE_NAME", rs.getString("ROLE_NAME"));
                userDetails.put("CLIENT_ID", rs.getInt("CLIENT_ID"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return userDetails;
    }


    public static int createAccount(final Account account) {
        int userId = 0;
        int clientId = 0;
        try {
            String sql = "INSERT INTO CLIENT(NAME, BIRTH_DATE, ADDRESS, POSTAL_CODE, EMAIL, LOCATION) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getName());
            statement.setDate(2, new java.sql.Date(account.getBirthDate().getTime()));
            statement.setString(3, account.getAddress());
            statement.setString(4, account.getPostalCode());
            statement.setString(5, account.getEmail());
            statement.setString(6, account.getLocation());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                clientId = rs.getInt(1);
            }
            rs.close();
            statement.close();
            if (clientId > 0) {
                int roleId = 0;
                sql = "SELECT ROLE_ID from ROLES WHERE ROLE_NAME = ?";
                statement = conn.prepareStatement(sql);
                statement.setString(1, account.getRole());
                rs = statement.executeQuery();
                // Loop through the data and print all artist names
                while (rs.next()) {
                    roleId = rs.getInt("ROLE_ID");
                }
                rs.close();
                statement.close();

                if (roleId > 0) {

                    sql = "INSERT INTO USERS(USERNAME, PASSWORD, CLIENT_ID, ROLE_ID) "
                            + "VALUES(?,?,?,?)";
                    statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, account.getUserName());
                    statement.setString(2, account.getPostalCode());
                    statement.setInt(3, userId);
                    statement.setInt(4, roleId);
                    statement.executeUpdate();
                    rs = statement.getGeneratedKeys();

                    if (rs != null && rs.next()) {
                        userId = rs.getInt(1);
                    }
                    rs.close();
                    statement.close();
                }
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return clientId;
    }


    public static List<String> getDrivers() {
        List<String> driverNames = new ArrayList<>();
        try {
            final String sql = "SELECT FIRST_NAME, LAST_NAME from DRIVER WHERE WORKING = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                driverNames.add(rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return driverNames;
    }

    public static List<String> getVehicles() {
        List<String> vehicleNames = new ArrayList<>();
        try {
            final String sql = "SELECT MODEL_NAME from TAXI WHERE ACTIVE = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehicleNames.add(rs.getString("MODEL_NAME"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return vehicleNames;
    }

    public static void deleteVehicle(String vehicleName) {
        try {
            final String sql = "DELETE from TAXI, TAXI_DRIVER WHERE MODEL_NAME = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehicleNames.add(rs.getString("MODEL_NAME"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
