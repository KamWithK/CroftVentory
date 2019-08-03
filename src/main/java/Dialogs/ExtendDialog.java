/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import java.time.LocalDate;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;

/**
 * 
 * Defines the dialog used for extending a loan
 * 
 * @author kamron
 * 
 */

public class ExtendDialog extends Dialog<LocalDate> {
    //Create ButtonType's for changing the date or canceling out of the dialog
    private ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    
    public ExtendDialog() {
        // Sets the window's basic attributes
        setTitle("Modify Booking Date In System");
        setHeaderText("New Due Date");
        
        DatePicker valuePicker = new DatePicker();
        
        getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
        getDialogPane().setContent(valuePicker);
        
        // Sets the previous boolean binding to be used on this dialog
        getDialogPane().lookupButton(createButton).disableProperty().bind(valuePicker.valueProperty().isNull());
        
        // Return the new date
        setResultConverter(button -> {
            if (button == createButton) { return valuePicker.getValue(); } else return null;
        });
    }
}
