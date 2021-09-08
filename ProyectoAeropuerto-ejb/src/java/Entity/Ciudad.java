//Persistence -> Entity Class
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "Ciudad")
@NamedQueries({
    @NamedQuery(name = "findCiudades", query = "SELECT h FROM Ciudad h"),
    @NamedQuery(name = "findByNombreDelPais", query = "SELECT c FROM Ciudad c WHERE c.estado.pais.nombre=:nombreDelPais")
})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "nombre", length = 35, nullable = false)
    private String nombre;
    
    @JoinColumn(name = "estado", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Estado estado;
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "origen")
    private List<Vuelo> vuelosOrigen;
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "destino")
    private List<Vuelo> vuelosDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Ciudad[ id=" + id + " ]";
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the vuelosOrigen
     */
    public List<Vuelo> getVuelosOrigen() {
        return vuelosOrigen;
    }

    /**
     * @param vuelosOrigen the vuelosOrigen to set
     */
    public void setVuelosOrigen(List<Vuelo> vuelosOrigen) {
        this.vuelosOrigen = vuelosOrigen;
    }

    /**
     * @return the vuelosDestino
     */
    public List<Vuelo> getVuelosDestino() {
        return vuelosDestino;
    }

    /**
     * @param vuelosDestino the vuelosDestino to set
     */
    public void setVuelosDestino(List<Vuelo> vuelosDestino) {
        this.vuelosDestino = vuelosDestino;
    }
    
}
