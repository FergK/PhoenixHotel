package prms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

/**
 *
 * @author Fergus Kelley
 */
public class EmployeesTabController implements Initializable {

    @FXML
    private Button modifyButton;

    @FXML
    private ChoiceBox modifyJobTitleChoiceBox;

    @FXML
    private TableView employeesTable;

    @FXML
    private void handleEmployeeApplyButtonPress(ActionEvent event) {

    }

    @FXML
    private void handleTableSelect(ActionEvent event) {
//        System.out.println("tableclicked");
//table.getSelectionModel().getSelectedIndex();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO implement the code that gets a list of possible job titles
        // then adds the list of job titles to the dropdowns
        String[] jobList = {"Manager", "Front-desk staff", "Custodial staff", "Bellhop", "Server", "Cook"};
        modifyJobTitleChoiceBox.getItems().addAll(jobList);

        // TODO implement the code that adds all the employees to the table
        ObservableList employees = employeesTable.getItems();
        employees.add(new Employee("Fergus", "Kelley", "a", "fkelley", ""));
        employees.add(new Employee("Andrew", "Truong", "b", "vtruong", ""));
        employees.add(new Employee("Viraj", "Shah", "c", "vshah", ""));
        employees.add(new Employee("Ryan", "Ocampo", "d", "rocampo", ""));
        employees.add(new Employee("Deividas", "Rutkauskas", "e", "drutkauskas", ""));

    }

}
