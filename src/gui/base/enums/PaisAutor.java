package gui.base.enums;

public enum PaisAutor {
    SELECCIONE("Seleccione"),
    ESPAÑA("España"),
    REPUBLICADOMINICANA("República dominicana"),
    COLOMBIA("Colombia"),
    PUERTORICO("Puerto Rico"),
    ESTADOSUNIDOS("Estados unidos"),
    ARGENTINA("Argentina"),
    COREA("Corea");

    private String valor;

    PaisAutor(String valor){
        this.valor = valor;
    }

    public String getValor(){

        return valor;
    }

}
