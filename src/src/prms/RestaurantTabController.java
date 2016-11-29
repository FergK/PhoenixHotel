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
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author deividas
 */
public class RestaurantTabController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView restaurantTable;
    @FXML
    private TableView cateredMealsTable;
    @FXML
    private TextField restaurantNameField;
    @FXML
    private TextField restaurantPriceField;
    @FXML
    private TextField restaurantDescriptionField;
    @FXML
    private Button addEditButton;
    @FXML
    private Button restaurantDeselectButton;
    @FXML
    private Button restaurantDeleteButton;
    @FXML
    private Button cateredMealsDeselectBtn;
    @FXML
    private Button cateredMealsDeleteBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateRestaurantTable();
        restaurantDeselectButton.setDisable(true);
        restaurantDeleteButton.setDisable(true);
        cateredMealsDeselectBtn.setDisable(true);
        cateredMealsDeleteBtn.setDisable(true);

    }

    public ArrayList<RestaurantItem> fetchRestaurantMenuFromDB() {

        ArrayList<RestaurantItem> menu = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            System.out.println("\n\nCONNECTED TO DB");
            String sql = "SELECT * FROM RestaurantItems";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                HotelRoom currentRoom = new HotelRoom(rs.getString("ROOMNUMBER"),
//                        rs.getDouble("PRICE"), rs.getInt("BEDS"), rs.getBoolean("allowsPets"),
//                        rs.getBoolean("disabilityAccessible"), rs.getBoolean("allowsSmoking"));
                RestaurantItem currentMenuItem = new RestaurantItem(rs.getString("itemName"),
                        rs.getDouble("price"), rs.getString("description"));

//                DecimalFormat df = new DecimalFormat("#.00");
//                double formattedPrice = Double.parseDouble(df.format(rs.getDouble("price")));
//                currentMenuItem.setPrice(formattedPrice);
                System.out.println(currentMenuItem.getItemName() + " added");
//                ResultSet rs2 = stmt.executeQuery("SELECT * FROM inventoryItems WHERE roomNumber ='" + currentRoom.getRoomNumber() + "';");
//                while (rs2.next()) {
//                    InventoryItem currentInventory = new InventoryItem(rs2.getString("NAME"), rs2.getInt("QUANTITY"), rs2.getInt("EXPECTEDQUANTITY"), rs2.getBoolean("ISCONSUMABLE"));
//                    currentRoom.inventory.add(currentInventory);
//                    System.out.println(currentInventory.getName() + " Inventory added");
//                }
                menu.add(currentMenuItem);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch Restaurant items from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return menu;
    }

    public void updateRestaurantTable() {
        restaurantTable.getItems().clear();
        restaurantTable.getItems().addAll(fetchRestaurantMenuFromDB());
        restaurantTable.sort();
    }

    
    @FXML
    public void handleRestaurantDelete() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                // Check if there is employee with that username
                String sql = "SELECT count(*) FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "';";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.getInt(1) == 0) {
                    // No employee found
                    System.out.println("Error removing employee:\nThere is no employee " + restaurantNameField.getText());
                    rs.close();
                    stmt.close();
                    c.close();
                    return;
                }

                // We can now go ahead and remove the employee
                sql = "DELETE FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "'";
                stmt.executeUpdate(sql);

                rs.close();
                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            restaurantTable.getSelectionModel().clearSelection();
            updateRestaurantTable();
            addEditButton.setDisable(false);
            addEditButton.setText("Add Item");
            
            restaurantPriceField.setText("");
            restaurantNameField.setText("");
            restaurantDescriptionField.setText("");
            selectedItem = null;
            
        }

    }

    @FXML
    public void handleCateredMealDeselect() {

    }

    @FXML
    public void handleAddEditRestaurantItem(MouseEvent event) {
        String originalItemName = restaurantNameField.getText();
        System.out.println(addEditButton.getText().equals("Add Item"));
        if (addEditButton.getText().equals("Add Item")) {

            boolean doubleTest;
            try {
                Double.parseDouble(restaurantPriceField.getText());
                doubleTest = true;
            } catch (NumberFormatException e) {
                doubleTest = false;
            }

            if (doubleTest && restaurantNameField.getText() != null) {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to add this item?");
                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(originalItemName);
                if (result.get() == ButtonType.OK) {

                    // Connect to the DB and perform the necessary queries
                    Connection c = null;
                    Statement stmt = null;
                    try {
                        c = DriverManager.getConnection(prms.PRMS.DBFILE);
                        stmt = c.createStatement();
                        String sql;
                        sql = "INSERT INTO restaurantItems VALUES ('" + restaurantNameField.getText() + "', '" + Double.parseDouble(restaurantPriceField.getText()) + "',   '" + restaurantDescriptionField.getText() + "' );";
                        stmt.executeUpdate(sql);
                        stmt.close();
                        c.close();

                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    updateRestaurantTable();
                    restaurantTable.getSelectionModel().clearSelection();

                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input! Please review your entries.");
                alert.showAndWait();
            }
        } else if (addEditButton.getText().equals("Edit Item")) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to edit this item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                // Connect to the DB and perform the necessary queries
                Connection c = null;
                Statement stmt = null;
                try {
                    c = DriverManager.getConnection(prms.PRMS.DBFILE);
                    stmt = c.createStatement();

// ASK FERGUS ABOUT THIS
//
                    String sql = "UPDATE restaurantItems SET itemName ='" + restaurantNameField.getText() +
                            "', price = '" + restaurantPriceField.getText() + "', description = '" + restaurantDescriptionField.getText() +
                            "' WHERE itemName ='" + selectedItem.getItemName() + "';";
                    stmt.executeUpdate(sql);

//                sql = "UPDATE restaurantItems SET price ='" + restaurantPriceField.getText() + "' WHERE itemName ='" + selectedItem.getItemName() + "';";
//                stmt.executeUpdate(sql);
//                
//                sql = "UPDATE restaurantItems SET description ='" + restaurantDescriptionField.getText() + "' WHERE itemName ='" + selectedItem.getItemName() + "';";
//                stmt.executeUpdate(sql);
                    //rs.close();
                    stmt.close();
                    c.close();

                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
                restaurantTable.getSelectionModel().clearSelection();
                updateRestaurantTable();
            }
        }

    }

    @FXML
    public void handleRestaurantDeselect(MouseEvent event) {
        restaurantTable.getSelectionModel().clearSelection();
        restaurantNameField.setText("");
        restaurantDescriptionField.setText("");
        restaurantPriceField.setText("");
        addEditButton.setText("Add Item");
    }

    @FXML
    public void handleCateredMealDelete(MouseEvent event) {

    }

    RestaurantItem selectedItem;

    @FXML
    public void handleRestaurantItemSelection(MouseEvent event) {
        selectedItem = (RestaurantItem) restaurantTable.getSelectionModel().getSelectedItem();
        if (restaurantTable.getSelectionModel().getSelectedItem() != null) {
            System.out.println(selectedItem.getItemName() + " has been selected!");

            restaurantNameField.setText(selectedItem.getItemName());
            restaurantDescriptionField.setText(selectedItem.getDescription());
            restaurantPriceField.setText(String.valueOf(selectedItem.getPrice()));
            addEditButton.setText("Edit Item");

            restaurantDeselectButton.setDisable(false);
            restaurantDeleteButton.setDisable(false);
        }

    }

}
