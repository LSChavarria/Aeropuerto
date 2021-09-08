//Persistence -> Entity Class
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "Vuelo")
@NamedQuery(name = "findVuelos", query = "SELECT h FROM Vuelo h")
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numeroVuelo", length = 20, nullable = false)
    private String numeroVuelo;

    @JoinColumn(name = "numeroAvion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Avion numeroAvion;

    @JoinColumn(name = "origen", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Ciudad origen;

    @JoinColumn(name = "destino", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Ciudad destino;
    
    @Column(name = "numeroPasajeros")
    private int numeroPasajeros;
    
    @Column(name= "fechaInicio")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name= "fechaFin")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    
    @Column(name= "horaInicio")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horaInicio;
    
    @Column(name= "horaFin")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horaFin;

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
        if (!(object instanceof Vuelo)) {
            return false;
        }
        Vuelo other = (Vuelo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Vuelo[ id=" + id + " ]";
    }

    /**
     * @return the numeroVuelo
     */
    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    /**
     * @param numeroVuelo the numeroVuelo to set
     */
    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    /**
     * @return the numeroAvion
     */
    public Avion getNumeroAvion() {
        return numeroAvion;
    }

    /**
     * @param numeroAvion the numeroAvion to set
     */
    public void setNumeroAvion(Avion numeroAvion) {
        this.numeroAvion = numeroAvion;
    }

    /**
     * @return the origen
     */
    public Ciudad getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public Ciudad getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }

    /**
     * @return the numeroPasajeros
     */
    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    /**
     * @param numeroPasajeros the numeroPasajeros to set
     */
    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
    
}
