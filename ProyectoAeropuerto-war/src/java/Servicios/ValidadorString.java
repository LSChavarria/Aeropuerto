//JavaClass
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sebas
 */
public class ValidadorString {
    
    private static final ValidadorString validadorString = new ValidadorString();
    private final Mensaje message = Mensaje.getInstance();
    
    public static ValidadorString getInstance(){
        return validadorString;
    }
    
    public ValidatorException stringValidator(Object value, String msjError, String regularExpresion) {
        if(value instanceof String) {
            String str = (String) value;
            if(!str.matches(regularExpresion)) {
                return new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR, msjError));
            }
        }
        else {
            return new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR,"El tipo de dato no es aceptable."));
        }
        return null;
    }
    
}
