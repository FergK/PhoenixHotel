/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prms;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {

    public SimpleStringProperty name = new SimpleStringProperty("");
    public SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    public SimpleIntegerProperty expectedQuantity = new SimpleIntegerProperty();
    public SimpleBooleanProperty isConsumable = new SimpleBooleanProperty();

    /* Creates an InventoryItem object
	 * to be sent to the database.
	 * 
	 * @precondition:
	 * all relevant InventoryItem 
	 * attributes like Name, Quantity,
	 * etc. have to be provided as parameters.
	 * 
	 * @postcondition:
	 * InventoryItem object is created and sent to 
	 * the UsedInventory.
     */

    public InventoryItem(String Name, int Quantity, int ExpectedQuantity, boolean IsConsumable) {
        setName(Name);
        setQuantity(Quantity);
        setExpectedQuantity(ExpectedQuantity);
        setIsConsumable(IsConsumable);
    }
    
    public void setName( String name ) {
        this.name.set(name);
    }

    public void setQuantity( int quantity ) {
        this.quantity.set(quantity);
    }

    public void setExpectedQuantity( int expquantity ) {
        expectedQuantity.set(expquantity);
    }

    public void setIsConsumable( boolean isconsumable ) {
        isConsumable.set(isconsumable);
    }
    
    
    public String getName() {
        return name.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public int getExpectedQuantity() {
        return expectedQuantity.get();
    }

    public boolean getIsConsumable() {
        return isConsumable.get();
    }
}
