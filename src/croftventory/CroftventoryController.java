/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import croftventory.Types.Device;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * Handles validation and everything UI related
 * 
 * @author kamron
 * 
 */

public class CroftventoryController implements Initializable {
    
    @FXML private DatePicker DueSearch;
    @FXML private TextField StudentSearch;
    @FXML private TextField DeviceSearch;
    
    SimpleStringProperty studentSearchStr = new SimpleStringProperty();
    SimpleStringProperty deviceSearchStr = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> searchDue = new SimpleObjectProperty<>();
    
    // Handle button presses
    @FXML
    private void handleExtendButton(ActionEvent event) {
        System.out.println("Extend Loan");
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
        System.out.println("Return the Device");
    }
    
    @FXML
    private void handleAddDeviceButton(ActionEvent event) {
        // Create a seperate dialog with input fields
        AddDeviceDialog dialog = new AddDeviceDialog();
        
        // Optional is used incase the dialog was canceled
        // Checks whether the Device is present or not
        // If so add it to the list
        Optional<Device> result = dialog.showAndWait();
        if (result.isPresent()) {
        }
    }
    
    @FXML
    private void handleImportStudentsButton(ActionEvent event) {
        System.out.println("Import Students from XML");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // The following events with lambda expressions detect changes in input
        // For the search fields
        DueSearch.valueProperty().addListener((newValue, oldValue, observable) -> {
            System.out.println(newValue.getValue().toString());
        });
        StudentSearch.textProperty().addListener((newValue, oldValue, observable) -> {
            System.out.println(newValue.getValue());
        });
        DeviceSearch.textProperty().addListener((newValue, oldValue, observable) -> {
            System.out.println(newValue.getValue());
        });
    }
    
}
