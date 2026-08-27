package com.sigev.modelo;

import java.util.Date;

public class Venta {

    private int idVenta;
    private int codigoProducto;
    private int cantidad;
    private Date fecha;
    private double total;

    public Venta() {
    }

    // Constructor sin id, para registrar ventas nuevas
    public Venta(int codigoProducto, int cantidad, Date fecha, double total) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venta N°: " + idVenta
                + " | Código producto: " + codigoProducto
                + " | Cantidad: " + cantidad
                + " | Fecha: " + fecha
                + " | Total: $" + total;
    }
}