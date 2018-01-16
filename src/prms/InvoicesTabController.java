/* Change Log
11/30/2016  Fergus
    Final debug, reorganization
    and code cleanup, upgraded
    and finalized layout
11/29/2016  Deividas
    Connected controller to the 
    database, added functions,
    but introduced bugs
10/28/2016  Deividas
    Initial code, stubs generation,
    preliminary FXML / layout added
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

                Invoice currentInvoice = new Invoice(rs.getString("invoiceUID"),
                        rs.getString("customerName"), rs.getString("creditCardNum"),
                        rs.getString("creditCardExp"));

                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM billableItems WHERE invoiceUID ='"
                        + currentInvoice.getUID() + "';");
                while (rs2.next()) {

                    BillableItem currentBillableItem = new BillableItem(rs2.getString("billableUID"),
                            rs2.getString("invoiceUID"), rs2.getString("billableName"),
                            rs2.getDouble("price"), rs2.getString("time"));

                    currentInvoice.billableItems.add(currentBillableItem);
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

    public void updateSelectedInvoiceFromDB() {
        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;

        try {
            c = DriverManager.getConnection(prms.PRMS.DBFILE);
            stmt = c.createStatement();
            stmt2 = c.createStatement();
            selectedInvoice.getBillableItems().clear();

            String sql = "SELECT * FROM billableItems WHERE invoiceUID ='" + selectedInvoice.getUID() + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                BillableItem currentBillableItem = new BillableItem(rs.getString("billableUID"),
                        rs.getString("invoiceUID"), rs.getString("billableName"),
                        rs.getDouble("price"), rs.getString("time"));

                selectedInvoice.billableItems.add(currentBillableItem);
                //System.out.println(currentBillableItem.getBillableName() + " bill added");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println("Could not fetch Invoices from database: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void updateInvoiceList() {
        invoiceList.getItems().clear();
        invoiceList.getItems().addAll(fetchInvoicesFromDB());
        invoiceList.sort();
    }

    public void updateBillableItemList() {

        billableItemsList.getItems().clear();
        updateSelectedInvoiceFromDB();
        billableItemsList.getItems().addAll(selectedInvoice.getBillableItems());
        billableItemsList.sort();
    }

    Invoice selectedInvoice;

    @FXML
    public void handleInvoiceListSelection(MouseEvent event) {

        selectedInvoice = (Invoice) invoiceList.getSelectionModel().getSelectedItem();
        if (invoiceList.getSelectionModel().getSelectedItem() != null) {
            //System.out.println("\ninvoice selected");

            billableItemsList.getItems().clear();
            ArrayList<BillableItem> bills = selectedInvoice.getBillableItems();
            billableItemsList.getItems().addAll(bills);
            billableItemsList.sort();
            addEditButton.setDisable(false);

        }
    }

    //BillableItem selectedItem;
    @FXML
    public void handleBillableItemSelection(MouseEvent event) {
        BillableItem selectedItem = (BillableItem) billableItemsList.getSelectionModel().getSelectedItem();
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
    public void handleDeselect(MouseEvent event) {
        billableItemsList.getSelectionModel().clearSelection();
        customerNameField.setText("");
        timeField.setText("");
        priceField.setText("");
        addEditButton.setText("Add Item");
        deleteButton.setDisable(true);
        deselectButton.setDisable(true);
    }

    @FXML
    public void handleDelete(MouseEvent event) {

        BillableItem selectedItem = (BillableItem) billableItemsList.getSelectionModel().getSelectedItem();
        if (billableItemsList.getSelectionModel().getSelectedItem() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

                    // Check if there is a billable item with that name
                    String sql = "SELECT count(*) FROM billableItems WHERE billableUID='" + selectedItem.getBillableUID() + "';";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.getInt(1) == 0) {
                        // No employee found
                        System.out.println("Error removing item:\nThere is no such item! " + selectedItem.getBillableUID());
                        rs.close();
                        stmt.close();
                        c.close();
                        return;
                    }

                    // We can now go ahead and remove the item
                    sql = "DELETE FROM billableItems WHERE billableUID='" + selectedItem.getBillableUID() + "'";
                    stmt.executeUpdate(sql);

                    rs.close();
                    stmt.close();
                    c.close();

                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                addEditButton.setDisable(false);
                addEditButton.setText("Add Item");

                priceField.setText("");
                timeField.setText("");
                customerNameField.setText("");
                deleteButton.setDisable(true);
                deselectButton.setDisable(true);
                selectedItem = null;
                updateBillableItemList();
            }
        }
    }

    @FXML
    public void handleAddEdit(MouseEvent event) {

        String mode;

        if (addEditButton.getText().equals("Add Item")) {
            mode = "Add";
        } else if (addEditButton.getText().equals("Edit Item")) {
            mode = "Edit";
        } else {
            return;
        }

        boolean doubleTest;
        try {
            Double.parseDouble(priceField.getText());
            doubleTest = true;
        } catch (NumberFormatException e) {
            doubleTest = false;
        }

        boolean isNotNullTest;

        if ((priceField.getText() == null && priceField.getText().isEmpty())
                && (timeField.getText() == null && timeField.getText().isEmpty())
                && (customerNameField.getText() == null && customerNameField.getText().isEmpty())) {
            isNotNullTest = false;
        } else {
            isNotNullTest = true;
        }
        try {
            priceField.getText();
            isNotNullTest = true;
        } catch (NumberFormatException e) {
            isNotNullTest = false;
        }

        if (mode.equals("Add") && isNotNullTest && doubleTest) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

                    String billableUID = UUID.randomUUID().toString();
                    String invoiceUID = selectedInvoice.getUID();
                    String billableName = customerNameField.getText();
                    Double price = Double.parseDouble(priceField.getText());
                    String time = timeField.getText();

                    // We can now go ahead and insert the new employee
                    String sql = "INSERT INTO billableItems VALUES ('" + billableUID + "', '"
                            + invoiceUID + "', '" + billableName + "', '" + price + "', '" + time + "' );";
                    stmt.executeUpdate(sql);

                    stmt.close();
                    c.close();

                    updateBillableItemList();

                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

            }

        } else if (mode.equals("Edit") && isNotNullTest && doubleTest) {

            BillableItem selectedBillableItem = (BillableItem) billableItemsList.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

                    String sql = "UPDATE billableItems SET billableName ='" + customerNameField.getText()
                            + "', price = '" + priceField.getText() + "', time = '" + timeField.getText()
                            + "' WHERE billableUID ='" + selectedBillableItem.getBillableUID() + "';";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    c.close();

                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
                //Invoice x = (Invoice) invoiceList.getSelectionModel().getSelectedItem();

                updateBillableItemList();
                //billableItemsList.getSelectionModel().clearSelection();
            }

        } else if (!doubleTest || !isNotNullTest) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input! Please review your entries.");
            alert.showAndWait();
        }

    }

}
