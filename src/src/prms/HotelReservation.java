/* Change Log
11/29/2016 Fergus
    Updated date format
    Removed unnecessary imports
    Merged into final project
11/01/2016 Viraj
    Converted fields to simple* type
10/24/2016  Fergus
    Change the type of startDate and endDate to LocalDate
    Added bill field
 */
package prms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HotelReservation {

    
    private SimpleStringProperty roomNumber = new SimpleStringProperty();
    private SimpleStringProperty startDate = new SimpleStringProperty();
    private SimpleStringProperty endDate = new SimpleStringProperty();
    private SimpleIntegerProperty children = new SimpleIntegerProperty();
    private SimpleIntegerProperty adults = new SimpleIntegerProperty();
    private Invoice bill;

    public HotelReservation(String roomNumber, int adults, int children, String startDate, String endDate) {
        setRoomNumber(roomNumber);
        setRoomAdults(adults);
        setRoomChildren(children);
        setStartDate(startDate);
        setEndDate(endDate);
    }
    
    public String getRoomNumber(){
        return roomNumber.get();
    }
    
    public int getRoomAdults(){
        return adults.get();
    }
    
    public int getRoomChildrem(){
        return children.get();
    }
    
    public String getStartDate(){
        return startDate.get();
    }
    
    public String getEndDate(){
        return endDate.get();
    }    
    
    public void setRoomNumber(String number){
        this.roomNumber.set(number);
    }
    
    public void setRoomAdults(int adults){
        this.adults.set(adults);
    }
    
    public void setRoomChildren(int children){
        this.children.set(children);
    }
    
    public void setStartDate(String date){
        this.startDate.set(date);
    }
    
    public void setEndDate(String date){
        this.endDate.set(date);
    }
    
    
}