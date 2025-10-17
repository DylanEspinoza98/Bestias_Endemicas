package com.example.bestiasendemicas.model;

public class Animal {
    private int id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;    // Solo para imágenes de galería
    private int regionId;
    private boolean esFavorito;
    private String tipo;
    private String soundUri;      // URI para MP3 (null si no hay audio)

    // Constructor completo (para animales con audio)
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

    // Constructor vacío
    public Animal() {
        this.soundUri = null;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getRutaImagen() { return rutaImagen; }
    public int getRegionId() { return regionId; }
    public boolean isEsFavorito() { return esFavorito; }
    public String getTipo() { return tipo; }
    public String getSoundUri() { return soundUri; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setRegionId(int regionId) { this.regionId = regionId; }
    public void setEsFavorito(boolean esFavorito) { this.esFavorito = esFavorito; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setSoundUri(String soundUri) { this.soundUri = soundUri; }

    // Métodos auxiliares
    public boolean tieneImagen() {
        return rutaImagen != null && !rutaImagen.isEmpty();
    }

    public boolean tieneAudio() {
        return soundUri != null && !soundUri.isEmpty();
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return id == animal.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public String getNombreRegion() {
        switch (regionId) {
            case 1: return "Norte";
            case 2: return "Centro";
            case 3: return "Sur";
            case 4: return "Austral";
            default: return "Sin región";
        }
    }

    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                regionId > 0;
    }
}
