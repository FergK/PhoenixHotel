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
public class BillableItems {
    private String BillableName;
    private double price;
    private int date;
    private int time;
    
    public BillableItems(String BillableName, double price, int date, int time){
        this.BillableName=BillableName;
        this.price=price;
        this.date=date;
        this.time=time;
    }
    public String getBillableName(){
        return BillableName;
    }
    public double getPrice(){
        return price;
    }
    public int getDate(){
        return date;
    }
    public int getTime(){
        return time;
    }
    public void setBillableName(String BillableName){
        this.BillableName=BillableName;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setDate(int date){
        this.date=date;
    }
    public void setTime(int time){
        this.time=time;
    }
    
}
