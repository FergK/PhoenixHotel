/* Change Log
11/11/2016  Viraj
    Built controller
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
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;


public class ReservationsTabController implements Initializable {

    
    ObservableList<String> priceList = FXCollections.observableArrayList("100+", "200+", "300+");
    ObservableList<String> beds = FXCollections.observableArrayList("1", "2","3", "4");
    ObservableList<String> options = FXCollections.observableArrayList("N/A", "Yes", "No");
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker checkInDatePicker;
    
    @FXML
    private DatePicker checkOutDatePicker;
    
    @FXML
    private Label errorText;
    
    @FXML
    private ChoiceBox<String> priceChoiceBox;
    
    @FXML
    private TabPane reservationTabPane;
    
    @FXML
    private Tab paymentTab;

    
    @FXML
    private ChoiceBox<String> allowsSmoking;
    @FXML
    private ChoiceBox<String> allowsPets;
    @FXML
    private ChoiceBox<String> hasKitchen;
    @FXML
    private ChoiceBox<String> hasFridge;
    
    @FXML
    private TableView hotelRoomTableView;
    
    @FXML
    private Button createInvoiceButton;
    
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
    private void handleInvoiceButton(ActionEvent event){        
        reservationTabPane.getSelectionModel().selectFirst();
        paymentTab.setDisable(true);
    }
    
    
    @FXML
    private void handleSubmitButton(ActionEvent event){
        // update quantity update of a selected InventoryItem
        
        if (checkInDatePicker.getValue() == null || checkOutDatePicker.getValue() == null ){
//            System.out.println("Enter Dates son");
            errorText.setText("Enter Dates for Reservation");
            return;
        }
        
        reservationTabPane.getSelectionModel().selectNext();
        paymentTab.setDisable(false);
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
    
    @FXML
    public void handleInventorySelection(MouseEvent event) {

        HotelRoom selectedRoom = (HotelRoom) hotelRoomTableView.getSelectionModel().getSelectedItem();
//        InventoryItem selectedInv = (InventoryItem) hotelRoomTableView.getSelectionModel().getSelectedItem();
        if (hotelRoomTableView.getSelectionModel().getSelectedItem() != null) {

              submitButton.setDisable(false);
        }
    }
    
    
    // Returns an arraylist of all the rooms in the DB
    public ArrayList<HotelRoom> fetchRoomsFromDB() {

        ArrayList<HotelRoom> rooms = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM hotelRooms";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                HotelRoom room = new HotelRoom(rs.getString("roomNumber"), rs.getDouble("price"), rs.getInt("beds"), rs.getBoolean("allowsPets"), rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"), rs.getString("dateLastCleaned"));
                rooms.add(room);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch rooms from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return rooms;
    }
    
}