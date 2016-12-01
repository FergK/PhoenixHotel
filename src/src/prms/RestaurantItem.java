package prms;

import java.text.DecimalFormat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AndrewAn
 */

// TODO
public class RestaurantItem {

    private SimpleStringProperty itemName = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty description = new SimpleStringProperty();

    public RestaurantItem(String itemName, double price, String description) {
        setItemName(itemName);
        setPrice(price);
        setDescription(description);
    }

    public String getItemName() {
        return itemName.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getDescription() {
        return description.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

}