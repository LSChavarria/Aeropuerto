//Java Class
//Clase usada para hacer validaciones desde nuestro archivo XHTML
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import Servicios.Mensaje;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@FacesValidator(value = "fechaValidator")
public class FechaValidator implements Validator {

    private final Mensaje message = Mensaje.getInstance();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            throw new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR,"El campo fecha no debe estar vacio."));
        }
        else {
            if(value instanceof Date) {
                Date dte = (Date) value;
                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                String str = formatter.format(dte);
                if(!str.matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
                    throw new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR,"La fecha ".concat(str).concat(" no es correcta, ingrese una fecha en un formato valido.")));
                }
                else {
                    throw new ValidatorException(message.setMessase(FacesMessage.SEVERITY_ERROR, "El tipo de dato no es aceptable."));
                }
            }
        }
    }
    
}
