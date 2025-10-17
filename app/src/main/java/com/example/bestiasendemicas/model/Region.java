package com.example.bestiasendemicas.model;

public class Region {
    private int id;
    private String nombre;

    /** Constructor vacío para inicializar sin datos */
    public Region() {}

    /** Constructor completo para inicializar id y nombre */
    public Region(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /** Getter */
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    /** Setter */
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(int id) { this.id = id; }
}