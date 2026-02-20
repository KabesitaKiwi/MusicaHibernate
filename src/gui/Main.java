package gui;

import gui.Controlador;
import gui.Vista;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.Utilidades;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Utilidades util = new Utilidades();
        Controlador controlador = new Controlador(modelo, vista, util);
    }
}