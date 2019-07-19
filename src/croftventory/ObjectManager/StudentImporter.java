/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.ObjectManager;

import croftventory.Types.Student;

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
