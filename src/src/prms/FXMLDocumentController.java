/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 *
 * @author Fergus Kelley
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public TabPane allTabs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // This code should stay commented out unless you are actively working on a tab
//        ObservableList<Tab> tabList = allTabs.getTabs();
//        for (Tab next : tabList) {
//            next.setDisable(false);
//        }
//        SingleSelectionModel<Tab> selectionModel = allTabs.getSelectionModel();
//        selectionModel.select(1);

    }

}
