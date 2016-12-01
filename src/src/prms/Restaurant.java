/* Change Log
10/24/2016  Fergus
    Changed the CCNum and CCExp types
    Added the arraylist of RestaurantItem and CateredMealItem
 */

package prms;

import java.util.ArrayList;
/**
 *
 * @author AndrewAn
 */
public class Restaurant {
    private int numofTable;
    private ArrayList<RestaurantItem> restaurantItems;
    private ArrayList<CateredMealItem> cateredMealItems;
    
    public Restaurant(int numofTable){
        this.numofTable=numofTable;
    }
    public int getnumofTable(){
        return numofTable;
    }
    public void setnumofTable(int numofTable){
        this.numofTable=numofTable;
    }
}
