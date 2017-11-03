/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Persona;
import pojo.TipoPersona;

/**
 *
 * @author RigoBono
 */
public class PersonaDAO {
    // Crea un objeto session
    Session session;
    
    // constructor
    public PersonaDAO(){
        // inicializa session interfaz para comunicacion con HibernateUtil
        session=HibernateUtil.getLocalSession();
    }
    // retorna el del objeto persona los datos que se encuentrarn en el id que llega
    public  Persona getPersonaById(int id){
        return (Persona)session.load(Persona.class, id);
    }
    // crea una lista tipo persona que obtiene parametros con el filtro (criterios) de nombre y los agrega
    public List<Persona>  getPersonaByName(String nombre){
        List<Persona> listaDePersonas=(List<Persona>)
                session.createCriteria(Persona.class)
                .add(Restrictions.eq("Nombre", nombre));
        //de la session se crea un criterio de la clase persona
        //retorna los datos que coincidan con el filtro en una lista
        return listaDePersonas;
    }
    
    //Actualiza la posicion id con los nuevos datos en el objeto persona
    public boolean updateById(int id,Persona persona){
        Persona personaAModificar=getPersonaById(id);
        try{
            // indica que se ecomprueba si se perimite realizar la trnsaccion(cambio/actualizacion)
            Transaction transaccion=session.beginTransaction();
            //si la anterior fue autorizada se obtiene el dato a modificar del objeto persona que llego 
            personaAModificar.setNombre(persona.getNombre());
            // se asigna ese nuevo valor al parametro ingresado
            session.update(personaAModificar);
            // se actualiza la base de datos
            transaccion.commit();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    //Crea una persona y la gurda
    public boolean savePersona(String nombre,String materno,String paterno,String telefono,int idTipoPersona){
        Persona personaDeTanque=new Persona();
        //Da inicio a una session y ingresa al id que llega para conocer el tipo persona
        TipoPersona tipoDeTanque=(TipoPersona)session.load(TipoPersona.class, idTipoPersona);
        // ingresa a sus parametros para ser llenados
        personaDeTanque.setNombre(nombre);
        personaDeTanque.setMaterno(materno);
        personaDeTanque.setPaterno(paterno);
        personaDeTanque.setTelefono(telefono);
        personaDeTanque.setTipoPersona(tipoDeTanque);
        try{
            // inicia tranzaccion
            Transaction transaccion=session.beginTransaction();
            // guarda el nuev objeto. 
            session.save(personaDeTanque);
            // Actualizacion
            transaccion.commit();
            return true;
        }catch(Exception e){
            
        }finally{
            
        }
        return true;
    }
    
}
