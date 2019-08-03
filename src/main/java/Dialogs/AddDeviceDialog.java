/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import SpecialField.RegexField;
import Types.Device;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;

/**
 * 
 * Defines the dialog used for adding a device to the database
 * 
 * @author kamron
 * 
 */

public class AddDeviceDialog extends Dialog<Device> {
    //Create ButtonType's for creating a new device and canceling out of the dialog
    private ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    
    public AddDeviceDialog() {
        // Sets the window's basic attributes
        setTitle("Add Device Into System");
        setHeaderText("Device Information");
        
        // Creates the grid pane to place everything onto
        GridPane inputGrid = new GridPane();
        
        // Creates indicator labels to show users what to enter into each TextField
        Label nameLabel = new Label("Name:");
        Label quantityLabel = new Label("Quantity:");
        Label valueLabel = new Label("Value (price): $");
        
        // Creates RegexFields for user input
        // RegexFields themselves validate text
        // Using provided regular expression
        // Note the input value is limited to integers for simplicity
        TextField nameField = new TextField();
        TextField quantityField = new RegexField("[^0-9]*");
        TextField valueField = new RegexField("[^0-9]*");
        
        // Adds all elements to the inputGrid
        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(quantityLabel, 0, 1);
        inputGrid.add(quantityField, 1, 1);
        inputGrid.add(valueLabel, 0, 2);
        inputGrid.add(valueField, 1, 2);
        
        getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
        getDialogPane().setContent(inputGrid);
        
        // Method for finding whether all input fields are filled out
        BooleanBinding showButtonBinding = nameField.textProperty().isEmpty()
                .or(quantityField.textProperty().isEmpty()
                .or(valueField.textProperty().isEmpty()));
        
        // Sets the previous boolean binding to be used on this dialog
        getDialogPane().lookupButton(createButton).disableProperty().bind(showButtonBinding);
        
        setResultConverter(button -> {
            if (button == createButton) {
                // TextField's produce strings, so they must be parsed into numeric data types
                return new Device(nameField.getText(), Integer.parseInt(quantityField.getText()),new BigDecimal(valueField.getText()));
            } else return null;
        });
    }
}
