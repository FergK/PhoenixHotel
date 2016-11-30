/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author youngvz
 */
public class Order {
    
    private SimpleStringProperty orderType = new SimpleStringProperty("");
    private SimpleStringProperty roomName = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty time = new SimpleStringProperty("");
    private SimpleStringProperty status = new SimpleStringProperty("");
    
    public Order( String orderType, String roomName, double price, String date, String time, String status ) {
        setOrderType(orderType);
        setRoomName(roomName);
        setPrice(price);
        setDate(date);
        setTime(time);
        setStatus(status);
        
    }

    Order() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setOrderType(String orderType){
        this.orderType.set(orderType);
    }
    
    public void setRoomName(String roomName){
        this.roomName.set(roomName);
    }
    
    public void setPrice(double price){
        this.price.set(price);
    }
    
    public void setDate(String date){
        this.date.set(date);
    }
    
    public void setTime(String time){
        this.time.set(time);
    }
    
    public void setStatus(String status){
        this.status.set(status);
    }
    
    public String getOrderType(){
        return orderType.get();
    }
     public String getRoomName(){
        return roomName.get();
    }
    
    public double getPrice(){
        return price.get();
    }
    
    public String getDate(){
        return date.get();
    }
    
    public String getTime(){
        return time.get();
    }
    
    public String getStatus(){
        return status.get();
    }
    
    
    
}
