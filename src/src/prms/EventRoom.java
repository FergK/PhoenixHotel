/* Change Log
10/26/2016  Fergus
    Renamed some variables and functions for clarity
 */
package prms;

public class EventRoom {

    private String roomName;
    private double price;
    private int maxCapacity;
    private Boolean hasStage;
    private Boolean hasAudioVisual;

    public EventRoom(String roomName, double price, int maxCapacity, Boolean hasStage, Boolean hasAudioVisual) {
        this.roomName = roomName;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.hasStage = hasStage;
        this.hasAudioVisual = hasAudioVisual;
    }

    public String getRoomName() {
        return roomName;
    }

    public double getPrice() {
        return price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean getHasStage() {
        return hasStage;
    }

    public boolean getHasAudioVisual() {
        return hasAudioVisual;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setHasStage(Boolean hasStage) {
        this.hasStage = hasStage;
    }

    public void setHasAudioVisual(Boolean hasAudioVisual) {
        this.hasAudioVisual = hasAudioVisual;
    }

    public String toString() {
        return "Event Room Name: " + this.getRoomName()
                + "\nPrice: $" + this.getPrice()
                + "\nmaxCapacity: " + this.getMaxCapacity()
                + "\nHas Stage: " + this.getHasStage()
                + "\nHas Audio/Visual Equipment: " + this.getHasAudioVisual();
    }

}
