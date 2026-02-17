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

    //cargar Datos

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

    //insertar datos
    public boolean insertar(Object o){
        try (Session sesion = sessionFactory.openSession()) {

            sesion.beginTransaction();
            sesion.save(o);
            sesion.getTransaction().commit();
            sesion.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // eliminar
    void eliminar(Object o){
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(o);
        sesion.getTransaction().commit();
        sesion.close();
    }

    //modificar
    void modificar(Object o){
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(o);
        sesion.getTransaction().commit();
        sesion.close();
    }

    //verificar
    public boolean existeProductora(String nombre) {
        try (Session sesion = sessionFactory.openSession()) {
            Query<Integer> q = sesion.createQuery(
                    "select 1 from Productora where lower(trim(nombre) ) = :nombre", Integer.class
            );
            q.setParameter("nombre", nombre);
            q.setMaxResults(1);
            return q.uniqueResult() != null;
        }
    }

    public boolean existeAutor(String nombre){
        try (Session sesion = sessionFactory.openSession()) {
            Query<Integer> q = sesion.createQuery(
                    "select 1 from Autor where lower(trim(nombreArtistico) ) = :nombre", Integer.class
            );
            q.setParameter("nombre", nombre);
            q.setMaxResults(1);
            return q.uniqueResult() != null;
        }
    }

    public boolean existeAlbumPorAutor(int idAutor, String titulo) {
        try (Session sesion = sessionFactory.openSession()) {
            Query<Integer> q = sesion.createQuery(
                    "select 1 from Album a where a.autor.idAutor = :idAutor and a.titulo = :titulo",
                    Integer.class
            );
            q.setParameter("idAutor", idAutor);
            q.setParameter("titulo", titulo);
            q.setMaxResults(1);
            return q.uniqueResult() != null;
        }
    }



}
