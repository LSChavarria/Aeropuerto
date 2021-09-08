//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Estado;
import Facade.EstadoFacade;
import Servicios.Mensaje;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author sebas
 */
@Named(value = "estadoController")
@SessionScoped
public class EstadoController implements Serializable {

    @EJB
    private EstadoFacade estadoFacade;
    private Estado estado = new Estado();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    
    public List<Estado> fingAllTypedQuery() {
        return estadoFacade.fingAllTypedQuery();
    }
    
    public List<Estado> fingAllNamedQuery() {
        return estadoFacade.fingAllNamedQuery();
    }
    
    public void clean() {
        getEstado().setCiudades(null);
        getEstado().setId(null);
        getEstado().setNombre(null);
        getEstado().setPais(null);
    }
    
    public void insertUpdate(String xhtml, String tryMessage, String catchMessage, boolean insert) {
        try {
            if(insert) estadoFacade.insert(getEstado());
            else estadoFacade.update(getEstado());
            message.printMessageInfo(xhtml, tryMessage);
            if(insert) clean();
        }catch(Exception e){
            message.printMessageError(xhtml, catchMessage);
        }
    }
    
    public String insert() {
        String xhtml = "estadosAlta";
        String tryMessage = "El registro de ".concat(getEstado().getNombre()).concat(" fue agregado exitosamente.");
        String catchMessage = "El registro de ".concat(getEstado().getNombre()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, true);
        return xhtml;
    }
    
    
    public String prepareEdit(Estado estado){
        setEstado(estado);
        return "estadosEdit";
    }
    
    public String update() {
        String xhtml = "estadosEdit";
        String tryMessage = "El registro de ".concat(getEstado().getNombre()).concat(" fue editado exitosamente.");
        String catchMessage = "El registro de ".concat(getEstado().getNombre()).concat(" no pudo ser editado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, false);
        return "";
    }
    
    public String mainClean(String url) {
        clean();
        setConfirm(false);
        return url;
    }
    
    public Estado find(Long id){
        return estadoFacade.find(id);
    }

    public String prepareDelete(){
        setConfirm(true);
        return "estadosList";
    }
    
    public void delete(Estado estado) {
        String xhtml = "estadosList";
        try {
            if(estado.getCiudades().isEmpty()){
                setEstado(estado);
                estadoFacade.delete(getEstado());
                message.printMessageInfo(xhtml, "El registro de ".concat(estado.getNombre()).concat(" fue eliminado exitosamente"));
            }
            else {
                message.printMessageError(xhtml, "El registro de ".concat(estado.getNombre()).concat(" no pudo ser eliminado porque tiene ciudades registradas."));
            }
        } catch (Exception e) {
            message.printMessageError(xhtml, "El registro de ".concat(estado.getNombre()).concat(" no pudo ser eliminado. Contacte a soporte."));
        }
        mainClean(xhtml);
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * @return the confirm
     */
    public boolean isConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
    
}
