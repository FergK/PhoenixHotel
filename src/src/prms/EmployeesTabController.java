package prms;

/**
 *
 * @author Fergus Kelley
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class EmployeesTabController implements Initializable {

    @FXML
    private TextField modifyFirstNameField;
    @FXML
    private TextField modifyLastNameField;
    @FXML
    private ChoiceBox<String> modifyJobTitleChoiceBox;
    @FXML
    private TextField modifyUsernameField;
    @FXML
    private PasswordField modifyPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton createRadio;
    @FXML
    private RadioButton modifyRadio;
    @FXML
    private RadioButton removeRadio;
    @FXML
    private Button modifyButton;
    @FXML
    private Label modifyLabel;
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private void handleCreateToggle(ActionEvent event) {
        // Reset all the fields when the Create radio button is selected
        modifyFirstNameField.setText("");
        modifyLastNameField.setText("");
        modifyUsernameField.setText("");
        modifyUsernameField.setDisable(false);
        modifyJobTitleChoiceBox.setValue(prms.PRMS.JOBTITLELIST[0]);
        modifyPasswordField.setText("");
        confirmPasswordField.setText("");
        modifyLabel.setText("");
    }

    @FXML
    private void handleModifyToggle(ActionEvent event) {
        // Update the fields with the currently selected employee when the Modify radio button is selected
        updateFieldsWithSelectedEmployee();
        modifyLabel.setText("");
    }

    @FXML
    private void handleRemoveToggle(ActionEvent event) {
        // Update the fields with the currently selected employee when the Remove radio button is selected
        updateFieldsWithSelectedEmployee();
        modifyLabel.setText("");
    }

    @FXML
    private void handleEmployeeApplyButtonPress(ActionEvent event) {
        if (createRadio.isSelected()) {
            createEmployee();
        }
        if (modifyRadio.isSelected()) {
            modifyEmployee();
        }
        if (removeRadio.isSelected()) {
            removeEmployee();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Get the possible jobTitle values and add them to the drop down box
        for (String jobTitle : prms.PRMS.JOBTITLELIST) {
            modifyJobTitleChoiceBox.getItems().add(jobTitle);
        }
        modifyJobTitleChoiceBox.setValue(prms.PRMS.JOBTITLELIST[0]);

        // Fetch the list of employees from the DB and add them to the table
        updateEmployeesTable();

        // Add a listener to handle table selections
        employeesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateFieldsWithSelectedEmployee();
            modifyRadio.setSelected(true);
        });

    }

    public void updateEmployeesTable() {
        employeesTable.getItems().clear();
        employeesTable.getItems().addAll(fetchEmployeesFromDB());
        employeesTable.sort();
    }

    public ArrayList<Employee> fetchEmployeesFromDB() {

        ArrayList<Employee> employees = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                employees.add(new Employee(rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("JOBTITLE"), rs.getString("USERNAME"), ""));
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch employees from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return employees;
    }

    public void createEmployee() {
        // Check if all fields are filled in and passwords match
        String username = modifyUsernameField.getText();
        String password = modifyPasswordField.getText();
        String firstName = modifyFirstNameField.getText();
        String lastName = modifyLastNameField.getText();
        String jobTitle = modifyJobTitleChoiceBox.getValue();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.equals("")) {
            modifyLabel.setText("Error creating employee:\nFirst name required");
            return;
        }
        if (lastName.equals("")) {
            modifyLabel.setText("Error creating employee:\nLast name required");
            return;
        }
        if (username.equals("")) {
            modifyLabel.setText("Error creating employee:\nUsername required");
            return;
        }
        if (password.equals("")) {
            modifyLabel.setText("Error creating employee:\nPassword required");
            return;
        }
        if (!password.equals(confirmPassword)) {
            modifyLabel.setText("Error creating employee:\nPassword fields do not match");
            return;
        }

        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Check if a employee already exists with that username
            String sql = "SELECT count(*) FROM employees WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getInt(1) > 0) {
                // username is taken, display a message and quit
                modifyLabel.setText("Error creating employee:\nAn employee with that username already exists");
                rs.close();
                stmt.close();
                c.close();
                return;
            }

            // We can now go ahead and insert the new employee
            sql = "INSERT INTO employees VALUES ('" + firstName + "', '" + lastName + "', '" + jobTitle + "', '" + username + "', '" + password + "' );";
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        Employee createdEmployee = new Employee(firstName, lastName, jobTitle, username, password);
        employeesTable.getItems().add(createdEmployee);
        employeesTable.sort();
        modifyLabel.setText("Created new employee: " + username);
//        employeesTable.getSelectionModel().select(createdEmployee);
    }

    public void modifyEmployee() {
        // Check if all fields are filled in and passwords match
        String username = modifyUsernameField.getText();
        String password = modifyPasswordField.getText();
        String firstName = modifyFirstNameField.getText();
        String lastName = modifyLastNameField.getText();
        String jobTitle = modifyJobTitleChoiceBox.getValue();
        String confirmPassword = confirmPasswordField.getText();

        if (username.equals("")) {
            modifyLabel.setText("Error modifying employee:\nUsername required");
            return;
        }
        if (!password.equals(confirmPassword)) {
            modifyLabel.setText("Error modifying employee:\nPassword fields do not match");
            return;
        }

        // Since we may only be modifying a few things, we can build the sql string based on what's filled out
        String sql = "UPDATE employees SET ";
        ArrayList<String> updatedColumns = new ArrayList<>();

        if (firstName.equals("")) {
            modifyLabel.setText("Error modifying employee:\nFirst name required");
            return;
        } else {
            updatedColumns.add("firstName='" + firstName + "'");
        }

        if (lastName.equals("")) {
            modifyLabel.setText("Error modifying employee:\nLast name required");
            return;
        } else {
            updatedColumns.add("lastName='" + lastName + "'");
        }

        updatedColumns.add("jobTitle='" + jobTitle + "'");

        if (!password.equals("")) {
            updatedColumns.add("password='" + password + "'");
        }

        while (updatedColumns.size() > 1) {
            sql += updatedColumns.get(0) + ", ";
            updatedColumns.remove(0);
        }
        sql += updatedColumns.get(0) + " WHERE username='" + username + "'";

        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Run the query
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        updateEmployeesTable();
        modifyLabel.setText("Modified employee: " + username);
    }

    public void removeEmployee() {
        // Check to see if the username field is filled
        String username = modifyUsernameField.getText();
        if (username.equals("")) {
            modifyLabel.setText("Error removing employee:\nUsername required");
            return;
        }

        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Check if there is employee with that username
            String sql = "SELECT count(*) FROM employees WHERE username='" + username + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getInt(1) == 0) {
                // No employee found
                modifyLabel.setText("Error removing employee:\nThere is no employee " + username);
                rs.close();
                stmt.close();
                c.close();
                return;
            }

            // We can now go ahead and remove the employee
            sql = "DELETE FROM employees WHERE username='" + username + "'";
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        updateEmployeesTable();
        modifyLabel.setText("Removed employee: " + username);
        modifyFirstNameField.setText("");
        modifyLastNameField.setText("");
        modifyUsernameField.setText("");
        modifyJobTitleChoiceBox.setValue(prms.PRMS.JOBTITLELIST[0]);
        modifyPasswordField.setText("");
        confirmPasswordField.setText("");
        createRadio.setSelected(true);
    }

    public void updateFieldsWithSelectedEmployee() {
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        if (employeesTable.getSelectionModel().getSelectedItem() != null) {
            modifyFirstNameField.setText(selectedEmployee.getFirstName());
            modifyLastNameField.setText(selectedEmployee.getLastName());
            modifyUsernameField.setText(selectedEmployee.getUsername());
            modifyUsernameField.setDisable(true);
            modifyJobTitleChoiceBox.setValue(selectedEmployee.getJobTitle());
            modifyPasswordField.setText("");
            confirmPasswordField.setText("");
        }
    }

}
