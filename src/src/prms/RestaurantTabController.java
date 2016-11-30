/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//TODO
package prms;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private TableView cateredMealTable;
    @FXML
    private TextField restaurantNameField;
    @FXML
    private TextField restaurantPriceField;
    @FXML
    private TextField restaurantDescriptionField;
    @FXML
    private TextField cateredMealNameField;
    @FXML
    private TextField cateredMealPriceField;
    @FXML
    private TextField cateredMealDescriptionField;
    @FXML
    private Button addEditButton;
    @FXML
    private Button caterAddEdit;
    @FXML
    private Button restaurantDeselectButton;
    @FXML
    private Button restaurantDeleteButton;
    @FXML
    private Button cateredMealDeselectBtn;
    @FXML
    private Button cateredMealDeleteBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateRestaurantTable();
        updateCateredMealTable();
        restaurantDeselectButton.setDisable(true);
        restaurantDeleteButton.setDisable(true);
        cateredMealDeselectBtn.setDisable(true);
        cateredMealDeleteBtn.setDisable(true);

    }

    public ArrayList<RestaurantItem> fetchRestaurantMenuFromDB() {

        ArrayList<RestaurantItem> menu = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            System.out.println("CONNECTED TO DB");
            String sql = "SELECT * FROM RestaurantItems";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                RestaurantItem currentMenuItem = new RestaurantItem(rs.getString("itemName"),
                        rs.getDouble("price"), rs.getString("description"));

                System.out.println(currentMenuItem.getItemName() + " added");
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

    public ArrayList<CateredMealItem> fetchCateredMealsMenuFromDB() {

        ArrayList<CateredMealItem> caterMenu = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            System.out.println("CONNECTED TO DB");
            String sql = "SELECT * FROM cateredMealItems";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CateredMealItem currentCaterMenuItem = new CateredMealItem(rs.getString("mealName"),
                        rs.getDouble("pricePerSeat"), rs.getString("mealDescription"));
                System.out.println(currentCaterMenuItem.getMealName() + " added");
                caterMenu.add(currentCaterMenuItem);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch Catering Menu items from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return caterMenu;
    }

    public void updateRestaurantTable() {
        restaurantTable.getItems().clear();
        restaurantTable.getItems().addAll(fetchRestaurantMenuFromDB());
        restaurantTable.sort();
    }

    public void updateCateredMealTable() {
        cateredMealTable.getItems().clear();
        cateredMealTable.getItems().addAll(fetchCateredMealsMenuFromDB());
        cateredMealTable.sort();
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

                // Check if there is item with that name
                String sql = "SELECT count(*) FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "';";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.getInt(1) == 0) {
                    // No employee found
                    System.out.println("Error removing item:\nThere is no such item! " + restaurantNameField.getText());
                    rs.close();
                    stmt.close();
                    c.close();
                    return;
                }

                // We can now go ahead and remove the item
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
                        restaurantDeselectButton.setDisable(true);
            restaurantDeleteButton.setDisable(true);
        }
    }

    @FXML
    public void handleCateredMealDelete(MouseEvent event) {
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

                // Check if there is item with that name
                String sql = "SELECT count(*) FROM cateredMealItems WHERE mealName='" + cateredMealNameField.getText() + "';";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.getInt(1) == 0) {
                    // No item found
                    System.out.println("Error removing item:\nThere is no such item! " + cateredMealNameField.getText());
                    rs.close();
                    stmt.close();
                    c.close();
                    return;
                }

                // We can now go ahead and remove the item
                sql = "DELETE FROM cateredMealItems WHERE mealName='" + cateredMealNameField.getText() + "'";
                stmt.executeUpdate(sql);

                rs.close();
                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            cateredMealTable.getSelectionModel().clearSelection();
            updateCateredMealTable();
            caterAddEdit.setDisable(false);
            caterAddEdit.setText("Add Item");

            cateredMealPriceField.setText("");
            cateredMealNameField.setText("");
            cateredMealDescriptionField.setText("");
            selectedItem = null;
                    cateredMealDeselectBtn.setDisable(true);
        cateredMealDeleteBtn.setDisable(true);
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
    public void handleCateredMealDeselect(MouseEvent event) {
        cateredMealTable.getSelectionModel().clearSelection();
        cateredMealNameField.setText("");
        cateredMealDescriptionField.setText("");
        cateredMealPriceField.setText("");
        caterAddEdit.setText("Add Item");
        cateredMealDeselectBtn.setDisable(true);
        cateredMealDeleteBtn.setDisable(true);

    }

    @FXML
    public void handleAddEditRestaurantItem(MouseEvent event) {

        if (addEditButton.getText().equals("Add Item")) {

            boolean doubleTest;
            try {
                Double.parseDouble(restaurantPriceField.getText());
                doubleTest = true;
            } catch (NumberFormatException e) {
                doubleTest = false;
            }

            Boolean pass = false;

            // Connect to the DB and perform the necessary queries
            Connection c2 = null;
            Statement stmt2 = null;
            try {
                c2 = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt2 = c2.createStatement();

                // Check if a employee already exists with that username
                String sql = "SELECT count(*) FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "'";
                ResultSet rs2 = stmt2.executeQuery(sql);
                if (rs2.getInt(1) > 0) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input! An item with that name already exists.");
                    alert.showAndWait();
                    rs2.close();
                    stmt2.close();
                    c2.close();
                    return;
                }

                pass = true;

                rs2.close();
                stmt2.close();
                c2.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            if (pass) {
                if (doubleTest && restaurantNameField.getText() != null) {

                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to add this item?");
                    Optional<ButtonType> result = alert.showAndWait();
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

                    String sql = "UPDATE restaurantItems SET itemName ='" + restaurantNameField.getText()
                            + "', price = '" + restaurantPriceField.getText() + "', description = '" + restaurantDescriptionField.getText()
                            + "' WHERE itemName ='" + selectedItem.getItemName() + "';";
                    stmt.executeUpdate(sql);
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
    public void handleAddEditCateredMealItem(MouseEvent event) {

        if (caterAddEdit.getText().equals("Add Item")) {

            boolean doubleTest;
            try {
                Double.parseDouble(cateredMealPriceField.getText());
                doubleTest = true;
            } catch (NumberFormatException e) {
                doubleTest = false;
            }

            Boolean pass = false;

            // Connect to the DB and perform the necessary queries
            Connection c2 = null;
            Statement stmt2 = null;
            try {
                c2 = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt2 = c2.createStatement();

                // Check if a employee already exists with that username
                String sql = "SELECT count(*) FROM cateredMealItems WHERE mealName='" + cateredMealNameField.getText() + "'";
                ResultSet rs2 = stmt2.executeQuery(sql);
                if (rs2.getInt(1) > 0) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input! An item with that name already exists.");
                    alert.showAndWait();
                    rs2.close();
                    stmt2.close();
                    c2.close();
                    return;
                }

                pass = true;

                rs2.close();
                stmt2.close();
                c2.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            if (pass) {
                if (doubleTest && cateredMealNameField.getText() != null) {

                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to add this item?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        // Connect to the DB and perform the necessary queries
                        Connection c = null;
                        Statement stmt = null;
                        try {
                            c = DriverManager.getConnection(prms.PRMS.DBFILE);
                            stmt = c.createStatement();
                            String sql;
                            sql = "INSERT INTO cateredMealItems VALUES ('" + cateredMealNameField.getText() + "', '" + Double.parseDouble(cateredMealPriceField.getText()) + "',   '" + cateredMealDescriptionField.getText() + "' );";
                            stmt.executeUpdate(sql);
                            stmt.close();
                            c.close();

                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                        updateCateredMealTable();
                        cateredMealTable.getSelectionModel().clearSelection();

                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input! Please review your entries.");
                    alert.showAndWait();
                }
            }
        } else if (caterAddEdit.getText().equals("Edit Item")) {
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

                    String sql = "UPDATE cateredMealItems SET mealName ='" + cateredMealNameField.getText()
                            + "', pricePerSeat = '" + cateredMealPriceField.getText() + "', mealDescription = '" + cateredMealDescriptionField.getText()
                            + "' WHERE mealName ='" + caterSelectedItem.getMealName() + "';";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    c.close();

                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
                cateredMealTable.getSelectionModel().clearSelection();
                updateCateredMealTable();
            }
        }
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

    CateredMealItem caterSelectedItem;

    @FXML
    public void handleCaterMealItemSelection(MouseEvent event) {
        caterSelectedItem = (CateredMealItem) cateredMealTable.getSelectionModel().getSelectedItem();
        if (cateredMealTable.getSelectionModel().getSelectedItem() != null) {
            System.out.println(caterSelectedItem.getMealName() + " has been selected!");

            cateredMealNameField.setText(caterSelectedItem.getMealName());
            cateredMealDescriptionField.setText(caterSelectedItem.getMealDescription());
            cateredMealPriceField.setText(String.valueOf(caterSelectedItem.getPricePerSeat()));
            caterAddEdit.setText("Edit Item");

            cateredMealDeselectBtn.setDisable(false);
            cateredMealDeleteBtn.setDisable(false);
        }

    }

}