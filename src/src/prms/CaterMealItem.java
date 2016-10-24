/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

/**
 *
 * @author AndrewAn
 */
public class CaterMealItem {
   private String MealName;
   private double PricePerSeat;
   private String CaterDescription;
   
   public CaterMealItem(String MealName, double PricePerSeat, String CaterDescription){
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
