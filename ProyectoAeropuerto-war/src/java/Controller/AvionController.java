//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Avion;
import Facade.AvionFacade;
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
@Named(value = "avionController")
@SessionScoped
public class AvionController implements Serializable {

    @EJB
    private AvionFacade avionFacade;
    private Avion avion  = new Avion();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    public List<Avion> fingAllTypedQuery() {
        return avionFacade.findAllTypedQuery();
    }
    
    public List<Avion> fingAllNamedQuery() {
        return avionFacade.findAllNamedQuery();
    }
    
    public List<Avion> findByNumeroDeAvionTypedQuery(){
        return avionFacade.findByNumeroAvion("4");
    }
    
    public List<Avion> findByNumeroDeAvionCapacidadDePasajerosNamedQuery(){
        return avionFacade.findByNumeroDeAvionAndCapacidadDePasajeros("1", 250);
    }
    
    public void clean() {
        getAvion().setAerolinea(null);
        getAvion().setCapacidadPasajeros(0);
        getAvion().setId(null);
        getAvion().setModelo(null);
        getAvion().setNumeroAvion(null);
        getAvion().setVuelos(null);
    }
    
    public void insertUpdate(String xhtml, String tryIfMessage, String tryElseMessage, String catchMessage, boolean insert) {
        try {
            if(avionFacade.numeroAvionUnico(getAvion().getNumeroAvion())){
                if(insert) avionFacade.insert(getAvion());
                else avionFacade.update(getAvion());
                message.printMessageInfo(xhtml, tryIfMessage);
                if(insert) clean();
            }
            else {
                message.printMessageError(xhtml, tryElseMessage);
            }
        } catch(Exception e){
                message.printMessageError(xhtml, catchMessage);
        }
    }
    
    public String insert() {
        String xhtml = "avionesAlta";
        String tryIfMessage = "El registro de ".concat(getAvion().getNumeroAvion()).concat(" fue agregado exitosamente.");
        String tryElseMessage = "Ya existe un avion registrado con el numero ".concat(getAvion().getNumeroAvion()).concat(", no pueden existir mas de uno con el mismo numero.");
        String catchMessage = "El registro de ".concat(getAvion().getNumeroAvion()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, true);
        return xhtml;
    }
    
    public String prepareEdit(Avion avion){
        setAvion(avion);
        return "avionesEdit";
    }
    
    public String update() {
        String xhtml = "avionesEdit";
        String tryIfMessage = "El registro de ".concat(getAvion().getNumeroAvion()).concat(" fue editado exitosamente.");
        String tryElseMessage = "Ya existe un avion registrado con el numero ".concat(getAvion().getNumeroAvion()).concat(", no pueden existir mas de uno con el mismo numero.");
        String catchMessage = "El registro de ".concat(getAvion().getNumeroAvion()).concat(" no pudo ser editado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, false);
        return "";
    }
    
    public String mainClean(String url) {
        clean();
        setConfirm(false);
        return url;
    }
    
    public Avion find(Long id) {
        return avionFacade.find(id);
    }
    
    public String prepareDelete() {
        setConfirm(true);
        return "avionesList";
    }
    
    public void delete(Avion avion) {
        String xhtml = "avionesList";
        try {
            if(avion.getVuelos().isEmpty()) {
                setAvion(avion);
                avionFacade.delete(getAvion());
                message.printMessageInfo(xhtml, "El registro de ".concat(avion.getNumeroAvion()).concat(" fue editado exitosamente."));
            }
            else {
                message.printMessageError(xhtml, "Ya existe un avion registrado con el numero ".concat(avion.getNumeroAvion()).concat(", no pueden existir mas de uno con el mismo numero."));
            }
        } catch(Exception e) {
            message.printMessageError(xhtml, "El registro de ".concat(avion.getNumeroAvion()).concat(" no pudo ser editado. Contacte a soporte."));
        }
        mainClean(xhtml);
    }

    /**
     * @return the avion
     */
    public Avion getAvion() {
        return avion;
    }

    /**
     * @param avion the avion to set
     */
    public void setAvion(Avion avion) {
        this.avion = avion;
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
