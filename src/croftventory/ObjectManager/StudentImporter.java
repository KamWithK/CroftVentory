/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.ObjectManager;

import croftventory.Types.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Manages importing students
 * 
 * @author kamron
 * 
 */

public class StudentImporter extends CSVImporter {
    // Only need to implement the create function as the rest are implemented by CSVImporter
    @Override
    public Student create(String[] line) {
        return new Student(line[1] + " " + line[2], line[4],line[3] , line[0]);
    }
}
