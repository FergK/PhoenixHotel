/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * FXML Controller class
 *
 * @author youngvz
 */
public class ReservationTabController implements Initializable {

    
    ObservableList<String> priceList = FXCollections.observableArrayList("100+", "200+", "300+");
    ObservableList<String> options = FXCollections.observableArrayList("N/A", "Yes", "No");
    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox priceChoiceBox;
    
    @FXML
    private ChoiceBox allowsSmoking;
    @FXML
    private ChoiceBox allowsPets;
    @FXML
    private ChoiceBox hasKitchen;
    @FXML
    private ChoiceBox hasFridge;
    
    @FXML 
    private Button submitButton;
    
//    @FXML
//    private TableView<HotelRoom> resultsTableView;
//    
//    @FXML
//    private TableColumn<HotelRoom, String> hotelRoomNumber;
//    
//     @FXML
//    private TableColumn<HotelRoom, String> hotelRoomPrice;
    
    
    @FXML
    private void handleSubmitButton(ActionEvent event){
        // update quantity update of a selected InventoryItem
        System.out.println(" text " );
    }
    
    
//    @FXML
//    private void initialize(){
//
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //updateResultsTable();
        setupComboBoxs();
        
        

        //hotelRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        //hotelRoomPrice.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        
    }    
    
    public void setupComboBoxs(){
        priceChoiceBox.setValue("100+");
        priceChoiceBox.setItems(priceList);
        
        allowsSmoking.setValue("Yes");
        allowsSmoking.setItems(options);
        allowsPets.setValue("Yes");
        allowsPets.setItems(options);
        hasKitchen.setValue("Yes");
        hasKitchen.setItems(options);
        hasFridge.setValue("Yes");
        hasFridge.setItems(options);

    }
    
    public void updateResultsTable(){
        //resultsTableView.getItems().clear();
        //resultsTableView.getItems().addAll(fetchEmployeesFromDB());
 
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
}
