/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import Types.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static ObjectManager.StorageController.getDeviceList;

/**
 * 
 * Defines the device dialog used for listing devices in the database
 * 
 * @author kamron
 * 
 */

public class DeviceListDialog extends Dialog<Device> {
    // Create ButtonType's for creating a new device and canceling out of the dialog
    private ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
    
    private TableView<Device> tableView = new TableView();
    private TableColumn<Device, String> nameColumn = new TableColumn("Name");
    private TableColumn<Device, Integer> quantityColumn = new TableColumn("Quantity");
    private TableColumn<Device, BigDecimal> valueColumn = new TableColumn("Value");
    
    private static ObservableList<Device> deviceList = FXCollections.observableArrayList(getDeviceList());
    
    public DeviceListDialog() {
        // Sets the window's basic attributes
        setTitle("View Devices");
        setHeaderText("Device Information");
        
        // Set up the table's basic properties
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        
        tableView.getColumns().addAll(nameColumn, quantityColumn, valueColumn);
        tableView.setItems(deviceList);
        
        // Add all elements into the UI
        getDialogPane().getButtonTypes().add(okButton);
        getDialogPane().setContent(tableView);
        
        // Return the selected Device
        setResultConverter(button -> {
            if (tableView.getSelectionModel().getSelectedItem()!= null) return tableView.getSelectionModel().getSelectedItem();
            else return null;
        });
    }
    
    private ObservableList<Device> quickSort(ObservableList<Device> list) {
        // Lists to store items greater than, equal to and less than the pivot
        ObservableList<Device> lessList = FXCollections.observableArrayList();
        ObservableList<Device> equalList = FXCollections.observableArrayList();
        ObservableList<Device> greaterList = FXCollections.observableArrayList();
        
        // Only run when there are elements in the list (to sort)
        // Otherwise just return null
        if (list.size() > 1) {
            // Pick a random pivot
            long pivot = ThreadLocalRandom.current().nextLong(deviceLow(list), deviceHigh(list));
            
            // Loop through all devices in the list
            for (Device device : list) {
                // Sorts elements into three separate arrays
                // Those less than, equal to and greater than the pivot value (in terms of ID)
                if (list.indexOf(device) < pivot) lessList.add(device);
                if (list.indexOf(device) == pivot) equalList.add(device);
                if (list.indexOf(device) > pivot) greaterList.add(device);
            }
            
            // Joins together the three lists (in one larger one)
            // Run quicksort on each of these separately
            // Through which separate lists will eventually form which are all in order
            ObservableList<Device> completeList = FXCollections.observableArrayList();
            completeList.addAll(quickSort(lessList));
            completeList.addAll(quickSort(equalList));
            completeList.addAll(quickSort(greaterList));
            
            // Return the new reordered list
            return completeList;
        }
        return null;
    }
    
    // Functions to find the element with the highest and lowest ID
    private long deviceLow(ObservableList<Device> list) {
        long low = 0;
        
        for (Device device : list) {
            if (device.getLngID() < low) low = device.getLngID();
        } return low;
    }
    
    private long deviceHigh(ObservableList<Device> list) {
        long high = 0;
        
        for (Device device : list) {
            if (device.getLngID() > high) high = device.getLngID();
        } return high;
    }
}
