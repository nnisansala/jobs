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
    private static String connectionUrl = "jdbc:mysql://remotemysql.com:3306/2Lq7oJ0sFQ?useSSL=false";
    private static String connectionUser = "2Lq7oJ0sFQ";
    private static String connectionPassword = "6kvqSw97LY";

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
            String sql = "SELECT * FROM APP_LOGIN WHERE USERNAME = ? and PASSWORD = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userDetails.put("USER_NAME", rs.getString("USERNAME"));
                userDetails.put("PASSWORD", rs.getString("PASSWORD"));
                userDetails.put("COMPANY_ID", rs.getInt("COMPANY_ID"));
                userDetails.put("CLIENT_ID", rs.getInt("CLIENT_ID"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userDetails;
    }


    public static int createAccount(Account account) {
        int clientId = 0;
        try {
            int addressId = insertAddress(account);
            int companyId = selectCompanyId();
            String sql = "INSERT INTO CLIENT(NAME, BIRTH_DATE, EMAIL, ADDRESS_ID, COMPANY_ID) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getName());
            statement.setDate(2, new java.sql.Date(account.getBirthDate().getTime()));
            statement.setString(3, account.getEmail());
            statement.setInt(4, addressId);
            statement.setInt(5, companyId);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                clientId = rs.getInt(1);
            }
            rs.close();
            statement.close();
            if (clientId > 0) {
                sql = "INSERT INTO APP_LOGIN(USERNAME, PASSWORD, CLIENT_ID) "
                        + "VALUES(?,?,?)";
                statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, account.getUserName());
                statement.setString(2, account.getPassword());
                statement.setInt(3, clientId);
                statement.executeUpdate();
                rs = statement.getGeneratedKeys();
                rs.close();
                statement.close();

            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return clientId;
    }

    private static int insertAddress(Account account) {
        int addressId = 0;
        try {
            String sql = "INSERT INTO ADDRESS(ADDRESS, POSTAL_CODE, LOCATION) "
                    + "VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getAddress());
            statement.setString(2, account.getPostalCode());
            statement.setString(3, account.getLocation());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                addressId = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return addressId;
    }

    private static int selectCompanyId() {
        int companyId = 0;
        try {
            String sql = "SELECT COMPANY_ID from TAXI_COMPANY";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.next();
            companyId = rs.getInt("COMPANY_ID");
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return companyId;
    }

    public static List<String> getDrivers() {
        List<String> driverNames = new ArrayList<>();
        try {
            String sql = "SELECT FIRST_NAME, LAST_NAME from DRIVER WHERE WORKING = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                driverNames.add(rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return driverNames;
    }

    public static List<String> getVehicles() {
        List<String> vehicleNames = new ArrayList<>();
        try {
            String sql = "SELECT MODEL_NAME from TAXI_MODEL";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehicleNames.add(rs.getString("MODEL_NAME"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return vehicleNames;
    }

    public static boolean deleteVehicleByVehicleName(String vehicleName) {
        boolean isDeleted = false;
        try {
            deleteBookingByVehicleName(vehicleName);
            List<Integer> registrationId = getRegistrationIdFromVehicleName(vehicleName);
            deleteTaxiByVehicleName(vehicleName);
            if(registrationId.size() > 0) {
                deleteRegistrationById(registrationId);
            }
            String sql = "DELETE from TAXI_MODEL WHERE MODEL_NAME = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, vehicleName);
            int rs = statement.executeUpdate();
            if (rs > 0) {
                isDeleted = true;
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return isDeleted;
    }

    private static void deleteBookingByVehicleName(String vehicleName) {
        try {
            String sql = "DELETE from BOOKING WHERE TAXI_ID in (SELECT T.ID from TAXI T, TAXI_MODEL TM WHERE T.MODEL_ID = TM.ID AND TM.MODEL_NAME = ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, vehicleName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(1);
            System.err.println(e.getMessage());
        }
    }

    private static List<Integer> getRegistrationIdFromVehicleName(String vehicleName) {
        List<Integer> registrationIds = new ArrayList<>();

        try {
            String sql = "SELECT REGISTRATION_ID from TAXI WHERE MODEL_ID in (SELECT MODEL_ID from TAXI_MODEL WHERE MODEL_NAME = ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, vehicleName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                registrationIds.add(rs.getInt("REGISTRATION_ID"));
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return registrationIds;
    }

    private static void deleteTaxiByVehicleName(String vehicleName) {
        try {
            String sql = "DELETE FROM TAXI WHERE MODEL_ID in (SELECT MODEL_ID FROM TAXI_MODEL WHERE MODEL_NAME = ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, vehicleName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void deleteRegistrationById(List<Integer> registrationIds) {
        try {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < registrationIds.size(); i++) {
                builder.append("?,");
            }

            String placeHolders = builder.deleteCharAt(builder.length() - 1).toString();

            String sql = "DELETE from REGISTRATION WHERE REGISTRATION_ID (" + placeHolders + ")";
            PreparedStatement statement = conn.prepareStatement(sql);
            int index = 1;
            for (int o : registrationIds) {
                statement.setInt(index++, o);
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean deleteDriverByDriverName(String driverName) {
        try {
            int driverId = getDriverIdByDriverName(driverName);
            if (driverId == 0) {
                return false;
            }
            int taxiId = getTaxiIdByDriverId(driverId);
            if (taxiId != 0) {
                deleteBookingByTaxiId(taxiId);
                deleteTaxiByTaxiId(taxiId);
            }
            deleteLicenceByDriverId(driverId);
            String sql = "DELETE FROM DRIVER WHERE DRIVER_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, driverId);
            int rs = statement.executeUpdate();
            if (rs > 0) {
                return true;
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    private static int getDriverIdByDriverName(String driverName) {
        int driverId = 0;
        try {

            String sql = "SELECT DRIVER_ID from DRIVER WHERE CONCAT(FIRST_NAME, ' ', LAST_NAME) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, driverName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                driverId = rs.getInt("DRIVER_ID");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return driverId;
    }

    private static int getTaxiIdByDriverId(int driverId) {
        int taxiId = 0;
        try {

            String sql = "SELECT ID from TAXI WHERE DRIVER_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, driverId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                taxiId = rs.getInt("ID");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return taxiId;
    }

    private static void deleteBookingByTaxiId(int taxiId) {
        try {
            String sql = "DELETE FROM BOOKING WHERE TAXI_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, taxiId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void deleteTaxiByTaxiId(int taxiId) {
        try {
            String sql = "DELETE FROM TAXI WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, taxiId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void deleteLicenceByDriverId(int driverId) {
        try {
            String sql = "DELETE FROM LICENCE WHERE DRIVER_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, driverId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static int addVehicle(Vehicle vehicle) {
        int taxiId = 0;
        int companyId = selectCompanyId();
        int modelId = addModel(vehicle);
        int registrationId = addRegistration(vehicle);
        try {
            String sql = "INSERT INTO TAXI(ACTIVE, DRIVER_ID, REGISTRATION_ID, MODEL_ID, COMPANY_ID) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, vehicle.isActive());
            statement.setInt(2, vehicle.getDriverId());
            statement.setInt(3, registrationId);
            statement.setInt(4, modelId);
            statement.setInt(5, companyId);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                taxiId = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return taxiId;
    }

    private static int addModel(Vehicle vehicle) {
        int modelId = 0;
        try {
            String sql = "INSERT INTO TAXI_MODEL(MODEL_NAME, MODEL_DESCRIPTION) "
                    + "VALUES(?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, vehicle.getModelName());
            statement.setString(2, vehicle.getModelDescription());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                modelId = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return modelId;
    }

    private static int addRegistration(Vehicle vehicle) {
        int registrationId = 0;
        try {
            String sql = "INSERT INTO REGISTRATION(REGISTRATION_DATE, LICENCE_PALTE_NR) "
                    + "VALUES(?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(vehicle.getRegistrationDate().getTime()));
            statement.setString(2, vehicle.getLicencePlateNumber());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                registrationId = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return registrationId;
    }

    public static int addDriver(Driver driver) {
        int companyId = selectCompanyId();
        int driverId = 0;
        try {
            String sql = "INSERT INTO DRIVER(FIRST_NAME, LAST_NAME, BIRTH_DATE, WORKING, COMPANY_ID) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setDate(3, new java.sql.Date(driver.getBirthDate().getTime()));
            statement.setBoolean(4, driver.isWorking());
            statement.setInt(5, companyId);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                driverId = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        addLicence(driver, driverId);
        return driverId;
    }

    private static void addLicence(Driver driver, int driverId) {
        try {
            String sql = "INSERT INTO LICENCE(LICENCE_NR, EXPIRY_DATE, DRIVER_ID) "
                    + "VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, driver.getLicenceNumber());
            statement.setDate(2, new java.sql.Date(driver.getExpiryDate().getTime()));
            statement.setInt(3, driverId);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static int addBooking(String modelName, int clientId) {
        int bookingId = 0;
        int taxiId = getTaxiIdByModelName(modelName);
        if (taxiId != 0) {
            try {
                String sql = "INSERT INTO BOOKING(CLIENT_ID, TAXI_ID) "
                        + "VALUES(?,?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, clientId);
                statement.setInt(2, taxiId);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    bookingId = rs.getInt(1);
                }
                rs.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }
        return bookingId;
    }

    private static int getTaxiIdByModelName(String modelName) {
        int taxiId = 0;
        try {
            String sql = "SELECT T.ID from TAXI T, TAXI_MODEL TM WHERE T.MODEL_ID = TM.ID AND TM.MODEL_NAME = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, modelName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                taxiId = rs.getInt("ID");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return taxiId;
    }

    public static boolean deleteClientByClientId(int clientId) {
        boolean isDeleted = false;
        deleteBookingByClientId(clientId);
        deleteLoggingByClientId(clientId);
        try {
            String sql = "DELETE FROM CLIENT WHERE CLIENT_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, clientId);
            int rs = statement.executeUpdate();
            if (rs > 0) {
                isDeleted = true;
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return isDeleted;
    }

    private static void deleteBookingByClientId(int clientId) {
        try {
            String sql = "DELETE FROM BOOKING WHERE CLIENT_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, clientId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void deleteLoggingByClientId(int clientId) {
        try {
            String sql = "DELETE FROM APP_LOGIN WHERE CLIENT_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, clientId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static int getDriverByIdOrName(String driver) {
        int driverId = 0;
        try {
            String sql = "SELECT DRIVER_ID from DRIVER WHERE CONCAT(FIRST_NAME, ' ', LAST_NAME) = ? OR DRIVER_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, driver);
            if (isInteger(driver)) {
                statement.setInt(2, Integer.parseInt(driver));
            } else {
                statement.setInt(2, 0);
            }
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                driverId = rs.getInt("DRIVER_ID");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return driverId;
    }

    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
