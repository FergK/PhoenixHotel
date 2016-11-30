/* Change Log
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
<<<<<<< HEAD
import java.util.UUID;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
=======
import javafx.beans.property.SimpleDoubleProperty;
>>>>>>> Fergus
import javafx.beans.property.SimpleStringProperty;

public class Invoice {

<<<<<<< HEAD
    private SimpleStringProperty UID = new SimpleStringProperty();
    private SimpleStringProperty customerName = new SimpleStringProperty();
    private SimpleIntegerProperty creditCardNum = new SimpleIntegerProperty();
    private SimpleIntegerProperty creditCardExp = new SimpleIntegerProperty();

    protected ArrayList<BillableItem> billItems;

    public Invoice(String customerName, int creditCardNum, int creditCardExp, String UID) {
        setCustomerName(customerName);
        setCreditCardNum(creditCardNum);
        setCreditCardExp(creditCardExp);
        setUID(UID);
        billItems = new ArrayList<BillableItem>();
        
        //generateUID();
        
    }

    public ArrayList<BillableItem> getBillItems() {
        return billItems;
    }

    public String getUID() {
        return UID.get();
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public int getCreditCardNo() {
        return creditCardNum.get();
    }

    public int getCreditCardExp() {
        return creditCardExp.get();
    }

    // Should be generated beforehand
    
//    public void generateUID() {
//        UID.set(UUID.randomUUID().toString());
//    }
    
        public void setUID(String UID) {
        this.UID.set(UID);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setCreditCardNum(int creditCardNum) {
        this.creditCardNum.set(creditCardNum);
    }

    public void setCreditCardExp(int creditCardExp) {
        this.creditCardExp.set(creditCardExp);
=======
    private SimpleStringProperty invoiceUID;
    private SimpleStringProperty customerName;
    private SimpleStringProperty creditCardNum;
    private SimpleStringProperty creditCardExp;
    private SimpleDoubleProperty amountPaid;
    private ArrayList<BillableItem> billableItems;

    public Invoice(String invoiceUID, String customerName, String creditCardNum, String creditCardExp) {
        setInvoiceUID(invoiceUID);
        setCustomerName(customerName);
        setCreditCardNum(creditCardNum);
        setCreditCardExp(creditCardExp);
        setAmountPaid( 0 );
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
>>>>>>> Fergus
    }
}
