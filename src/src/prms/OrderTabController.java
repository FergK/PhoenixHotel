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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    ObservableList<String> orderTypes = FXCollections.observableArrayList("Table Service", "Catered Meal","Room Service");
    ObservableList<String> floorNumbers = FXCollections.observableArrayList("1", "2", "3", "4");
    ObservableList<String> roomNumbers = FXCollections.observableArrayList("1", "2", "3", "4","5", "6", "7", "8","9", "10", "11", "12");
    ObservableList<String> times = FXCollections.observableArrayList("8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM","5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM","9:00 PM", "10:00 PM");

    @FXML
    void submitOrderHandle(ActionEvent event) {
        
        System.out.println("Button Pressed");
        //Create Order
        
        if (datePicker.getValue() == null){
            
            FeedbackLabel.setText("Enter a value for Date");
            return;
        }
        
        createOrder();        
    }
    
    public void createOrder(){
    
        String orderType = orderTypeChoiceBox.getValue();
        String roomFloor = roomFloorChoiceBox.getValue();
        String roomNumber = roomNumberChoiceBox.getValue();
        String roomName = roomFloor + " - " + roomNumber;
        double price = 129.0;
        String date = datePicker.getValue().toString();
        String time = timeChoiceBox.getValue();
        String status = "Pending";
        
        Connection c = null;
        Statement stmt = null;
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();            

            String sql = "INSERT INTO orders VALUES ('" + orderType + "', '" + roomName + "', '" + price + "', '" + date + "', '" + time + "', '"+ status +"' );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
    
        Order currentOrder = new Order(orderType, roomName, price, date, time, status);
        ordersTableView.getItems().add(currentOrder);
        ordersTableView.getSelectionModel().select(currentOrder);
        OrderTabPane.getSelectionModel().selectNext();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupInputs();
        updateOrdersTable();
        
    }    
    
    public void updateOrdersTable(){
        ordersTableView.getItems().clear();
        ordersTableView.getItems().addAll(fetchOrdersFromDB());
        ordersTableView.sort();
    }
    
    public ArrayList<Order> fetchOrdersFromDB() {

        ArrayList<Order> orders = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM orders";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                
                Order currentOrder = new Order(rs.getString("orderType"), rs.getString("roomName"), rs.getDouble("price"), rs.getString("date"), rs.getString("time"), rs.getString("status"));

                orders.add(currentOrder);
            }

            rs.close();
            stmt.close();
            c.close();
            
        } catch (Exception e) {
            System.err.println("Could not fetch Hotel Rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return orders;
    }
    
    
    public void setupInputs(){

    orderTypeChoiceBox.setValue("Table Service");
    orderTypeChoiceBox.setItems(orderTypes);
    
    roomFloorChoiceBox.setValue("1");
    roomFloorChoiceBox.setItems(floorNumbers);
    
    roomNumberChoiceBox.setValue("1");
    roomNumberChoiceBox.setItems(roomNumbers);
    
    timeChoiceBox.setValue("2:00 PM");
    timeChoiceBox.setItems(times);
        
    }
    
    
    
    
}
