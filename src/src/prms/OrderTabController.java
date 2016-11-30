/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author youngvz
 */
public class OrderTabController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TabPane OrderTabPane;

    @FXML
    private Tab CreateOrderTab;

    @FXML
    private ChoiceBox<String> orderTypeChoiceBox;

    @FXML
    private ChoiceBox<String> roomFloorChoiceBox;

    @FXML
    private ChoiceBox<String> roomNumberChoiceBox;
    
    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private Button submitOrderButton;

    @FXML
    private Label FeedbackLabel;

    @FXML
    private Tab FindOrderTab;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView ordersTableView;
    
    
    
    ObservableList<String> orderTypes = FXCollections.observableArrayList("Menu Item #1", "Menu Item #2","Menu Item #3","Menu Item #4","Menu Item #5", "Menu Item #6");
    ObservableList<String> floorNumbers = FXCollections.observableArrayList("1", "2", "3", "4");
    ObservableList<String> roomNumbers = FXCollections.observableArrayList("1", "2", "3", "4","5", "6", "7", "8","9", "10", "11", "12");
    ObservableList<String> times = FXCollections.observableArrayList("11:00", "12:00", "1:00", "2:00", "3:00", "4:00","5:00", "6:00", "7:00", "8:00","9:00", "10:00");

    @FXML
    void submitOrderHandle(ActionEvent event) {
        
        System.out.println("Button Pressed");
        OrderTabPane.getSelectionModel().selectNext();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupInputs();
        
    }    
    
    public void setupInputs(){
//         @FXML
//    private ChoiceBox<String> orderTypeChoiceBox;
//
//    @FXML
//    private ChoiceBox<String> roomFloorChoiceBox;
//
//    @FXML
//    private ChoiceBox<String> roomNumberChoiceBox;
//    
//    @FXML
//    private ChoiceBox<String> timeChoiceBox;

    orderTypeChoiceBox.setValue("Menu Item #1");
    orderTypeChoiceBox.setItems(orderTypes);
    
    roomFloorChoiceBox.setValue("1");
    roomFloorChoiceBox.setItems(floorNumbers);
    
    roomNumberChoiceBox.setValue("1");
    roomNumberChoiceBox.setItems(roomNumbers);
    
    timeChoiceBox.setValue("2:00");
    timeChoiceBox.setItems(times);

        
    }
    
    
    
    
}
