/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectManager;

import Types.Booking;
import Types.Device;
import Types.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * 
 * Manages storage of data in memory
 * As well as reading directly from the disk
 * 
 * @author kamron
 * 
 */

public class StorageController {
    private static ObservableList<Student> studentList = FXCollections.observableArrayList();
    private static ObservableList<Device> deviceList = FXCollections.observableArrayList();
    private static ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    
    public static ObservableList<Student> getStudentList() { return studentList; }
    public static ObservableList<Device> getDeviceList() { return deviceList; }
    public static ObservableList<Booking> getBookingList() { return bookingList; }
    
    public static void addStudent(Student student) { studentList.add(student); }
    public static void addDevice(Device device) { deviceList.add(device); }
    public static void addBooking(Booking booking) { bookingList.add(booking); }
    
    public static void addStudents(List<Student> students) { studentList.addAll(students); }
    public static void addDevices(List<Device> devices) { deviceList.addAll(devices); }
    public static void addBookings(List<Booking> bookings) { bookingList.addAll(bookings); }
    
    // Functions to remove objects from lists
    // Polymorphism is used to allow removal at an index or with the provided student
    // TODO check whether index is needed in code later on
    public static void removeStudent(Student student) { studentList.remove(student); }
    public static void removeStudent(int index) { studentList.remove(index); }
    static void removeDevice(Device device) { deviceList.remove(device); }
    public static void removeDevice(int index) { deviceList.remove(index); }
    static void removeBooking(Booking booking) { bookingList.remove(booking); }
    static void removeBooking(int index) { bookingList.remove(index); }
    
    // Methods to find a student, device or booking object using an ID
    // These loop through all elements in their associated lists
    // Before they find and return their desired element
    public static Student getStudent(String ID) {
        for(Student student : studentList) {
            // Note null checks aren't required
            // Since student ID's should previously have been checked
            // By either this program and/or the database
            if (student.getID().equals(ID)) { return student; }
        } return null;
    }
    
    public static Device getDevice(long ID) {
        for(Device device : deviceList) {
            if (device.getLngID() == ID) { return device; }
        } return null;
    }
    
    public static Booking getBooking(long ID) {
        for(Booking booking : bookingList) {
            if (booking.getID() == ID) { return booking; }
        } return null;
    }
}
