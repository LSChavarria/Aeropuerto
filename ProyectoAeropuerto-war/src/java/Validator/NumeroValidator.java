//Java Class
//Clase usada para hacer validaciones desde nuestro archivo XHTML
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import Servicios.ValidadorString;
import Servicios.Mensaje;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sebas
 */
@FacesValidator(value = "numeroValidator")
public class NumeroValidator implements Validator {

    private final ValidadorString validadorString = ValidadorString.getInstance();
    private final Mensaje message = Mensaje.getInstance();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String regularExpresion = "[0-9]+([.,][0-9]+)?";
        ValidatorException validatorException = null;
        if(value instanceof Number) {
            String str = Integer.toString((Integer) value);
            String msjError = "El numero ".concat(Integer.toString((Integer) value)).concat(" no es correcto, ingrese un numero valido.");
            if(!str.matches(regularExpresion)) {
                validatorException = new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR, msjError));
            }
        }
        else{
            String msjErrorInt = "El numero ".concat((String) value).concat(" no es correcto, ingrese un numero valido.");
            validatorException = validadorString.stringValidator(value, msjErrorInt, regularExpresion);
        }
        if(validatorException != null)
            throw validatorException;
    }
    
}
