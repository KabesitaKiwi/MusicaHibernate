package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.base.enums.LocalizacionProductora;
import gui.base.enums.PaisAutor;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame{

    private JPanel panel1;
    private final static String TITULO_FRAME="Canciones info";
    public JTabbedPane tabbedPane1;

    //canciones
    public JPanel PanelCanciones;
    public JTextField campoTituloCancion;
    public JComboBox campoALbum;
    public JComboBox campoAutor;
    public JComboBox campoProd;
    public JTextField campoGenero;
    public JSpinner campoNumParticipantes;
    public JSpinner campoDuracion;
    public JSlider campoValoracion;
    public JCheckBox dembowCheckBox;
    public JCheckBox inglesCheckBox;
    public JCheckBox españolCheckBox;
    public JButton añadirButton;
    public JButton modificarButton;
    public JButton eliminarButton;
    public JTable tablaCanciones;

    //autores
    public JPanel JpanelAutor;
    public JTextField campoNombreArtistico;
    public JTextField campoNombreReal;
    public JSpinner campoEdad;
    public JComboBox campoPais;
    public JRadioButton siRadioButton;
    public JRadioButton noRadioButton;
    public JButton botonAñadirAutor;
    public JButton botonModificarAutor;
    public JButton botonEliminarAutor;
    public JTable tablaAutor;
    public DatePicker campoFechaPrimeraPubli;

    //album
    public JPanel JpanelAlbum;
    public JTextField campoTituloAlbum;
    public JSpinner campoNumCanciones;
    public JSpinner campoNumDuracion;
    public JButton botonAñadirAlbum;
    public JButton botonModificarAlbum;
    public JButton botonEliminarAlbum;
    public JTable tablaAlbum;
    public DatePicker campoFechaSalidaAlbum;

    //productora
    public JPanel JpanelProductora;
    public JTextField campoNombreProd;
    public JComboBox comboLocalizacion;
    public JSpinner campoNumTrabajadores;
    public JTextField campoPropietario;
    public JButton botonAñadirProductora;
    public JButton botonModificarProd;
    public JButton botonEliminarProd;
    public JTable tablaProductora;
    public JScrollPane tabla;
    public DatePicker campoFechaFundacion;
    public JComboBox comboAutores;
    public JComboBox comboProductora;
    public JList listaCanciones;
    public JList listaAutores;
    public JList listaAlbumes;
    public JList listaProductora;
    //busqueda
    private JLabel etiquetasEstado;

    //default table model
    DefaultListModel dtmCanciones;
    DefaultListModel dtmAutores;
    DefaultListModel dtmAlbum;
    DefaultListModel dtmProductora;

    //menuBar
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;
    JMenuItem itemConectar;




    public Vista(){
        super(TITULO_FRAME);
        initFrame();
        initComponents();
    }

    public void initFrame() {
        this.setContentPane(panel1);

        this.pack();

        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        //llama al menu
        setMenu();
        //cargo tableModels
        crearModelos();
        //cargo Enums
        setEnumComboBox();
        itemDesconectar.setEnabled(false);
    }


    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        itemConectar = new JMenuItem("Conectar");
        itemConectar.setActionCommand("Conectar");
        menu.add(itemDesconectar);
        menu.add(itemConectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        this.setJMenuBar(mbBar);
    }


    public void crearModelos(){
        //tablas de autores, de canciones, de albumes y de productoras
        dtmCanciones = new DefaultListModel();
        listaCanciones.setModel(dtmCanciones);

        dtmAlbum = new DefaultListModel();
        listaAlbumes.setModel(dtmAlbum);

        dtmAutores = new DefaultListModel();
        listaAutores.setModel(dtmAutores);

        dtmProductora = new DefaultListModel();
        listaProductora.setModel(dtmProductora);


    }


    public void initComponents(){
        //valor predeterminado en diferentes spiner

        SpinnerNumberModel duracionCancion = new SpinnerNumberModel(3.5, 1, 600.0, 0.5);
        campoDuracion.setModel(duracionCancion);


        SpinnerNumberModel duracionAlbum = new SpinnerNumberModel(15, 1, 600.0, 1);
        campoNumDuracion.setModel(duracionAlbum);

        SpinnerNumberModel edad = new SpinnerNumberModel(16, 14, 100, 1);
        campoEdad.setModel(edad);

        SpinnerNumberModel participantes = new SpinnerNumberModel(1, 1, 10, 1);
        campoNumParticipantes.setModel(participantes);
        ((JSpinner.DefaultEditor) campoNumParticipantes.getEditor()).getTextField().setEditable(false);

        SpinnerNumberModel numeroCanciones = new SpinnerNumberModel(1, 1, 40, 1);
        campoNumCanciones.setModel(numeroCanciones);

        SpinnerNumberModel numeroTrabajadores = new SpinnerNumberModel(1, 1, 100, 1);
        campoNumTrabajadores.setModel(numeroTrabajadores);

        //valores de slider
        campoValoracion.setMinimum(0);     // Valor mínimo
        campoValoracion.setMaximum(10);    // Valor máximo
        campoValoracion.setValue(5);       // Valor inicialç
        campoValoracion.setMajorTickSpacing(1);  // Separación grande entre marcas
        campoValoracion.setPaintLabels(true);    // Mostrar los números debajo


        comboLocalizacion.setSelectedIndex(0);
        campoPais.setSelectedIndex(0);
    }

    private void setEnumComboBox(){
        //recorrer los enumerados y los cargo en el comboBox correspondiente
        //.values cogemos valores del enumerado
        //.getValor los añadimos al combo

        for(PaisAutor constant: PaisAutor.values()){
            campoPais.addItem(constant.getValor());
        }
        // se coloca en una posicion que no tenga valor
        campoPais.setSelectedIndex(-1);

        for (LocalizacionProductora cosntant: LocalizacionProductora.values()){
            comboLocalizacion.addItem(cosntant.getValor());
        }
        comboLocalizacion.setSelectedIndex(-1);
    }

}
