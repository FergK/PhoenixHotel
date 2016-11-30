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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author youngvz
 */
public class ReservationBookingTabController implements Initializable {

    
    @FXML
    private TabPane ReservationBookingTabPane;

    @FXML
    private DatePicker checkInDatePicker;
    
    @FXML
    private DatePicker checkOutDatePicker;
    

    @FXML
    private ChoiceBox adultsChioiceBox;

    @FXML
    private ChoiceBox<String> floorNumberChoiceBox;

    @FXML
    private ChoiceBox<String> childrenChoiceBox;
    
    @FXML
    private ChoiceBox<String> roomNumberChoiceBox;
    
    @FXML
    private TextField guestNameTextField;

    @FXML
    private Button hotelReservationButton;

    @FXML
    private Label FeedbackLabel;

    @FXML
    private TableView reservationTableView;

    @FXML
    private TableColumn companyName;

    @FXML
    private TableColumn roomName;

    @FXML
    private TableColumn dateReserved;

    @FXML
    private Button bookReservationButton;

    @FXML
    private Tab ReservationPaymentTab;

    @FXML
    private TextField ccNumberTextField;

    @FXML
    private TextField ccExpirationTextField;

    @FXML
    private TextField ccCodeTextField;

    @FXML
    private Button createInvoiceButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ConfirmationLabel;

    @FXML
    private Label guestNameLabel;

    
    
    @FXML
    void handleReservationSelection(MouseEvent event) {
        bookReservationButton.setDisable(false);
    }

    @FXML
    void handleReservationBooking(ActionEvent event) {
        
        HotelReservation selectedRoom = (HotelReservation) reservationTableView.getSelectionModel().getSelectedItem();
       
       guestNameLabel.setText(selectedRoom.getGuestName());
       dateLabel.setText(selectedRoom.getEndDate());
       roomNameLabel.setText(selectedRoom.getRoomNumber());
       priceLabel.setText("$1,920");
       ConfirmationLabel.setText("");

      ReservationBookingTabPane.getSelectionModel().selectNext();
      ReservationPaymentTab.setDisable(false);
        
    }

    @FXML
    void handleReservationInvoice(ActionEvent event) {

    }

    @FXML
    void reserveHotelRoomHandle(ActionEvent event) {
        System.out.println("Button Pressed");
        
        //check for valid values
        
        if (checkInDatePicker.getValue() == null || checkOutDatePicker.getValue() == null){
//            System.out.println("Enter Dates son");
            FeedbackLabel.setText("Please select a date");
            return;
        }
        
        if (guestNameTextField.getText().isEmpty() || guestNameTextField.getText() == null){
            FeedbackLabel.setText("Please enter the Guest's name");
            
            return;
        }
        
        
        createReservation();
        
        
    }

    
    public void createReservation() {
        // Check if all fields are filled in and passwords match
        
        
        
        String  guestName = guestNameTextField.getText();
        String checkInDate = checkInDatePicker.getValue().toString();
        String checkOutDate = checkOutDatePicker.getValue().toString();
        String numberOfAdults = (String) adultsChioiceBox.getValue();
        String numberOfChildren = (String) adultsChioiceBox.getValue();
        String floorNumber =  floorNumberChoiceBox.getValue();
        String roomNames = roomNumberChoiceBox.getValue();
        
        String roomnumber = floorNumber + "-" + roomNames;
       
        String billInvoiceNumber = guestName+"000"+ roomnumber;
 
        
        //Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();            

            // We can now go ahead and insert the new employee
            String sql = "INSERT INTO hotelreservations VALUES ('" + roomnumber + "', '" + numberOfAdults + "', '" + numberOfChildren + "', '" + checkInDate + "', '" + checkOutDate + "', '" + guestName + "', '" + billInvoiceNumber + "' );";
            
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        HotelReservation currentReservation = new HotelReservation(roomnumber, numberOfAdults, numberOfChildren, checkInDate, checkOutDate, guestName);
        reset();
        reservationTableView.getItems().add(currentReservation);
        reservationTableView.getSelectionModel().select(currentReservation);
        ReservationBookingTabPane.getSelectionModel().selectNext();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        setupInputs();
        updateHotelReservations();
    }   
    
    public void updateHotelReservations(){
        reservationTableView.getItems().clear();
        reservationTableView.getItems().addAll(fetchReservationsFromDB());
        reservationTableView.sort();

    }
    
    public ArrayList<HotelReservation> fetchReservationsFromDB() {

        ArrayList<HotelReservation> reservations = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM hotelreservations";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
 
                HotelReservation currentReservation = new HotelReservation(rs.getString("roomnumber"), rs.getString("adults"), rs.getString("children"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("guestName"));
                reservations.add(currentReservation);
            }

            rs.close();
            stmt.close();
            c.close();
            
        } catch (Exception e) {
            System.err.println("Could not fetch Hotel Rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return reservations;
    }
    
    ObservableList<String> childrenList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
    ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4");
    ObservableList<String> roomNumbers = FXCollections.observableArrayList("1", "2", "3", "4","5", "6", "7", "8","9", "10", "11", "12");
    
    
    public void setupInputs(){
        
       adultsChioiceBox.setValue("1");
       adultsChioiceBox.setItems(options);
       
       childrenChoiceBox.setValue("0");
       childrenChoiceBox.setItems(childrenList);
       
       floorNumberChoiceBox.setValue("1");
       floorNumberChoiceBox.setItems(options);
       
       roomNumberChoiceBox.setValue("1");
       roomNumberChoiceBox.setItems(roomNumbers);
       
       FeedbackLabel.setText("");
       
       
    

    }
    
    public void reset(){
        setupInputs();
        guestNameTextField.setText("");
    }
    
}
