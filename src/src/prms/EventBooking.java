
/* Change Log
10/24/2016  Fergus
    Change the type of startDate and endDate to LocalDate
    Added bill field
 */

package prms;

// TODO This entire class

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EventBooking {

    private SimpleStringProperty roomName = new SimpleStringProperty("");
    private SimpleStringProperty companyName = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleIntegerProperty maxCapacity = new SimpleIntegerProperty();
    private SimpleStringProperty startDate = new SimpleStringProperty("");
    private SimpleStringProperty endDate = new SimpleStringProperty("");
    private SimpleStringProperty billUID = new SimpleStringProperty("");


    public EventBooking(String roomName, String companyName, double price, int maxCapacity, String startDate, String endDate, String bill) {
        setName(roomName);
        setCompanyName(companyName);
        setPrice(price);
        setMaxCapacity(maxCapacity);
        setStartDate(startDate);
        setEndDate(endDate);
        setBillUID(bill);
    }
    
    public String getCompanyName(){
        return companyName.get();
    }

    
    
    public String getStartDate() {
        return startDate.get();
    }

    public String getEndDate() {
        return endDate.get();
    }
    
    public String getName(){
        return roomName.get();
    }
    
    public double getPrice(){
        return price.get();
    }
    
    public int getMaxCapacity(){
        return maxCapacity.get();
    }
    
    
    
    public String getBillUID(){
        return billUID.get();
    }
    
    public void setCompanyName(String name){
        this.companyName.set(name);
    }

    public void setBillUID(String billUID){
        this.billUID.set(billUID);
    }
    
    public void setStartDate(String startDate) {
        this.startDate.setValue(startDate);
    }

    public void setEndDate(String endDate) {
        this.endDate.setValue(endDate);
    }
    
    public void setName(String roomName){
        this.roomName.setValue(roomName);
    }
    
    public void setPrice(double price){
        this.price.setValue(price);
    }
    
    public void setMaxCapacity( int maxCapacity){
        this.maxCapacity.setValue(maxCapacity);
    }
    
}
    
