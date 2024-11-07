/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example.servlets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import com.hfme.database.DatabaseConnection;
import com.hfme.model.Usuario; // Suponiendo que tienes una clase Usuario

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación básica
        if (email.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.html?error=empty_fields");
            return;
        }

        DatabaseConnection db = new DatabaseConnection();

        try {
            Usuario usuario = db.validarUsuario(email, password); // Método para validar usuario en DatabaseConnection
            if (usuario != null) {
                // Crear una sesión y almacenar información del usuario
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Redireccionar a la página protegida
                response.sendRedirect("protected.jsp");
            } else {
                response.sendRedirect("login.html?error=invalid_credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Página de error personalizada
        } finally {
            db.cerrarConexion(); // Cerrar la conexión a la base de datos
        }
    }
}
}
