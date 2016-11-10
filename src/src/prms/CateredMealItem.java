/* Change Log
10/24/2016  Fergus
    Fixed capitalization and names of some variables
 */

package prms;

/**
 *
 * @author AndrewAn
 */
public class CateredMealItem {

    private String mealName;
    private double pricePerSeat;
    private String mealDescription;

    public CateredMealItem(String mealName, double pricePerSeat, String mealDescription) {
        this.mealName = mealName;
        this.pricePerSeat = pricePerSeat;
        this.mealDescription = mealDescription;
    }

    public String getMealName() {
        return mealName;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }
}
