
public class EventRoom {
	private String roomNumber;
	private double price;
	private int party;
	private int hasStage;
	private int hasAudioVisual;
	
	public EventRoom(String room, double price, int party, int hasStage, int hasAudioVisual){
		this.roomNumber= room;
		this.price= price;
		this.party= party;
		this.hasStage= hasStage;
		this.hasAudioVisual= hasAudioVisual;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public double getPrice() {
		return price;
	}

	public int getParty() {
		return party;
	}

	public boolean getHasStage() {
		if(this.hasStage==1){
			return true;
		}
		return false;
	}

	public boolean getHasAudioVisual() {
		if(this.hasAudioVisual==1){
			return true;
		}
		return false;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setParty(int party) {
		this.party = party;
	}

	public void setHasStage(int hasStage) {
		this.hasStage = hasStage;
	}

	public void setHasAudioVisual(int hasAudioVisual) {
		this.hasAudioVisual = hasAudioVisual;
	}
	
	public String toString(){
		return "Room Number: "+this.getRoomNumber()+"\nPrice: $"+this.getPrice()+"\nParty: "+this.getParty()+"Has Stage: "+this.getHasStage()+"Has Audio/Visual Equipment: "+this.getHasAudioVisual();
	}

	
}
