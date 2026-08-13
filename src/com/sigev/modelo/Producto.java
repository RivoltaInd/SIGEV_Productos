package com.sigev.modelo;

/**
 * Clase modelo que representa un producto del inventario de SIGEV.
 * Sigue el estándar de codificación definido en la evidencia
 * GA7-220501096-AA1-EV02 (nombramiento de clases, atributos y métodos).
 *
 * Aprendiz: Faber Stiven Londoño Gil
 */
public class Producto {

    // Atributos privados (encapsulación)
    private int codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros (sin código, para registrar productos nuevos)
    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Constructor completo (incluye código, para productos ya existentes)
    public Producto(int codigo, String nombre, String categoria, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos accesores (get) y modificadores (set)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Representación en texto del objeto, útil para mostrarlo en consola
    @Override
    public String toString() {
        return "Código: " + codigo
                + " | Nombre: " + nombre
                + " | Categoría: " + categoria
                + " | Precio: $" + precio
                + " | Stock: " + stock;
    }
}