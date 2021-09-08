//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Pais;
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
public class PaisFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Pais> fingAllNamedQuery(){
        Query query;
        query = em.createNamedQuery("findPaises");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Pais> findByNombre(String nombreDelPais){
        Query query;
        query = em.createNamedQuery("findByNombre");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        query.setParameter("nombreDelPais", nombreDelPais);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Pais> fingAllTypedQuery(){
        TypedQuery<Pais> query;
        query = em.createQuery("SELECT p FROM Pais p", Pais.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Pais pais) {
        em.persist(pais);
    }
    
    public void update(Pais pais) {
        em.merge(pais);
    }
    
    public Pais find(Long id){
        return em.find(Pais.class, id);
    }
    
    public void delete(Pais pais){
        em.remove(em.merge(pais));
    }
    
}
