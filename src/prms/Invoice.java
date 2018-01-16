/* Change Log
11/30/2016  Deividas
    Minor debug
11/28/2016  Fergus
    Added amount paid field
11/28/2016  Deividas
    Converted fields to simple* type
10/24/2016  Fergus
    Changed the CCNum and CCExp types
    Added the arraylist of BillableItem
 */
package prms;

import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Invoice {

    private SimpleStringProperty invoiceUID  = new SimpleStringProperty();
    private SimpleStringProperty customerName = new SimpleStringProperty();
    private SimpleStringProperty creditCardNum = new SimpleStringProperty();
    private SimpleStringProperty creditCardExp = new SimpleStringProperty();
    private SimpleDoubleProperty amountPaid = new SimpleDoubleProperty();
    protected ArrayList<BillableItem> billableItems;

    public Invoice(String invoiceUID, String customerName, String creditCardNum,String creditCardExp ) {
        setInvoiceUID(invoiceUID);
        setCustomerName(customerName);
        setCreditCardNum(creditCardNum);
        setCreditCardExp(creditCardExp);
        setAmountPaid( 0 );
        billableItems = new ArrayList<BillableItem>();
    }
    
    

    public ArrayList<BillableItem> getBillableItems() {
        return billableItems;
    }

    public String getUID() {
        return invoiceUID.get();
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public String getCreditCardNum() {
        return creditCardNum.get();
    }

    public String getCreditCardExp() {
        return creditCardExp.get();
    }
    
    public double getAmountPaid() {
        return amountPaid.get();
    }
    
    public void setInvoiceUID(String UID) {
        this.invoiceUID.set(UID);
    }
    
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum.set(creditCardNum);
    }

    public void setCreditCardExp(String creditCardExp) {
        this.creditCardExp.set(creditCardExp);
    }
    
    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }
}
