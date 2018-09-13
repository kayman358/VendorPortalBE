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
import javax.sql.DataSource;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TN041502
 */
  @WebServlet(urlPatterns = {"/Approve"})
  public class Approve extends HttpServlet {
 @Resource(name="jdbc/SupplierCapture")
	private DataSource ds;

    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    String approveddate = null;
    
     public String getCurrentTimeStamp() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
}
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       
     HttpSession session = request.getSession(true);
     String name = (String) session.getAttribute("myname");
        
        
//     try {
//         //DataSource ds = new Datasource();
//         con = ds.getConnection();
//     } catch (SQLException ex) {
//         Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
//     }finally {
//         if (rst != null) {
//                try {
//                    rst.close();
//                } catch (SQLException e) {
//                    /* ignored */
//                }
//            }
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    /* ignored */
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    /* ignored */
//                }
//            }
//     }
     
        String id = request.getParameter("confirmedIds");
        
         System.out.println("checked is "+id);
        if (id.isEmpty()){
            response.sendRedirect("bad-request.jsp");
        }
        
        
        String[] spl;
        spl = id.split(" ");
        System.out.println("inde 0 ");
        System.out.println("Id of the clicked button is " + id);
        System.out.println("index of 0 "+spl[0]);
        approveddate = getCurrentTimeStamp();
        
                 String sql2;
                 
    for (String spl1 : spl) {
        try {
            sql2 = "Update VendorInformation set status = 'Approved', approveddate =? where id =?";
          // if(con == null){
            con = ds.getConnection();
           //  }
            stmt = con.prepareStatement(sql2);
            stmt.setString(1, approveddate);
            stmt.setString(2, spl1);
            stmt.executeUpdate();
            
            String sql3 = "Update VendorInformation set initiatedBy = ? where id = ?";
             if(con == null){
            con = ds.getConnection();
             }
            stmt = con.prepareStatement(sql3);
            stmt.setString(1, name);
            stmt.setString(2, spl1);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
             if (!id.isEmpty()){  
           response.sendRedirect("success.jsp");
             }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
