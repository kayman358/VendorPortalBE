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
@WebServlet(urlPatterns = {"/Create"})
public class Create extends HttpServlet {

    @Resource(name = "jdbc/SupplierCapture")
    private DataSource ds;

    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    String createdDate = null;
    String sessionidd = null;

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpSession session = request.getSession(true);
        String sessionId = session.getAttribute("sessionId").toString();
        String username1 = session.getAttribute("username").toString();
        String time = "select sessionId from UserProfile where username = ?";

        try {
            stmt = con.prepareStatement(time);
            stmt.setString(1, username1);
            rst = stmt.executeQuery();
            while (rst.next()) {
                sessionidd = rst.getString("sessionId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("session id " + sessionId);
        System.out.println("DB session id " + sessionidd);
        if (!sessionidd.equals(sessionId)) {      //this block is getting executed even for the new session where lastlogged in time should equal to session logged in time                       

            String pageToForward = request.getContextPath();
            response.sendRedirect(pageToForward + "/admin-reply.jsp");
            //request.getRequestDispatcher("/admin-response.jsp").forward(request, response);
        } else if (sessionidd.equals(sessionId)) {

            //HttpSession session = request.getSession();
            String uname = (String) session.getAttribute("myname");

            System.out.println("Name of administrator is " + uname);
            createdDate = getCurrentTimeStamp();

            String username = request.getParameter("newusername");
            String role = request.getParameter("role");
            String department = request.getParameter("fbndept");
            
            
            
            String name = adUserDetails(username);
            System.out.println("my name is "+name);
            String[] splitUsername = name.split("\\|");
            String FullName = splitUsername[0];
            String email = splitUsername[1];
            System.out.println("index 0 is "+FullName);
            System.out.println("index 1 is "+email);
            
            try {

                con = ds.getConnection();
                String query = "SELECT UserName from UserProfile where UserName = ?";

                stmt = con.prepareStatement(query);
                stmt.setString(1, username);
                rst = stmt.executeQuery();
                while (rst.next()) {
                    String name2 = rst.getString("UserName");
                    if (name2 != null) {
                        response.sendRedirect("exists.jsp");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
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

            try {

                con = ds.getConnection();
                String query = "INSERT INTO UserProfile (UserName, EmailAddress, FullName, SolID, RoleID, CreatedDate) values (?,?,?,?,?,?)";
                stmt = con.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, FullName);
                stmt.setString(4, department);
                stmt.setString(5, role);
                stmt.setString(6, createdDate);
                stmt.executeUpdate();
                session.setAttribute("created", FullName);
                response.sendRedirect("thank-you.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
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

    }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

    private static String adUserDetails(java.lang.String username) {
        com.Auth.Service service = new com.Auth.Service();
        com.Auth.ServiceSoap port = service.getServiceSoap();
        return port.adUserDetails(username);
    }

   

}
