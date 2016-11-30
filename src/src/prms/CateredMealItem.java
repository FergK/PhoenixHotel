/* Change Log
10/24/2016  Fergus
    Fixed capitalization and names of some variables
 */

package prms;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AndrewAn
 */
public class CateredMealItem {
    
    private SimpleStringProperty mealName = new SimpleStringProperty();
    private SimpleDoubleProperty pricePerSeat = new SimpleDoubleProperty();
    private SimpleStringProperty mealDescription = new SimpleStringProperty();
    
    
    public CateredMealItem(String itemName, double price, String description) {
        setMealName(itemName);
        setPricePerSeat(price);
        setMealDescription(description);
    }

    public String getMealName() {
        return mealName.get();
    }

    public double getPricePerSeat() {
        return pricePerSeat.get();
    }

    public String getMealDescription() {
        return mealDescription.get();
    }

    public void setMealName(String mealName) {
        this.mealName.set(mealName);
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat.set(pricePerSeat);
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription.set(mealDescription);
    }
}
