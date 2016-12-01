package prms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fergus Kelley
 */
public class PRMS extends Application {

    // Here we can define globals and constants that will be used throughout the application
    // List of all the possible jobTitle values. Used in the DB and in the UI
    static final String[] JOBTITLELIST = {"Manager", "Front-desk", "Custodial", "Staff", "Kitchen"};

    // String pointing to database location
    static final String DBFILE = "jdbc:sqlite:PhoenixHotel.db";

    // Date and time formats used throughout
    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // Employee object holding the currently logged in employee
    static Employee loggedInEmployee = null;
    
    // Utility function for getting the current date in the standard format
    public static String getCurrentDateString() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(cal.getTime());
    }
    
    // Utility function for getting the current date in the standard format
    public static Calendar parseDateString(String dateString) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            cal.setTime(sdf.parse(dateString));
        } catch (Exception e) {
            System.err.println("Could not parse date string: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return cal;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Phoenix Resort Management System"); // I will learn to spell our team name someday -F
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        // Connect to the DB and create any tables if they are missing
        System.out.print("\nAttempting to connect to SQLite database: " + prms.PRMS.DBFILE + "...");
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            System.out.println(" Connected!");

            String sql;

            // This is how we should be creating the table definitions, so that
            // the database can be rebuilt if something breaks or is lost
            // We'll probably want to put the table definitions somewhere else
            // eventually, but for the time being we can stick them here.
            // Create employees table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS employees (\n"
                    + " firstName     TEXT    NOT NULL,\n"
                    + " lastName      TEXT    NOT NULL,\n"
                    + " jobTitle      TEXT    NOT NULL,\n"
                    + " username      TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " password      TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // If the employees table is empty, create a default account so we can login
            sql = "SELECT count(*) FROM employees;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getInt(1) == 0) {
                System.out.println("\n\tEmployees table was empty, created a temporary employee:");
                System.out.println("\tusername: admin");
                System.out.println("\tpassword: admin");
                System.out.println("\tThis employee should be deleted as soon as possible!\n");
                sql = "INSERT INTO employees VALUES ('admin', 'admin', 'Manager', 'admin', 'admin');";
                stmt.executeUpdate(sql);
            }

            // Create hotelRooms table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS hotelRooms (\n"
                    + " roomNumber    TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " price         REAL    NOT NULL,\n"
                    + " beds          INT     NOT NULL,\n"
                    + " allowsPets      NUMERIC    NOT NULL,\n"
                    + " disabilityAccessible      NUMERIC    NOT NULL,\n"
                    + " allowsSmoking      NUMERIC    NOT NULL,\n"
                    + " dateLastCleaned      TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // Create inventoryItems table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS inventoryItems (\n"
                    + " name            TEXT    NOT NULL,\n"
                    + " roomNumber       TEXT    NOT NULL,\n"
                    + " quantity        INT    NOT NULL,\n"
                    + " expectedQuantity        INT   NOT NULL,\n"
                    + " isConsumable        NUMERIC    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // Create eventRooms table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS eventRooms (\n"
                    + " roomName        TEXT        NOT NULL,\n"
                    + " price           REAL        NOT NULL,\n"
                    + " maxCapacity     INT         NOT NULL,\n"
                    + " hasStage        NUMERIC     NOT NULL,\n"
                    + " hasAudioVisual  NUMERIC     NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Create hotelReservations table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS hotelReservations (\n"
                    + " roomnumber      TEXT    NOT NULL,\n"
                    + " adults          INT     NOT NULL,\n"
                    + " children        INT     NOT NULL,\n"
                    + " startDate       TEXT    NOT NULL,\n"
                    + " endDate         TEXT    NOT NULL,\n"
                    + " invoiceUID      TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Create eventBookings table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS eventBookings (\n"
                    + " roomName    TEXT    NOT NULL, \n"
                    + " companyName    TEXT    NOT NULL, \n"
                    + " price    REAL    NOT NULL, \n"
                    + " maxCapacity    INT    NOT NULL, \n"
                    + " startDate       TEXT    NOT NULL,\n"
                    + " endDate         TEXT    NOT NULL,\n"
                    + " invoiceUID      TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Create invoices table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS invoices (\n"
                    + " invoiceUID      TEXT    PRIMARY KEY      NOT NULL,\n"
                    + " customerName    TEXT    NOT NULL,\n"
                    + " creditCardNum   INT     NOT NULL,\n"
                    + " creditCardExp   INT     NOT NULL,\n"
                    + " amountPaid      REAL    NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Create billableItems table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS billableItems (\n"
                    + " billableUID     TEXT    PRIMARY KEY     NOT NULL,\n"
                    + " invoiceUID      TEXT    NOT NULL,\n"
                    + " billableName    TEXT    NOT NULL,\n"
                    + " price           REAL    NOT NULL,\n"
                    + " time            TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Create orders table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS orders (\n"
                    + " orderType    TEXT       NOT NULL,\n"
                    + " mealName    TEXT    NOT NULL,\n"
                    + " roomName    TEXT    NOT NULL,\n"
                    + " price         REAL    NOT NULL,\n"
                    + " date          TEXT     NOT NULL,\n"
                    + " time      TEXT    NOT NULL,\n"
                    + " status  TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // TODO Figure out the schema for orders? One table for all types?
            // or maybe a differ table for each type? That would make coding easier
            
                     /*
             sql = "CREATE TABLE IF NOT EXISTS roomserviceorder (\n"
                                + " roomNumber   INT    PRIMARY KEY   NOT NULL,\n"
                                + ");";
              stmt.execute(sql);
            
             sql = "CREATE TABLE IF NOT EXISTS tableserviceorder (\n"
                                    + " tableNumber    INT    PRIMARY KEY   NOT NULL,\n"
                                    + ");";
               stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS cateredmealorder (\n"
                                        + " roomname    TEXT    PRIMARY KEY   NOT NULL,\n"
                                        + ");";
             stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS maintenanceorder (\n"
                                            + " roomname    TEXT    PRIMARY KEY   NOT NULL,\n"
                                            + " description         TEXT    NOT NULL,\n"
                                            + ");";
              stmt.execute(sql);
             */
            
            // Create restaurantItems table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS restaurantItems (\n"
                    + " itemName        TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " price           REAL    NOT NULL,\n"
                    + " description     TEXT    \n"
                    + ");";
            stmt.execute(sql);
            
            // Create cateredMealItems table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS cateredMealItems (\n"
                    + " mealName            TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " pricePerSeat        REAL    NOT NULL,\n"
                    + " mealDescription     TEXT     \n"
                    + ");";
            stmt.execute(sql);
            
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        launch(args);
    }

}
