/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Interface for importers to implements
 * Allows an easy and standardized way to implement reading from different files into different classes
 * 
 * @author kamron
 * 
 */

public interface Importer<T> {
    // Note can't use generic wild-type for list
    // As otherwise objects CAN'T be added to it
    List<Object> list = new ArrayList<>();
    
    // A method to read through the file line by line
    void readLines(String path) throws IOException;
    <T> T create(String[] line);
    <T> List<T> get();
}
