/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
    

    


public class tempHotelRoom {

    private SimpleStringProperty hotelRoomNumber = new SimpleStringProperty("");
    private SimpleDoubleProperty hotelRoomPrice = new SimpleDoubleProperty();
    private SimpleBooleanProperty allowsSmoking = new SimpleBooleanProperty();
    private SimpleIntegerProperty numberOfBeds = new SimpleIntegerProperty();
    private SimpleBooleanProperty allowsPets = new SimpleBooleanProperty();
    private SimpleBooleanProperty allowsDisability = new SimpleBooleanProperty();

    public tempHotelRoom(String roomNumber, double price, int beds, Boolean allowsPets, Boolean disableAccessible, Boolean allowsSmoking) {
        setRoomNumber(roomNumber);
        setRoomPrice(price);
        setNumberOfBeds(beds);
        setPetProperty(allowsPets);
        setDisabilityProperty(disableAccessible);
        setSmokingProperty(allowsSmoking);
    }
    
    public void setRoomNumber(String number){
        hotelRoomNumber.set(number);
    }
    
    public void setRoomPrice(double price){
        hotelRoomPrice.set(price);
    }
    
    public void setSmokingProperty( Boolean smoking ) {
        allowsSmoking.set(smoking);
    }
    
    public void setNumberOfBeds( int number){
        numberOfBeds.set(number);
    }
    
    public void setPetProperty( Boolean pets){
        allowsPets.set(pets);
    }
    
    public void setDisabilityProperty( Boolean disability){
        allowsDisability.set(disability);
    }
    
}