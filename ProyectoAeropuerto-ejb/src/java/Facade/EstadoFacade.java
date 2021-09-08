//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Estado;
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
public class EstadoFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Estado> fingAllNamedQuery(){
        Query query;
        query = em.createNamedQuery("findEstados");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Estado> fingAllTypedQuery(){
        TypedQuery<Estado> query;
        query = em.createQuery("SELECT e FROM Estado e", Estado.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Estado estado){
        em.persist(estado);
    }
    
    public void update(Estado estado){
        em.merge(estado);
    }
    
    public Estado find(Long id){
        return em.find(Estado.class, id);
    }
    
    public void delete(Estado estado) {
        em.remove(em.merge(estado));
    }
    
}
