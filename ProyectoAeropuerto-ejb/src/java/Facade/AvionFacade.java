//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Avion;
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
public class AvionFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Avion> findAllNamedQuery() {
        Query query;
        query = em.createNamedQuery("findAviones"); //Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList(); //Obtenemos los resultados de las consultas
    }
    
    public List<Avion> findByNumeroDeAvionAndCapacidadDePasajeros(String numeroAvion, int capacidadPasajeros) {
        Query query;
        query = em.createNamedQuery("findByNumeroDeAvionAndCapacidadDePasajeros"); //Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        query.setParameter("numeroAvion", numeroAvion); //Declaramos los parametros usados en la consulta mediante setParameter
        query.setParameter("capacidadPasajeros", capacidadPasajeros);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Avion> findAllTypedQuery() {
        TypedQuery<Avion> query;
        query = em.createQuery("SELECT a FROM Avion a", Avion.class); //Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Avion> findByNumeroAvion(String num) {
        TypedQuery<Avion> query;
        query = em.createQuery("SELECT a FROM Avion a WHERE a.numeroAvion=:numeroAvion", Avion.class); //Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        query.setParameter("numeroAvion", num); //Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Avion avion) {
        em.persist(avion);
    }
    
    public void update(Avion avion) {
        em.merge(avion);
    }
    
    public Avion find(Long id) {
        return em.find(Avion.class, id);
    }
    
    public void delete(Avion avion) {
        em.remove(em.merge(avion));
    }
    
    //Validaciones
    
    //Obtengo los aviones que tengan ese numero y si la lista esta vacia aun se puede usar ese numero
    public boolean numeroAvionUnico(String numeroAvion) {
        List<Avion> aviones;
        aviones = findByNumeroAvion(numeroAvion);
        return aviones.isEmpty();
    }
    
}
