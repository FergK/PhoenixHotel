

package prms;

/**
 *
 * @author AndrewAn
 */
public class CateredMealItem {
   private String MealName;
   private double PricePerSeat;
   private String CaterDescription;
   
   public CateredMealItem(String MealName, double PricePerSeat, String CaterDescription){
       this.MealName=MealName;
       this.PricePerSeat=PricePerSeat;
       this.CaterDescription=CaterDescription;
   }
   public String getMealName(){
       return MealName;
   }
   public double getPricePerSeat(){
       return PricePerSeat;
   }
   public String getCaterDescription(){
       return CaterDescription;
   }
   public void setMealName(String MealName){
       this.MealName=MealName;
   }
   public void setPricePerSeat(double PricePerSeat){
       this.PricePerSeat=PricePerSeat;
   }
   public void setCaterDescription(String CaterDescription){
       this.CaterDescription=CaterDescription;
   }
}
