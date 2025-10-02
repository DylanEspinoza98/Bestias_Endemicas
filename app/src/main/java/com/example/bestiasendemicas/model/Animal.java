package com.example.bestiasendemicas.model;

public class Animal {
    private int id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;    // Solo para imágenes de galería
    private int regionId;
    private boolean esFavorito;

    // Constructor completo
    public Animal(int id, String nombre, String descripcion, String rutaImagen, int regionId, boolean esFavorito) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
    }

    // Constructor sin ID (para insertar nuevos animales)
    public Animal(String nombre, String descripcion, String rutaImagen, int regionId, boolean esFavorito) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
    }

    // Constructor vacío
    public Animal() {
    }

    // ================================
    // GETTERS
    // ================================
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

    // ================================
    // SETTERS
    // ================================
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

    // ================================
    // MÉTODOS ADICIONALES
    // ================================

    // Método para verificar si tiene imagen
    public boolean tieneImagen() {
        return rutaImagen != null && !rutaImagen.isEmpty();
    }

    // Método toString para debugging
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

    // Método equals para comparaciones
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Animal animal = (Animal) obj;
        return id == animal.id;
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    // Método para obtener nombre de región (útil para displays)
    public String getNombreRegion() {
        switch (regionId) {
            case 1: return "Norte";
            case 2: return "Centro";
            case 3: return "Sur";
            case 4: return "Austral";
            default: return "Sin región";
        }
    }

    // Método para validar datos antes de guardar
    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                regionId > 0;
    }
}
