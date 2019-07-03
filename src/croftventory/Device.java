/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import javafx.beans.property.*;

/**
 *
 * Defines the Device object
 * 
 * @author kamron
 * 
 */
public class Device {
    // Properties
    // These are used over variables as they can utilize events
    private SimpleStringProperty StrName = new SimpleStringProperty();
    private SimpleIntegerProperty IntQuantity = new SimpleIntegerProperty();
    private SimpleIntegerProperty IntValue = new SimpleIntegerProperty();
    private SimpleIntegerProperty IntID = new SimpleIntegerProperty();
    
    // Initialiser
    public Device(String name, int quantity, int value, int ID) {
        this.StrName.set(name);
        this.IntQuantity.set(quantity);
        this.IntValue.set(value);
        this.IntID.set(ID);
    }
    
    // Getters
    // Getters which return properties
    // Useful for UI elements
    public StringProperty nameProperty() { return StrName; }
    public IntegerProperty QuantityProperty() { return IntQuantity; }
    public IntegerProperty ValueProperty() { return IntValue; }
    public IntegerProperty IDProperty() { return IntID; }
    
    //Getters for variables
    //Useful for validation and other purposes
    public String getStrName() { return StrName.get(); }
    public Integer getIntQuantity() { return IntQuantity.get(); }
    public Integer getIntValue() { return IntValue.get(); }
    public Integer getIntID() { return IntID.get(); }
    
   // Setters
   public void setStrName(String value) { StrName.set(value); }
   public void setIntQuantity(int value) { IntQuantity.set(value); }
   public void setIntValue(int value) { IntValue.set(value); }
   // Note, ID is read only, and so there is no setter for the property
}
