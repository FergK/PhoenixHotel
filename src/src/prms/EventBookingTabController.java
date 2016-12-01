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
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;
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
    
    
    private String UID;

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
       
//       EventRoom selectedRoom = (EventRoom) eventBookingTableView.getSelectionModel().getSelectedItem();
//       
//       companyNameLabel.setText(selectedRoom.getCompanyName());
//       dateLabel.setText(selectedRoom.getDateReserved());
//       roomNameLabel.setText(selectedRoom.getRoomName());
//       priceLabel.setText("$1,920");
       ConfirmationLabel.setText("");

      EventBookingTabPane.getSelectionModel().selectNext();
      eventPaymentTab.setDisable(false);

    }

    @FXML
    void handleEventInvoice(ActionEvent event) {
        
       

        System.out.println("Button Pressed");
        if (ccNumberTextField.getText().isEmpty() || ccNumberTextField.getText() == null){
          ConfirmationLabel.setText("Enter values for credit card number!");
          return;
        }
        
        if (ccExpirationTextField.getText().isEmpty() || ccExpirationTextField.getText() == null){
          ConfirmationLabel.setText("Enter values for credit card exipration!");
          return;
        }
        
        if (ccCodeTextField.getText().isEmpty() || ccCodeTextField.getText() == null){
          ConfirmationLabel.setText("Enter values for credit card code!");
          return;
        }
        
        
        
        String room = roomChoiceBox.getValue();
       
        String invoiceUID = UUID.randomUUID().toString();
        UID = invoiceUID;
        //String creditCardNum = ccNumberTextField.getText();
        
        int creditCardNum = 00010102011;
        int creditCardExp = 0115;
        String customerName = companyNameLabel.getText();
        //String creditCardExp = ccExpirationTextField.getText();
        String time = Instant.now().toString();
        double price = 129.0;
        
        Connection c = null;
        Connection c2 = null;
        
        Statement stmt = null;
        Statement stmt2 = null;
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();            

            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO invoices VALUES ('" + invoiceUID + "', '" + customerName + "', '" + creditCardNum + "', '" + creditCardExp + "', '" + price + "' );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        try {
            c2 = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt2 = c2.createStatement();            

            
//           
            
            
            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO billableItems VALUES ('" + invoiceUID + "', '" + invoiceUID + "', '" + room + "', '" + price + "', '" + time + "' );";
            stmt2.executeUpdate(sql);

            stmt2.close();
            c2.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    
//        Invoice eventInvoice = new Invoice(invoiceUID, customerName, creditCardNum, creditCardExp);
//        BillableItem eventBill = new BillableItem(room, price, time, invoiceUID, invoiceUID);
//        
//        
        

//        eventBookingTableView.


        EventBookingTabPane.getSelectionModel().select(1);
        eventPaymentTab.setDisable(true);

    }
    
    @FXML
    void reserveEvenetHandle(ActionEvent event) {
                    
        
        FeedbackLabel.setVisible(true);

        if (datePicker.getValue() == null ){
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
        //eventBookingTableView.getItems().addAll(fetchEventsFromDB());
        eventBookingTableView.sort();
    }
    
    public ArrayList<EventBooking> fetchEventBookingsFromDB() {

        ArrayList<EventBooking> eventBookings = new ArrayList<>();
        
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM eventBookings";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                
            EventBooking newEventBooking = new EventBooking(rs.getString("roomName"),rs.getString("companyName"), rs.getDouble("price"), rs.getInt("maxcapacity"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("invoiceUID"));
            eventBookings.add(newEventBooking);
            }

            rs.close();
            stmt.close();
            c.close();
            
        } catch (Exception e) {
            System.err.println("Could not fetch Hotel Rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return eventBookings;
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
        
        String uniqueID = UUID.randomUUID().toString();
        String dateReserved = localDate.toString();
        

//        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
 
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();            

            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO eventBookings VALUES ('" + roomName + "', '" + companyName + "', '" + price + "', '" + maxCapacity + "', '" + dateReserved + "', '" + dateReserved + "', '"+ uniqueID +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        

        EventBooking newEventBooking = new EventBooking(roomName,companyName, price, maxCapacity, dateReserved, dateReserved, uniqueID);
        
        eventBookingTableView.getItems().add(newEventBooking);
        eventBookingTableView.getSelectionModel().select(newEventBooking);
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
