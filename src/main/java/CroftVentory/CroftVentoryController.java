/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CroftVentory;

import Dialogs.AddBookingDialog;
import Dialogs.AddDeviceDialog;
import Dialogs.DeviceListDialog;
import Dialogs.ExtendDialog;
import ObjectManager.DAO;
import ObjectManager.Importer;
import ObjectManager.StorageController;
import ObjectManager.StudentImporter;
import Types.Booking;
import Types.Device;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static ObjectManager.DAO.modifyData;
import static ObjectManager.StorageController.getBookingList;

/**
 *
 * Handles validation and everything UI related
 * 
 * @author kamron
 * 
 */

public class CroftVentoryController implements Initializable {
    // Define variables used in the user interface (main screen)
    @FXML private DatePicker DueSearch;
    @FXML private TextField StudentSearch;
    @FXML private TextField DeviceSearch;
    @FXML private CheckBox CheckBoxSearch;
    
    @FXML private TableView<Booking> tableView;
    @FXML private TableColumn<Booking, String> nameColumn;
    @FXML private TableColumn<Booking, String> studentIDColumn;
    @FXML private TableColumn<Booking, String> deviceColumn;
    @FXML private TableColumn<Booking, LocalDate> borrowedColumn;
    @FXML private TableColumn<Booking, LocalDate> dueColumn;
    
    SimpleStringProperty studentSearchStr = new SimpleStringProperty();
    SimpleStringProperty deviceSearchStr = new SimpleStringProperty();
    SimpleObjectProperty<LocalDate> searchDue = new SimpleObjectProperty<>();
    
    private FilteredList<Booking> filteredList = new FilteredList<>(getBookingList(), p -> true);
    
    // Handle button presses
    @FXML
    private void handleNewButton(ActionEvent event) throws SQLException {
        // Create a separate dialog with input fields
        AddBookingDialog dialog = new AddBookingDialog();
        
        // Optional is used in-case the dialog was canceled
        // Checks whether the Booking is present or not
        // If so add it to the list
        Optional<Booking> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Add booking to the application's internal list and the database
            StorageController.addBooking(result.get());
            DAO.addBooking(result.get());
        }
    }
    
    @FXML
    private void handleExtendButton(ActionEvent event) throws SQLException {
        // Only run if an item is selected
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            // Create a separate dialog for getting new date
            ExtendDialog dialog = new ExtendDialog();

            // Optional is used in-case the dialog was canceled
            // Checks whether the Booking is present or not
            // If so modify lists
            Optional<LocalDate> result = dialog.showAndWait();
            if (result.isPresent()) {
                // Modify the Booking in memory and the database
                // Then refresh the TableView and filter its content
                tableView.getSelectionModel().getSelectedItem().setDateDue(result.get());
                modifyData("Booking", "DueOn", tableView.getSelectionModel().getSelectedItem().getDateDue(), tableView.getSelectionModel().getSelectedItem().getID());
                tableView.refresh();
                verify();
            }
        }
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) throws SQLException {
        // Only run if an item is selected
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            // Modify the Booking in memory and the database
            // Then refresh the TableView and filter its content
            tableView.getSelectionModel().getSelectedItem().setReturned(!tableView.getSelectionModel().getSelectedItem().getReturned());
            modifyData("Booking", "Returned", tableView.getSelectionModel().getSelectedItem().getReturned(), tableView.getSelectionModel().getSelectedItem().getID());
            tableView.refresh();
            verify();
        }
    }
    
    @FXML
    private void handleAddDeviceButton(ActionEvent event) throws SQLException {
        // Create a separate dialog with input fields
        AddDeviceDialog dialog = new AddDeviceDialog();
        
        // Optional is used in-case the dialog was canceled
        // Checks whether the Device is present or not
        // If so add it to the list
        Optional<Device> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Add device to the application's internal list and the database
            StorageController.addDevice(result.get());
            DAO.addDevice(result.get());
        }
    }
    
    @FXML
    private void handleImportStudentsButton(ActionEvent event) throws IOException, SQLException {
        Alert studentReplaceAlert = new Alert(AlertType.CONFIRMATION);
        studentReplaceAlert.setTitle("Import New Student Spreadsheet");
        studentReplaceAlert.setContentText("Are you sure you'd like to import new students, this will overide any already existing ones");
        Optional<ButtonType> result = studentReplaceAlert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            // Create a new Importer for students
            // Then read the file with student details
            Importer csvStudentImporter = new StudentImporter();
            csvStudentImporter.readLines("students.csv");
            
            // Add students to the application's internal list and the database
            StorageController.addStudents(csvStudentImporter.get());
            DAO.addStudents(csvStudentImporter.get());
        }
    }
    
    @FXML
    private void handleDeviceListButton(ActionEvent event) {
        // Create a separate dialog with input fields
        DeviceListDialog devices = new DeviceListDialog();
        
        devices.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("Student"));
        deviceColumn.setCellValueFactory(new PropertyValueFactory<>("DeviceName"));
        borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("DateLent"));
        dueColumn.setCellValueFactory(new PropertyValueFactory<>("DateDue"));
        
        // Set items after filtering the list for the first time for the first time
        verify();
        tableView.setItems(filteredList);
        
        // The following events with lambda expressions detect changes in input
        // For the search fields
        DueSearch.valueProperty().addListener(cl -> verify());
        StudentSearch.textProperty().addListener(cl -> verify());
        DeviceSearch.textProperty().addListener(cl -> verify());
        CheckBoxSearch.selectedProperty().addListener(cl -> verify());
    }
    
    // Removes redundancy by providing a single function to set a predicate
    private void verify() {
        filteredList.setPredicate(booking -> {
            return verifyStudent(booking.getStudentName()) && verifyDevice(booking.getDeviceName()) && verifyDate(booking.getDateDue()) && verifyReturned(booking.getReturned());
            });
    }
    
    // Individual functions to safely test whether a field matches a search term
    // These all themselves check for null values where needed
    // Avoiding potential (common) pitfalls with the java type system
    private boolean verifyStudent(String ours) {
        if (StudentSearch.getText() == null || StudentSearch.getText() == "") {
            System.out.println("NULL");
            return true;
        }
        return ours.contains(StudentSearch.getText());
    }
    
    private boolean verifyDevice(String ours) {
        if (DeviceSearch.getText() == null || DeviceSearch.getText() == "") {
            System.out.println("NULL");
            return true;
        }
        return ours.contains(DeviceSearch.getText());
    }
    
    private boolean verifyDate(LocalDate ours) {
        if (DueSearch.getValue() == null) {
            return true;
        }
        return DueSearch.getValue().equals(ours);
    }
    
    private boolean verifyReturned(boolean ours) {
        return (CheckBoxSearch.isSelected() == ours);
    }
}
