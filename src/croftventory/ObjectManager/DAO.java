/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.ObjectManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.tools.RunScript;

/**
 *
 * @author kamron
 */
public class DAO {
    private static Connection connection;
    
    public void setup() {
        try {
            connection = getConnection("jdbc:h2:~/NetBeansProjects/Croftventory", "sa", "");
            RunScript.execute(connection, new FileReader("autocreate.sql"));
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
