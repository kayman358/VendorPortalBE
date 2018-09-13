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
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {
@Resource(name="jdbc/SupplierCapture")
	private DataSource ds;
    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    
    public String getCurrentTimeStamp() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpSession session = request.getSession();
        String username = (String)  session.getAttribute("username");
        System.out.println("username is "+username);
        
        
        if (username != null) try {      
     try {  
                    con = ds.getConnection();
                    String Logout = "Update UserProfile set LastLogoutDate = ? where username = ?";
                    String timeloggedout = getCurrentTimeStamp();
                    stmt = con.prepareStatement(Logout);
                    stmt.setString(1, timeloggedout);
                    stmt.setString(2, username);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
         if (rst != null) {
                try {
                    rst.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
     }
//    session.removeAttribute("username");
   session.invalidate();
//     try {
//        con.close();
//    } catch (SQLException ex) {
//        Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, null, ex);
//    }
    //String pageToForward = request.getContextPath();
    response.sendRedirect("index.jsp");
   
        }
catch (Exception sqle)
{
    System.out.println("error UserValidateServlet message : " + sqle.getMessage());
    System.out.println("error UserValidateServlet exception : " + sqle);
}
else
{
  //session already null/ expired
    String pageToForward = request.getContextPath();
    response.sendRedirect(pageToForward); 
}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
