/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import javafx.beans.property.*;

/**
 *
 * Defines the Student object
 * 
 * @author kamron
 * 
 */
public class Student {
    // Properties
    // These are used over variables as they can utilize events
    // Additionally they are useful for the TableView
    private SimpleStringProperty StrName = new SimpleStringProperty();
    private SimpleStringProperty StrClass = new SimpleStringProperty();
    private SimpleStringProperty StrEmail = new SimpleStringProperty();
    private SimpleIntegerProperty IntID = new SimpleIntegerProperty();
    
    // Initialiser
    public Student(String name, String _class, String email, int ID) {
        this.StrName.set(name);
        this.StrClass.set(_class);
        this.StrEmail.set(email);
        this.IntID.set(ID);
    }
    
    // Getters
    // Getters which return properties
    // Useful for UI elements
    public StringProperty nameProperty() { return StrName; }
    public StringProperty classProperty() { return StrClass; }
    public StringProperty emailProperty() { return StrEmail; }
    public IntegerProperty IDProperty() { return IntID; }
    
    //Getters for variables
    //Useful for validation and other purposes
    public String getStrName() { return StrName.get(); }
    public String getStrClass() { return StrClass.get(); }
    public String getStrEmail() { return StrEmail.get(); }
    public Integer getIntID() { return IntID.get(); }
    
   // Setters
   public void setStrName(String value) { StrName.set(value); }
   public void setStrClass(String value) { StrClass.set(value); }
   public void setStrEmail(String value) { StrEmail.set(value); }
   // Note, ID is read only, and so there is no setter for the property
}