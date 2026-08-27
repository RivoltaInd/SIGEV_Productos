package com.sigev.dao;

import com.sigev.modelo.Venta;
import com.sigev.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Registra una nueva venta
    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO venta (codigo_producto, cantidad, fecha, total) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, venta.getCodigoProducto());
            sentencia.setInt(2, venta.getCantidad());
            sentencia.setDate(3, new java.sql.Date(venta.getFecha().getTime()));
            sentencia.setDouble(4, venta.getTotal());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
            return false;
        }
    }

    // Consulta y retorna todas las ventas registradas
    public List<Venta> consultarVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        String sql = "SELECT id_venta, codigo_producto, cantidad, fecha, total FROM venta";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(resultado.getInt("id_venta"));
                venta.setCodigoProducto(resultado.getInt("codigo_producto"));
                venta.setCantidad(resultado.getInt("cantidad"));
                venta.setFecha(resultado.getDate("fecha"));
                venta.setTotal(resultado.getDouble("total"));
                listaVentas.add(venta);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar las ventas: " + e.getMessage());
        }

        return listaVentas;
    }

    // Elimina una venta según su id
    public boolean eliminarVenta(int idVenta) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idVenta);
            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
            return false;
        }
    }
}