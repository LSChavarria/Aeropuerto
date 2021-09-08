//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Vuelo;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author sebas
 */
@Stateless
@LocalBean
public class VueloFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Vuelo> fingAllNamedQuery(){
        Query query;
        query = em.createNamedQuery("findVuelos");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Vuelo> fingAllTypedQuery(){
        TypedQuery<Vuelo> query;
        query = em.createQuery("SELECT v FROM Vuelo v", Vuelo.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Vuelo> findByNumeroDeVuelo(String numeroVuelo) {
        TypedQuery<Vuelo> query;
        query = em.createQuery("SELECT v FROM Vuelo v WHERE v.numeroVuelo=:numeroVuelo", Vuelo.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        query.setParameter("numeroVuelo", numeroVuelo);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Vuelo vuelo) {
        em.persist(vuelo);
    }
    
    public void update(Vuelo vuelo) {
        em.merge(vuelo);
    }
    
    public Vuelo find(Long id) {
        return em.find(Vuelo.class, id);
    }
    
    public void delete(Vuelo vuelo){
        em.remove(em.merge(vuelo));
    }
    
    //Validaciones
    
    //Obtiene los vuelos con ese numero y si la lista es vacia se puede usar ese numero
    public boolean numeroVueloUnico(String numeroVuelo) {
        List<Vuelo> vuelos;
        vuelos = findByNumeroDeVuelo(numeroVuelo);
        return vuelos.isEmpty();
    }
    
    //Comprueba que el origen y el destino sean diferentes: true si son diferentes y false si son iguales
    public boolean diferenteOrigenDestino(Vuelo vuelo) {
        String ciudadOrigen;
        String ciudadDestino;
        String estadoOrigen;
        String estadoDestino;
        boolean esIgual;
        ciudadOrigen = vuelo.getOrigen().getNombre();
        ciudadDestino = vuelo.getDestino().getNombre();
        estadoOrigen = vuelo.getOrigen().getEstado().getNombre();
        estadoDestino = vuelo.getDestino().getEstado().getNombre();
        esIgual = ciudadOrigen.equalsIgnoreCase(ciudadDestino) && estadoOrigen.equalsIgnoreCase(estadoDestino);
        return !esIgual;
    }
    
    //Retorna la diferencia entre dos fechas en dias
    public long diferenciaDias(Vuelo vuelo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateDespegue[] = sdf.format(vuelo.getFechaInicio()).split("-");
        String dateAterrizaje[] = sdf.format(vuelo.getFechaFin()).split("-");
        
        LocalDate fechaDespegue = LocalDate.of(Integer.parseInt(dateDespegue[2]), Integer.parseInt(dateDespegue[1]), Integer.parseInt(dateDespegue[0]));
        LocalDate fechaAterrizaje = LocalDate.of(Integer.parseInt(dateAterrizaje[2]), Integer.parseInt(dateAterrizaje[1]), Integer.parseInt(dateAterrizaje[0]));
        
        return ChronoUnit.DAYS.between(fechaDespegue, fechaAterrizaje);
    }
    
    //Valida que haya al menos una hora de diferencia entre el despegue y el aterrizaje
    public boolean unaHoraDespues(Vuelo vuelo) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH: mm: ss");
        String horaDespegue[] = sdf.format(vuelo.getHoraInicio()).split(":");
        String horaAterrizaje[] = sdf.format(vuelo.getHoraFin()).split(":");
        
        int difHoras = Integer.parseInt(horaAterrizaje[0]) - Integer.parseInt(horaDespegue[0]);
        
        return difHoras >= 1;
    }
    
}
