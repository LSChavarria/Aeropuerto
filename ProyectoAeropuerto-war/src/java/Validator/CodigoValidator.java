//Java Class
//Clase usada para hacer validaciones desde nuestro archivo XHTML
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import Servicios.ValidadorString;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sebas
 */
@FacesValidator(value="CodigoValidator") //Nombre con el que se le llamara desde el XHTML
public class CodigoValidator implements Validator {

    private final ValidadorString validadorString = ValidadorString.getInstance();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String regularExpresion = "[a-zA-Z0-9]*";
        String msjError = "El codigo ".concat((String) value).concat(" no es correcto, ingrese un valor valido.");
        ValidatorException validatorException = validadorString.stringValidator(value, msjError, regularExpresion);
        if(validatorException != null)
            throw validatorException;
    }
    
}
