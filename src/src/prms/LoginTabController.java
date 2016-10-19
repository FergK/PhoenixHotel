package prms;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 *
 * @author Fergus Kelley
 */
public class LoginTabController implements Initializable {

    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button logoutButton;

    @FXML
    private void handleLoginButtonPress(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean success = false;
        
        Connection c = null;
	  Statement stmt = null;
	  
	  try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:PhoenixHotel.db");
	    c.setAutoCommit(false);
	
	      stmt = c.createStatement();
	      
	      
	      //String all= "SELECT * FROM EMPLOYEE ;";
	      String verify= "SELECT * FROM EMPLOYEE WHERE userName== '"+username+"' ;";
	      c.commit();
	         
	      ResultSet rs = stmt.executeQuery(verify);
	      
	      while ( rs.next() ) {
	    	  //String firstName= rs.getString("FIRSTNAME");	
	    	  //String lastName= rs.getString("LASTNAME");
	    	  String userName= rs.getString("USERNAME");
	    	  String passWord= rs.getString("PASSWORD");
	    	  //String uid= rs.getString("UID");
	    	  //int isAdmin= rs.getInt("ISADMIN");
	    	  
	    	  System.out.println("PASSWORD: " + password);
	    	  
	    	  if(password.compareTo(passWord)==0){
		    	success=true;  
		    	
		      }
	    	  System.out.println(isLoggedin);	    	  
	      }
	      rs.close();
	      stmt.close(); 
	      c.close();
	  } catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	  }

        // Here is where we will put the code to query the database for the given username and check the password
        // set success to true if the login was successful, false otherwise
        

        if (success) {
            usernameField.setDisable(true);

            passwordField.setText("");
            passwordField.setDisable(true);

            loginLabel.setText("Login successful!");

            loginButton.setVisible(false);
            logoutButton.setVisible(true);

            // This next chunk of code loops over all the tabs and enables them
            // We will want to change this so that it only enables the tabs that
            // are accessible to this user (manager, cleaning staff, restaurant, etc.)
            TabPane allTabs = (TabPane) loginLabel.getScene().lookup("#allTabs");
            ObservableList<Tab> tabList = allTabs.getTabs();
            for (Tab next : tabList) {
                next.setDisable(false);
            }

        } else {
            loginLabel.setText("Username or password incorrect, please try again.");
        }
    }

    @FXML
    private void handleLogoutButtonPress(ActionEvent event) {
        // code to logout and disable the other tabs
        usernameField.setText("");
        usernameField.setDisable(false);
        usernameField.requestFocus();

        passwordField.setText("");
        passwordField.setDisable(false);

        loginLabel.setText("");

        loginButton.setVisible(true);
        logoutButton.setVisible(false);

        // Disable all the tabs but this one
        TabPane allTabs = (TabPane) loginLabel.getScene().lookup("#allTabs");
        ObservableList<Tab> tabList = allTabs.getTabs();
        for (Tab next : tabList) {
            if (!next.getId().equals("loginTab")) {
                next.setDisable(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
