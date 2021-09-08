//Java Class
//Clase usada para declarar los metodos necesarios para guardar un objeto como atributo de una tabla en nuestra base de datos
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import Controller.AvionController;
import Entity.Avion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sebas
 */
@FacesConverter(forClass = Avion.class)
public class AvionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        AvionController avionController = context.getApplication().evaluateExpressionGet(context, "#{avionController}", AvionController.class);
        return avionController.find(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Avion avion = (Avion) value;
        return avion.getId().toString();
    }
    
}
