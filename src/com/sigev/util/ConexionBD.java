package com.sigev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos MySQL
 * del proyecto SIGEV, utilizando JDBC.
 *
 * IMPORTANTE: antes de ejecutar el proyecto, ajuste el usuario y la
 * contraseña de su instalación local de MySQL en las constantes USUARIO
 * y CONTRASENA.
 *
 * Aprendiz: Faber Stiven Londoño Gil
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sigev_db?useUnicode=true&characterEncoding=UTF-8";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Sigev2027";

    /**
     * Abre y retorna una conexión activa hacia la base de datos sigev_db.
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. "
                    + "Verifique que la librería mysql-connector-j esté en el classpath.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}