//Java Class
//Clase usada para declarar los metodos necesarios para guardar un objeto como atributo de una tabla en nuestra base de datos
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import Controller.EstadoController;
import Entity.Estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sebas
 */
@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        EstadoController estadoController = context.getApplication().evaluateExpressionGet(context, "#{estadoController}", EstadoController.class);
        return estadoController.find(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Estado estado = (Estado) value;
        return estado.getId().toString();
    }
    
}
