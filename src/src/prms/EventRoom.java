
package prms;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class EventRoom {
    
    private SimpleStringProperty eventRoomName = new SimpleStringProperty("");
    private SimpleStringProperty companyName = new SimpleStringProperty();
    private SimpleDoubleProperty eventRoomPrice = new SimpleDoubleProperty();
    private SimpleIntegerProperty eventRoomMaxCapacity = new SimpleIntegerProperty();
    private SimpleBooleanProperty hasStage = new SimpleBooleanProperty();
    private SimpleBooleanProperty hasAudioVisual = new SimpleBooleanProperty();
    private SimpleStringProperty dateReserved = new SimpleStringProperty("");


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
