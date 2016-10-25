/* Change Log
10/24/2016  Fergus
    Removed the startDate and endDate fields since those will be in the reservation
    Change the type of hasPets, disabilityAccessible, allowsSmoking to Boolean
    Renamed some variables and functions for clarity
    Added the inventory arraylist field.
 */
package prms;

import java.util.ArrayList;
import java.util.Date;

public class HotelRoom {

    private String roomNumber;
    private double price;
    private int beds;
    private Boolean allowsPets;
    private Boolean disabilityAccessible;
    private Boolean allowsSmoking;
    private ArrayList<InventoryItem> Inventory;
    private Date dateLastCleaned;

    public HotelRoom(String roomNumber, int price, int beds, Boolean allowsPets, Boolean disableAccessible, Boolean allowsSmoking) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.beds = beds;
        this.allowsPets = allowsPets;
        this.disabilityAccessible = disableAccessible;
        this.allowsSmoking = allowsSmoking;
    }

    public void setAllowsSmoking(Boolean allowsSmoking) {
        this.allowsSmoking = allowsSmoking;
    }

    public double getPrice() {
        return price;
    }

    public int getBeds() {
        return beds;
    }

    public boolean getAllowsPets() {
        return allowsPets;
    }

    public boolean getDisabilityAccessible() {
        return disabilityAccessible;
    }

    public boolean getAllowsSmoking() {
        return allowsSmoking;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public void setAllowsPets(Boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    public void setDisabilityAccessible(Boolean disabilityAccessible) {
        this.disabilityAccessible = disabilityAccessible;
    }

    @Override
    public String toString() {
        return "Hotel room name: $" + this.roomNumber
                + "\nprice: $" + this.price
                + "\n# of beds: " + this.beds
                + "\nDisability Accessible: " + this.disabilityAccessible
                + "\nAllows Pets: " + this.allowsPets
                + "\nAllows Smoking: " + this.allowsSmoking;
    }

}
