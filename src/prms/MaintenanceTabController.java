/* Change Log
11/29/2016 Fergus
    Updated date format
    Removed unnecessary imports
    Fixed date last cleaned update
    Minor interface changes
    Added update and replenish quantity features
    Merged into final project
11/18/2016 Deividas
    Created initial version
 */
package prms;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MaintenanceTabController implements Initializable {

    @FXML
    private TableView inventoryTable;
    @FXML
    private TableView roomTable;
    @FXML
    private TextField roomServiceField;
    @FXML
    private Button roomServiceUpdateBtn;
    @FXML
    private Label modifyQuantityLabel;
    @FXML
    private TextField modifyQuantityField;
    @FXML
    private Button quantityUpdateButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<HotelRoom> rooms = fetchRoomsFromDB();

        updateRoomsTable();

        resetFields();

        // Add a listener to update the rooms table when this tab is selected
        // TODO
    }

    public void resetFields() {
        roomServiceField.setText("");
        roomServiceUpdateBtn.setDisable(true);
        modifyQuantityLabel.setText("Item quantity:");
        modifyQuantityField.setDisable(true);
        modifyQuantityField.setText("");
        quantityUpdateButton.setDisable(true);
    }

    @FXML
    public void handleRoomSelection(MouseEvent event) {

        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            updateInventoryTable(selectedRoom);
            resetFields();
            roomServiceUpdateBtn.setDisable(false);
            roomServiceField.setText(selectedRoom.getDateLastCleaned());
        }

    }

    @FXML
    public void handleInventorySelection(MouseEvent event) {

        InventoryItem selectedInv = (InventoryItem) inventoryTable.getSelectionModel().getSelectedItem();
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            modifyQuantityLabel.setText(selectedInv.getName() + " quantity:");
            modifyQuantityField.setText(((Integer) selectedInv.getQuantity()).toString());
            modifyQuantityField.setDisable(false);
            quantityUpdateButton.setDisable(false);
        }
    }

    @FXML
    private void handleRoomServiceUpdate(MouseEvent event) {

        String formattedCurrentDate = prms.PRMS.getCurrentDateString();

        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                String sql = "UPDATE hotelRooms SET dateLastCleaned='" + formattedCurrentDate + "' WHERE roomNumber='" + selectedRoom.getRoomNumber() + "';";
                // Run the query
                stmt.executeUpdate(sql);

                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            roomServiceField.setText(formattedCurrentDate);
            roomServiceUpdateBtn.setDisable(true);

            updateRoomsTable();

        }
    }

    @FXML
    private void handleQuantityUpdate(MouseEvent event) {
        InventoryItem selectedInventory = (InventoryItem) inventoryTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {

            // Parse the quantity field
            int newQuantity = -1;
            try {
                newQuantity = Integer.parseInt(modifyQuantityField.getText());
            } catch (NumberFormatException e) {
                return;
            }

            HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                // Run the query
                String sql = "UPDATE inventoryItems SET quantity ='" + newQuantity + "' WHERE roomNumber='" + selectedRoom.getRoomNumber() + "' AND name='" + selectedInventory.getName() + "';";
//                System.out.println(sql);
                stmt.executeUpdate(sql);

                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            selectedRoom.inventory = fetchInventoryFromDB(selectedRoom.getRoomNumber());
            updateInventoryTable(selectedRoom);
            resetFields();

        }
    }

    public void updateInventoryTable(HotelRoom selectedRoom) {

        inventoryTable.getItems().clear();
        ArrayList<InventoryItem> roomInv = selectedRoom.getInventory();
        inventoryTable.getItems().addAll(roomInv);
        inventoryTable.sort();

    }

    public void updateRoomServiceField(HotelRoom selectedRoom) {
        if (selectedRoom != null) {
            String temp = selectedRoom.getDateLastCleaned();
        } else {
            roomServiceField.setText("");
        }
    }

    public void updateRoomsTable() {

        roomTable.getItems().clear();
        roomTable.getItems().addAll(fetchRoomsFromDB());
        roomTable.sort();
    }

    public ArrayList<InventoryItem> fetchInventoryFromDB(String roomNumber) {
        ArrayList<InventoryItem> inventory = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();

            String sql = "SELECT * FROM inventoryItems WHERE roomNumber ='" + roomNumber + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                inventory.add(new InventoryItem(rs.getString("NAME"), rs.getInt("QUANTITY"), rs.getInt("EXPECTEDQUANTITY"), rs.getBoolean("ISCONSUMABLE")));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch inventoryItems from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return inventory;
    }

    public ArrayList<HotelRoom> fetchRoomsFromDB() {

        ArrayList<HotelRoom> hotelRooms = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            stmt2 = c.createStatement();

            String sql = "SELECT * FROM hotelRooms";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HotelRoom currentRoom = new HotelRoom(rs.getString("roomNumber"), rs.getDouble("price"), rs.getInt("beds"), rs.getBoolean("allowsPets"), rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"), rs.getString("dateLastCleaned"));
//                System.out.println(currentRoom.getRoomNumber() + " Room added");
                currentRoom.inventory = fetchInventoryFromDB(currentRoom.getRoomNumber());
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM inventoryItems WHERE roomNumber ='" + currentRoom.getRoomNumber() + "';");
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
