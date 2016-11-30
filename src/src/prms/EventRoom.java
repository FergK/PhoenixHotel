<<<<<<< HEAD

package prms;

import java.time.LocalDate;
import java.util.ArrayList;
=======
/* Change Log
11/11/2016  Fergus
    Converted fields to simple* type
10/24/2016  Fergus
    Renamed some variables and functions for clarity
 */
package prms;

>>>>>>> Fergus
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

<<<<<<< HEAD

=======
>>>>>>> Fergus
public class EventRoom {
    
    private SimpleStringProperty eventRoomName = new SimpleStringProperty("");
    private SimpleStringProperty companyName = new SimpleStringProperty();
    private SimpleDoubleProperty eventRoomPrice = new SimpleDoubleProperty();
    private SimpleIntegerProperty eventRoomMaxCapacity = new SimpleIntegerProperty();
    private SimpleBooleanProperty hasStage = new SimpleBooleanProperty();
    private SimpleBooleanProperty hasAudioVisual = new SimpleBooleanProperty();
    private SimpleStringProperty dateReserved = new SimpleStringProperty("");

<<<<<<< HEAD

    public EventRoom(String roomName, double price, int maxCapacity, Boolean hasStage, Boolean hasAudioVisual, String companyName, String dateReserved) {
        setRoomName(roomName);
        setRoomPrice(price);
        setEventRoomMaxCapacity(maxCapacity);
        setStage(hasStage);
        setAudioVisual(hasAudioVisual);
        setCompanyName(companyName);
        setDateReserved(dateReserved);
        
    }

    EventRoom(String roomName, double price, int maxCapacity, int bool, int bool0, String companyName, String dateReserved) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    EventRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getDateReserved(){
        return dateReserved.get();
    }

    public String getRoomName() {
        return eventRoomName.get();
    }

    public double getRoomPrice() {
        return eventRoomPrice.get();
    }

    public int getRoomMaxCapacity(){
        return eventRoomMaxCapacity.get();
    }
    
    public Boolean getStage(){
        return hasStage.get();
    }
    public Boolean getAudioVisual(){
        return hasAudioVisual.get();
    }
    
    public String getCompanyName(){
        return companyName.get();
    }
    
    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
=======
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
>>>>>>> Fergus
    }
    
    public void setDateReserved(String date){
        this.dateReserved.set(date);
    }
    
    public void setRoomName(String name){
        this.eventRoomName.set(name);
    }
    
    public void setRoomPrice(double price){
        this.eventRoomPrice.set(price);
    }
    
    public void setEventRoomMaxCapacity(int capacity){
        this.eventRoomMaxCapacity.set(capacity);
    }
    
    public void setStage(Boolean hasStage){
        this.hasStage.set(hasStage);
    }
    
    public void setAudioVisual(Boolean hasVisual){
        this.hasAudioVisual.setValue(hasVisual);
    }
    
    public String toString() {
        return "Event Room Name: " + getRoomName()
                + "\nPrice: $" + getRoomPrice()
                + "\nmaxCapacity: " + getRoomMaxCapacity()
                + "\nHas Stage: " + getStage()
                + "\nHas Audio/Visual Equipment: " + getAudioVisual()
                + "\ncompanyName: " + getCompanyName()
                + "\ndateReserved: " + getDateReserved();
    }
}
