/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private final String DB_URL = "jdbc:mysql://localhost:3306/hfme";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "tu_contraseña";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        CartUpdateRequest updateRequest = new Gson().fromJson(reader, CartUpdateRequest.class);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE carrito SET cantidad = ? WHERE usuario_id = ? AND producto_id = ?")) {
            
            int userId = (int) request.getSession().getAttribute("userId");
            stmt.setInt(1, updateRequest.cantidad);
            stmt.setInt(2, userId);
            stmt.setInt(3, updateRequest.productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class CartUpdateRequest {
        int productId;
        int cantidad;
    }
}
