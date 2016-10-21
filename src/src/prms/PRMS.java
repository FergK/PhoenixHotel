package prms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fergus Kelley
 */
public class PRMS extends Application {
    
    // Here we can define constants that will be used throughout the application
    
    // List of all the possible jobTitle values. Used in the DB and in the UI
    static final String[] jobTitleList = {"Manager", "Front-desk", "Custodial", "Staff", "Kitchen"};

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Pheonix Resort Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
