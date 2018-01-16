/* Change Log
11/26/2016 Fergus
    Modified from HotelRooms tab
11/10/2016 Fergus
    Created initial version
 */
package prms;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author MonstroUser
 */
public class EventRoomsTabController implements Initializable {

    @FXML
    private TextField modifyNameField;
    @FXML
    private TextField modifyPriceField;
    @FXML
    private TextField modifyCapacityField;
    @FXML
    private CheckBox modifyHasStageCheckBox;
    @FXML
    private CheckBox modifyHasAudioVisualCheckBox;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton createRadio;
    @FXML
    private RadioButton modifyRadio;
    @FXML
    private RadioButton removeRadio;
    @FXML
    private Button modifyButton;
    @FXML
    private Label modifyLabel;
    @FXML
    private TableView<EventRoom> eventRoomsTable;

    @FXML
    private void handleCreateToggle(ActionEvent event) {
        // Reset all the fields when the Create radio button is selected
        resetFields();
    }

    @FXML
    private void handleModifyToggle(ActionEvent event) {
        // Update the fields with the currently selected room when the Modify radio button is selected
//        updateFieldsWithSelectedRoom();
        modifyLabel.setText("");
    }

    @FXML
    private void handleRemoveToggle(ActionEvent event) {
        // Update the fields with the currently selected room when the Modify radio button is selected
//        updateFieldsWithSelectedRoom();
        modifyLabel.setText("");
    }

    @FXML
    private void handleHotelRoomApplyButtonPress(ActionEvent event) {
        if (createRadio.isSelected()) {
            createRoom();
        }
        if (modifyRadio.isSelected()) {
            modifyRoom();
        }
        if (removeRadio.isSelected()) {
            removeRoom();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Fetch the list of rooms from the DB and add them to the table
        updateRoomsTable();

        // Reset the fields
        resetFields();

        // Add a listener to handle table selections
        eventRoomsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            updateFieldsWithSelectedRoom();
            modifyRadio.setSelected(true);
        });

    }

    // Sets the fields to default values and makes sure they are all enabled
    public void resetFields() {
        modifyNameField.setText("");
        modifyNameField.setDisable(false);
        modifyPriceField.setText("");
        modifyCapacityField.setText("25");
        modifyHasStageCheckBox.setSelected(false);
        modifyHasAudioVisualCheckBox.setSelected(false);
    }

    // Fills the table with all the rooms in the DB
    public void updateRoomsTable() {
        eventRoomsTable.getItems().clear();
        eventRoomsTable.getItems().addAll(fetchRoomsFromDB());
        eventRoomsTable.sort();
    }

    // Returns an arraylist of all the rooms in the DB
    public ArrayList<EventRoom> fetchRoomsFromDB() {

        ArrayList<EventRoom> rooms = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM eventRooms";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EventRoom room = new EventRoom(rs.getString("roomName"), rs.getDouble("price"), rs.getInt("maxCapacity"), rs.getBoolean("hasStage"), rs.getBoolean("hasAudioVisual"));
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

    public void createRoom() {

        String nameField = modifyNameField.getText();
        String priceField = modifyPriceField.getText();
        String capacityField = modifyCapacityField.getText();
        int hasStage = (modifyHasStageCheckBox.isSelected()) ? 1 : 0;
        int hasAudioVisual = (modifyHasAudioVisualCheckBox.isSelected()) ? 1 : 0;

        // Check if all fields are filled in
        if (nameField.equals("")) {
            modifyLabel.setText("Error creating room:\nName required");
            return;
        }
        if (priceField.equals("")) {
            modifyLabel.setText("Error creating room:\nPrice required");
            return;
        }
        if (capacityField.equals("")) {
            modifyLabel.setText("Error creating room:\nCapacity required");
            return;
        }

        // Parse the price field
        double price = -1;
        try {
            price = Double.parseDouble(priceField);
            //round the price to 2 digits (cents)
            price *= 100;
            price = Math.round(price);
            price /= 100;
        } catch (NumberFormatException e) {
            modifyLabel.setText("Error creating room:\nMalformed price field");
            return;
        }

        // Parse the capacity field
        int capacity = -1;
        try {
            capacity = Integer.parseInt(capacityField);
        } catch (NumberFormatException e) {
            modifyLabel.setText("Error creating room:\nMalformed capacity field");
            return;
        }

        // Sanity check for capacity and prices
        if (price < 1) {
            modifyLabel.setText("Error creating room:\nPrice < 1");
            return;
        }
        if (capacity < 1) {
            modifyLabel.setText("Error creating room:\nCapacity < 1");
            return;
        }

        Boolean success = addRoomToDB(nameField, price, capacity, hasStage, hasAudioVisual);
        if (success) {
            modifyLabel.setText("Successfully created event room '" + nameField + "'!");
            updateRoomsTable();
        } else {
            modifyLabel.setText("Unable to create event room '" + nameField + "' - a room with that name may already exist");
        }
        return;
    }

    public void modifyRoom() {

//        if (eventRoomsTable.getSelectionModel().getSelectedItem() == null) {
//            modifyLabel.setText("Please select a room to modify");
//            return;
//        }
//
//        String floorField = modifyFloorField.getText();
//        String numberField = modifyNumberField.getText();
//        String priceField = modifyPriceField.getText();
//        String bedsField = modifyBedsField.getText();
//        int allowsPets = (modifyAllowsPetsCheckbox.isSelected()) ? 1 : 0;
//        int disabilityAccessible = (modifyDisabilityAccessibleCheckBox.isSelected()) ? 1 : 0;
//        int allowsSmoking = (modifyAllowsSmokingCheckbox.isSelected()) ? 1 : 0;
//
//        // Check if all fields are filled in
//        if (floorField.equals("")) {
//            modifyLabel.setText("Error modifying room:\nFloor required");
//            return;
//        }
//        if (numberField.equals("")) {
//            modifyLabel.setText("Error modifying room:\nNumber required");
//            return;
//        }
//        if (priceField.equals("")) {
//            modifyLabel.setText("Error modifying room:\nPrice required");
//            return;
//        }
//        if (bedsField.equals("")) {
//            modifyLabel.setText("Error modifying room:\nBeds required");
//            return;
//        }
//
//        // Parse the floor and number fields and make the roomNumber
//        int floor = Integer.parseInt(floorField);
//        int room = Integer.parseInt(numberField);
//        String roomNumber = makeRoomNumber(floor, room);
//
//        // Parse the price field
//        double price = -1;
//        try {
//            price = Double.parseDouble(priceField);
//            //round the price to 2 digits (cents)
//            price *= 100;
//            price = Math.round(price);
//            price /= 100;
//        } catch (NumberFormatException e) {
//            modifyLabel.setText("Error creating room:\nMalformed price field");
//            return;
//        }
//
//        // Parse the beds field
//        int beds = -1;
//        try {
//            beds = Integer.parseInt(bedsField);
//        } catch (NumberFormatException e) {
//            modifyLabel.setText("Error creating room:\nMalformed beds field");
//            return;
//        }
//
//        // Sanity check for beds and prices
//        if (price < 1) {
//            modifyLabel.setText("Error creating room:\nPrice < 1");
//            return;
//        }
//        if (beds < 1) {
//            modifyLabel.setText("Error creating room:\nBeds < 1");
//            return;
//        }
//
//        // Connect to the DB and perform the necessary queries
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            c = DriverManager.getConnection(prms.PRMS.DBFILE);
//            stmt = c.createStatement();
//
//            String sql = "UPDATE hotelRooms SET "
//                    + "price='" + price + "', "
//                    + "beds='" + beds + "', "
//                    + "allowsPets='" + allowsPets + "', "
//                    + "disabilityAccessible='" + disabilityAccessible + "', "
//                    + "allowsSmoking='" + allowsSmoking + "' "
//                    + "WHERE roomNumber='" + roomNumber + "'";
//            System.out.println(sql);
//
//            stmt.executeUpdate(sql);
//
//            stmt.close();
//            c.close();
//
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//        updateRoomsTable();
//        modifyLabel.setText("Modified room " + roomNumber);
    }

    public void removeRoom() {

//        if (eventRoomsTable.getSelectionModel().getSelectedItem() == null) {
//            modifyLabel.setText("Please select a room to remove");
//            return;
//        }
//
//        String floorField = modifyFloorField.getText();
//        String numberField = modifyNumberField.getText();
//
//        // Check if floor and room fields are filled in
//        if (floorField.equals("")) {
//            modifyLabel.setText("Error removing room:\nFloor required");
//            return;
//        }
//        if (numberField.equals("")) {
//            modifyLabel.setText("Error removing room:\nNumber required");
//            return;
//        }
//
//        // Parse the floor and number fields and make the roomNumber
//        int floor = Integer.parseInt(floorField);
//        int room = Integer.parseInt(numberField);
//        String roomNumber = makeRoomNumber(floor, room);
//
//        // Connect to the DB and perform the necessary queries
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            c = DriverManager.getConnection(prms.PRMS.DBFILE);
//            stmt = c.createStatement();
//
//            // Check if there is room with that room number
//            String sql = "SELECT count(*) FROM hotelRooms WHERE roomNumber='" + roomNumber + "'";
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.getInt(1) == 0) {
//                // No room found
//                modifyLabel.setText("Error removing room:\nThere is no room " + roomNumber);
//                rs.close();
//                stmt.close();
//                c.close();
//                return;
//            }
//
//            // We can now go ahead and remove the room
//            sql = "DELETE FROM hotelRooms WHERE roomNumber='" + roomNumber + "'";
//            stmt.executeUpdate(sql);
//
//            rs.close();
//            stmt.close();
//            c.close();
//
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//        updateRoomsTable();
//        modifyLabel.setText("Removed room " + roomNumber);
//        resetFields();
//        createRadio.setSelected(true);

    }

    public Boolean addRoomToDB(String roomName, double price, int capacity, int hasStage, int hasAudioVisual) {
        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Check if a room already exists with that number
            String sql = "SELECT count(*) FROM eventRooms WHERE roomName ='" + roomName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getInt(1) > 0) {
                // A room with that number already exists is taken, display a message and quit
                rs.close();
                stmt.close();
                c.close();
//                return "Error creating room:\nRoom " + roomNumber + " already exists";
                return false;
            }

            // We can now go ahead and insert the new room
            sql = "INSERT INTO eventRooms VALUES ('" + roomName + "', '" + price + "', '" + capacity + "', '" + hasStage + "', '" + hasAudioVisual + "' );";
//            System.out.println(sql);
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        return true;
    }

//    public void updateFieldsWithSelectedRoom() {
//        HotelRoom selectedRoom = eventRoomsTable.getSelectionModel().getSelectedItem();
//        if (eventRoomsTable.getSelectionModel().getSelectedItem() != null) {
//            int[] roomNumber = splitRoomNumber(selectedRoom.getRoomNumber());
//            modifyFloorField.setText("" + roomNumber[0]);
//            modifyFloorField.setDisable(true);
//
//            modifyNumberField.setText("" + roomNumber[1]);
//            modifyNumberField.setDisable(true);
//
//            modifyPriceField.setText("" + selectedRoom.getPrice());
//            modifyBedsField.setText("" + selectedRoom.getBeds());
//
//            modifyAllowsPetsCheckbox.setSelected(selectedRoom.getAllowsPets());
//            modifyAllowsSmokingCheckbox.setSelected(selectedRoom.getAllowsSmoking());
//            modifyDisabilityAccessibleCheckBox.setSelected(selectedRoom.getDisabilityAccessible());
//        }
//    }
    
}