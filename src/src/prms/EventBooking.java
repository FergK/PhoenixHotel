package prms;

public class EventBooking {
	private int startDate;
	private int endDate;
	
	public EventBooking(int startDate, int endDate){
		this.startDate= startDate;
		this.endDate= endDate;
		
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
	
//	public void addInvoice( eventRoom m){
//		Invoice n= new Invoice(m.getRoomNumber());
//	}

}
