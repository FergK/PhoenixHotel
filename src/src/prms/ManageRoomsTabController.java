/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import static java.lang.Integer.parseInt;
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
public class ManageRoomsTabController implements Initializable {

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
        modifyFloorField.setText("");
        modifyNumberField.setText("");
        modifyBedsField.setText("1");
        modifyAllowsPetsCheckbox.setSelected(false);
        modifyAllowsSmokingCheckbox.setSelected(false);
        modifyDisabilityAccessibleCheckBox.setSelected(false);
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
//            modifyRoom();
        }
        if (removeRadio.isSelected()) {
//            removeRoom();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Fetch the list of employees from the DB and add them to the table
        updateRoomsTable();

        // Reset the fields
        modifyFloorField.setText("");
        modifyNumberField.setText("");
        modifyBedsField.setText("1");
        modifyAllowsPetsCheckbox.setSelected(false);
        modifyAllowsSmokingCheckbox.setSelected(false);
        modifyDisabilityAccessibleCheckBox.setSelected(false);

        // Add a listener to handle table selections
        roomsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            updateFieldsWithSelectedRoom();
            modifyRadio.setSelected(true);
        });

    }

    public void updateRoomsTable() {
        roomsTable.getItems().clear();
        roomsTable.getItems().addAll(fetchRoomsFromDB());
        roomsTable.sort();
    }

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
                HotelRoom room = new HotelRoom(rs.getString("roomNumber"), rs.getDouble("price"), rs.getInt("beds"), rs.getBoolean("allowsPets"), rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"));
                HotelRoom currentRoom = new HotelRoom(rs.getString("roomNumber"), rs.getDouble("price"), rs.getInt("beds"), rs.getBoolean("allowsPets"), rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"));
                currentRoom.setDateLastCleaned(rs.getString("dateLastCleaned"));
                rooms.add(room);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch employees from database: " + e.getClass().getName() + ": " + e.getMessage());
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
                } else if ( ( minFloor < 1 ) || ( maxFloor < 1 ) || ( minFloor > 99 ) || ( maxFloor > 99 ) ) {
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
                
                if ( ( floor < 1 ) || ( floor > 99 ) ) {
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
                } else if ( ( minRoom < 1 ) || ( maxRoom < 1 ) || ( minRoom > 99 ) || ( maxRoom > 99 ) ) {
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
                
                if ( ( room < 1 ) || ( room > 99 ) ) {
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

        // If neither the floor or the room number is a range, we are just creating a single room
        if (!floorRange && !roomRange) {
            String roomNumber = "" + floor;
            if ( room < 10 ) {
                roomNumber += "0";
            }
            roomNumber += room;
            Boolean success = addRoomToDB(roomNumber, price, beds, allowsPets, disabilityAccessible, allowsSmoking );
            modifyLabel.setText("creating room: " + success);
        }

//        // Connect to the DB and perform the necessary queries
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            c = DriverManager.getConnection(prms.PRMS.DBFILE);
//            stmt = c.createStatement();
//
//            // Check if a employee already exists with that username
//            String sql = "SELECT count(*) FROM employees WHERE username='" + username + "'";
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.getInt(1) > 0) {
//                // username is taken, display a message and quit
//                modifyLabel.setText("Error creating employee:\nAn employee with that username already exists");
//                rs.close();
//                stmt.close();
//                c.close();
//                return;
//            }
//
//            // We can now go ahead and insert the new employee
//            sql = "INSERT INTO employees VALUES ('" + firstName + "', '" + lastName + "', '" + jobTitle + "', '" + username + "', '" + password + "' );";
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
//        Employee createdEmployee = new Employee(firstName, lastName, jobTitle, username, password);
//        employeesTable.getItems().add(createdEmployee);
//        employeesTable.sort();
//        modifyLabel.setText("Created new room");

        updateRoomsTable();
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
            sql = "INSERT INTO hotelRooms VALUES ('" + roomNumber + "', '" + price + "', '" + beds + "', '" + allowsPets + "', '" + disabilityAccessible + "', '" + allowsSmoking + "', '" + 11162016 + "' );";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
//        return "Created new room: " + roomNumber;
        return true;
    }
}
