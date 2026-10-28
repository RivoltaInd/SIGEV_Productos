package com.sigev.dao;

import com.sigev.modelo.Producto;
import com.sigev.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para el módulo de Productos de SIGEV.
 * Contiene la conexión con la base de datos por medio de JDBC y las
 * funcionalidades de inserción, consulta, actualización y eliminación
 * solicitadas en la evidencia GA7-220501096-AA2-EV01.
 *
 * Aprendiz: Faber Stiven Londoño Gil
 */
public class ProductoDAO {

    // Inserta un nuevo producto en la base de datos y le asigna el código generado
    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, producto.getNombre());
            sentencia.setString(2, producto.getCategoria());
            sentencia.setDouble(3, producto.getPrecio());
            sentencia.setInt(4, producto.getStock());

            int filasAfectadas = sentencia.executeUpdate();

            // Código AUTO_INCREMENT que MySQL le dio al producto
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    producto.setCodigo(claves.getInt(1));
                }
            }
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
            return false;
        }
    }

    // Consulta y retorna todos los productos registrados
    public List<Producto> consultarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, categoria, precio, stock FROM producto";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("codigo"),
                        resultado.getString("nombre"),
                        resultado.getString("categoria"),
                        resultado.getDouble("precio"),
                        resultado.getInt("stock")
                );
                listaProductos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los productos: " + e.getMessage());
        }
        return listaProductos;
    }

    // Consulta un producto puntual por su código
    public Producto consultarProductoPorCodigo(int codigo) {
        String sql = "SELECT codigo, nombre, categoria, precio, stock FROM producto WHERE codigo = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigo);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return new Producto(
                        resultado.getInt("codigo"),
                        resultado.getString("nombre"),
                        resultado.getString("categoria"),
                        resultado.getDouble("precio"),
                        resultado.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el producto: " + e.getMessage());
        }
        return null;
    }

    // Actualiza los datos de un producto existente
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE codigo = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, producto.getNombre());
            sentencia.setString(2, producto.getCategoria());
            sentencia.setDouble(3, producto.getPrecio());
            sentencia.setInt(4, producto.getStock());
            sentencia.setInt(5, producto.getCodigo());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    // Elimina un producto según su código
    public boolean eliminarProducto(int codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigo);
            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }
}