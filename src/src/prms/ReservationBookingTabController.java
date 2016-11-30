/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author youngvz
 */
public class ReservationBookingTabController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TabPane ReservationBookingTabPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> guestChoiceBox;

    @FXML
    private ChoiceBox<String> needsProjectorChoiceBox;

    @FXML
    private ChoiceBox<String> needStageChoiceBox;

    @FXML
    private ChoiceBox<String> roomChoiceBox;

    @FXML
    private Button reserveEventButton;

    @FXML
    private Label FeedbackLabel;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TableView<String> eventBookingTableView;

    @FXML
    private TableColumn companyName;

    @FXML
    private TableColumn roomName;

    @FXML
    private TableColumn dateReserved;

    @FXML
    private Button bookReservationButton;

    @FXML
    private Tab ReservationPaymentTab;

    @FXML
    private TextField ccNumberTextField;

    @FXML
    private TextField ccExpirationTextField;

    @FXML
    private TextField ccCodeTextField;

    @FXML
    private Button createInvoiceButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ConfirmationLabel;

    @FXML
    private Label companyNameLabel;

    @FXML
    void handleEventBookingSelection(MouseEvent event) {

    }

    @FXML
    void handleEventInvoice(ActionEvent event) {

    }

    @FXML
    void handleReservationBooking(ActionEvent event) {

    }

    @FXML
    void reserveEvenetHandle(ActionEvent event) {

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
