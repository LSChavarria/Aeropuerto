//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Vuelo;
import Facade.VueloFacade;
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
@Named(value = "vueloController")
@SessionScoped
public class VueloController implements Serializable {

    @EJB
    private VueloFacade vueloFacade;
    private Vuelo vuelo = new Vuelo();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    public String returnMenu(){
        return "/menu";
    }
            
    public List<Vuelo> fingAllTypedQuery() {
        return vueloFacade.fingAllTypedQuery();
    }
    
    public List<Vuelo> fingAllNamedQuery() {
        return vueloFacade.fingAllNamedQuery();
    }
    
    public List<Vuelo> findByNumeroDeVueloTypedQuery(){
        return vueloFacade.findByNumeroDeVuelo("A113");
    }
    
    public void clean() {
        getVuelo().setDestino(null);
        getVuelo().setFechaFin(null);
        getVuelo().setFechaInicio(null);
        getVuelo().setHoraFin(null);
        getVuelo().setHoraInicio(null);
        getVuelo().setId(null);
        getVuelo().setNumeroAvion(null);
        getVuelo().setNumeroPasajeros(0);
        getVuelo().setNumeroVuelo(null);
        getVuelo().setOrigen(null);
    }
    
    public void insertUpdate(String xhtml, String tryMessage, String catchMessage, boolean insert) {
        try {
            if(vueloFacade.numeroVueloUnico(getVuelo().getNumeroVuelo())){
                if(vueloFacade.diferenteOrigenDestino(getVuelo())){
                    if(vueloFacade.diferenciaDias(getVuelo()) == 0){
                        if(vueloFacade.unaHoraDespues(getVuelo())){
                            if(insert) vueloFacade.insert(getVuelo());
                            else vueloFacade.update(getVuelo());
                            message.printMessageInfo(xhtml, tryMessage);
                            if(insert) clean();
                        }
                        else {
                            message.printMessageError(xhtml, "Al ser el despegue y el aterrizaje el mismo dia debe haber al menos una hora de difetencia entre estos.");
                        }
                    }
                    else {
                        if(vueloFacade.diferenciaDias(getVuelo()) == 1){
                            if(insert) vueloFacade.insert(getVuelo());
                            else vueloFacade.update(getVuelo());
                            message.printMessageInfo(xhtml, tryMessage);
                            if(insert) clean();
                        }
                        else{
                            message.printMessageError(xhtml, "La fecha de aterrizaje tiene que ser el mismo dia o uno despues.");
                        }
                    }
                }
                else {
                    message.printMessageError(xhtml, "El origen y el destino no pueden ser los mismos.");
                }
            }
            else {
                message.printMessageError(xhtml, "Ya existe un vuelo con el numero ".concat(getVuelo().getNumeroVuelo()).concat(", no puede existir mas de uno con el mismo numero."));
            }
        } catch(Exception e){
            message.printMessageError(xhtml, catchMessage);
        }        
    }
    
    public String insert() {
        String xhtml = "vuelosAlta";
        String tryMessage = "El registro de ".concat(getVuelo().getNumeroVuelo()).concat(" fue agregado exitosamente.");
        String catchMessage = "El registro de ".concat(getVuelo().getNumeroVuelo()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, true);
        return xhtml;
    }
    
    public String prepareEdit(Vuelo vuelo){
        setVuelo(vuelo);
        return "vuelosEdit";
    }
    
    public String update() {
        String xhtml = "vuelosEdit";
        String tryMessage = "El registro de ".concat(getVuelo().getNumeroVuelo()).concat(" fue editado exitosamente.");
        String catchMessage = "El registro de ".concat(getVuelo().getNumeroVuelo()).concat(" no pudo ser editado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, false);
        return xhtml;
    }
    
    public String mainClean(String url){
        clean();
        setConfirm(false);
        return url;
    }
    
    public Vuelo find(Long id){
        return vueloFacade.find(id);
    }
    
    public String prepareDelete(){
        setConfirm(true);
        return "vuelosList";
    }
    
    public void delete(Vuelo vuelo){
        String xhtml = "vuelosList";
        try{
            setVuelo(vuelo);
            vueloFacade.delete(getVuelo());
            message.printMessageInfo(xhtml, "El registro de ".concat(vuelo.getNumeroVuelo()).concat(" fue eliminado exitosamente"));
        }catch(Exception e){
            message.printMessageInfo(xhtml, "El registro de ".concat(vuelo.getNumeroVuelo()).concat(" no pudo ser eliminado. Contacte a soporte."));
        }
        mainClean(xhtml);
    }

    /**
     * @return the vuelo
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * @param vuelo the vuelo to set
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
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
