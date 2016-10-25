
/* Change Log
10/26/2016  Fergus
    Change the type of startDate and endDate to Boolean
    Added bill field
 */
package prms;

import java.util.Date;

public class HotelReservation {

    private int adults;
    private int children;
    private Date startDate;
    private Date endDate;
    private Invoice bill;

    public HotelReservation(int adults, int children, Date startDate, Date endDate, Invoice bill) {
        this.adults = adults;
        this.children = children;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bill = bill;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
