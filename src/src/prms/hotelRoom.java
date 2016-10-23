

public class hotelRoom {
	private double price;
	private int beds;
	private int hasPets;
	private int disabilityAccessible;
	private int allowsSmoking;
	private int startDate;		//assumed to be unix timestamp in database//
	private int endDate;		//assumed to be unix timestamp in database//
	
	public hotelRoom(int price, int beds, int hasPets, int disableAccessible, int allowsSmoking,int startDate, int endDate ){
		this.price=price;
		this.beds=beds;
		this.hasPets=hasPets;
		this.disabilityAccessible= disableAccessible;
		this.allowsSmoking= allowsSmoking;
		this.startDate= startDate;
		this.endDate= endDate;
		
	}

	public boolean getAllowsSmoking() {
		if(this.allowsSmoking==1){
			return true;
		}
		return false;
	}

	public void setAllowsSmoking(int allowsSmoking) {
		this.allowsSmoking = allowsSmoking;
	}

	public double getPrice() {
		return price;
	}

	public int getBeds() {
		return beds;
	}

	public boolean hasPets(){
		if (hasPets==1){
			return true;
		}
		return false;
	}

	public boolean getDisabilityAccessible() {
		if(disabilityAccessible==1){
			return true;
		}
		return false;
	}

	public boolean allowsSmoking(){
		if(allowsSmoking==1){
			return true;
		}
		return false;
	}
	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public void setHasPets(int hasPets) {
		this.hasPets = hasPets;
	}

	public void setDisabilityAccessible(int disabilityAccessible) {
		this.disabilityAccessible = disabilityAccessible;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(int numberOfDays) {
		this.endDate = startDate + numberOfDays;
	}
	
	public String toString(){
		return "Room price: $"+this.getPrice()+"\n# of beds: "+this.beds+"\nDisability Accessible: "+this.getDisabilityAccessible()+"\nHas Pets: "+this.hasPets()+"\nAllows Smoking: "+this.allowsSmoking()+"\nStart Date: "+this.startDate+"End Date: "+this.endDate;
		
	}
	
}
