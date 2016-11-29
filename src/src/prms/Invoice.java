/* Change Log
10/24/2016  Fergus
    Changed the CCNum and CCExp types
    Added the arraylist of BillableItems
 */
package prms;

import java.time.YearMonth;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AndrewAn
 */
public class Invoice {

    private SimpleIntegerProperty UID = new SimpleIntegerProperty();
    private SimpleStringProperty customerName = new SimpleStringProperty();
    private SimpleIntegerProperty creditCardNum = new SimpleIntegerProperty();
    private SimpleIntegerProperty creditCardExp = new SimpleIntegerProperty();
    
    private ArrayList<BillableItems> billItems;
    
    public Invoice(String customerName, int creditCardNum, int creditCardExp) {
        setCustomerName(customerName);
        setCreditCardNum(creditCardNum);
        setCreditCardExp(creditCardExp);
        billItems = new ArrayList<BillableItems>();
    }
    
        public ArrayList<BillableItems> getBillItems() {
        return billItems;
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

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
    
    public void setCreditCardNum(int creditCardNum){
        this.creditCardNum.set(creditCardNum);
    }
    
        public void setCreditCardExp(int creditCardExp){
        this.creditCardExp.set(creditCardExp);
    }
}
