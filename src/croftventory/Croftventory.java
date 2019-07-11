/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory;

import croftventory.ObjectManager.DAO;
import static croftventory.ObjectManager.DAO.getBookings;
import static croftventory.ObjectManager.DAO.getDevices;
import static croftventory.ObjectManager.DAO.getStudents;
import croftventory.ObjectManager.StorageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Run at startup
 * 
 * @author kamron
 * 
 */

public class Croftventory extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Croftventory.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Croftventory");
        stage.setScene(scene);
        stage.show();
        
        // Provides a logger object
        DAO logger = new DAO();
        logger.setup();
        
        StorageController.addStudents(getStudents());
        StorageController.addDevices(getDevices());
        StorageController.addBookings(getBookings());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
