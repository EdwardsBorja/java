/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hfme.database;

/**
 *
 * @author edwar
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hfme";
    private static final String USER = "root"; // Cambia por tu usuario de MySQL
    private static final String PASSWORD = "tu_contraseña"; // Cambia por tu contraseña de MySQL

    /**
     * Establece la conexión con la base de datos.
     * @return Connection objeto de conexión a la base de datos.
     */
    public Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Método para insertar un nuevo usuario en la tabla `usuarios`.
     */
    public void insertarUsuario(String nombre, String correo, String contrasenaHash, String direccion, int codigoPostal, String pais, String estado, String ciudad) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena_hash, direccion, codigo_postal, pais, estado, ciudad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, contrasenaHash);
            pstmt.setString(4, direccion);
            pstmt.setInt(5, codigoPostal);
            pstmt.setString(6, pais);
            pstmt.setString(7, estado);
            pstmt.setString(8, ciudad);
            pstmt.executeUpdate();
            System.out.println("Usuario insertado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    /**
     * Método para obtener todos los usuarios.
     */
    public void obtenerUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Correo: " + rs.getString("correo"));
                System.out.println("Dirección: " + rs.getString("direccion"));
                System.out.println("Código Postal: " + rs.getInt("codigo_postal"));
                System.out.println("Ciudad: " + rs.getString("ciudad"));
                System.out.println("----");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
    }

    /**
     * Método para actualizar la dirección de un usuario.
     */
    public void actualizarDireccionUsuario(int id, String nuevaDireccion) {
        String sql = "UPDATE usuarios SET direccion = ? WHERE id = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nuevaDireccion);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dirección actualizada correctamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la dirección: " + e.getMessage());
        }
    }

    /**
     * Método para eliminar un usuario por su ID.
     */
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        
        // Ejemplo de inserción
        db.insertarUsuario("Juan Pérez", "juan@example.com", "hash_contraseña", "Calle 123", 12345, "México", "CDMX", "Ciudad de México");
        
        // Obtener usuarios
        db.obtenerUsuarios();
        
        // Actualizar dirección de usuario
        db.actualizarDireccionUsuario(1, "Nueva Calle 456");
        
        // Eliminar usuario
        db.eliminarUsuario(1);
    }
}

