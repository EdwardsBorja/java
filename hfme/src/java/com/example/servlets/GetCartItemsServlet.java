/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Asegúrate de reemplazar con la URL correcta de la base de datos
@WebServlet("/GetCartItemsServlet")
public class GetCartItemsServlet extends HttpServlet {
    private final String DB_URL = "jdbc:mysql://localhost:3306/hfme";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "tu_contraseña";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT p.id, p.nombre, p.precio, c.cantidad FROM carrito c JOIN productos p ON c.producto_id = p.id WHERE c.usuario_id = ?")) {
            
            // Supongamos que tienes el ID de usuario almacenado en la sesión
            int userId = (int) request.getSession().getAttribute("userId");
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("cantidad")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(cartItems));
    }

    private class CartItem {
        int producto_id;
        String nombre;
        double precio;
        int cantidad;

        CartItem(int producto_id, String nombre, double precio, int cantidad) {
            this.producto_id = producto_id;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
        }
    }
}


