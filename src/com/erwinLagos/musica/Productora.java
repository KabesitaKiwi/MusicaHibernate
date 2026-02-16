package com.erwinLagos.musica;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Productora {
    private int idProductora;
    private String nombre;
    private String localizacion;
    private int trabajadores;
    private Date fechaFundacion;
    private String propietario;
    private List<Cancion> Canciones;
    private List<Album> albumes;
    @Override
    public String toString(){
        return "Productora: " + nombre + " | Localizacion: " + localizacion + " | Trabajadores: " + trabajadores + " | Fecha fundación: " + fechaFundacion + " | Propietario " + propietario;
    }

    @Id
    @Column(name = "idProductora")
    public int getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(int idProductora) {
        this.idProductora = idProductora;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "localizacion")
    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    @Basic
    @Column(name = "trabajadores")
    public int getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(int trabajadores) {
        this.trabajadores = trabajadores;
    }

    @Basic
    @Column(name = "fechaFundacion")
    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    @Basic
    @Column(name = "propietario")
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Productora that = (Productora) o;
        return idProductora == that.idProductora &&
                trabajadores == that.trabajadores &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(localizacion, that.localizacion) &&
                Objects.equals(fechaFundacion, that.fechaFundacion) &&
                Objects.equals(propietario, that.propietario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductora, nombre, localizacion, trabajadores, fechaFundacion, propietario);
    }

    @OneToMany(mappedBy = "productora")
    public List<Cancion> getCanciones() {
        return Canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        Canciones = canciones;
    }

    @OneToMany(mappedBy = "productora")
    public List<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }
}
