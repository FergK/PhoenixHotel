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
            sql = "CREATE TABLE IF NOT EXISTS hotelroom (\n"
                    + " roomNumber    TEXT    PRIMARY KEY   NOT NULL,\n"
                    + " price         REAL    NOT NULL,\n"
                    + " beds          INT     NOT NULL,\n"
                    + " allowsPets      NUMERIC    NOT NULL,\n"
                    + " disabilityAccessible      NUMERIC    NOT NULL,\n"
                    + " allowssmoking      NUMERIC    NOT NULL,\n"
                    + " dateLastCleaned      INT    NOT NULL\n"
                    + ");";
            stmt.execute(sql);

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
            // Create eventroom table if it doesn't already exist
            sql = "CREATE TABLE IF NOT EXISTS eventrooms (\n"
                    + " roomnumber     TEXT    NOT NULL,\n"
                    + " price      TEXT    NOT NULL,\n"
                    + " maxcapacity      TEXT    NOT NULL,\n"
                    + " hasStage      NUMERIC      NOT NULL,\n"
                    + " hasAudioVisual      NUMERIC    NOT NULL\n"
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
