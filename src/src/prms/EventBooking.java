
/* Change Log
10/24/2016  Fergus
    Change the type of startDate and endDate to LocalDate
    Added bill field
 */

package prms;

// TODO This entire class

import java.time.LocalDate;

public class EventBooking {

    private LocalDate startDate;
    private LocalDate endDate;
    private Invoice bill;

    public EventBooking(LocalDate startDate, LocalDate endDate, Invoice bill) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.bill = bill;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

//	public void addInvoice( EventRoom m){
//		Invoice n= new Invoice(m.getRoomNumber());
//	}
}
