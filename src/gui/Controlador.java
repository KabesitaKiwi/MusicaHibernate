package gui;

import com.erwinLagos.musica.Album;
import com.erwinLagos.musica.Autor;
import com.erwinLagos.musica.Cancion;
import com.erwinLagos.musica.Productora;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
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
        vista.botonAñadirAutor.addActionListener(listener);
        vista.botonAñadirAutor.setActionCommand("anadirAutor");
        vista.botonAñadirAlbum.addActionListener(listener);
        vista.botonAñadirAlbum.setActionCommand("anadirAlbum");
        vista.botonAñadirProductora.addActionListener(listener);
        vista.botonAñadirProductora.setActionCommand("anadirProd");
        vista.añadirButton.addActionListener(listener);
        vista.añadirButton.setActionCommand("anadirCancion");
        vista.eliminarButton.addActionListener(listener);
        vista.eliminarButton.setActionCommand("eliminarCancion");
        vista.botonEliminarAutor.addActionListener(listener);
        vista.botonEliminarAutor.setActionCommand("eliminarAutor");
        vista.botonEliminarAlbum.addActionListener(listener);
        vista.botonEliminarAlbum.setActionCommand("eliminarAlbum");
        vista.botonEliminarProd.addActionListener(listener);
        vista.botonEliminarProd.setActionCommand("eliminarProductora");
        vista.botonModificarAutor.addActionListener(listener);
        vista.botonModificarAutor.setActionCommand("actualizarAutor");
        vista.botonModificarAlbum.addActionListener(listener);
        vista.botonModificarAlbum.setActionCommand("actualizarAlbum");
        vista.botonModificarProd.addActionListener(listener);
        vista.botonModificarProd.setActionCommand("actualizarProductora");
        vista.modificarButton.addActionListener(listener);
        vista.modificarButton.setActionCommand("actualizarCancion");
        vista.itemSalir.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        vista.itemConectar.addActionListener(listener);
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
            case "anadirProd":
                registrarProductora();
                break;
            case "anadirAutor":
                registrarAutor();
                break;
            case "anadirAlbum":
                registrarAlbum();
                break;
        }
        limpiarCampos();
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
            return autor.getNombreArtistico();
        }
    }

    public void registrarProductora(){
        if (!Util.comprobarCampoVacio(vista.campoNombreProd)) {
            Util.lanzaAlertaVacio(vista.campoNombreProd);
        } else if (Util.comprobarCombobox(vista.comboLocalizacion)) {
            Util.lanzaAlertaCombo(vista.comboLocalizacion);
        } else if (!Util.comprobarSpinner(vista.campoNumTrabajadores)) {
            JOptionPane.showMessageDialog(null, "El campo Trabajadores no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaFundacion)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaFundacion);
        } else if (!Util.comprobarCampoVacio(vista.campoPropietario)) {
            Util.lanzaAlertaVacio(vista.campoPropietario);
        } else {
            Productora nuevaProductora = new Productora();
            nuevaProductora.setNombre(vista.campoNombreProd.getText());
            nuevaProductora.setLocalizacion(vista.comboLocalizacion.getSelectedItem().toString());
            nuevaProductora.setTrabajadores((Integer)vista.campoNumTrabajadores.getValue());
            //transformamos el LocalDate a Date, ya que la base de datos recibe Date y en el proyecto existe LocalDate
            LocalDate id = vista.campoFechaFundacion.getDate();
            nuevaProductora.setFechaFundacion(java.sql.Date.valueOf(id));
            nuevaProductora.setPropietario(vista.campoPropietario.getText());

            if (modelo.existeProductora(nuevaProductora.getNombre())){
                JOptionPane.showMessageDialog(null, "Esta Productora ya existe, cambia el nombre");
                vista.campoNombreProd.setText("");
                return;
            }

            if (modelo.insertar(nuevaProductora)) {
                JOptionPane.showMessageDialog(null, "La Productora ha sido registrado correctamente");
                borrarCamposProductora();

            } else {
                JOptionPane.showMessageDialog(null, "Productora no registrado ");
            }

        }
    }

    public void registrarAutor(){
        if (!Util.comprobarCampoVacio(vista.campoNombreArtistico)) {
            Util.lanzaAlertaVacio(vista.campoNombreArtistico);
        } else if (!Util.comprobarCampoVacio(vista.campoNombreReal)) {
            Util.lanzaAlertaVacio(vista.campoNombreReal);
        } else if (!Util.comprobarSpinner(vista.campoEdad)) {
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        } else if (Util.comprobarCombobox(vista.campoPais)) {
            Util.lanzaAlertaCombo(vista.campoPais);
        } else if (Util.campoVacioCalendario(vista.campoFechaPrimeraPubli)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
        } else {
            Autor nuevoAutor = new Autor();
            boolean gira = vista.siRadioButton.isSelected();
            nuevoAutor.setNombreArtistico(vista.campoNombreArtistico.getText());
            nuevoAutor.setNombreReal(vista.campoNombreReal.getText());
            nuevoAutor.setEdad((Integer)vista.campoEdad.getValue());
            nuevoAutor.setPais(vista.campoPais.getSelectedItem().toString());

            LocalDate id = vista.campoFechaPrimeraPubli.getDate();
            nuevoAutor.setFechaPrimeraPublicacion(java.sql.Date.valueOf(id));
            nuevoAutor.setGira(gira);

            if (modelo.existeAutor(nuevoAutor.getNombreArtistico())){
                JOptionPane.showMessageDialog(null, "Este artista ya existe, cambia el nombre artistico");
                vista.campoNombreProd.setText("");
                return;
            }

            if (modelo.insertar(nuevoAutor)) {
                JOptionPane.showMessageDialog(null, "El Autor ha sido registrado correctamente");
                borrarCamposProductora();

            } else {
                JOptionPane.showMessageDialog(null, "Autor no registrado ");
            }

        }
    }

    public void registrarAlbum(){
        if (!Util.comprobarCampoVacio(vista.campoTituloAlbum)) {
            Util.lanzaAlertaVacio(vista.campoTituloAlbum);
        } else if (!Util.comprobarSpinner(vista.campoNumCanciones)) {
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        } else if (!Util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaSalidaAlbum)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        } else if (Util.comprobarCombobox(vista.comboProductora)) {
            Util.lanzaAlertaCombo(vista.comboProductora);
        } else {
            Album nuevoAlbum = new Album();
            nuevoAlbum.setTitulo(vista.campoTituloAlbum.getText());

            //tengo que sacar el valor real del combovox del wrapper  que ya uso para el combobox
            AutorItem item = (AutorItem) vista.comboAutores.getSelectedItem();
            Autor autorReal = item.getAutor();
            nuevoAlbum.setAutor(autorReal);

            nuevoAlbum.setNumeroCanciones((Integer) vista.campoNumCanciones.getValue());
            nuevoAlbum.setDuracionMinutos(((Number)vista.campoNumDuracion.getValue()).intValue());

            LocalDate id = vista.campoFechaSalidaAlbum.getDate();
            nuevoAlbum.setFechaSalida(java.sql.Date.valueOf(id));

            ProductoraItem item2 = (ProductoraItem) vista.comboProductora.getSelectedItem();
            Productora productoraReal = item2.getProductora();
            nuevoAlbum.setProductora(productoraReal);

            if (modelo.existeAlbumPorAutor(nuevoAlbum.getAutor().getIdAutor(),nuevoAlbum.getTitulo())){
                JOptionPane.showMessageDialog(null, "Este Artista ya tiene un album con ese nombre, cambia el arttista o el nombre del album");
                vista.campoTituloAlbum.setText("");
                vista.campoAutor.setSelectedIndex(0);
                return;
            }

            if (modelo.insertar(nuevoAlbum)) {
                JOptionPane.showMessageDialog(null, "El Album ha sido registrado correctamente");
                borrarCamposAlbum();

            } else {
                JOptionPane.showMessageDialog(null, "Album no registrado ");
            }
        }
    }

    //limpiar Campos
    private void borrarCamposCancion() {
        vista.campoTituloCancion.setText("");
        vista.campoALbum.setSelectedIndex(0);
        vista.campoAutor.setSelectedIndex(0);
        vista.campoGenero.setText("");
        vista.campoProd.setSelectedIndex(0);
        vista.campoNumParticipantes.setValue(1);
        vista.campoDuracion.setValue(1);
        vista.españolCheckBox.setSelected(false);
        vista.inglesCheckBox.setSelected(false);
        vista.dembowCheckBox.setSelected(false);
    }


    private void borrarCamposProductora() {
        vista.campoNombreProd.setText("");
        vista.comboLocalizacion.setSelectedIndex(0);
        vista.campoNumTrabajadores.setValue(0);
        vista.campoFechaFundacion.setText("");
        vista.campoPropietario.setText("");
    }

    private void borrarCamposAutor() {
        vista.campoNombreArtistico.setText("");
        vista.campoNombreReal.setText("");
        vista.campoEdad.setValue(16);
        vista.campoPais.setSelectedIndex(0);
        vista.campoFechaPrimeraPubli.setText("");
        vista.siRadioButton.isSelected();
    }

    private void borrarCamposAlbum() {
        vista.campoTituloAlbum.setText("");
        vista.comboAutores.setSelectedIndex(0);
        vista.campoNumCanciones.setValue(1);
        vista.campoDuracion.setValue(30);
        vista.campoFechaSalidaAlbum.setText("");
        vista.comboProductora.setSelectedIndex(0);
    }

    private void limpiarCampos() {
        borrarCamposAlbum();
        borrarCamposCancion();
        borrarCamposAutor();
        borrarCamposProductora();
    }



}
