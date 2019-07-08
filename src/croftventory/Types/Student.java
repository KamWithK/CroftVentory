/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.Types;

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
    private SimpleIntegerProperty IntDemeritPoints = new SimpleIntegerProperty();
    private SimpleStringProperty StrID = new SimpleStringProperty();
    
    // Initialisers
    // Uses polymorphisem to handle storing and retrieving data from database
    // To avoid having to enter demerit points when non-existant
    public Student(String name, String _class, String email, String ID) {
        this.StrName.set(name);
        this.StrClass.set(_class);
        this.StrEmail.set(email);
        IntDemeritPoints.set(0);
        this.StrID.set(ID);
    }
    
    public Student(String name, String _class, String email, Integer demeritPoints, String ID) {
        this.StrName.set(name);
        this.StrClass.set(_class);
        this.StrEmail.set(email);
        this.IntDemeritPoints.set(demeritPoints);
        this.StrID.set(ID);
    }
    
    // Getters
    // Getters which return properties
    // Useful for UI elements
    public StringProperty nameProperty() { return StrName; }
    public StringProperty classProperty() { return StrClass; }
    public StringProperty emailProperty() { return StrEmail; }
    public StringProperty IDProperty() { return StrID; }
    
    //Getters for variables
    //Useful for validation and other purposes
    public String getStrName() { return StrName.get(); }
    public String getStrClass() { return StrClass.get(); }
    public String getStrEmail() { return StrEmail.get(); }
    public Integer getIntDemeritPoints() { return IntDemeritPoints.get(); }
    public String getStrID() { return StrID.get(); }
    
    // Setters
    public void setStrName(String value) { StrName.set(value); }
    public void setStrClass(String value) { StrClass.set(value); }
    public void setStrEmail(String value) { StrEmail.set(value); }
    public void setIntDemeritPoints(int value) { IntDemeritPoints.set(value); }
    // Note, ID is read only, and so there is no setter for the property
    
    // Functions split full name into first and second name
    // By seperating the path before and after a space
    public String getFName() {
        return StrName.get().split(" ", 2)[0];
    }
    
    public String getSName() {
        return StrName.get().split(" ", 2)[1];
    }
}
