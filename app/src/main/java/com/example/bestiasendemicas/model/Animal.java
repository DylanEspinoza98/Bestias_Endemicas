package com.example.bestiasendemicas.model;

public class Animal {
    private int id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;    //Solo para imágenes de galería
    private int regionId;
    private boolean esFavorito;
    private String tipo;

    //Constructor completo
    public Animal(int id, String nombre, String descripcion, String rutaImagen,
                  int regionId, boolean esFavorito, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
        this.tipo = tipo;
    }

    //Constructor sin ID (para insertar nuevos animales)
    public Animal(String nombre, String descripcion, String rutaImagen,
                  int regionId, boolean esFavorito, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
        this.tipo = tipo;
    }

    //Constructor vacío
    public Animal() {
    }


    //Getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public int getRegionId() {
        return regionId;
    }

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public String getTipo() {
        return tipo;
    }


    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    //Metodos adicionales


    //Metodo para verificar si tiene imagen
    public boolean tieneImagen() {
        return rutaImagen != null && !rutaImagen.isEmpty();
    }

    //Metodo toString
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", regionId=" + regionId +
                ", esFavorito=" + esFavorito +
                '}';
    }

    //Metodo equals para comparaciones
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Animal animal = (Animal) obj;
        return id == animal.id;
    }

    //Metodo hashCode para animales base
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    //Metodo para obtener nombre de región
    public String getNombreRegion() {
        switch (regionId) {
            case 1: return "Norte";
            case 2: return "Centro";
            case 3: return "Sur";
            case 4: return "Austral";
            default: return "Sin región";
        }
    }

    //Metodo para validar datos antes de guardar
    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                regionId > 0;
    }
}
