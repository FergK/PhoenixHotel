
public class HotelReservation {
	private int adults;
	private int children;
	private int startDate;	//assuming date entered in database is a unix timestamp(epoch)//
	private int endDate;	//assuming date entered in database is a unix timestamp(epoch)//
	public HotelReservation(int adults, int children, int startDate, int endDate){
		this.adults= adults;
		this.children= children;
		this.startDate= startDate;
		this.endDate= endDate;
	}
	public int getAdults() {
		return adults;
	}
	public int getChildren() {
		return children;
	}
	public int getStartDate() {
		return startDate;
	}
	public int getEndDate() {
		return endDate;
	}
	public void setAdults(int adults) {
		this.adults = adults;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
}
