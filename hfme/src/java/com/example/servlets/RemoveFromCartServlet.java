/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example.servlets;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private final String DB_URL = "jdbc:mysql://localhost:3306/hfme";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "tu_contraseña";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        RemoveRequest removeRequest = new Gson().fromJson(reader, RemoveRequest.class);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM carrito WHERE usuario_id = ? AND producto_id = ?")) {
            
            int userId = (int) request.getSession().getAttribute("userId");
            stmt.setInt(1, userId);
            stmt.setInt(2, removeRequest.productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class RemoveRequest {
        int productId;
    }
}
