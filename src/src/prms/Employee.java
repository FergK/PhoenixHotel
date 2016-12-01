
package prms;

import javafx.beans.property.SimpleStringProperty;

public class Employee {
    
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty jobTitle = new SimpleStringProperty("");
    private SimpleStringProperty username = new SimpleStringProperty("");
    private String password;
    
    
    public Employee( String first, String last, String job, String user, String pass ) {
        setFirstName(first);
        setLastName(last);
        setJobTitle(job);
        setUsername(user);
        setPassword(pass);
    }

    public void setFirstName( String first ) {
        firstName.set(first);
    }

    public void setLastName( String last ) {
        lastName.set(last);
    }

    public void setJobTitle( String job ) {
        jobTitle.set(job);
    }

    public void setUsername( String user ) {
        username.set(user);
    }

    public void setPassword( String pass ) {
        password = pass;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getJobTitle() {
        return jobTitle.get();
    }

    public String getUsername() {
        return username.get();
    }
    
    @Override
    public String toString() {
        return "First Name: " + getFirstName()
            + "\nLast Name: " + getLastName()
            + "\nJob Title: " + getJobTitle()
            + "\nUsername: " + getUsername();
    }
    
}
