package gui;

import com.erwinLagos.musica.Album;
import com.erwinLagos.musica.Autor;
import com.erwinLagos.musica.Cancion;
import com.erwinLagos.musica.Productora;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Modelo {
    SessionFactory sessionFactory;

    public void desconectar() {
        if (sessionFactory != null && sessionFactory.isOpen())
            sessionFactory.close();
    }

    public void conectar(){
        Configuration configuracion  = new Configuration();

        configuracion.configure("hibernate.cfg.xml");

        configuracion.addAnnotatedClass(Album.class);
        configuracion.addAnnotatedClass(Autor.class);
        configuracion.addAnnotatedClass(Cancion.class);
        configuracion.addAnnotatedClass(Productora.class);

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(
                configuracion.getProperties()).build();

        sessionFactory = configuracion.buildSessionFactory(ssr);

    }

    ArrayList<Productora> getProd(){
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Productora");
        ArrayList<Productora> listaProductora = (ArrayList<Productora>)query.getResultList();
        sesion.close();
        return listaProductora;
    }

    ArrayList<Cancion>getCancion(){
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Cancion");
        ArrayList<Cancion> listarCancion =  (ArrayList<Cancion>)query.getResultList();
        sesion.close();
        return listarCancion;
    }

    ArrayList<Album>getAlbum(){
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Album");
        ArrayList<Album> listarAlbum =  (ArrayList<Album>)query.getResultList();
        sesion.close();
        return listarAlbum;
    }

    ArrayList<Autor>getAutor(){
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Autor");
        ArrayList<Autor> listarAutor =  (ArrayList<Autor>)query.getResultList();
        sesion.close();
        return listarAutor;
    }

}
