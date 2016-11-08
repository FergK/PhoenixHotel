package prms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

    // Employee object holding the currently logged in employee
    static Employee loggedInEmployee = null;

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
            // Create employee table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS employees (\n"
                    + " firstName     TEXT    NOT NULL,\n"
                    + " lastName      TEXT    NOT NULL,\n"
                    + " jobTitle      TEXT    NOT NULL,\n"
                    + " username      TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " password      TEXT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // If the employee table is empty, create a default account so we can login
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

            // Create hotelroom table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS hotelRooms (\n"
                    + " roomNumber    TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " price         REAL    NOT NULL,\n"
                    + " beds          INT     NOT NULL,\n"
                    + " allowsPets      NUMERIC    NOT NULL,\n"
                    + " disabilityAccessible      NUMERIC    NOT NULL,\n"
                    + " allowssmoking      NUMERIC    NOT NULL,\n"
                    + " dateLastCleaned      INT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // THIS BLOCK IS FOR TROUBLESHOOTING ROOM LIST ONLY
            // If the room table is empty, create a default one 
            sql = "SELECT count(*) FROM hotelRooms;";
            ResultSet rsroom = stmt.executeQuery(sql);
            if (rsroom.getInt(1) == 0) {
                System.out.println("\n\tRoom table was empty, created a temporary Room:");
                System.out.println("\tThis room should be deleted later on!\n");
                sql = "INSERT INTO hotelRooms VALUES ('100', '80.00', '2', '0', '1', '0', '01012016');";
                stmt.executeUpdate(sql);
            }

            // Create inventoryItems table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS inventoryItems (\n"
                    + " name            TEXT    NOT NULL,\n"
                    + "roomNumber       TEXT    NOT NULL,\n"
                    + " quantity        INT    NOT NULL,\n"
                    + " expectedQuantity        INT   NOT NULL,\n"
                    + " isConsumable        NUMERIC    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // If the inventoryItem table is empty, create a placeholder
            // for troubleshooting
            sql = "SELECT count(*) FROM inventoryItems;";
            ResultSet rsinv = stmt.executeQuery(sql);
            if (rsinv.getInt(1) == 0) {
                System.out.println("\n\tinventory table was empty, created a temporary item:");
                sql = "INSERT INTO inventoryItems VALUES ('Soap','100', '5', '5', '1');";
                System.out.println("\tSoap added!");
                stmt.executeUpdate(sql);
            }

            // Create hotelreservations table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS hotelreservations (\n"
                    + " roomnumber     TEXT    NOT NULL,\n"
                    + " adults           INT      NOT NULL,\n"
                    + " children      INT    NOT NULL,\n"
                    + " startDate    INT    NOT NULL,\n"
                    + " endDate     INT    NOT NULL,\n"
                    + " bill      INT    UNIQUE    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // boolean values stored in table should be of type NUMERIC
            // Create eventrooms table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS eventrooms (\n"
                    + " roomname     TEXT    NOT NULL,\n"
                    + " price      REAL    NOT NULL,\n"
                    + " maxcapacity     INT     NOT NULL,\n"
                    + " hasStage      NUMERIC      NOT NULL,\n"
                    + " hasAudioVisual      NUMERIC    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            // boolean values stored in table should be of type NUMERIC
            // Create eventrooms table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS eventrooms (\n"
                    + " startDate    INT    NOT NULL,\n"
                    + " endDate     INT    NOT NULL,\n"
                    + " bill        INT    UNIQUE   NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            
            // Duplicate eventrooms tables here; which one is the definitive one?
            // -Deividas

            
            // NOTICE:
            // The following tables cause the following SQLITE syntax error:
            // Database connection error: org.sqlite.SQLiteException:
            // [SQLITE_ERROR] SQL error or missing database (near ")": syntax error)
            // I commented them out for now for the program to be compilable
            // and until we figure out the bug. 
            // -Deividas
            
             /*  
                       sql = "CREATE TABLE IF NOT EXISTS invoice (\n"
                + " UID    INT    PRIMARY KEY   NOT NULL,\n"
                + " customerName         TEXT    NOT NULL,\n"
                + " CCNum          TEXT     NOT NULL,\n"
                + " CCExp    INT    NOT NULL,\n"
                + ");";
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS restaurant (\n"
                    + " numofTable    TEXT    PRIMARY KEY   NOT NULL,\n"
                    + ");";
             stmt.execute(sql); 
            
            sql = "CREATE TABLE IF NOT EXISTS billableitems (\n"
                        + " billableName    TEXT    PRIMARY KEY   NOT NULL,\n"
                        + " price         REAL    NOT NULL,\n"
                        + " date          INT     NOT NULL,\n"
                        + " time      INT    NOT NULL,\n"
                        + ");";
             stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS order (\n"
                            + " InvoiceNumber    TEXT    PRIMARY KEY   NOT NULL,\n"
                            + " orderDate         INT    NOT NULL,\n"
                            + " orderStatus          INT     NOT NULL,\n"
                            + " billables      TEXT    NOT NULL,\n"
                            + ");";
             stmt.execute(sql);
            
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
            
            sql = "CREATE TABLE IF NOT EXISTS restaurantmenuitem (\n"
                                                + " itemname    TEXT    PRIMARY KEY   NOT NULL,\n"
                                                + " price         REAL    NOT NULL,\n"
                                                + " description          TEXT     NOT NULL,\n"
                                                + ");";
              stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS cateredmealitem (\n"
                                                    + " mealname    TEXT    PRIMARY KEY   NOT NULL,\n"
                                                    + " priceperseat         REAL    NOT NULL,\n"
                                                    + " caterdescription          TEXT     NOT NULL,\n"
                                                    + ");";
               stmt.execute(sql);
             */
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
