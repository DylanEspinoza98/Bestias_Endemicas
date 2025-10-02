package com.example.bestiasendemicas.model;

public class Animal {
    private int id;
    private String nombre;
    private String descripcion;
    private String foto_url;
    private int regionId; // Cambio a region_id como foreign key
    private boolean esFavorito;
    private String fechaIngreso;

    // Constructor vacío
    public Animal() {}

    // Constructor completo
    public Animal(String nombre, String descripcion, String foto_url, int regionId, boolean esFavorito) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto_url = foto_url;
        this.regionId = regionId;
        this.esFavorito = esFavorito;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFoto_url() { return foto_url; }
    public void setFoto_url(String foto_url) { this.foto_url = foto_url; }

    public int getRegionId() { return regionId; }
    public void setRegionId(int regionId) { this.regionId = regionId; }

    public boolean isEsFavorito() { return esFavorito; }
    public void setEsFavorito(boolean esFavorito) { this.esFavorito = esFavorito; }

    public String getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}