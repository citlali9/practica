/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hbm;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author RigoBono
 */
public class HibernateUtil {

    
    //Crea y maneja las sesiones
    private static final SessionFactory sessionFactory;
    //Indica que se creara uno y solo un hilo para cada sesion
    private static final ThreadLocal localSession;
    
    // indica que Solo se ejectua una vez
    static {
        try {
            
            //Archivo que da los protocolo e indica los mapeos a utilizar
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            //Se crea un objeto y una instancia de tipo SStandardServiceRegistryBuilder
            //protocolo que registra y guarda las sesiones 
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
            //Registra las sesiones (un protocolo de eventos en la session)         
            applySettings(config.getProperties());
            //Asigna las sesiones a un build 
            sessionFactory = config.buildSessionFactory(builder.build());
            //cundo no se pudo hacer la sesion
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        //manda la secion en un hilo
        localSession = new ThreadLocal();
    }
    
    // objeto que retorna sesion factory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    //Este metodo obtiene la sesion si no existe la crea. 
     public static Session getLocalSession() {
        Session session = (Session) localSession.get();
        if (session == null) {
            session = sessionFactory.openSession();
            localSession.set(session);
            System.out.println("\nsesion iniciada");
        }
        return session;
    }
     
     // Este metodo elimina la sesion.
     public static void closeLocalSession() {
        Session session = (Session) localSession.get();
        if (session != null) session.close();
        localSession.set(null);
        System.out.println("sesion cerrada\n");
    }
}
