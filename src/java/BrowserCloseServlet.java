/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author TN041502
 */
@WebServlet(urlPatterns = {"/BrowserCloseServlet"})
public class BrowserCloseServlet extends HttpServlet {
@Resource(name="jdbc/SupplierCapture")
	private DataSource ds;
    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        con = ds.getConnection();
    } catch (SQLException ex) {
        Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("username");
         String disable = "Update UserProfile set active = ? where username = ?";
            try {
                stmt = con.prepareStatement(disable);
                stmt.setInt(1, 0);
                stmt.setString(2, username);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.removeAttribute("username");
            session.invalidate();
            session = null;
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
