/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.ObjectManager;

import static croftventory.ObjectManager.StorageController.removeDevice;
import croftventory.Types.Booking;
import croftventory.Types.Device;
import croftventory.Types.Student;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.tools.RunScript;

/**
 * 
 * Data Access Object
 * Deals with retrieving and setting data into/from the database
 * 
 * @author kamron
 * 
 */

public class DAO {
    private static Connection connection;
    
    public void setup() {
        try {
            connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
            RunScript.execute(connection, new FileReader("autocreate.sql"));
            connection.close();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addStudents(List<Student> students) throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        
        // Setup format for adding student to the database
        // Uses MERGE instead of INSERT to override already existing fields
        String newStudent = "MERGE INTO Student (ID, FirstName, SecondName, Email, Class, DemeritPoints) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(newStudent, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // Loop through each student adding them to the prepared statement batch
        students.forEach(student -> {
            try {
                preparedStatement.setString(1, student.getStrID());
                preparedStatement.setString(2, student.getFName());
                preparedStatement.setString(3, student.getSName());
                preparedStatement.setString(4, student.getStrEmail());
                preparedStatement.setString(5, student.getStrClass());
                preparedStatement.setInt(6, student.getIntDemeritPoints());
                preparedStatement.addBatch();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Execute the batch statement
        // Note that all students are added at once
        preparedStatement.executeLargeBatch();
        
        connection.close();
    }
    
    public static void addStudent(Student student) throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        
        // Setup format for adding student to the database
        // Uses MERGE instead of INSERT to override already existing fields
        String newStudent = "MERGE INTO Student (ID, FirstName, SecondName, Email, Class, DemeritPoints) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(newStudent, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // Add student info to prepared statement
        preparedStatement.setString(1, student.getStrID());
        preparedStatement.setString(2, student.getFName());
        preparedStatement.setString(3, student.getSName());
        preparedStatement.setString(4, student.getStrEmail());
        preparedStatement.setString(5, student.getStrClass());
        preparedStatement.setInt(6, student.getIntDemeritPoints());
        
        preparedStatement.execute();
    }
    
    public static void addDevice(Device device) throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        
        // Setup format for adding student to the database
        // Uses MERGE instead of INSERT to override already existing fields
        String newDevice = "INSERT INTO Device (Name, Quantity, Value) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(newDevice, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // Add student info to prepared statement
        // Note the integer needs to be converted to a big decimal
        preparedStatement.setString(1, device.getStrName());
        preparedStatement.setInt(2, device.getIntQuantity());
        preparedStatement.setBigDecimal(3, device.getDeciValue());
        
        // If database returns status indicating an addition
        // Remove device and recreate it with an ID this time
        // Note this happens instead of providing a setID method
        // To ensure that at no point is the deviceID falsly set
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 0)  {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    removeDevice(device);
                    StorageController.addDevice(new Device(device.getStrName(), device.getIntQuantity(), device.getDeciValue(), generatedKeys.getLong(1)));
                }
            }
        }
    }
    
    public static void addBooking(Booking booking) throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        
        // Setup format for adding student to the database
        // Uses MERGE instead of INSERT to override already existing fields
        String newBooking = "INSERT INTO Booking (StudentID, DeviceID, Quantity, LentOn, DueOn, Returned) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(newBooking, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // Add student info to prepared statement
        // Note setObject must be used instead of setDate as the new LocalDate class is used
        preparedStatement.setString(1, booking.getStrStudent());
        preparedStatement.setLong(2, booking.getLngDevice());
        preparedStatement.setInt(3, booking.getIntQuantity());
        preparedStatement.setObject(4, booking.getDateLent());
        preparedStatement.setObject(5, booking.getDateDue());
        preparedStatement.setBoolean(6, booking.getBoolReturned());
        
        preparedStatement.execute();
    }
    
    public static List<Student> getStudents() throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        // Read all students from the database
        String getStudentData = "SELECT * FROM Student";
        ResultSet resultSet = connection.createStatement().executeQuery(getStudentData);
        
        // Create a collection to store students
        List<Student> students = new ArrayList<>();
        
        // Iterate through all rows to read all students in the table
        while (resultSet.next()) {
            Student student = new Student(resultSet.getString("FirstName") + " " + resultSet.getString("SecondName"), resultSet.getString("Class"), resultSet.getString("Email"), resultSet.getString("ID"));
            students.add(student);
        }
        
        // Return all found students in the database
        return students;
    }
    
    public static List<Device> getDevices() throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        // Read all students from the database
        String getStudentData = "SELECT * FROM Device";
        ResultSet resultSet = connection.createStatement().executeQuery(getStudentData);
        
        // Create a collection to store students
        List<Device> devices = new ArrayList<>();
        
        // Iterate through all rows to read all students in the table
        while (resultSet.next()) {
            Device device = new Device(resultSet.getString("Name"), resultSet.getInt("Quantity"), resultSet.getBigDecimal("Value"), resultSet.getLong("ID"));
            devices.add(device);
        }
        
        // Return all found students in the database
        return devices;
    }
    
    public static List<Booking> getBookings() throws SQLException {
        connection = getConnection("jdbc:h2:~/Development/Java/Croftventory", "sa", "");
        // Read all students from the database
        String getStudentData = "SELECT * FROM Booking";
        ResultSet resultSet = connection.createStatement().executeQuery(getStudentData);
        
        // Create a collection to store students
        List<Booking> bookings = new ArrayList<>();
        
        // Iterate through all rows to read all students in the table
        while (resultSet.next()) {
            Booking booking = new Booking(resultSet.getString("StudentID"), resultSet.getLong("DeviceID"), resultSet.getInt("Quantity"), resultSet.getObject("LentOn", LocalDate.class), resultSet.getObject("DueOn", LocalDate.class), resultSet.getBoolean("Returned"), resultSet.getLong("ID"));
            bookings.add(booking);
        }
        
        // Return all found students in the database
        return bookings;
    }
}
