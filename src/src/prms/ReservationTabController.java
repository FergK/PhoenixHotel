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
import javafx.scene.control.TabPane;




/**
 * FXML Controller class
 *
 * @author youngvz
 */
public class ReservationTabController implements Initializable {

    
    ObservableList<String> priceList = FXCollections.observableArrayList("100+", "200+", "300+");
    ObservableList<String> beds = FXCollections.observableArrayList("1", "2","3", "4");
    ObservableList<String> options = FXCollections.observableArrayList("N/A", "Yes", "No");
    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox priceChoiceBox;
    
    @FXML
    private TabPane reservationTabPane;
    
    @FXML
    private ChoiceBox allowsSmoking;
    @FXML
    private ChoiceBox allowsPets;
    @FXML
    private ChoiceBox hasKitchen;
    @FXML
    private ChoiceBox hasFridge;
    
    @FXML
    private TableView hotelRoomTableView;
    
    
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
        reservationTabPane.getSelectionModel().selectNext();
    }
    
    
//    @FXML
//    private void initialize(){
//
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateResultsTable();
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
        hasKitchen.setValue("1");
        hasKitchen.setItems(beds);
        hasFridge.setValue("Yes");
        hasFridge.setItems(options);

    }
    
    public void updateResultsTable(){
        hotelRoomTableView.getItems().clear();
        hotelRoomTableView.getItems().addAll(fetchRoomsFromDB());
        hotelRoomTableView.sort();
    }
    
    public ArrayList<HotelRoom> fetchRoomsFromDB() {

        ArrayList<HotelRoom> hotelRooms = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM hotelRooms";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HotelRoom currentRoom = new HotelRoom(rs.getString("roomNumber"), rs.getDouble("price"), rs.getInt("beds"), rs.getBoolean("allowsPets"), rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"));
                
System.out.println(currentRoom);
                hotelRooms.add(currentRoom);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch Hotel Rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return hotelRooms;
    }
    
}
