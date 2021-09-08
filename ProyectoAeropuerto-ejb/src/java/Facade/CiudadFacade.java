//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Ciudad;
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
public class CiudadFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Ciudad> fingAllNamedQuery(){
        Query query;
        query = em.createNamedQuery("findCiudades");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Ciudad> findByNombreDelPais(String nombreDelPais){
        Query query;
        query = em.createNamedQuery("findByNombreDelPais");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        query.setParameter("nombreDelPais", nombreDelPais);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Ciudad> fingAllTypedQuery(){
        TypedQuery<Ciudad> query;
        query = em.createQuery("SELECT h FROM Ciudad h", Ciudad.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Ciudad> findByNombreEstado(String nombreEstado){
        TypedQuery query;
        query = em.createQuery("SELECT c FROM Ciudad c WHERE c.estado.nombre=:nombreEstado", Ciudad.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        query.setParameter("nombreEstado", nombreEstado);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Ciudad ciudad) {
        em.persist(ciudad);
    }
    
    public void update(Ciudad ciudad) {
        em.merge(ciudad);
    }
    
    public Ciudad find(Long id) {
        return em.find(Ciudad.class, id);
    }
    
    public void delete(Ciudad ciudad) {
        em.remove(em.merge(ciudad));
    }
    
    //Validaciones
    
    //Obtengo las ciudades con ese nombre y ese estado y si la lista es vacia se puede usar ese nombre
    public boolean nombreDeCiudadUnicoPorEstado(Ciudad ciudad) {
        TypedQuery query;
        query = em.createQuery("SELECT c FROM Ciudad c WHERE UPPER(c.nombre)=:nombre AND UPPER(c.estado.nombre)=:estado", Ciudad.class);
        query.setParameter("nombre", ciudad.getNombre().toUpperCase());
        query.setParameter("estado", ciudad.getEstado().getNombre().toUpperCase());
        return query.getResultList().isEmpty();
    }
    
}
