package util;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class Util {
    public static boolean comprobarCampoVacio (JTextField campo){
        return !campo.getText().isEmpty();
    }

    public static void lanzaAlertaVacio(JTextField campo){
        JOptionPane.showMessageDialog(null, "El campo " + campo.getName() +" es obligatorio");
    }

    public static boolean comprobarCombobox(JComboBox combo){
        return combo.getSelectedIndex()== 0;
    }

    public static void lanzaAlertaCombo(JComboBox combo){
        JOptionPane.showMessageDialog(null, "Seleccione una opccion en: " + combo.getName() );
    }

    public static boolean comprobarSpinner(JSpinner spinner){
        return ((Number) spinner.getValue()).intValue()>=0;
    }

    public static void lanzaAlertaCero(JSpinner spinner){
        JOptionPane.showMessageDialog(null, "El campo " + spinner.getName() + " no puede ser 0");
    }

    public static boolean campoVacioCalendario(DatePicker campo){
        return campo.getDate() == null;
    }

    public static void lanzaAlertaVacioCalendar(DatePicker campo){
        JOptionPane.showMessageDialog(null,"El campo " + campo.getName() + "es obligatorio");
    }


    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
