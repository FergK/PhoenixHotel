package prms;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Fergus Kelley
 */
public class EmployeesTabController implements Initializable {

    @FXML   private Button modifyButton;
    @FXML   private ChoiceBox<String> modifyJobTitleChoiceBox;
    @FXML   private TableView<Employee> employeesTable;
    @FXML   private RadioButton createRadio;
    @FXML   private RadioButton modifyRadio;
    @FXML   private RadioButton removeRadio;
    @FXML   private TextField modifyFirstNameField;
    @FXML   private TextField modifyLastNameField;
    @FXML   private TextField modifyUsernameField;
    @FXML   private PasswordField modifyPasswordField;
    @FXML   private PasswordField confirmPasswordField;

    @FXML
    private void handleEmployeeApplyButtonPress(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Get the possible jobTitle values and add them to the drop down box
        for (String jobTitle : prms.PRMS.jobTitleList) {
            modifyJobTitleChoiceBox.getItems().add(jobTitle);
        }

        // Fetch the list of employees from the DB and add them to the table
        employeesTable.getItems().addAll(fetchEmployeesFromDB());

        // Add a listener to handle table selections
        employeesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
            if (employeesTable.getSelectionModel().getSelectedItem() != null) {
                modifyRadio.setSelected(true);
                modifyFirstNameField.setText(selectedEmployee.getFirstName());
                modifyLastNameField.setText(selectedEmployee.getLastName());
                modifyUsernameField.setText(selectedEmployee.getUsername());
                modifyJobTitleChoiceBox.setValue(selectedEmployee.getJobTitle());
                modifyPasswordField.setText("");
                confirmPasswordField.setText("");
            }
        });

    }

    public ArrayList<Employee> fetchEmployeesFromDB() {

        ArrayList<Employee> employees = new ArrayList();

        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:PhoenixHotel.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            String verify = "SELECT * FROM EMPLOYEE";
            c.commit();

            ResultSet rs = stmt.executeQuery(verify);

            while (rs.next()) {
                employees.add(new Employee(rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("JOBTITLE"), rs.getString("USERNAME"), ""));
                System.out.println(employees.get(employees.size() - 1).getFirstName());
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return employees;
    }

}
