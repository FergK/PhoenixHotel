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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class InvoicesTabController implements Initializable {
    
 @FXML
    private TableView invoiceList;
    @FXML
    private TableView billableItemsList;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField timeField;
    @FXML
    private Button addEditButton;
    @FXML
    private Button deselectButton;
    @FXML
    private Button deleteButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateInvoiceList();
//        restaurantDeselectButton.setDisable(true);
//        restaurantDeleteButton.setDisable(true);
    }    
    
     public ArrayList<Invoice> fetchInvoicesFromDB() {

        ArrayList<Invoice> invoices = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            stmt2 = c.createStatement();

            String sql = "SELECT * FROM invoices";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Invoice currentInvoice = new Invoice(rs.getString("customerName"), rs.getInt("creditCardNum"), rs.getInt("creditCardExp"), rs.getString("UID"));
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM billableItems WHERE UID ='" + currentInvoice.getUID() + "';");
                while (rs2.next()) {
                    BillableItem currentBillableItem = new BillableItem(rs2.getString("billableName"), rs2.getDouble("price"), rs2.getInt("time"), rs2.getString("UID"));
                    currentInvoice.billItems.add(currentBillableItem);
                    System.out.println(currentBillableItem.getBillableName() + " Inventory added");
                }
                invoices.add(currentInvoice);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch Invoices from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return invoices;
    }

       public void updateInvoiceList() {
        invoiceList.getItems().clear();
        invoiceList.getItems().addAll(fetchInvoicesFromDB());
        invoiceList.sort();
    }
       
//           @FXML
//    public void handleRestaurantDelete() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Are you sure you want to delete this item?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//
//            // Connect to the DB and perform the necessary queries
//            Connection c = null;
//            Statement stmt = null;
//            try {
//                c = DriverManager.getConnection(prms.PRMS.DBFILE);
//                stmt = c.createStatement();
//
//                // Check if there is item with that name
//                String sql = "SELECT count(*) FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "';";
//                ResultSet rs = stmt.executeQuery(sql);
//                if (rs.getInt(1) == 0) {
//                    // No employee found
//                    System.out.println("Error removing item:\nThere is no such item! " + restaurantNameField.getText());
//                    rs.close();
//                    stmt.close();
//                    c.close();
//                    return;
//                }
//
//                // We can now go ahead and remove the item
//                sql = "DELETE FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "'";
//                stmt.executeUpdate(sql);
//
//                rs.close();
//                stmt.close();
//                c.close();
//
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
//            }
//            restaurantTable.getSelectionModel().clearSelection();
//            updateRestaurantTable();
//            addEditButton.setDisable(false);
//            addEditButton.setText("Add Item");
//
//            restaurantPriceField.setText("");
//            restaurantNameField.setText("");
//            restaurantDescriptionField.setText("");
//            selectedItem = null;
//        }
//    }
//    
//        @FXML
//    public void handleRestaurantDeselect(MouseEvent event) {
//        restaurantTable.getSelectionModel().clearSelection();
//        restaurantNameField.setText("");
//        restaurantDescriptionField.setText("");
//        restaurantPriceField.setText("");
//        addEditButton.setText("Add Item");
//    }
//    
//       @FXML
//    public void handleAddEditRestaurantItem(MouseEvent event) {
//
//        if (addEditButton.getText().equals("Add Item")) {
//
//            boolean doubleTest;
//            try {
//                Double.parseDouble(restaurantPriceField.getText());
//                doubleTest = true;
//            } catch (NumberFormatException e) {
//                doubleTest = false;
//            }
//
//            Boolean pass = false;
//
//            // Connect to the DB and perform the necessary queries
//            Connection c2 = null;
//            Statement stmt2 = null;
//            try {
//                c2 = DriverManager.getConnection(prms.PRMS.DBFILE);
//                stmt2 = c2.createStatement();
//
//                // Check if a employee already exists with that username
//                String sql = "SELECT count(*) FROM restaurantItems WHERE itemName='" + restaurantNameField.getText() + "'";
//                ResultSet rs2 = stmt2.executeQuery(sql);
//                if (rs2.getInt(1) > 0) {
//
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Invalid input! An item with that name already exists.");
//                    alert.showAndWait();
//                    rs2.close();
//                    stmt2.close();
//                    c2.close();
//                    return;
//                }
//
//                pass = true;
//
//                rs2.close();
//                stmt2.close();
//                c2.close();
//
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
//            }
//            if (pass) {
//                if (doubleTest && restaurantNameField.getText() != null) {
//
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Confirmation Dialog");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Are you sure you want to add this item?");
//                    Optional<ButtonType> result = alert.showAndWait();
//                    if (result.get() == ButtonType.OK) {
//
//                        // Connect to the DB and perform the necessary queries
//                        Connection c = null;
//                        Statement stmt = null;
//                        try {
//                            c = DriverManager.getConnection(prms.PRMS.DBFILE);
//                            stmt = c.createStatement();
//                            String sql;
//                            sql = "INSERT INTO restaurantItems VALUES ('" + restaurantNameField.getText() + "', '" + Double.parseDouble(restaurantPriceField.getText()) + "',   '" + restaurantDescriptionField.getText() + "' );";
//                            stmt.executeUpdate(sql);
//                            stmt.close();
//                            c.close();
//
//                        } catch (Exception e) {
//                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                            System.exit(0);
//                        }
//                        updateRestaurantTable();
//                        restaurantTable.getSelectionModel().clearSelection();
//
//                    }
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Invalid input! Please review your entries.");
//                    alert.showAndWait();
//                }
//            }
//        } else if (addEditButton.getText().equals("Edit Item")) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText(null);
//            alert.setContentText("Are you sure you want to edit this item?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK) {
//
//                // Connect to the DB and perform the necessary queries
//                Connection c = null;
//                Statement stmt = null;
//                try {
//                    c = DriverManager.getConnection(prms.PRMS.DBFILE);
//                    stmt = c.createStatement();
//
//                    String sql = "UPDATE restaurantItems SET itemName ='" + restaurantNameField.getText()
//                            + "', price = '" + restaurantPriceField.getText() + "', description = '" + restaurantDescriptionField.getText()
//                            + "' WHERE itemName ='" + selectedItem.getItemName() + "';";
//                    stmt.executeUpdate(sql);
//                    stmt.close();
//                    c.close();
//
//                } catch (Exception e) {
//                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                    System.exit(0);
//                }
//                restaurantTable.getSelectionModel().clearSelection();
//                updateRestaurantTable();
//            }
//        }
//    }
//    
//    
//    
//        RestaurantItem selectedItem;
//
//    @FXML
//    public void handleRestaurantItemSelection(MouseEvent event) {
//        selectedItem = (RestaurantItem) restaurantTable.getSelectionModel().getSelectedItem();
//        if (restaurantTable.getSelectionModel().getSelectedItem() != null) {
//            System.out.println(selectedItem.getItemName() + " has been selected!");
//
//            restaurantNameField.setText(selectedItem.getItemName());
//            restaurantDescriptionField.setText(selectedItem.getDescription());
//            restaurantPriceField.setText(String.valueOf(selectedItem.getPrice()));
//            addEditButton.setText("Edit Item");
//
//            restaurantDeselectButton.setDisable(false);
//            restaurantDeleteButton.setDisable(false);
//        }
//
//    }
}
