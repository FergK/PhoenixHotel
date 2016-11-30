/* Change Log
10/24/2016  Fergus
    Fixed capitalization and names of some variables
    Changed dat and time variable to single var of LocalDateTime
 */

package prms;

import java.time.LocalDateTime;

/**
 *
 * @author AndrewAn
 */
public class BillableItems {

    private String billableName;
    private double price;
    private LocalDateTime time;

    public BillableItems(String billableName, double price, LocalDateTime time) {
        this.billableName = billableName;
        this.price = price;
        this.time = time;
    }

    public String getBillableName() {
        return billableName;
    }

    public double getPrice() {
        return price;
    }
    
    public LocalDateTime getTime() {
        return time;
    }

    public void setBillableName(String billableName) {
        this.billableName = billableName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}