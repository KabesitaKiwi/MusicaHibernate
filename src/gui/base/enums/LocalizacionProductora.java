package gui.base.enums;

public enum LocalizacionProductora {
    SELECCIONE("Seleccione"),
    ESPAÑA("España"),
    REPUBLICADOMINICANA("República dominicana"),
    COLOMBIA("Colombia"),
    PUERTORICO("Puerto Rico"),
    ESTADOSUNIDOS("Estados unidos"),
    ARGENTINA("Argentina"),
    COREA("Corea"),
    ANDORRA("Andorra"),
    ALEMANIA("Alemania"),
    PERU("Perú"),
    COCHABAMBA("Cochabamba"),
    GUARROMAN("Guarroman"),
    CHINA("China");

    private String valor;

    LocalizacionProductora(String valor){
        this.valor = valor;
    }

    public String getValor(){

        return valor;
    }
}
