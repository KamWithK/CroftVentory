/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package croftventory.SpecialField;

import javafx.scene.control.TextField;

/**
 * 
 * A TextField limited to a Regular Expression
 * 
 * @author kamron
 * 
 */
public class RegexField extends TextField{
    public RegexField(String regex) {
        setOnKeyTyped(event -> {
            // Event must be removed to stop the normal addition of the new character to the TextField
            // After the character is stored in a new variable
            String character = event.getCharacter();
            event.consume();
            
            if (character.matches(regex)) {
            setText(getText().concat(character));
        }
        });
    }
}
