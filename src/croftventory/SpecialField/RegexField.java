/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.SpecialField;

import javafx.scene.control.TextField;

/**
 * 
 * A TextField which automatically removes any matching Regular Expression
 * 
 * @author kamron
 * 
 */
public class RegexField extends TextField{
    public RegexField(String regex) {
        textProperty().addListener((observable, oldValue, newValue) -> {
            setText(newValue.replaceAll(regex, ""));
        });
    }
}
