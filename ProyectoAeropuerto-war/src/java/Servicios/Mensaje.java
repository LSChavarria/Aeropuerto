//Java Class
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author sebas
 */
public class Mensaje {
    
    private static final Mensaje mensaje = new Mensaje();
    private final FacesMessage facesMessage = new FacesMessage();
    
    public static Mensaje getInstance() {
        return mensaje;
    }
    
    public FacesMessage setMessase(Severity severity, String summary) {
        facesMessage.setSeverity(severity);
        facesMessage.setSummary(summary);
        facesMessage.setDetail("");
        return facesMessage;
    }
    
    public void printMessageError(String page, String summary){
        FacesMessage msj;
        msj = setMessase(FacesMessage.SEVERITY_ERROR, summary);
        FacesContext.getCurrentInstance().addMessage(page, msj);
    }
    
    public void printMessageInfo(String page, String summary){
        FacesMessage msj;
        msj = setMessase(FacesMessage.SEVERITY_INFO, summary);
        FacesContext.getCurrentInstance().addMessage(page, msj);
    }
    
}
