package gui;

import com.erwinLagos.musica.Album;
import com.erwinLagos.musica.Autor;
import com.erwinLagos.musica.Cancion;
import com.erwinLagos.musica.Productora;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;

    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;

        addActionListeners(this);
        addListSelectionListener(this);
    }

    private void addActionListeners(ActionListener listener) {
        vista.itemConectar.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.botonEliminarProd.addActionListener(listener);
        vista.botonAñadirProductora.addActionListener(listener);
        vista.botonModificarProd.addActionListener(listener);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        vista.listaCanciones.addListSelectionListener(listener);
        vista.listaAlbumes.addListSelectionListener(listener);
        vista.listaAutores.addListSelectionListener(listener);
        vista.listaProductora.addListSelectionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (!conectado && !comando.equalsIgnoreCase("Conectar")){
            JOptionPane.showMessageDialog(null,"No te has conectado con la base de datos", "Error de conexion", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (comando){
            case "Salir":
                modelo.desconectar();
                System.exit(0);
                break;
            case "Conectar":
                vista.itemConectar.setEnabled(false);
                modelo.conectar();
                conectado = true;
                break;
            case "listarProductoras":
                listarProductoras(modelo.getProd());
                break;
            case "listarCancion":
                listarCancion(modelo.getCancion());
                break;
            case "listarAlbum":
                listarAlbum(modelo.getAlbum());
                break;
            case "listarAutor":
                listarAutor(modelo.getAutor());
                break;
        }
        actualizar();
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {

    }

    public void listarProductoras(ArrayList<Productora> lista){
        vista.dtmProductora.clear();
        for (Productora unaProductora : lista){
            vista.dtmProductora.addElement(unaProductora);
        }
        vista.campoProd.removeAllItems();
        vista.comboProductora.removeAllItems();

        for (Productora p : lista) {
            vista.campoProd.addItem(new ProductoraItem(p));
            vista.comboProductora.addItem(new ProductoraItem(p));// combo solo nombre
        }

        vista.campoProd.setSelectedIndex(0);
    }
    public void listarCancion(ArrayList<Cancion> lista){
        vista.dtmCanciones.clear();
        for (Cancion unaCancion : lista){
            vista.dtmCanciones.addElement(unaCancion);
        }
    }

    public void listarAlbum(ArrayList<Album> lista){
        vista.dtmAlbum.clear();
        for (Album unAlbum : lista){
            vista.dtmAlbum.addElement(unAlbum);
        }
        vista.campoALbum.removeAllItems();

        for (Album a : lista) {
            vista.campoALbum.addItem(new AlbumItem(a));
        }
        vista.campoALbum.setSelectedItem(0);
    }

    public void listarAutor(ArrayList<Autor> lista){
        vista.dtmAutores.clear();
        for (Autor unAutor : lista){
            vista.dtmAutores.addElement(unAutor);
        }
        vista.campoAutor.removeAllItems();
        vista.comboAutores.removeAllItems();

        for (Autor a : lista) {
            vista.campoAutor.addItem(new AutorItem(a));
            vista.comboAutores.addItem(new AutorItem(a));
        }
        vista.campoAutor.setSelectedItem(0);
    }

    private void actualizar(){
        listarProductoras(modelo.getProd());
        listarCancion(modelo.getCancion());
        listarAlbum(modelo.getAlbum());
        listarAutor(modelo.getAutor());
    }

    //metodo para cargar SOLO el nombre del album para los combobox sin render (Hecho con Wrappersa)
    public class AlbumItem {
        private final Album album;

        public AlbumItem(Album album) { this.album = album; }
        public Album getAlbum() { return album; }

        @Override
        public String toString() { return album.getTitulo(); }
    }
    public class ProductoraItem {
        private final Productora productora;

        public ProductoraItem(Productora productora) {
            this.productora = productora;
        }

        public Productora getProductora() {
            return productora;
        }
        @Override
        public String toString() {
            return productora.getNombre(); // solo el nombre en el combo
        }
    }

    public class AutorItem {
        private final Autor autor;

        public AutorItem(Autor autor) {
            this.autor = autor;
        }
        public Autor getAutor() {
            return autor;
        }
        @Override
        public String toString() {
            return autor.getNombreReal();
        }
    }

}
