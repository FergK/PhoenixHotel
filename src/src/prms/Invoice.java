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
public class Invoice {

    private String names;
    private int CCNum;
    private int CCExp;
    
    public Invoice(String names, int CCNum, int CCExp){
        this.names=names;
        this.CCNum=CCNum;
        this.CCExp=CCExp;
    }
    public String getNames(){
        return names;
    }
    public int CCNum(){
        return CCNum;
    }
    public int CCExp(){
        return CCExp;
    }
    public void setNames(String names){
        this.names=names;
    }
    public void setCCNum(int CCNum){
        this.CCNum=CCNum;
    }
    public void setCCExp(int CCExp){
        this.CCExp=CCExp;
    }
}
