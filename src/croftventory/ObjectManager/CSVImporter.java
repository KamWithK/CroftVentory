/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.ObjectManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 
 * An abstract class defining the read function for CSV's
 * This allows future classes to read CSV's for different purposes
 * Without needing to redefine the basic functionality
 * 
 * @author kamron
 * 
 */

public abstract class CSVImporter<T> implements Importer {
    // The implementation of readLines splits the line up as well
    @Override
    public void readLines(String path) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            // Skip the first line (a header describing the following data)
            br.readLine();
            
            // Loop through every line in the document until there is no content on the line
            // Meaning the line doesn't exist
            // Note this works as "" != null
            while ((line = br.readLine()) != null) {
                // Creates objects out of line
                // Before adding them to the list
                // Use a comma as the delimiter
                list.add(create(line.split(",")));
            }
        }
    }
    
    @Override
    public List<T> get() {
        // The list must be cast
        return (List<T>) list;
    }
}