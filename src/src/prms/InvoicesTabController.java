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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
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
        deselectButton.setDisable(true);
        deleteButton.setDisable(true);
        addEditButton.setDisable(true);
    }

    public ArrayList<Invoice> fetchInvoicesFromDB() {

        ArrayList<Invoice> invoices = new ArrayList<>();

//        Connection c = null;
//        Statement stmt = null;
//        Statement stmt2 = null;
//        try {
//            c = DriverManager.getConnection(prms.PRMS.DBFILE);
//            stmt = c.createStatement();
//            stmt2 = c.createStatement();
//
//            String sql = "SELECT * FROM invoices";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                Invoice currentInvoice = new Invoice(rs.getString("customerName"), rs.getInt("creditCardNum"), rs.getInt("creditCardExp"), rs.getString("UID"));
//                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM billableItems WHERE UID ='" + currentInvoice.getUID() + "';");
//                while (rs2.next()) {
//                    BillableItem currentBillableItem = new BillableItem(rs2.getString("billableName"), rs2.getDouble("price"), rs2.getInt("time"), rs2.getString("UID"), rs2.getString("billableUID"));
//                    currentInvoice.billItems.add(currentBillableItem);
//                    System.out.println(currentBillableItem.getBillableName() + " bill added");
//                }
//                invoices.add(currentInvoice);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println("Could not fetch Invoices from database: " + e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }

        return invoices;
    }

    public void updateInvoiceList() {
        invoiceList.getItems().clear();
        invoiceList.getItems().addAll(fetchInvoicesFromDB());
        invoiceList.sort();
    }

    public void updateBillableItemList(Invoice selectedInvoice) {
        System.out.println(selectedInvoice.getUID());

        billableItemsList.getItems().clear();
        ArrayList<BillableItem> bills = selectedInvoice.getBillableItems();
        billableItemsList.getItems().addAll(bills);
        billableItemsList.sort();

//        billableItemsList.getItems().clear();
//        billableItemsList.getItems().addAll(fetchInvoicesFromDB());
//        billableItemsList.sort();
        BillableItem selectedBillItem = (BillableItem) invoiceList.getSelectionModel().getSelectedItem();
        if (invoiceList.getSelectionModel().getSelectedItem() != null) {
            System.out.println("\nRoom inventory retrieved");
        }

    }

    @FXML
    public void handleInvoiceListSelection(MouseEvent event) {

        Invoice selectedInvoice = (Invoice) invoiceList.getSelectionModel().getSelectedItem();
        if (invoiceList.getSelectionModel().getSelectedItem() != null) {
            updateBillableItemList(selectedInvoice);
        }
    }

    BillableItem selectedItem;

    @FXML
    public void handleBillableItemSelection(MouseEvent event) {
        selectedItem = (BillableItem) billableItemsList.getSelectionModel().getSelectedItem();
        if (billableItemsList.getSelectionModel().getSelectedItem() != null) {
            System.out.println(selectedItem.getBillableName() + " has been selected!");

            customerNameField.setText(selectedItem.getBillableName());
            timeField.setText(String.valueOf(selectedItem.getTime()));
            priceField.setText(String.valueOf(selectedItem.getPrice()));
            addEditButton.setText("Edit Item");

            deleteButton.setDisable(false);
            deselectButton.setDisable(false);
        }

    }

    @FXML
    public void handleAddEdit(MouseEvent event) {

        if (addEditButton.getText().equals("Add Item")) {

            boolean doubleTest;
            try {
                Double.parseDouble(priceField.getText());
                doubleTest = true;
            } catch (NumberFormatException e) {
                doubleTest = false;
            }

            boolean timeTest;
            try {
                parseInt(priceField.getText());
                timeTest = true;
            } catch (NumberFormatException e) {
                timeTest = false;
            }

            Boolean pass = false;

            // Connect to the DB and perform the necessary queries
            Connection c2 = null;
            Statement stmt2 = null;
            try {
                c2 = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt2 = c2.createStatement();

                Invoice x = (Invoice) invoiceList.getSelectionModel().getSelectedItem();
                String invoiceUID = x.getUID();

//                // Check if a billable already exists with that username
//                String sql = "SELECT count(*) FROM BillableItems WHERE UID='" + invoiceUID + "'";
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
                pass = true;

//                rs2.close();
//                stmt2.close();
//                c2.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            if (pass) {
                if (doubleTest && customerNameField.getText() != null && timeTest) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to add this item?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        // Connect to the DB and perform the necessary queries
                        Connection c = null;
                        Statement stmt = null;

                        Invoice x = (Invoice) invoiceList.getSelectionModel().getSelectedItem();
                        String invoiceUID = x.getUID();

                        try {
                            c = DriverManager.getConnection(prms.PRMS.DBFILE);
                            stmt = c.createStatement();
                            String sql;
                            String genUID = UUID.randomUUID().toString();
                            sql = "INSERT INTO billableItems VALUES ('" + customerNameField.getText() + "', '" + Double.parseDouble(priceField.getText()) + "',   '" + parseInt(timeField.getText()) + "',   '" + invoiceUID + "',   '" + genUID + "' );";
                            stmt.executeUpdate(sql);
                            stmt.close();
                            c.close();

                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                        updateBillableItemList(x);
                        billableItemsList.getSelectionModel().clearSelection();

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input! Please review your entries.");
                    alert.showAndWait();
                }
            }
        } else if (addEditButton.getText().equals("Edit Item")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to edit this item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

//                // Connect to the DB and perform the necessary queries
//                Connection c = null;
//                Statement stmt = null;
//                try {
//                    c = DriverManager.getConnection(prms.PRMS.DBFILE);
//                    stmt = c.createStatement();
//
//                    String sql = "UPDATE billableItems SET customerName ='" + customerNameField.getText()
//                            + "', price = '" + priceField.getText() + "', time = '" + timeField.getText()
//                            + "' WHERE billableUID ='" + selectedItem.getBillableUID() + "';";
//                    stmt.executeUpdate(sql);
//                    stmt.close();
//                    c.close();
//
//                } catch (Exception e) {
//                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                    System.exit(0);
//                }
//                Invoice x = (Invoice) invoiceList.getSelectionModel().getSelectedItem();
//
//                updateBillableItemList(x);
//                billableItemsList.getSelectionModel().clearSelection();
            }
        }
    }
    
    
    
            @FXML
    public void handleDeselect(MouseEvent event) {
        billableItemsList.getSelectionModel().clearSelection();
        customerNameField.setText("");
        timeField.setText("");
        priceField.setText("");
        addEditButton.setText("Add Item");
    }
    
    
    
    
            @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            BillableItem test = (BillableItem) billableItemsList.getSelectionModel().getSelectedItem();

            // Connect to the DB and perform the necessary queries
            Connection c = null;
            Statement stmt = null;
            try {
                c = DriverManager.getConnection(prms.PRMS.DBFILE);
                stmt = c.createStatement();

                // Check if there is item with that name
                String sql = "SELECT count(*) FROM billableItems WHERE billableUID='" + test.getBillableUID() + "';";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.getInt(1) == 0) {
                    // No employee found
                    System.out.println("Error removing item:\nThere is no such item! " + test.getBillableUID());
                    rs.close();
                    stmt.close();
                    c.close();
                    return;
                }

                // We can now go ahead and remove the item
                sql = "DELETE FROM restaurantItems WHERE billableUID ='" + test.getBillableUID() + "'";
                stmt.executeUpdate(sql);

                rs.close();
                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            billableItemsList.getSelectionModel().clearSelection();
                Invoice x = (Invoice) invoiceList.getSelectionModel().getSelectedItem();

                updateBillableItemList(x);
            addEditButton.setDisable(false);
            addEditButton.setText("Add Item");

            customerNameField.setText("");
            timeField.setText("");
            priceField.setText("");
            selectedItem = null;
        }
    }
    
    

//           @FXML
//    public void handleRoomSelection(MouseEvent event) {
//        
//
//        
//        HotelRoom selectedRoom = (HotelRoom) roomTable.getSelectionModel().getSelectedItem();
//        if (roomTable.getSelectionModel().getSelectedItem() != null) {
//            updateInventoryTable(selectedRoom);
//            replenishUpdateLabel.setVisible(false);
//            dateUpdateLabel.setVisible(false);
//            System.out.println(selectedRoom.getDateLastCleaned());
//            roomServiceUpdateBtn.setDisable(false);
//
//            String temp = selectedRoom.getDateLastCleaned();
//            roomServiceField.setText(
//                    temp.substring(0, 2) + "-"
//                    + temp.substring(2, 4) + "-"
//                    + temp.substring(4)
//            );
//        }
//
//    }
//       
//       
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