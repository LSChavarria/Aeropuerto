//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Ciudad;
import Facade.CiudadFacade;
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
@Named(value = "ciudadController")
@SessionScoped
public class CiudadController implements Serializable {

    @EJB
    private CiudadFacade ciudadFacade;
    private Ciudad ciudad = new Ciudad();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    public List<Ciudad> fingAllTypedQuery() {
        return ciudadFacade.fingAllTypedQuery();
    }
    
    public List<Ciudad> fingAllNamedQuery() {
        return ciudadFacade.fingAllNamedQuery();
    }
    
    public List<Ciudad> findByNombreEstadoTypedQuery(){
        return ciudadFacade.findByNombreEstado("California");
    }
    
    public List<Ciudad> findByNombreDelPaisNamedQuery(){
        return ciudadFacade.findByNombreDelPais("EUA");
    }
    
    public void clean() {
        getCiudad().setEstado(null);
        getCiudad().setId(null);
        getCiudad().setNombre(null);
        getCiudad().setVuelosDestino(null);
        getCiudad().setVuelosOrigen(null);
    }
    
    public void insertUpdate(String xhtml, String tryIfMessage, String tryElseMessage, String catchMessage, boolean insert) {
        try {
            if(ciudadFacade.nombreDeCiudadUnicoPorEstado(getCiudad())) {
                if(insert) ciudadFacade.insert(getCiudad());
                else ciudadFacade.update(getCiudad());
                message.printMessageInfo(xhtml, tryIfMessage);
                if(insert) clean();
            }
            else {
                message.printMessageError(xhtml, tryElseMessage);
            }
        } catch(Exception e) {
            message.printMessageError(xhtml, catchMessage);
        }
    }
    
    public String insert() {
        String xhtml = "ciudadesAlta";
        String tryIfMessage = "El registro de ".concat(getCiudad().getNombre()).concat(" fue agregado exitosamente.");
        String tryElseMessage = "Ya hay una ciudad llamada ".concat(getCiudad().getNombre()).concat(", no pueden existir mas de una con el mismo nomnbre.");
        String catchMessage = "El registro de ".concat(getCiudad().getNombre()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, true);
        return xhtml;
    }
    
    public String prepareEdit(Ciudad ciudad) {
        setCiudad(ciudad);
        return "ciudadesEdit";
    }
    
    public String update() {
        String xhtml = "ciudadesEdit";
        String tryIfMessage = "El registro de ".concat(getCiudad().getNombre()).concat(" fue editado exitosamente.");
        String tryElseMessage = "Ya hay una ciudad llamada ".concat(getCiudad().getNombre()).concat(", no pueden existir mas de una con el mismo nomnbre.");
        String catchMessage = "El registro de ".concat(getCiudad().getNombre()).concat(" no pudo ser editado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, false);
        return "";
    }
    
    public String mainClean(String url){
        clean();
        setConfirm(false);
        return url;
    }
    
    public Ciudad find(Long id){
        return ciudadFacade.find(id);
    }
    
    public String prepareDelete(){
        setConfirm(true);
        return "ciudadesList";
    }
    
    public void delete(Ciudad ciudad) {
        String xhtml = "ciudadesList";
        try {
            if(ciudad.getVuelosDestino().isEmpty() && ciudad.getVuelosOrigen().isEmpty()) {
                setCiudad(ciudad);
                ciudadFacade.delete(getCiudad());
                message.printMessageInfo(xhtml, "El registro de ".concat(ciudad.getNombre()).concat(" fue eliminado exitosamente"));
            }
            else {
                message.printMessageError(xhtml, "El registro de ".concat(ciudad.getNombre()).concat(" no pudo ser eliminado porque tiene vuelos asignados."));
            }
        } catch(Exception e) {
            message.printMessageError(xhtml, "El registro de ".concat(ciudad.getNombre()).concat(" no pudo ser eliminado. Contacte a soporte."));
        }
        mainClean(xhtml);
    }

    /**
     * @return the ciudad
     */
    public Ciudad getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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
