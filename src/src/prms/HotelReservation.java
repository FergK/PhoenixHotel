/* Change Log
10/24/2016  Fergus
    Change the type of startDate and endDate to LocalDate
    Added bill field
 */
package prms;

import java.time.LocalDate;

public class HotelReservation {

    private int adults;
    private int children;
    private LocalDate startDate;
    private LocalDate endDate;
    private Invoice bill;

    public HotelReservation(int adults, int children, LocalDate startDate, LocalDate endDate, Invoice bill) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
