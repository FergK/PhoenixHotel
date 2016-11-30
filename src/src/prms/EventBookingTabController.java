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
import java.time.LocalDate;
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
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;



/**
 * FXML Controller class
 *
 * @author youngvz
 */


public class EventBookingTabController implements Initializable {

    @FXML
    private TabPane EventBookingTabPane;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> guestChoiceBox;

    @FXML
    private ChoiceBox<String> needsProjectorChoiceBox;

    @FXML
    private ChoiceBox<String> needStageChoiceBox;

    @FXML
    private ChoiceBox<String> roomChoiceBox;
    
    @FXML
    private Label FeedbackLabel;

    @FXML
    private Button reserveEventButton;
    
    @FXML
    private TableView eventBookingTableView;
    
    @FXML
    private Button bookReservationButton;
    
    @FXML
    void handleEventBookingSelection(MouseEvent event) {

    }

    @FXML
    void handleReservationBooking(ActionEvent event) {

    }

    
    @FXML
    void reserveEvenetHandle(ActionEvent event) {
        
        if (datePicker.getValue() == null ){
//            System.out.println("Enter Dates son");
            FeedbackLabel.setVisible(true);
            return;
        }
        
        //Create Booking
        createEventBooking();
        FeedbackLabel.setText("Event Room Reserved!");
        FeedbackLabel.setTextFill(Color.web("#0076a3"));
        //EventBookingTabPane.getSelectionModel().selectFirst();
    }
    
    
    ObservableList<String> roomList = FXCollections.observableArrayList("The Grand Suite", "Litte Buisiness Suite", "The Mac-Daddy Suite");
    ObservableList<String> options = FXCollections.observableArrayList("Yes", "No");
    ObservableList<String> guestList = FXCollections.observableArrayList("0-25","25-50","50-75","75+");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       setupChoiceBoxInputs();
       updateResultsTable();
    }    
    
    public void updateResultsTable(){
        eventBookingTableView.getItems().clear();
        eventBookingTableView.getItems().addAll(fetchRoomsFromDB());
        eventBookingTableView.sort();
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
    
    public void setupChoiceBoxInputs(){

        
        roomChoiceBox.setValue("The Grand Suite");
        roomChoiceBox.setItems(roomList);
        
        guestChoiceBox.setValue("0-25");
        guestChoiceBox.setItems(guestList);
        
        needsProjectorChoiceBox.setValue("Yes");
        needsProjectorChoiceBox.setItems(options);
        
        needStageChoiceBox.setValue("Yes");
        needStageChoiceBox.setItems(options);
        
        FeedbackLabel.setVisible(false);


    }
    public int getInt(String test){
        try{
            return Integer.parseInt(test.trim());
        }catch(Exception e){
            return 0;
        }
    }
    
    public int getGuests(String guests){
            //ObservableList<String> guestList = FXCollections.observableArrayList("0-25","25-50","50-75","75+");

       if (guests == "0-25"){
           return 25;
       }else if (guests == "25-50"){
           return 50;
       }else if (guests == "50-75"){
           return 75;
       }else{
           return 100;
       }
       
    }
    
    public void createEventBooking() {
        // Check if all fields are filled in and passwords match
        String roomName = roomChoiceBox.getValue();
        double price = 105;
        String numberOfGuests = guestChoiceBox.getValue();
        
         int maxCapacity = 25;

        int bool = 0;
        String needsStage = needStageChoiceBox.getValue();
        String needsAudioVisual = needsProjectorChoiceBox.getValue();
        LocalDate localDate = datePicker.getValue();
        
        

//        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO eventrooms VALUES ('" + roomName + "', '" + price + "', '" + maxCapacity + "', '" + bool + "', '" + bool + "' );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }
}
