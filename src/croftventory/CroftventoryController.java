/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import croftventory.ObjectManager.DAO;
import croftventory.ObjectManager.Importer;
import croftventory.ObjectManager.StorageController;
import static croftventory.ObjectManager.StorageController.getBookingList;
import croftventory.ObjectManager.StudentImporter;
import croftventory.Types.Booking;
import croftventory.Types.Device;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * Handles validation and everything UI related
 * 
 * @author kamron
 * 
 */

public class CroftventoryController implements Initializable {
    // Define variables used in the user interface (main screen)
    @FXML private DatePicker DueSearch;
    @FXML private TextField StudentSearch;
    @FXML private TextField DeviceSearch;
    
    @FXML private TableView<Booking> tableView;
    @FXML private TableColumn<Booking, String> nameColumn;
    @FXML private TableColumn<Booking, String> studentIDColumn;
    @FXML private TableColumn<Booking, String> deviceColumn;
    @FXML private TableColumn<Booking, LocalDate> borrowedColumn;
    @FXML private TableColumn<Booking, LocalDate> dueColumn;
    
    SimpleStringProperty studentSearchStr = new SimpleStringProperty();
    SimpleStringProperty deviceSearchStr = new SimpleStringProperty();
    SimpleObjectProperty<LocalDate> searchDue = new SimpleObjectProperty<>();
    
    FilteredList<Booking> filteredList = new FilteredList<>(getBookingList(), p -> true);
    
    // Handle button presses
    @FXML
    private void handleNewButton(ActionEvent event) throws SQLException {
        // Create a seperate dialog with input fields
        AddBookingDialog dialog = new AddBookingDialog();
        
        // Optional is used incase the dialog was canceled
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
    private void handleExtendButton(ActionEvent event) {
        System.out.println("Extend Loan");
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
        System.out.println("Return the Device");
    }
    
    @FXML
    private void handleAddDeviceButton(ActionEvent event) throws SQLException {
        // Create a seperate dialog with input fields
        AddDeviceDialog dialog = new AddDeviceDialog();
        
        // Optional is used incase the dialog was canceled
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("StrStudentName"));
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("StrStudent"));
        deviceColumn.setCellValueFactory(new PropertyValueFactory<>("StrDeviceName"));
        borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("DateLent"));
        dueColumn.setCellValueFactory(new PropertyValueFactory<>("DateDue"));
        
        tableView.setItems(filteredList);
        
        // The following events with lambda expressions detect changes in input
        // For the search fields
        DueSearch.valueProperty().addListener((listener) -> verify());
        StudentSearch.textProperty().addListener((listener) -> verify());
        DeviceSearch.textProperty().addListener((listener) -> verify());
    }
    
    // Removes redundancy by providing a single function to set a predicate
    private void verify() {
        filteredList.setPredicate(booking -> {
                if (verifyStudent(booking.getStrStudentName()) && verifyDevice(booking.getStrDeviceName()) && verifyDate(booking.getDateDue())) {
                    return true;
                } else return false;
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
}
