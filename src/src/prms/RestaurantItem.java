
package prms;

/**
 *
 * @author AndrewAn
 */
public class RestaurantItem {
    private String ItemName;
    private double price;
    private String Description;
    
    public RestaurantItem(String ItemName, double price, String Description){
        this.ItemName=ItemName;
        this.price=price;
        this.Description=Description;
    }
    public String getItemName(){
        return ItemName;
    }
    public double getPrice(){
        return price;
    }
    public String getDescription(){
        return Description;
    }
    public void setItemName(String ItemName){
        this.ItemName=ItemName;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setDescription(String Description){
        this.Description=Description;
    }
    
}
