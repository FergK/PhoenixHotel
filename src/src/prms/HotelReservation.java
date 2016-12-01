/* Change Log
10/24/2016  Fergus
    Change the type of startDate and endDate to LocalDate
    Added bill field
 */
package prms;


import javafx.beans.property.SimpleStringProperty;

public class HotelReservation {

    private  SimpleStringProperty guestName = new SimpleStringProperty();
    private  SimpleStringProperty roomNumber = new SimpleStringProperty();
    private  SimpleStringProperty startDate = new SimpleStringProperty();
    private  SimpleStringProperty endDate = new SimpleStringProperty();
    private  SimpleStringProperty children = new SimpleStringProperty();
    private  SimpleStringProperty adults = new SimpleStringProperty();
    private Invoice bill;

    public HotelReservation(String roomNumber, String adults, String children, String startDate, String endDate, String guestName) {
        setRoomNumber(roomNumber);
        setRoomAdults(adults);
        setRoomChildren(children);
        setStartDate(startDate);
        setEndDate(endDate);
        setGuestName(guestName);
    }
    
    public String getGuestName(){
        return guestName.get();
    }
    
    public String getRoomNumber(){
        return roomNumber.get();
    }
    
    public String getRoomAdults(){
        return adults.get();
    }
    
    public String getRoomChildrem(){
        return children.get();
    }
    
    public String getStartDate(){
        return startDate.get();
    }
    
    public String getEndDate(){
        return endDate.get();
    }    
    public void setGuestName(String name){
        this.guestName.set(name);
    }
    
    public void setRoomNumber(String number){
        this.roomNumber.set(number);
    }
    
    public void setRoomAdults(String adults){
        this.adults.set(adults);
    }
    
    public void setRoomChildren(String children){
        this.children.set(children);
    }
    
    public void setStartDate(String date){
        this.startDate.set(date);
    }
    
    public void setEndDate(String date){
        this.endDate.set(date);
    }
    
    @Override
    public String toString() {
        return "Guest Name: " + getGuestName()
            + "\nStart Date: " + getStartDate()
            + "\nEnd Date: " + getEndDate()
            + "\nRoom Number: " + getRoomNumber();
    }
    
}
