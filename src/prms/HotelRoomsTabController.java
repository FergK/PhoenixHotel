/* Change Log
11/26/2016 Fergus
    Added creation of ranges of rooms
    Added modifying and removing rooms
    Added default inventory on room creation
11/18/2016 Fergus
    Added reading of DB into table
    Added creation of single room
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
import java.util.Calendar;
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
public class HotelRoomsTabController implements Initializable {

    @FXML
    private TextField modifyFloorField;
    @FXML
    private TextField modifyNumberField;
    @FXML
    private TextField modifyPriceField;
    @FXML
    private TextField modifyBedsField;
    @FXML
    private CheckBox modifyAllowsPetsCheckbox;
    @FXML
    private CheckBox modifyAllowsSmokingCheckbox;
    @FXML
    private CheckBox modifyDisabilityAccessibleCheckBox;
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
    private TableView<HotelRoom> roomsTable;

    @FXML
    private void handleCreateToggle(ActionEvent event) {
        // Reset all the fields when the Create radio button is selected
        resetFields();
    }

    @FXML
    private void handleModifyToggle(ActionEvent event) {
        // Update the fields with the currently selected room when the Modify radio button is selected
        updateFieldsWithSelectedRoom();
        modifyLabel.setText("");
    }

    @FXML
    private void handleRemoveToggle(ActionEvent event) {
        // Update the fields with the currently selected room when the Modify radio button is selected
        updateFieldsWithSelectedRoom();
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
        roomsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateFieldsWithSelectedRoom();
            modifyRadio.setSelected(true);
        });

    }

    // Sets the fields to default values and makes sure they are all enabled
    public void resetFields() {
        modifyFloorField.setText("");
        modifyFloorField.setDisable(false);
        modifyNumberField.setText("");
        modifyNumberField.setDisable(false);
        modifyPriceField.setText("");
        modifyBedsField.setText("1");
        modifyAllowsPetsCheckbox.setSelected(false);
        modifyAllowsSmokingCheckbox.setSelected(false);
        modifyDisabilityAccessibleCheckBox.setSelected(false);
    }

    // Fills the table with all the rooms in the DB
    public void updateRoomsTable() {
        roomsTable.getItems().clear();
        roomsTable.getItems().addAll(fetchRoomsFromDB());
        roomsTable.sort();
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

    public void createRoom() {

        String floorField = modifyFloorField.getText();
        String numberField = modifyNumberField.getText();
        String priceField = modifyPriceField.getText();
        String bedsField = modifyBedsField.getText();
        int allowsPets = (modifyAllowsPetsCheckbox.isSelected()) ? 1 : 0;
        int disabilityAccessible = (modifyDisabilityAccessibleCheckBox.isSelected()) ? 1 : 0;
        int allowsSmoking = (modifyAllowsSmokingCheckbox.isSelected()) ? 1 : 0;

        // Check if all fields are filled in
        if (floorField.equals("")) {
            modifyLabel.setText("Error creating room:\nFloor required");
            return;
        }
        if (numberField.equals("")) {
            modifyLabel.setText("Error creating room:\nNumber required");
            return;
        }
        if (priceField.equals("")) {
            modifyLabel.setText("Error creating room:\nPrice required");
            return;
        }
        if (bedsField.equals("")) {
            modifyLabel.setText("Error creating room:\nBeds required");
            return;
        }

        // Parse the floor field to handle ranges and invalid input
        Boolean floorRange = false;
        int minFloor = -1;
        int maxFloor = -1;
        int floor = -1;
        int dashLocation = floorField.indexOf("-");
        if (dashLocation != -1) {
            // The textfield contains a "-" character, so treat it like a range
            floorRange = true;
            try {
                minFloor = Integer.parseInt(floorField.substring(0, dashLocation));
                maxFloor = Integer.parseInt(floorField.substring(dashLocation + 1, floorField.length()));

                if (minFloor > maxFloor) {
                    modifyLabel.setText("Error creating room:\nMalformed floor field\nminFloor > maxFloor");
                    return;
                } else if ((minFloor < 1) || (maxFloor < 1) || (minFloor > 99) || (maxFloor > 99)) {
                    modifyLabel.setText("Error creating room:\nMalformed floor field\nFloors must be between 1 and 99");
                    return;
                } else if (minFloor == maxFloor) {
                    floorRange = false;
                    floor = minFloor;
                }

            } catch (NumberFormatException e) {
                modifyLabel.setText("Error creating room:\nMalformed floor field");
                return;
            }
        } else {
            // This textfield should be a regular old number, try to parse it into an int
            try {
                floor = Integer.parseInt(floorField);

                if ((floor < 1) || (floor > 99)) {
                    modifyLabel.setText("Error creating room:\nMalformed floor field\nFloors must be between 1 and 99");
                    return;
                }

            } catch (NumberFormatException e) {
                modifyLabel.setText("Error creating room:\nMalformed floor field");
                return;
            }
        }

        // Parse the room field to handle ranges and invalid input
        Boolean roomRange = false;
        int minRoom = -1;
        int maxRoom = -1;
        int room = -1;
        dashLocation = numberField.indexOf("-");
        if (dashLocation != -1) {
            // The textfield contains a "-" character, so treat it like a range
            roomRange = true;
            try {
                minRoom = Integer.parseInt(numberField.substring(0, dashLocation));
                maxRoom = Integer.parseInt(numberField.substring(dashLocation + 1, numberField.length()));

                if (minRoom > maxRoom) {
                    modifyLabel.setText("Error creating room:\nMalformed room number field\nminRoom > maxRoom");
                    return;
                } else if ((minRoom < 1) || (maxRoom < 1) || (minRoom > 99) || (maxRoom > 99)) {
                    modifyLabel.setText("Error creating room:\nMalformed room number field\nRoom numbers must be between 1 and 99");
                    return;
                } else if (minRoom == maxRoom) {
                    roomRange = false;
                    room = minRoom;
                }

            } catch (NumberFormatException e) {
                modifyLabel.setText("Error creating room:\nMalformed room number field");
                return;
            }
        } else {
            // This textfield should be a regular old number, try to parse it into an int
            try {
                room = Integer.parseInt(numberField);

                if ((room < 1) || (room > 99)) {
                    modifyLabel.setText("Error creating room:\nMalformed room number field\nRoom numbers must be between 1 and 99");
                    return;
                }

            } catch (NumberFormatException e) {
                modifyLabel.setText("Error creating room:\nMalformed room number field");
                return;
            }
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

        // Parse the beds field
        int beds = -1;
        try {
            beds = Integer.parseInt(bedsField);
        } catch (NumberFormatException e) {
            modifyLabel.setText("Error creating room:\nMalformed beds field");
            return;
        }

        // Sanity check for beds and prices
        if (price < 1) {
            modifyLabel.setText("Error creating room:\nPrice < 1");
            return;
        }
        if (beds < 1) {
            modifyLabel.setText("Error creating room:\nBeds < 1");
            return;
        }

        // If neither the floor or the room number is a range, we are just creating a single room
        if (!floorRange && !roomRange) {
            String roomNumber = makeRoomNumber(floor, room);
            Boolean success = addRoomToDB(roomNumber, price, beds, allowsPets, disabilityAccessible, allowsSmoking);
            if (success) {
                modifyLabel.setText("Successfully created room " + roomNumber + "!");
                updateRoomsTable();
            } else {
                modifyLabel.setText("Unable to create room " + roomNumber + " - a room with that number may already exist");
            }
            return;
        }

        modifyLabel.setText("... Working ...");

        // We are generating a range of rooms, so let's make an arraylist of the all the numbers
        ArrayList<String> roomNumbers = new ArrayList<>();

        if (!floorRange) {
            for (int i = minRoom; i <= maxRoom; i++) {
                roomNumbers.add(makeRoomNumber(floor, i));
            }
        } else {
            for (int i = minFloor; i <= maxFloor; i++) {
                if (roomRange) {
                    for (int j = minRoom; j <= maxRoom; j++) {
                        roomNumbers.add(makeRoomNumber(i, j));
                    }
                } else {
                    roomNumbers.add(makeRoomNumber(i, room));
                }
            }
        }

        // Using that arraylist, add the rooms to the DB.
        // Build a string to act as the message
        int successes = 0;
        int failures = 0;
        String successString = "Successfully created rooms:";
        String failureString = "Unable to create rooms:";
        String finalString = "";
        for (String roomNumber : roomNumbers) {
            Boolean success = addRoomToDB(roomNumber, price, beds, allowsPets, disabilityAccessible, allowsSmoking);
            if (success) {
                successString += " " + roomNumber;
                successes++;
            } else {
                failureString += " " + roomNumber;
                failures++;
            }
        }
        if (successes > 0) {
            finalString += successString;
        }
        if ((successes > 0) && (failures > 0)) {
            finalString += "\n";
        }
        if (failures > 0) {
            finalString += failureString;
        }

        modifyLabel.setText(finalString);
        updateRoomsTable();
    }

    public void modifyRoom() {

        if (roomsTable.getSelectionModel().getSelectedItem() == null) {
            modifyLabel.setText("Please select a room to modify");
            return;
        }

        String floorField = modifyFloorField.getText();
        String numberField = modifyNumberField.getText();
        String priceField = modifyPriceField.getText();
        String bedsField = modifyBedsField.getText();
        int allowsPets = (modifyAllowsPetsCheckbox.isSelected()) ? 1 : 0;
        int disabilityAccessible = (modifyDisabilityAccessibleCheckBox.isSelected()) ? 1 : 0;
        int allowsSmoking = (modifyAllowsSmokingCheckbox.isSelected()) ? 1 : 0;

        // Check if all fields are filled in
        if (floorField.equals("")) {
            modifyLabel.setText("Error modifying room:\nFloor required");
            return;
        }
        if (numberField.equals("")) {
            modifyLabel.setText("Error modifying room:\nNumber required");
            return;
        }
        if (priceField.equals("")) {
            modifyLabel.setText("Error modifying room:\nPrice required");
            return;
        }
        if (bedsField.equals("")) {
            modifyLabel.setText("Error modifying room:\nBeds required");
            return;
        }

        // Parse the floor and number fields and make the roomNumber
        int floor = Integer.parseInt(floorField);
        int room = Integer.parseInt(numberField);
        String roomNumber = makeRoomNumber(floor, room);

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

        // Parse the beds field
        int beds = -1;
        try {
            beds = Integer.parseInt(bedsField);
        } catch (NumberFormatException e) {
            modifyLabel.setText("Error creating room:\nMalformed beds field");
            return;
        }

        // Sanity check for beds and prices
        if (price < 1) {
            modifyLabel.setText("Error creating room:\nPrice < 1");
            return;
        }
        if (beds < 1) {
            modifyLabel.setText("Error creating room:\nBeds < 1");
            return;
        }

        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "UPDATE hotelRooms SET "
                    + "price='" + price + "', "
                    + "beds='" + beds + "', "
                    + "allowsPets='" + allowsPets + "', "
                    + "disabilityAccessible='" + disabilityAccessible + "', "
                    + "allowsSmoking='" + allowsSmoking + "' "
                    + "WHERE roomNumber='" + roomNumber + "'";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        updateRoomsTable();
        modifyLabel.setText("Modified room " + roomNumber);
    }

    public void removeRoom() {

        if (roomsTable.getSelectionModel().getSelectedItem() == null) {
            modifyLabel.setText("Please select a room to remove");
            return;
        }

        String floorField = modifyFloorField.getText();
        String numberField = modifyNumberField.getText();

        // Check if floor and room fields are filled in
        if (floorField.equals("")) {
            modifyLabel.setText("Error removing room:\nFloor required");
            return;
        }
        if (numberField.equals("")) {
            modifyLabel.setText("Error removing room:\nNumber required");
            return;
        }

        // Parse the floor and number fields and make the roomNumber
        int floor = Integer.parseInt(floorField);
        int room = Integer.parseInt(numberField);
        String roomNumber = makeRoomNumber(floor, room);

        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Check if there is room with that room number
            String sql = "SELECT count(*) FROM hotelRooms WHERE roomNumber='" + roomNumber + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getInt(1) == 0) {
                // No room found
                modifyLabel.setText("Error removing room:\nThere is no room " + roomNumber);
                rs.close();
                stmt.close();
                c.close();
                return;
            }

            // We can now go ahead and remove the room
            sql = "DELETE FROM hotelRooms WHERE roomNumber='" + roomNumber + "'";
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        updateRoomsTable();
        modifyLabel.setText("Removed room " + roomNumber);
        resetFields();
        createRadio.setSelected(true);

    }

    public String makeRoomNumber(int floor, int room) {
        String roomNumber = "" + floor;
        if (room < 10) {
            roomNumber += "0";
        }
        roomNumber += room;
        return roomNumber;
    }

    public int[] splitRoomNumber(String roomNumber) {
        int[] result = new int[2];
        result[0] = Integer.parseInt(roomNumber.substring(0, roomNumber.length() - 2));
        result[1] = Integer.parseInt(roomNumber.substring(roomNumber.length() - 2, roomNumber.length()));
        return result;
    }

    public Boolean addRoomToDB(String roomNumber, double price, int beds, int allowsPets, int disabilityAccessible, int allowsSmoking) {
        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Check if a room already exists with that number
            String sql = "SELECT count(*) FROM hotelRooms WHERE roomNumber ='" + roomNumber + "'";
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
            sql = "INSERT INTO hotelRooms VALUES ('" + roomNumber + "', '" + price + "', '" + beds + "', '" + allowsPets + "', '" + disabilityAccessible + "', '" + allowsSmoking + "', '" + prms.PRMS.getCurrentDateString() + "' );";
//            System.out.println(sql);
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        // Assuming we've succeeded in inserting the new room, let's give it an inventory
        addDefaultInventoryToRoom(roomNumber);

        return true;
    }

    public void updateFieldsWithSelectedRoom() {
        HotelRoom selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (roomsTable.getSelectionModel().getSelectedItem() != null) {
            int[] roomNumber = splitRoomNumber(selectedRoom.getRoomNumber());
            modifyFloorField.setText("" + roomNumber[0]);
            modifyFloorField.setDisable(true);

            modifyNumberField.setText("" + roomNumber[1]);
            modifyNumberField.setDisable(true);

            modifyPriceField.setText("" + selectedRoom.getPrice());
            modifyBedsField.setText("" + selectedRoom.getBeds());

            modifyAllowsPetsCheckbox.setSelected(selectedRoom.getAllowsPets());
            modifyAllowsSmokingCheckbox.setSelected(selectedRoom.getAllowsSmoking());
            modifyDisabilityAccessibleCheckBox.setSelected(selectedRoom.getDisabilityAccessible());
        }
    }
    
    public void addDefaultInventoryToRoom(String roomNumber) {
        // Connect to the DB and perform the necessary queries
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            // Build an SQL statement with the default inventory
            String sql = "INSERT INTO inventoryItems ( name, roomNumber, quantity, expectedQuantity, isConsumable ) VALUES "
                    + " ( 'Soap', '" + roomNumber + "', 2, 2, 1 ),"
                    + " ( 'Shampoo', '" + roomNumber + "', 1, 1, 1 ),"
                    + " ( 'Conditioner', '" + roomNumber + "', 1, 1, 1 ),"
                    + " ( 'Lotion', '" + roomNumber + "', 1, 1, 1 ),"
                    + " ( 'Towels', '" + roomNumber + "', 4, 4, 1 ),"
                    + " ( 'Hand Towels', '" + roomNumber + "', 2, 2, 1 ),"
                    + " ( 'Pillows', '" + roomNumber + "', 2, 2, 0 ),"
                    + " ( 'Sheets', '" + roomNumber + "', 2, 2, 0 ),"
                    + " ( 'Lamp', '" + roomNumber + "', 2, 2, 0 ),"
                    + " ( 'Phone', '" + roomNumber + "', 1, 1, 0 ),"
                    + " ( 'Remote', '" + roomNumber + "', 1, 1, 0 ),"
                    + " ( 'TV', '" + roomNumber + "', 1, 1, 0 );";
//            System.out.println(sql);
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
    }
}
