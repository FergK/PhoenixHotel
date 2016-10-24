package prms;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 *
 * @author Fergus Kelley
 */
public class LoginTabController implements Initializable {

    @FXML   private Label loginLabel;
    @FXML   private TextField usernameField;
    @FXML   private PasswordField passwordField;
    @FXML   private Button loginButton;
    @FXML   private Button logoutButton;

    @FXML
    private void handleLoginButtonPress(ActionEvent event) {
        // TODO set a global variable containing the currently logged in employee
        
        // Get the login info from the text boxes
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Here is where we will put the code to query the database for the given username and check the password
        // set success to true if the login was successful, false otherwise
        
        // Attempt to connect to the DB and see if the given login info matches anything in the DB
        boolean success = false;
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM employees WHERE username='" + username + "' ;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String correctPassword = rs.getString("PASSWORD");
                if (password.compareTo(correctPassword) == 0) {
                    // Set the logged in employee object
                    prms.PRMS.loggedInEmployee = new Employee(rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("JOBTITLE"), rs.getString("USERNAME"), "");
//                    System.out.println( "Logged in as:\n" + prms.PRMS.loggedInEmployee.toString());
                    success = true;
                    break;
                }
            }
            
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println("Login database error: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        if (success) {
            
            // Update the UI pieces
            usernameField.setDisable(true);
            passwordField.setText("");
            passwordField.setDisable(true);
            loginLabel.setText("Login successful!");
            loginButton.setVisible(false);
            logoutButton.setVisible(true);

            // This next chunk of code loops over all the tabs and enables them
            // We will want to change this so that it only enables the tabs that
            // are accessible to this user (manager, cleaning staff, restaurant, etc.)
            TabPane allTabs = (TabPane) loginLabel.getScene().lookup("#allTabs");
            ObservableList<Tab> tabList = allTabs.getTabs();
            for (Tab next : tabList) {
                next.setDisable(false);
            }

        } else {
            loginLabel.setText("Username or password incorrect, please try again.");
        }
    }

    @FXML
    private void handleLogoutButtonPress(ActionEvent event) {
        
        // Reset the logged in employee object
        prms.PRMS.loggedInEmployee = null;
        
        // Clear the fields, display the login button, and disable the other tabs
        usernameField.setText("");
        usernameField.setDisable(false);
        usernameField.requestFocus();

        passwordField.setText("");
        passwordField.setDisable(false);

        loginLabel.setText("");

        loginButton.setVisible(true);
        logoutButton.setVisible(false);

        // Disable all the tabs but this one
        TabPane allTabs = (TabPane) loginLabel.getScene().lookup("#allTabs");
        ObservableList<Tab> tabList = allTabs.getTabs();
        for (Tab next : tabList) {
            if (!next.getId().equals("loginTab")) {
                next.setDisable(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}