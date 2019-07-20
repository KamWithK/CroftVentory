/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import static croftventory.ObjectManager.StorageController.getDeviceList;
import static croftventory.ObjectManager.StorageController.getStudentList;
import croftventory.SpecialField.RegexField;
import croftventory.Types.Booking;
import croftventory.Types.Device;
import croftventory.Types.Student;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * 
 * Defines the dialog used for adding a bookings to the database
 * 
 * @author kamron
 * 
 */

public class AddBookingDialog extends Dialog<Booking> {
    //Create ButtonType's for creating a new device and canceling out of the dialog
    private ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    
    
    // Creates RegexField for user quantity input
    // RegexFields themselves validate text
    // Using provided regular expression
    // Combobox and DatePicker's are used to allow limitations of what can be inputed
    private ComboBox<Student> studentCombo = new ComboBox(getStudentList());
    private ComboBox<Device> deviceCombo = new ComboBox(getDeviceList());
    private TextField quantityField = new RegexField("[^0-9]*");
    private DatePicker duePicker = new DatePicker();
    
    // Custom binding to define whether selected date is valid
    BooleanBinding dateBinding = Bindings.createBooleanBinding(() -> {
           LocalDate min = LocalDate.now();
           LocalDate chosen = duePicker.getValue();
           
           // Disable if no date has been chosen or if date chosen predates the current date
           return (chosen == null || (chosen.isBefore(min)));
    }, duePicker.valueProperty());
    
    // Custom binding to define whether selected quantity is valid
    BooleanBinding quantityBinding = Bindings.createBooleanBinding(() -> {
        // Note, regardless of why quantityInt returns 0, it always indicates a failure
        // As zero or no-entered value both should yeld in returning true
        int quantityInt = tryParseInt(quantityField.getText());
        
        if (deviceCombo.getValue() != null && quantityInt != 0) {
            return (quantityInt > deviceCombo.getValue().getRemaining());
        } else return true;
    }, quantityField.textProperty(), deviceCombo.valueProperty());
    
    public AddBookingDialog() {
        // Sets the window's basic attributes
        setTitle("Add Booking Into System");
        setHeaderText("Booking Information");
        
        // Creates the grid pane to place everything onto
        GridPane inputGrid = new GridPane();
        
        // Creates indicator labels to show users what to enter into each textfield
        Label studentLabel = new Label("Student:");
        Label deviceLabel = new Label("Device:");
        Label quantityLabel = new Label("Quantity:");
        Label dueLabel = new Label("Due On: ");
        
        // Adds all elements to the inputGrid
        inputGrid.add(studentLabel, 0, 0);
        inputGrid.add(studentCombo, 1, 0);
        inputGrid.add(deviceLabel, 0, 1);
        inputGrid.add(deviceCombo, 1, 1);
        inputGrid.add(quantityLabel, 0, 2);
        inputGrid.add(quantityField, 1, 2);
        inputGrid.add(dueLabel, 0, 3);
        inputGrid.add(duePicker, 1, 3);
        
        getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
        getDialogPane().setContent(inputGrid);
        
        // Set bindings
        setBindings();
        
        setResultConverter(button -> {
            // TextField's produce strings, so they must be parsed into numeric datatypes
            // Storing value into variable avoids having to parse the integer twice
            // Note, normal srting to integer parsing method may be used as input has already been verified
            if (button == createButton) {
                return new Booking(studentCombo.getValue().getStrID(), deviceCombo.getValue().getLngID(), Integer.parseInt(quantityField.getText()), LocalDate.now(), duePicker.getValue(), false);
            } else return null;
        });
    }
    
    private void setBindings() {
        // Method for finding whether all input fields are filled out
        // As well as whether they meet a set criteria
        BooleanBinding showButtonBinding = quantityField.textProperty().isEmpty()
                .or(studentCombo.valueProperty().isNull()
                .or(deviceCombo.valueProperty().isNull())
                .or(quantityBinding)
                .or(dateBinding));
        
        // Sets the previous boolean binding to be used on this dialog
        getDialogPane().lookupButton(createButton).disableProperty().bind(showButtonBinding);
    }
    
    private int tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
