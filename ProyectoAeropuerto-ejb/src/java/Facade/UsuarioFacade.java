//Enterprise JavaBeans -> Session Bean
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Usuario;
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
public class UsuarioFacade {

    @PersistenceContext(unitName = "ProyectoAeropuerto-ejbPU")
    private EntityManager em;
    
    //Named Query se declara desde la declaracion de la tabla
    
    public List<Usuario> findAllNamedQuery() {
        Query query;
        query = em.createNamedQuery("findUsuarios");//Llamamos al metodo createNamedQuery y le pasamos el nombre del query que queremos invocar
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Typed Query se declara como parametro del metodo createQuery
    
    public List<Usuario> findAllTypedQuery() {
        TypedQuery<Usuario> query;
        query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Usuario> findEmailContrasena(String email, String contrasena) {
        TypedQuery<Usuario> query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.email=:email AND u.contrasena=:contrasena", Usuario.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        query.setParameter("email", email);//Declaramos los parametros usados en la consulta mediante setParameter
        query.setParameter("contrasena", contrasena);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    public List<Usuario> findCorreo(String email) {
        TypedQuery<Usuario> query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.email=:email", Usuario.class);//Llamamos al metodo createQuery y le pasamos el query que queremos hacer
        query.setParameter("email", email);//Declaramos los parametros usados en la consulta mediante setParameter
        return query.getResultList();//Obtenemos los resultados de las consultas
    }
    
    //Altas, bajas, modificaciones y consultas por ID
    
    public void insert(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void update(Usuario usuario) {
        em.merge(usuario);
    }
    
    public Usuario find(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public void delete(Usuario usuario) {
        em.remove(em.merge(usuario));
    }
    
    //Validaciones
    
    //Se valida que el perfil este entre 0 y 3
    public boolean perfilValido(int perfil){
        return perfil == 0 || perfil == 1 || perfil == 2 || perfil == 3;
    }
}
