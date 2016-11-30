/* Change Log
11/11/2016  Fergus
    Converted fields to simple* type
10/24/2016  Fergus
    Renamed some variables and functions for clarity
 */
package prms;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EventRoom {

    private SimpleStringProperty roomName = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleIntegerProperty maxCapacity = new SimpleIntegerProperty();
    private SimpleBooleanProperty hasStage = new SimpleBooleanProperty();
    private SimpleBooleanProperty hasAudioVisual = new SimpleBooleanProperty();

    public EventRoom(String roomName, double price, int maxCapacity, Boolean hasStage, Boolean hasAudioVisual) {
        setRoomName(roomName);
        setPrice(price);
        setMaxCapacity(maxCapacity);
        setHasStage(hasStage);
        setHasAudioVisual(hasAudioVisual);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getMaxCapacity() {
        return maxCapacity.get();
    }

    public boolean getHasStage() {
        return hasStage.get();
    }

    public boolean getHasAudioVisual() {
        return hasAudioVisual.get();
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity.set(maxCapacity);
    }

    public void setHasStage(Boolean hasStage) {
        this.hasStage.set(hasStage);
    }

    public void setHasAudioVisual(Boolean hasAudioVisual) {
        this.hasAudioVisual.set(hasAudioVisual);
    }

    public String toString() {
        return "Event Room Name: " + this.getRoomName()
                + "\nPrice: $" + this.getPrice()
                + "\nmaxCapacity: " + this.getMaxCapacity()
                + "\nHas Stage: " + this.getHasStage()
                + "\nHas Audio/Visual Equipment: " + this.getHasAudioVisual();
    }

}
