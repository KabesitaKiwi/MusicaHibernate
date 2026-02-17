package com.erwinLagos.musica;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cancion {
    private int idCancion;
    private String titulo;
    private String genero;
    private int participantes;
    private float duracion;
    private String idioma;
    private int valoracion;
    private Productora productora;
    private Autor autor;
    private Album album;

    public String toString(){
        return "Titulo: " + titulo + " | Album: " + album.getTitulo() + " | Autor: " + autor.getNombreArtistico() + " | Género " + genero + " | Productora " + productora.getNombre() + " | Nº Participantes " + participantes + " | duracion: " + duracion + " | Valoración: " + valoracion + " | Idioma: " + idioma;
    }

    @Id
    @Column(name = "idCancion")
    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
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
    @Column(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Basic
    @Column(name = "participantes")
    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    @Basic
    @Column(name = "duracion")
    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    @Basic
    @Column(name = "idioma")
    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Basic
    @Column(name = "valoracion")
    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancion cancion = (Cancion) o;
        return idCancion == cancion.idCancion &&
                participantes == cancion.participantes &&
                Double.compare(cancion.duracion, duracion) == 0 &&
                valoracion == cancion.valoracion &&
                Objects.equals(titulo, cancion.titulo) &&
                Objects.equals(genero, cancion.genero) &&
                Objects.equals(idioma, cancion.idioma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCancion, titulo, genero, participantes, duracion, idioma, valoracion);
    }

    @ManyToOne
    @JoinColumn(name = "idProductora", referencedColumnName = "idProductora", nullable = false)
    public Productora getProductora() {
        return productora;
    }

    public void setProductora(Productora productora) {
        this.productora = productora;
    }

    @ManyToOne
    @JoinColumn(name = "idAutor", referencedColumnName = "idAutor", nullable = false)
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @ManyToOne
    @JoinColumn(name = "idAlbum", referencedColumnName = "idAlbum", nullable = false)
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
