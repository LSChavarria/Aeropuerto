//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Pais;
import Facade.PaisFacade;
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
@Named(value = "paisController")
@SessionScoped
public class PaisController implements Serializable {

    @EJB
    private PaisFacade paisFacade;
    private Pais pais = new Pais();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    public List<Pais> fingAllTypedQuery() {
        return paisFacade.fingAllTypedQuery();
    }
    
    public List<Pais> fingAllNamedQuery() {
        return paisFacade.fingAllNamedQuery();
    }

    public List<Pais> findByNombreNamedQuery(){
        return paisFacade.findByNombre("EUA");
    }
    
    public void clean(){
        getPais().setEstados(null);
        getPais().setId(null);
        getPais().setNombre(null);
    }
    
    public void insertUpdate(String xhtml, String tryMessage, String catchMessage, boolean insert) {
        try {
            if(insert) paisFacade.insert(getPais());
            else paisFacade.update(getPais());
            message.printMessageInfo(xhtml, tryMessage);
            if(insert) clean();
        } catch(Exception e) {
            message.printMessageError(xhtml, catchMessage);
        }
    }
    
    public String insert(){
        String xhtml = "paisesAlta";
        String tryMessage = "El registro de ".concat(getPais().getNombre()).concat(" fue agregado exitosamente.");
        String catchMessage = "El registro de ".concat(getPais().getNombre()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, true);
        return xhtml;
    }
    
    public String prepareEdit(Pais pais) {
        setPais(pais);
        return "paisesEdit";
    }
    
    public String update() {
        String xhtml = "paisesEdit";
        String tryMessage = "El registro de ".concat(getPais().getNombre()).concat(" fue editado exitosamente.");
        String catchMessage = "El registro de ".concat(getPais().getNombre()).concat(" no pudo ser editado. Contacte a soporte.");
        insertUpdate(xhtml, tryMessage, catchMessage, false);
        return "";
    }
    
    public String mainClean(String url) {
        clean();
        setConfirm(false);
        return url;
    }
    
    public Pais find(Long id){
        return paisFacade.find(id);
    }
    
    public String prepareDelete(){
        setConfirm(true);
        return "paisesList";
    }
    
    public void delete(Pais pais){
        String xhtml = "paisesList";
        try{
            if(pais.getEstados().isEmpty()){
                setPais(pais);
                paisFacade.delete(getPais());
                message.printMessageInfo(xhtml, "El registro de ".concat(pais.getNombre()).concat(" fue eliminado exitosamente"));
            }
            else{
                message.printMessageError(xhtml, "El registro de ".concat(pais.getNombre()).concat(" no pudo ser eliminado porque tiene estados registrados."));
            }
        }catch(Exception e){
            message.printMessageError(xhtml, "El registro de ".concat(pais.getNombre()).concat(" no pudo ser eliminado. Contacte a soporte."));
        }
        mainClean(xhtml);
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
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
