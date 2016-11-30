/* Change Log
10/24/2016  Fergus
    Changed the CCNum and CCExp types
    Added the arraylist of BillableItem
 */
package prms;

import java.util.ArrayList;
import java.util.UUID;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AndrewAn
 */
public class Invoice {

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
    }
}
