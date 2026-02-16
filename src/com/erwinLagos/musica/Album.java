package com.erwinLagos.musica;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Album {
    private int idAlbum;
    private String titulo;
    private int numeroCanciones;
    private int duracionMinutos;
    private Date fechaSalida;
    private Productora productora;
    private List<Cancion> canciones;
    private Autor autor;

    @Override
    public String toString(){
        return "Titulo " + titulo + " | Autor: " + autor.getNombreArtistico() + " | Nº Canciones: " + numeroCanciones + " | Duración: " + duracionMinutos + " | Feccha de Salida " + fechaSalida + " | Productora: " + productora.getNombre();
    }

    @Id
    @Column(name = "idAlbum")
    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    @Basic
    @Column(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Basic
    @Column(name = "numeroCanciones")
    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }

    @Basic
    @Column(name = "duracionMinutos")
    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    @Basic
    @Column(name = "fechaSalida")
    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return idAlbum == album.idAlbum &&
                numeroCanciones == album.numeroCanciones &&
                duracionMinutos == album.duracionMinutos &&
                Objects.equals(titulo, album.titulo) &&
                Objects.equals(fechaSalida, album.fechaSalida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlbum, titulo, numeroCanciones, duracionMinutos, fechaSalida);
    }

    @ManyToOne
    @JoinColumn(name = "idProductora", referencedColumnName = "idProductora", nullable = false)
    public Productora getProductora() {
        return productora;
    }

    public void setProductora(Productora productora) {
        this.productora = productora;
    }

    @OneToMany(mappedBy = "album")
    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    @ManyToOne
    @JoinColumn(name = "idAutor", referencedColumnName = "idAutor", nullable = false)
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
