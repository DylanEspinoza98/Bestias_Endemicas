package com.example.bestiasendemicas.model;

public class Animal {
    // Propiedades de la clase Animal
    private int id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;    // Solo para imágenes de galería
    private int regionId;
    private boolean esFavorito;
    private String tipo;
    private String soundUri;      // URI para MP3 (null si no hay audio)

    /** Constructor completo para inicializar todas las propiedades, incluyendo audio */
    public Animal(String nombre, String descripcion, String rutaImagen,
                  int regionId, boolean esFavorito, String tipo, String soundUri) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
        this.tipo = tipo;
        this.soundUri = soundUri; // puede ser null si no hay audio
    }

    /** Constructor vacío, útil para inicializar sin datos */
    public Animal() {
        this.soundUri = null;
    }

    /** Getters */
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getRutaImagen() { return rutaImagen; }
    public int getRegionId() { return regionId; }
    public boolean isEsFavorito() { return esFavorito; }
    public String getTipo() { return tipo; }
    public String getSoundUri() { return soundUri; }

    /** Setters */
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setRegionId(int regionId) { this.regionId = regionId; }
    public void setEsFavorito(boolean esFavorito) { this.esFavorito = esFavorito; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setSoundUri(String soundUri) { this.soundUri = soundUri; }

    /** Retorna true si el animal tiene una imagen asignada */
    public boolean tieneImagen() {
        return rutaImagen != null && !rutaImagen.isEmpty();
    }

    /** Retorna true si el animal tiene audio asignado */
    public boolean tieneAudio() {
        return soundUri != null && !soundUri.isEmpty();
    }

    /** Representación en string del objeto Animal */
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", regionId=" + regionId +
                ", esFavorito=" + esFavorito +
                ", tipo='" + tipo + '\'' +
                ", soundUri='" + soundUri + '\'' +
                '}';
    }

    /** Dos animales son iguales si tienen el mismo id */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return id == animal.id;
    }

    /** HashCode basado en el id */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /** Retorna el nombre de la región según el id */
    public String getNombreRegion() {
        switch (regionId) {
            case 1: return "Norte";
            case 2: return "Centro";
            case 3: return "Sur";
            case 4: return "Austral";
            default: return "Sin región";
        }
    }

    /** Valida si el animal tiene nombre, descripción y región válidos */
    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                regionId > 0;
    }
}
