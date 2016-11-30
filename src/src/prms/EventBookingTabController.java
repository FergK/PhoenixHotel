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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;



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
    private TextField companyNameTextField;
    
    @FXML
    private TextField ccNumberTextField;

    @FXML
    private TextField ccExpirationTextField;

    @FXML
    private TextField ccCodeTextField;
    
    @FXML
    private Label dateLabel;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ConfirmationLabel;
    
    @FXML
    private Label companyNameLabel;
    
    @FXML
    private Tab eventPaymentTab;
    
    @FXML
    private Button createInvoiceButton;
    
    @FXML
    void handleEventBookingSelection(MouseEvent event) {
                
        bookReservationButton.setDisable(false);
    }

    @FXML
    void handleReservationBooking(ActionEvent event) {
       
       EventRoom selectedRoom = (EventRoom) eventBookingTableView.getSelectionModel().getSelectedItem();
       
       companyNameLabel.setText(selectedRoom.getCompanyName());
       dateLabel.setText(selectedRoom.getDateReserved());
       roomNameLabel.setText(selectedRoom.getRoomName());
       priceLabel.setText("$1,920");
       ConfirmationLabel.setText("");

      EventBookingTabPane.getSelectionModel().selectNext();
      eventPaymentTab.setDisable(false);

    }

    @FXML
    void handleEventInvoice(ActionEvent event) {
        ConfirmationLabel.setText("Invoice created!");
        
        EventBookingTabPane.getSelectionModel().select(1);

        eventPaymentTab.setDisable(true);

    }
    
    @FXML
    void reserveEvenetHandle(ActionEvent event) {
                    
        
        FeedbackLabel.setVisible(true);

        if (datePicker.getValue() == null ){
//            System.out.println("Enter Dates son");
            FeedbackLabel.setText("Please enter a day");
            return;
        }
        
        if (companyNameTextField.getText().isEmpty() || companyNameTextField.getText() == null){
            FeedbackLabel.setText("Please enter a Company Name");
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
        eventBookingTableView.getItems().addAll(fetchEventsFromDB());
        eventBookingTableView.sort();
    }
    
    public ArrayList<EventRoom> fetchEventsFromDB() {

        ArrayList<EventRoom> eventRooms = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM eventrooms";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                
                EventRoom currentRoom = new EventRoom(rs.getString("roomname"), rs.getDouble("price"), rs.getInt("maxcapacity"), rs.getBoolean("hasStage"), rs.getBoolean("hasAudioVisual"), rs.getString("companyName"), rs.getString("dateReserved"));

                eventRooms.add(currentRoom);
            }

            rs.close();
            stmt.close();
            c.close();
            
        } catch (Exception e) {
            System.err.println("Could not fetch Hotel Rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return eventRooms;
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
        String companyName = companyNameTextField.getText();
        double price = 105;
        String numberOfGuests = guestChoiceBox.getValue();
        System.out.println(numberOfGuests);
        
        int maxCapacity = getGuests(numberOfGuests);

        int bool = 0;
        
        Boolean flag = false;
        String needsStage = needStageChoiceBox.getValue();
        String needsAudioVisual = needsProjectorChoiceBox.getValue();
        LocalDate localDate = datePicker.getValue();
        
        String dateReserved = localDate.toString();
        

//        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();            

            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO eventrooms VALUES ('" + roomName + "', '" + price + "', '" + maxCapacity + "', '" + bool + "', '" + bool + "', '"+ companyName +"', '"+ dateReserved +"' );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        EventRoom newEvent = new EventRoom(roomName, price, maxCapacity, flag, flag, companyName, dateReserved);
        eventBookingTableView.getItems().add(newEvent);
        eventBookingTableView.getSelectionModel().select(newEvent);
        reset();
        EventBookingTabPane.getSelectionModel().selectNext();


    }
    
    public void reset(){
        
        roomChoiceBox.setValue("The Grand Suite");
        
        guestChoiceBox.setValue("0-25");
        
        needsProjectorChoiceBox.setValue("Yes");
        
        needStageChoiceBox.setValue("Yes");
        
        datePicker.setValue(null);
        companyNameTextField.setText("");
        
        FeedbackLabel.setVisible(false);
    }
}
