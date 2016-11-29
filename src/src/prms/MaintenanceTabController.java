package prms;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/*
    Hasn't yet implemented:
        - current date updates for room service

    -Deividas
 */
public class MaintenanceTabController implements Initializable {

    @FXML
    private TableView inventoryTable;
    @FXML
    private TableView roomTable;
    @FXML
    private Button roomServiceUpdateBtn;
    @FXML
    private Button quantityUpdateBtn;
    @FXML
    private Label replenishUpdateLabel;
    @FXML
    private Label dateUpdateLabel;
    @FXML
    private TextField roomServiceField;

    @FXML
    public void handleRoomSelection(MouseEvent event) {

        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            updateInventoryTable(selectedRoom);
            replenishUpdateLabel.setVisible(false);
            dateUpdateLabel.setVisible(false);
            System.out.println(selectedRoom.getDateLastCleaned());
            roomServiceUpdateBtn.setDisable(false);

            String temp = selectedRoom.getDateLastCleaned();

            roomServiceField.setText(
                    temp.substring(0, 2) + "-"
                    + temp.substring(2, 4) + "-"
                    + temp.substring(4)
            );
        }

    }

    @FXML
    public void handleInventorySelection(MouseEvent event) {

        InventoryItem selectedInv = (InventoryItem) inventoryTable.getSelectionModel().getSelectedItem();
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            roomServiceUpdateBtn.setDisable(false);
            quantityUpdateBtn.setDisable(false);
            replenishUpdateLabel.setVisible(false);
            dateUpdateLabel.setVisible(false);
        }
    }

    @FXML
    private void handleRoomServiceUpdate(MouseEvent event) {
        // update Room Service Date to tomorrow in DB
        System.out.println("Room service handle connected");
        LocalDate currentDate = LocalDate.now();
        String formattedCurrentDate
                = currentDate.toString().substring(5, 7)
                + currentDate.toString().substring(8)
                + currentDate.toString().substring(0, 4);

        System.out.println(formattedCurrentDate);

        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {

            String sql = "UPDATE hotelRooms SET dateLastCleaned='" + formattedCurrentDate + "' WHERE roomNumber='" + selectedRoom.getRoomNumber() + "';";

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                // Run the query
                stmt.executeUpdate(sql);

                stmt.close();
                c.close();

                roomServiceField.setText(
                        formattedCurrentDate.substring(0, 2) + "-"
                        + formattedCurrentDate.substring(2, 4) + "-"
                        + formattedCurrentDate.substring(4)
                );

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("success!");
            inventoryTable.getSelectionModel().clearSelection();
            roomServiceUpdateBtn.setDisable(true);
            quantityUpdateBtn.setDisable(true);
            dateUpdateLabel.setVisible(true);

        }
    }

    @FXML
    private void handleQuantityUpdate(MouseEvent event) {
        System.out.println("update quantity handle connected");
        InventoryItem selectedInventory = (InventoryItem) inventoryTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            int expinv = selectedInventory.getExpectedQuantity();
            HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();

            String sql = "UPDATE inventoryItems SET quantity ='" + expinv + "' WHERE roomNumber='" + selectedRoom.getRoomNumber() + "' AND name='" + selectedInventory.getName() + "';";

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                // Run the query
                stmt.executeUpdate(sql);

                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("success!");
            inventoryTable.getSelectionModel().clearSelection();
            //roomServiceUpdateBtn.setDisable(true);
            quantityUpdateBtn.setDisable(true);
            selectedInventory.setQuantity(expinv);
            updateInventoryTable(selectedRoom);
            replenishUpdateLabel.setVisible(true);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        roomServiceUpdateBtn.setDisable(true);
        quantityUpdateBtn.setDisable(true);
        replenishUpdateLabel.setVisible(false);
        dateUpdateLabel.setVisible(false);

        ArrayList<HotelRoom> rooms = fetchRoomsFromDB();

        updateRoomTable(rooms);
        updateInventoryTable(rooms);

    }

    public void updateInventoryTable(ArrayList<HotelRoom> rooms) {

        inventoryTable.getItems().clear();
        inventoryTable.getItems().addAll(fetchRoomsFromDB());
        inventoryTable.sort();

        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            System.out.println("\nRoom inventory retrieved");
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

            roomServiceField.setText(
                    temp.substring(0, 2) + "-"
                    + temp.substring(2, 4) + "-"
                    + temp.substring(4)
            );

        } else {
            roomServiceField.setText("");
        }
    }

    public void updateRoomTable(ArrayList<HotelRoom> rooms) {

        roomTable.getItems().clear();
        roomTable.getItems().addAll(fetchRoomsFromDB());
        roomTable.sort();
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
                currentRoom.setDateLastCleaned(rs.getString("dateLastCleaned"));
                System.out.println(currentRoom.getRoomNumber() + " Room added");
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM inventoryItems WHERE roomNumber ='" + currentRoom.getRoomNumber() + "';");
                while (rs2.next()) {
                    InventoryItem currentInventory = new InventoryItem(rs2.getString("NAME"), rs2.getInt("QUANTITY"), rs2.getInt("EXPECTEDQUANTITY"), rs2.getBoolean("ISCONSUMABLE"));
                    currentRoom.inventory.add(currentInventory);
                    System.out.println(currentInventory.getName() + " Inventory added");
                }
System.out.println(currentRoom);
                hotelRooms.add(currentRoom);
                System.out.println("dess");
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
