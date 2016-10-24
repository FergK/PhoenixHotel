package prms;

public class EventBooking {

    private int startDate;
    private int endDate;
    private Invoice bill;

    public EventBooking(int startDate, int endDate, Invoice bill) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.bill = bill;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

//	public void addInvoice( EventRoom m){
//		Invoice n= new Invoice(m.getRoomNumber());
//	}
}
