//Java Class
//Clase usada para declarar los metodos necesarios para guardar un objeto como atributo de una tabla en nuestra base de datos
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import Controller.CiudadController;
import Entity.Ciudad;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sebas
 */
@FacesConverter(forClass = Ciudad.class)
public class CiudadConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CiudadController ciudadController = context.getApplication().evaluateExpressionGet(context, "#{ciudadController}", CiudadController.class);
        return ciudadController.find(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Ciudad ciudad = (Ciudad) value;
        return ciudad.getId().toString();
    }
    
}
