/* Change Log
10/24/2016  Fergus
    Changed the CCNum and CCExp types
    Added the arraylist of BillableItems
 */
package prms;

import java.time.YearMonth;
import java.util.ArrayList;

/**
 *
 * @author AndrewAn
 */
public class Invoice {

    private String customerName;
    private String CCNum;
    private YearMonth CCExp;
    private ArrayList<BillableItems> items;

    public Invoice(String customerName, String CCNum, YearMonth CCExp) {
        this.customerName = customerName;
        this.CCNum = CCNum;
        this.CCExp = CCExp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String CCNum() {
        return CCNum;
    }

    public YearMonth CCExp() {
        return CCExp;
    }

    public void setNames(String customerName) {
        this.customerName = customerName;
    }

    public void setCCNum(String CCNum) {
        this.CCNum = CCNum;
    }

    public void setCCExp(YearMonth CCExp) {
        this.CCExp = CCExp;
    }
}
