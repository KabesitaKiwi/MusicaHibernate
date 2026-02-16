package com.erwinLagos.musica;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Autor {
    private int idAutor;
    private String nombreArtistico;
    private String nombreReal;
    private int edad;
    private String pais;
    private Date fechaPrimeraPublicacion;
    private boolean gira;
    private List<Cancion> canciones;

    @Id
    @Column(name = "idAutor")
    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    @Basic
    @Column(name = "nombreArtistico")
    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    @Basic
    @Column(name = "nombreReal")
    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    @Basic
    @Column(name = "edad")
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Basic
    @Column(name = "pais")
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Basic
    @Column(name = "fechaPrimeraPublicacion")
    public Date getFechaPrimeraPublicacion() {
        return fechaPrimeraPublicacion;
    }

    public void setFechaPrimeraPublicacion(Date fechaPrimeraPublicacion) {
        this.fechaPrimeraPublicacion = fechaPrimeraPublicacion;
    }

    @Basic
    @Column(name = "gira")
    public boolean isGira() {
        return gira;
    }

    public void setGira(boolean gira) {
        this.gira = gira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return idAutor == autor.idAutor &&
                edad == autor.edad &&
                gira == autor.gira &&
                Objects.equals(nombreArtistico, autor.nombreArtistico) &&
                Objects.equals(nombreReal, autor.nombreReal) &&
                Objects.equals(pais, autor.pais) &&
                Objects.equals(fechaPrimeraPublicacion, autor.fechaPrimeraPublicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutor, nombreArtistico, nombreReal, edad, pais, fechaPrimeraPublicacion, gira);
    }

    @OneToMany(mappedBy = "autor")
    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
}
