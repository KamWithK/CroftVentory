/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CroftVentory;

import ObjectManager.DAO;
import ObjectManager.StorageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

import static ObjectManager.DAO.*;

/**
 *
 * Starts the JavaFX Application
 * 
 * @author kamron
 * 
 */

public class CroftVentoryFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/java/CroftVentory/CroftVentory.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("CroftVentory");
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
    public static void main(String[] args) { launch(args); }
}
