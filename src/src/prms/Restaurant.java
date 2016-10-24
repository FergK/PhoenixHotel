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
public class Restaurant {
    private int numofTable;
    
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
