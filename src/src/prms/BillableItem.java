/* Change Log
11/28/2016  Andrew
    Converted fields to simple* type
10/24/2016  Fergus
    Fixed capitalization and names of some variables
    Changed dat and time variable to single var of LocalDateTime
10/20/2016  Andrew
    Created inital version
 */
package prms;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BillableItem {

    private SimpleStringProperty billableName = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty invoiceUID = new SimpleStringProperty();
    private SimpleStringProperty billableUID = new SimpleStringProperty();

    public BillableItem(String billableName, double price, String time, String invoiceUID, String billableUID) {
        setBillableName(billableName);
        setPrice(price);
        setTime(time);
        setInvoiceUID(invoiceUID);
        setBillableUID(billableUID);
    }
    
    public String getInvoiceUID() {
        return invoiceUID.get();
    }

    public String getBillableUID() {
        return billableUID.get();
    }

    public String getBillableName() {
        return billableName.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getTime() {
        return time.get();
    }

    public void setInvoiceUID(String UID) {
        this.invoiceUID.set(UID);
    }

    public void setBillableName(String billableName) {
        this.billableName.set(billableName);
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public void setTime(String time) {
        this.time.set(time);
    }
    
    public void setBillableUID(String billableUID) {
        this.billableUID.set(billableUID);
    }
    

}