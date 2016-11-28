package prms;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

/**
 *
 * @author Fergus Kelley
 */
public class MaintenanceTabController implements Initializable {
/*
    @FXML
    private Button modifyButton;

    @FXML
    private ChoiceBox modifyJobTitleChoiceBox;
*/
    @FXML
    private TableView maintenanceTable;
    @FXML
    private Button roomServiceUpdateBtn;
    @FXML
    private Button quantityUpdateBtn;
    @FXML
    private Button expectedQuantityUpdateBtn;

    @FXML
    private void handleRoomServiceUpdate(ActionEvent event) {
        // update Room Service Date to tomorrow in DB
        //System.out.println("room serv handle connected");
    }
    
    @FXML
    private void handleQuantityUpdate(ActionEvent event){
        // update quantity update of a selected InventoryItem
    }
    
    @FXML
    private void handleExpectedQuantityUpdate(ActionEvent event){
        // update quantity update of a selected InventoryItem
    }
    
    /*@FXML
    private void handleItemSelection(MouseEvent event){
        //quantityUpdateBtn.setDisable(false);
        //expectedQuantityUpdateBtn.setDisable(false);
    }
*/
    @FXML
    private void handleTableSelect(ActionEvent event) {
//        System.out.println("tableclicked");
//table.getSelectionModel().getSelectedIndex();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO implement the code that gets a list of possible job titles
        // then adds the list of job titles to the dropdowns
        //String[] jobList = {"Manager", "Front-desk staff", "Custodial staff", "Bellhop", "Server", "Cook"};
        //modifyJobTitleChoiceBox.getItems().addAll(jobList);
        


        // TODO implement the code that adds all the employees to the table
//        ObservableList inventoryItems = maintenanceTable.getItems();
//        inventoryItems.add(new InventoryItem("Towel", 3, 3, false));
//        inventoryItems.add(new InventoryItem("Soap", 5, 5, true));
//        inventoryItems.add(new InventoryItem("Water Bottle", 3,0, true));
        

        //set inventory update buttons to disabled
        //before an InventoryItem is selected
        //quantityUpdateBtn.setDisable(true);
        //expectedQuantityUpdateBtn.setDisable(true);
        

    }

}
