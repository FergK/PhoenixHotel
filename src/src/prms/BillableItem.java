/* Change Log
10/24/2016  Fergus
    Fixed capitalization and names of some variables
    Changed dat and time variable to single var of LocalDateTime
 */
package prms;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AndrewAn
 */
public class BillableItem {

    private SimpleStringProperty billableName = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleIntegerProperty time = new SimpleIntegerProperty();
    private SimpleStringProperty UID = new SimpleStringProperty();
    private SimpleStringProperty billableUID = new SimpleStringProperty();

    public BillableItem(String billableName, double price, int time, String billedTo, String billableUID) {
        setBillableName(billableName);
        setPrice(price);
        setTime(time);
        setBilledTo(billedTo);
        setBillableUID(billableUID);
    }
    
        public String getBillableUID() {
        return billableUID.get();
    }

    public String getUID() {
        return UID.get();
    }

    public String getBillableName() {
        return billableName.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getTime() {
        return time.get();
    }

    public void setBilledTo(String UID) {
        this.UID.set(UID);
    }

    public void setBillableName(String billableName) {
        this.billableName.set(billableName);
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public void setTime(int time) {
        this.time.set(time);
    }
    
        public void setBillableUID(String billableUID) {
        this.billableUID.set(billableUID);
    }
    

}
