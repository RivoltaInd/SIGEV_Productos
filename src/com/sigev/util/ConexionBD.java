package com.sigev.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexión a la base de datos MySQL
 * del proyecto SIGEV, utilizando JDBC.
 *
 * IMPORTANTE: las credenciales se leen del archivo src/db.properties,
 * que no se sube a git. Antes de ejecutar el proyecto, copie
 * src/db.properties.example como src/db.properties y ajuste el usuario
 * y la contraseña de su instalación local de MySQL.
 *
 * Aprendiz: Faber Stiven Londoño Gil
 */
public class ConexionBD {

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
        Properties config = cargarConfiguracion();
        return DriverManager.getConnection(config.getProperty("db.url"),
                config.getProperty("db.usuario"), config.getProperty("db.contrasena"));
    }

    /**
     * Lee db.properties desde la raíz del classpath.
     */
    private static Properties cargarConfiguracion() throws SQLException {
        Properties config = new Properties();
        try (InputStream entrada = ConexionBD.class.getResourceAsStream("/db.properties")) {
            if (entrada == null) {
                throw new SQLException("No se encontró db.properties. "
                        + "Cree el archivo a partir de src/db.properties.example.");
            }
            config.load(entrada);
        } catch (IOException e) {
            throw new SQLException("No se pudo leer db.properties.", e);
        }
        return config;
    }
}
