package gui;

import com.erwinLagos.musica.Album;
import com.erwinLagos.musica.Autor;
import com.erwinLagos.musica.Cancion;
import com.erwinLagos.musica.Productora;
import util.Utilidades;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;
    private Utilidades util;

    public Controlador(Modelo modelo, Vista vista, Utilidades util) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;
        this.util = util;

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
                vista.itemDesconectar.setEnabled(true);
                modelo.conectar();
                JOptionPane.showMessageDialog(null, "Usted se ha conectado a la base de datos, todo funcionará a la perfección");

                conectado = true;
                break;
            case"Desconectar":
                vista.itemDesconectar.setEnabled(false);
                vista.itemConectar.setEnabled(true);
                vista.dtmCanciones.clear();
                vista.dtmAlbum.clear();
                vista.dtmAutores.clear();
                vista.dtmProductora.clear();
                modelo.desconectar();
                JOptionPane.showMessageDialog(null, "Usted se ha desconectado de la base de datos, no funcionará nada");
                conectado = false;
                limpiarCampos();
                return;
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
            case "anadirCancion":
                registrarCancion();
                break;
            case "eliminarProductora":
                eliminarProductora();
                break;
            case "eliminarCancion":
                eliminarCancion();
                break;
            case "eliminarAutor":
                eliminarAutor();
                break;
            case"eliminarAlbum":
                eliminarAlbum();
                break;
            case "actualizarCancion":
                modificarCancion();
                break;
            case "actualizarAlbum":
                modificarAlbum();
                break;
            case"actualizarProductora":
                modificarProductora();
                break;
            case"actualizarAutor":
                modificarAutor();
                break;
        }
        limpiarCampos();
        actualizar();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()){
            if (e.getSource() == vista.listaCanciones){
                Cancion cancionSeleccion = (Cancion) vista.listaCanciones.getSelectedValue();
                Album a = cancionSeleccion.getAlbum();
                Autor au = cancionSeleccion.getAutor();
                Productora prod = cancionSeleccion.getProductora();
                vista.campoTituloCancion.setText(String.valueOf(cancionSeleccion.getTitulo()));
                vista.campoALbum.setSelectedItem(new AlbumItem(a));
                vista.campoAutor.setSelectedItem(new AutorItem(au));
                vista.campoTituloCancion.setText(String.valueOf(cancionSeleccion.getTitulo()));
                vista.campoGenero.setText(String.valueOf(cancionSeleccion.getGenero()));
                vista.campoProd.setSelectedItem(new ProductoraItem(prod));
                vista.campoDuracion.setValue(cancionSeleccion.getDuracion());
                vista.campoNumParticipantes.setValue(cancionSeleccion.getParticipantes());
                vista.campoValoracion.setValue(cancionSeleccion.getValoracion());

                String idiomas = cancionSeleccion.getIdioma();
                vista.españolCheckBox.setSelected(false);
                vista.dembowCheckBox.setSelected(false);
                vista.inglesCheckBox.setSelected(false);

                if (idiomas != null && !idiomas.isBlank()) {
                    // split por coma, quitando espacios
                    String[] parte = idiomas.split("\\s*,\\s*");
                    for (String s : parte) {
                        if ("Español".equalsIgnoreCase(s)) vista.españolCheckBox.setSelected(true);
                        else if ("Ingles".equalsIgnoreCase(s)) vista.inglesCheckBox.setSelected(true);
                        else if ("Dembow".equalsIgnoreCase(s)) vista.dembowCheckBox.setSelected(true);
                    }
                }
            }
            if (e.getSource() == vista.listaAutores){
                Autor autorSeleccion = (Autor) vista.listaAutores.getSelectedValue();

                vista.campoNombreArtistico.setText(autorSeleccion.getNombreArtistico());
                vista.campoNombreReal.setText(autorSeleccion.getNombreReal());
                vista.campoEdad.setValue(autorSeleccion.getEdad());
                vista.campoPais.setSelectedItem(autorSeleccion.getPais());
                vista.campoFechaPrimeraPubli.setDate(autorSeleccion.getFechaPrimeraPublicacion().toLocalDate());

                boolean gira = autorSeleccion.isGira();
                vista.siRadioButton.setSelected(gira);
                vista.noRadioButton.setSelected(!gira);
            }
            if (e.getSource() == vista.listaAlbumes){
                Album albumSeleccion = (Album) vista.listaAlbumes.getSelectedValue();
                Autor au = albumSeleccion.getAutor();
                Productora prod = albumSeleccion.getProductora();
                vista.campoTituloAlbum.setText(albumSeleccion.getTitulo());
                vista.campoNumCanciones.setValue(albumSeleccion.getNumeroCanciones());
                vista.campoNumDuracion.setValue(albumSeleccion.getDuracionMinutos());
                vista.campoFechaSalidaAlbum.setDate(albumSeleccion.getFechaSalida().toLocalDate());
                vista.comboAutores.setSelectedItem(new AutorItem(au));
                vista.comboProductora.setSelectedItem(new ProductoraItem(prod));

            }
            if (e.getSource() == vista.listaProductora){
                Productora prodSeleccion = (Productora) vista.listaProductora.getSelectedValue();
                vista.campoNombreProd.setText(prodSeleccion.getNombre());
                vista.comboLocalizacion.setSelectedItem(prodSeleccion.getLocalizacion());
                vista.campoNumTrabajadores.setValue(prodSeleccion.getTrabajadores());
                vista.campoFechaFundacion.setDate(prodSeleccion.getFechaFundacion().toLocalDate());
                vista.campoPropietario.setText(prodSeleccion.getPropietario());

            }
        }
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
        vista.campoALbum.setSelectedIndex(0);
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

        public AlbumItem(Album album) {
            this.album = album;
        }

        public Album getAlbum() {
            return album;
        }

        @Override
        public String toString() {
            return album.getTitulo();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AlbumItem)) return false;

            AlbumItem that = (AlbumItem) o;

            if (this.album == null || that.album == null) return false;
            return this.album.getIdAlbum() == that.album.getIdAlbum();
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(album != null ? album.getIdAlbum() : 0);
        }
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
            return productora.getNombre();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProductoraItem)) return false;

            ProductoraItem that = (ProductoraItem) o;

            if (this.productora == null || that.productora == null) return false;
            return this.productora.getIdProductora() == that.productora.getIdProductora();
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(productora != null ? productora.getIdProductora() : 0);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AutorItem)) return false;

            AutorItem that = (AutorItem) o;

            if (this.autor == null || that.autor == null) return false;
            return this.autor.getIdAutor() == that.autor.getIdAutor();
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(autor != null ? autor.getIdAutor() : 0);
        }
    }

    public void registrarProductora(){
        if (!util.comprobarCampoVacio(vista.campoNombreProd)) {
            util.lanzaAlertaVacio(vista.campoNombreProd);
        } else if (util.comprobarCombobox(vista.comboLocalizacion)) {
            util.lanzaAlertaCombo(vista.comboLocalizacion);
        } else if (!util.comprobarSpinner(vista.campoNumTrabajadores)) {
            JOptionPane.showMessageDialog(null, "El campo Trabajadores no puede ser menor que 0");
        } else if (util.campoVacioCalendario(vista.campoFechaFundacion)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaFundacion);
        } else if (!util.comprobarCampoVacio(vista.campoPropietario)) {
            util.lanzaAlertaVacio(vista.campoPropietario);
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
        if (!util.comprobarCampoVacio(vista.campoNombreArtistico)) {
            util.lanzaAlertaVacio(vista.campoNombreArtistico);
        } else if (!util.comprobarCampoVacio(vista.campoNombreReal)) {
            util.lanzaAlertaVacio(vista.campoNombreReal);
        } else if (!util.comprobarSpinner(vista.campoEdad)) {
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        } else if (util.comprobarCombobox(vista.campoPais)) {
            util.lanzaAlertaCombo(vista.campoPais);
        } else if (util.campoVacioCalendario(vista.campoFechaPrimeraPubli)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
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
        if (!util.comprobarCampoVacio(vista.campoTituloAlbum)) {
            util.lanzaAlertaVacio(vista.campoTituloAlbum);
        } else if (!util.comprobarSpinner(vista.campoNumCanciones)) {
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        } else if (!util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        } else if (util.campoVacioCalendario(vista.campoFechaSalidaAlbum)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        } else if (util.comprobarCombobox(vista.comboProductora)) {
            util.lanzaAlertaCombo(vista.comboProductora);
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

    public void registrarCancion(){
        if (!util.comprobarCampoVacio(vista.campoTituloCancion)) {
            util.lanzaAlertaVacio(vista.campoTituloCancion);
        } else if (!util.comprobarCampoVacio(vista.campoGenero)) {
            util.lanzaAlertaVacio(vista.campoGenero);
        } else if (!util.comprobarSpinner(vista.campoNumParticipantes)) {
            JOptionPane.showMessageDialog(null, "El Nº de participantes no puede ser menor que 0");
        } else if (!util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "La duración no puede ser menor o igual que 0");
        } else if (!vista.españolCheckBox.isSelected() && !vista.inglesCheckBox.isSelected() && !vista.dembowCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecciona al menos un idioma");
        } else {

            Cancion nuevaCancion = new Cancion();

            nuevaCancion.setTitulo(vista.campoTituloCancion.getText());

            AlbumItem itemAlbum = (AlbumItem) vista.campoALbum.getSelectedItem();
            Album albumReal = itemAlbum.getAlbum();
            nuevaCancion.setAlbum(albumReal);


            AutorItem itemAutor = (AutorItem) vista.campoAutor.getSelectedItem();
            Autor autorReal = itemAutor.getAutor();
            nuevaCancion.setAutor(autorReal);

            nuevaCancion.setGenero(vista.campoGenero.getText());

            ProductoraItem itemProd = (ProductoraItem) vista.campoProd.getSelectedItem();
            Productora prodReal = itemProd.getProductora();
            nuevaCancion.setProductora(prodReal);

            nuevaCancion.setParticipantes(((Number)vista.campoNumParticipantes.getValue()).intValue());
            nuevaCancion.setDuracion(((Number)vista.campoDuracion.getValue()).floatValue());
            nuevaCancion.setValoracion(vista.campoValoracion.getValue());

            StringBuilder idioma = new StringBuilder();

            if (vista.españolCheckBox.isSelected()) idioma.append("Español,");
            if (vista.inglesCheckBox.isSelected()) idioma.append("Ingles,");
            if (vista.dembowCheckBox.isSelected()) idioma.append("Dembow,");
            idioma.deleteCharAt(idioma.length() - 1);

            nuevaCancion.setIdioma(idioma.toString());

            if(modelo.existeCancionPorAutor(nuevaCancion.getAutor().getIdAutor(), nuevaCancion.getTitulo())){
                JOptionPane.showMessageDialog(null, "Esa canción ya existe en este Artista.");
                vista.campoTituloCancion.setText("");
                return;
            }
            if (modelo.existeCancionPorAlbum(nuevaCancion.getAlbum().getIdAlbum(), nuevaCancion.getTitulo())) {
                JOptionPane.showMessageDialog(null, "Esa canción ya existe en este álbum.");
                vista.campoTituloCancion.setText("");
                return;
            }

            if (modelo.insertar(nuevaCancion)){
                JOptionPane.showMessageDialog(null, "La canción se ha registrado correctamente");
                borrarCamposCancion();
            } else {
                JOptionPane.showMessageDialog(null, "Canción no registrada");
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
    private void eliminarProductora(){
        Productora p = (Productora) vista.listaProductora.getSelectedValue();
        if (!comprobarProductoraCancion(p.getIdProductora()) || !comprobarProductoraAlbum(p.getIdProductora())) {
            JOptionPane.showMessageDialog(null, "Esta productora tiene canciones asociadas. Elimina o reasigna primero esas canciones.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modelo.eliminar(p);
        JOptionPane.showMessageDialog(null, "Productora eliminada correctamente");

    }
    private void eliminarAlbum(){
        Album a = (Album) vista.listaAlbumes.getSelectedValue();
        if (!comprobarAlbumCancion(a.getIdAlbum())) {
            JOptionPane.showMessageDialog(null, "Este Album tiene canciones asociadas, Elimina o cambia las canciones",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modelo.eliminar(a);
        JOptionPane.showMessageDialog(null, "Album eliminado correctamente");

    }
    private void eliminarCancion(){
        Cancion c = (Cancion) vista.listaCanciones.getSelectedValue();
        modelo.eliminar(c);
        JOptionPane.showMessageDialog(null, "Canción eliminada correctamente");
    }
    private void eliminarAutor(){
        Autor a = (Autor) vista.listaAutores.getSelectedValue();
        if (!comprobarAutorCancion(a.getIdAutor())) {
            JOptionPane.showMessageDialog(null, "Este Autor tiene canciones asociadas, Elimina o cambia las canciones",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modelo.eliminar(a);
        JOptionPane.showMessageDialog(null, "Autor eliminado correctamente");
    }

    private boolean comprobarProductoraCancion(int id){
        return !modelo.productoraTieneCanciones(id);
    }

    private boolean comprobarProductoraAlbum(int id){
        return !modelo.productoraTieneAlbumes(id);
    }

    private boolean comprobarAlbumCancion(int id){
        return !modelo.albumTieneCancion(id);
    }

    private boolean comprobarAutorCancion(int id){
        return !modelo.autorTieneCancion(id);
    }

    //modificar
    private void modificarCancion(){
        if (!util.comprobarCampoVacio(vista.campoTituloCancion)) {
            util.lanzaAlertaVacio(vista.campoTituloCancion);
        } else if (!util.comprobarCampoVacio(vista.campoGenero)) {
            util.lanzaAlertaVacio(vista.campoGenero);
        } else if (!util.comprobarSpinner(vista.campoNumParticipantes)) {
            JOptionPane.showMessageDialog(null, "El Nº de participantes no puede ser menor que 0");
        } else if (!util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "La duración no puede ser menor o igual que 0");
        } else if (!vista.españolCheckBox.isSelected() && !vista.inglesCheckBox.isSelected() && !vista.dembowCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecciona al menos un idioma");
        } else {
            Cancion c = (Cancion)vista.listaCanciones.getSelectedValue();
            c.setTitulo(vista.campoTituloCancion.getText());

            AlbumItem itemAlbum = (AlbumItem) vista.campoALbum.getSelectedItem();
            Album albumReal = itemAlbum.getAlbum();
            c.setAlbum(albumReal);


            AutorItem itemAutor = (AutorItem) vista.campoAutor.getSelectedItem();
            Autor autorReal = itemAutor.getAutor();
            c.setAutor(autorReal);

            c.setGenero(vista.campoGenero.getText());

            ProductoraItem itemProd = (ProductoraItem) vista.campoProd.getSelectedItem();
            Productora prodReal = itemProd.getProductora();
            c.setProductora(prodReal);

            c.setParticipantes(((Number)vista.campoNumParticipantes.getValue()).intValue());
            c.setDuracion(((Number)vista.campoDuracion.getValue()).floatValue());
            c.setValoracion(vista.campoValoracion.getValue());

            StringBuilder idioma = new StringBuilder();

            if (vista.españolCheckBox.isSelected()) idioma.append("Español,");
            if (vista.inglesCheckBox.isSelected()) idioma.append("Ingles,");
            if (vista.dembowCheckBox.isSelected()) idioma.append("Dembow,");
            idioma.deleteCharAt(idioma.length() - 1);

            c.setIdioma(idioma.toString());


            if (modelo.modificar(c)){
                JOptionPane.showMessageDialog(null, "La canción se ha actualizado correctamente");
                borrarCamposCancion();
            } else {
                JOptionPane.showMessageDialog(null, "Canción no Actualizada");
            }
        }
    }

    private void modificarAlbum(){
        if (!util.comprobarCampoVacio(vista.campoTituloAlbum)) {
            util.lanzaAlertaVacio(vista.campoTituloAlbum);
        } else if (!util.comprobarSpinner(vista.campoNumCanciones)) {
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        } else if (!util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        } else if (util.campoVacioCalendario(vista.campoFechaSalidaAlbum)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        } else {
            Album a = (Album) vista.listaAlbumes.getSelectedValue();
            a.setTitulo(vista.campoTituloAlbum.getText());

            //tengo que sacar el valor real del combovox del wrapper  que ya uso para el combobox
            AutorItem item = (AutorItem) vista.comboAutores.getSelectedItem();
            Autor autorReal = item.getAutor();
            a.setAutor(autorReal);

            a.setNumeroCanciones((Integer) vista.campoNumCanciones.getValue());
            a.setDuracionMinutos(((Number)vista.campoNumDuracion.getValue()).intValue());

            LocalDate id = vista.campoFechaSalidaAlbum.getDate();
            a.setFechaSalida(java.sql.Date.valueOf(id));

            ProductoraItem item2 = (ProductoraItem) vista.comboProductora.getSelectedItem();
            Productora productoraReal = item2.getProductora();
            a.setProductora(productoraReal);


            if (modelo.modificar(a)) {
                JOptionPane.showMessageDialog(null, "El Album ha sido actualizado correctamente");
                borrarCamposAlbum();

            } else {
                JOptionPane.showMessageDialog(null, "Album no actualizado ");
            }
        }
    }

    public void modificarAutor(){
        if (!util.comprobarCampoVacio(vista.campoNombreArtistico)) {
            util.lanzaAlertaVacio(vista.campoNombreArtistico);
        } else if (!util.comprobarCampoVacio(vista.campoNombreReal)) {
            util.lanzaAlertaVacio(vista.campoNombreReal);
        } else if (!util.comprobarSpinner(vista.campoEdad)) {
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        } else if (util.comprobarCombobox(vista.campoPais)) {
            util.lanzaAlertaCombo(vista.campoPais);
        } else if (util.campoVacioCalendario(vista.campoFechaPrimeraPubli)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
        } else {
            Autor au = (Autor) vista.listaAutores.getSelectedValue();
            boolean gira = vista.siRadioButton.isSelected();
            au.setNombreArtistico(vista.campoNombreArtistico.getText());
            au.setNombreReal(vista.campoNombreReal.getText());
            au.setEdad((Integer)vista.campoEdad.getValue());
            au.setPais(vista.campoPais.getSelectedItem().toString());

            LocalDate id = vista.campoFechaPrimeraPubli.getDate();
            au.setFechaPrimeraPublicacion(java.sql.Date.valueOf(id));
            au.setGira(gira);

            if (modelo.modificar(au)) {
                JOptionPane.showMessageDialog(null, "El Autor ha sido actualizado correctamente");
                borrarCamposProductora();

            } else {
                JOptionPane.showMessageDialog(null, "Autor no actualizado ");
            }

        }
    }

    public void modificarProductora(){
        if (!util.comprobarCampoVacio(vista.campoNombreProd)) {
            util.lanzaAlertaVacio(vista.campoNombreProd);
        } else if (util.comprobarCombobox(vista.comboLocalizacion)) {
            util.lanzaAlertaCombo(vista.comboLocalizacion);
        } else if (!util.comprobarSpinner(vista.campoNumTrabajadores)) {
            JOptionPane.showMessageDialog(null, "El campo Trabajadores no puede ser menor que 0");
        } else if (util.campoVacioCalendario(vista.campoFechaFundacion)) {
            util.lanzaAlertaVacioCalendar(vista.campoFechaFundacion);
        } else if (!util.comprobarCampoVacio(vista.campoPropietario)) {
            util.lanzaAlertaVacio(vista.campoPropietario);
        } else {
            Productora prod = (Productora) vista.listaProductora.getSelectedValue();
            prod.setNombre(vista.campoNombreProd.getText());
            prod.setLocalizacion(vista.comboLocalizacion.getSelectedItem().toString());
            prod.setTrabajadores((Integer)vista.campoNumTrabajadores.getValue());
            //transformamos el LocalDate a Date, ya que la base de datos recibe Date y en el proyecto existe LocalDate
            LocalDate id = vista.campoFechaFundacion.getDate();
            prod.setFechaFundacion(java.sql.Date.valueOf(id));
            prod.setPropietario(vista.campoPropietario.getText());

            if (modelo.modificar(prod)) {
                JOptionPane.showMessageDialog(null, "La Productora ha sido actualizada correctamente");
                borrarCamposProductora();

            } else {
                JOptionPane.showMessageDialog(null, "Productora no actualizada ");
            }

        }
    }

}
