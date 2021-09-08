//JavaServer Faces -> JSF CDI Bean -> cambiar Scope de dependent a Session
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Usuario;
import Facade.UsuarioFacade;
import Servicios.Mensaje;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author sebas
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario = new Usuario();
    private boolean confirm = false;
    private final Mensaje message = Mensaje.getInstance();
    
    private String email;
    private String contrasena;
    
    public List<Usuario> findAllTypedQuery() {
        return usuarioFacade.findAllTypedQuery();
    }
    
    public List<Usuario> findAllNamedQuery() {
        return usuarioFacade.findAllNamedQuery();
    }
    
    public void clean() {
        getUsuario().setContrasena(null);
        getUsuario().setEmail(null);
        getUsuario().setId(null);
        getUsuario().setNombre(null);
        getUsuario().setPerfil(0);
    }
    
    public String iniciarSecion() {
        String xhtml = "index";
        List<Usuario> usuario = usuarioFacade.findEmailContrasena(getEmail(), getContrasena());
        if(usuario.isEmpty()) {
            usuario = usuarioFacade.findCorreo(getEmail());
            if(usuario.isEmpty()) {
                message.printMessageError(xhtml, "El correo ".concat(getEmail()).concat(" no se encontro."));
            }
            else {
                message.printMessageError(xhtml, "Contraseña incorrecta");
            }
            return xhtml;
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario.get(0));
            return "menu";
        }
    }
    
    public void insertUpdate(String xhtml, String tryIfMessage, String tryElseMessage, String catchMessage, boolean insert) {
        try {
            if(usuarioFacade.perfilValido(getUsuario().getPerfil())) {
                if(insert) usuarioFacade.insert(getUsuario());
                else usuarioFacade.update(getUsuario());
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
        String xhtml = "usuariosAlta";
        String tryIfMessage = "El registro de ".concat(getUsuario().getNombre()).concat(" fue agregado exitosamente.");
        String tryElseMessage = "El perfil ".concat(String.valueOf(getUsuario().getPerfil())).concat(" no es valido.");
        String catchMessage = "El registro de ".concat(getUsuario().getNombre()).concat(" no pudo ser agregado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, true);
        return xhtml;
    }
    
    public String prepareEdit(Usuario usuario) {
        setUsuario(usuario);
        return "usuariosEdit";
    }
    
    public String update() {
        String xhtml = "usuariosAlta";
        String tryIfMessage = "El registro de ".concat(getUsuario().getNombre()).concat(" fue actualizado exitosamente.");
        String tryElseMessage = "El perfil ".concat(String.valueOf(getUsuario().getPerfil())).concat(" no es valido.");
        String catchMessage = "El registro de ".concat(getUsuario().getNombre()).concat(" no pudo ser actualizado. Contacte a soporte.");
        insertUpdate(xhtml, tryIfMessage, tryElseMessage, catchMessage, false);
        return "";
    }
    
    public String mainClean(String url){
        clean();
        setConfirm(false);
        return url;
    }
    
    public Usuario find(Long id) {
        return usuarioFacade.find(id);
    }
    
    public String prepareDelete() {
        setConfirm(true);
        return "usuariosList";
    }
    
    public void delete(Usuario usuario) {
        String xhtml = "usuariosEdit";
        try {
            setUsuario(usuario);
            usuarioFacade.delete(getUsuario());
            message.printMessageInfo(xhtml, "El registro de ".concat(usuario.getNombre()).concat(" fue eliminado exitosamente."));
        } catch(Exception e) {
            message.printMessageError(xhtml, "El registro de ".concat(usuario.getNombre()).concat(" no pudo ser eliminado. Contacte a soporte."));
        }
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
