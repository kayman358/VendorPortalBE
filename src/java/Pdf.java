/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
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
@WebServlet(urlPatterns = {"/Pdf"})
public class Pdf extends HttpServlet {
private static final int BUFFER_SIZE = 16177215;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Resource(name="jdbc/SupplierCapture")
    private DataSource ds;
    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    String sessionidd = null;
    
    
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         
         
         
        
      HttpSession session = request.getSession(true);
      String sessionId = session.getAttribute("sessionId").toString();
      String username = session.getAttribute("username").toString();
      String time = "select sessionId from UserProfile where username = ?";
            try {
        con = ds.getConnection();
    } catch (SQLException ex) {
        Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
    }
      try {
                stmt = con.prepareStatement(time);
                stmt.setString(1, username);
            rst =    stmt.executeQuery();
            while (rst.next()){
                sessionidd = rst.getString("sessionId");
            }
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("session id "+sessionId);
            System.out.println("DB session id "+sessionidd);
        if (!sessionidd.equals(sessionId)){      //this block is getting executed even for the new session where lastlogged in time should equal to session logged in time                       
              
            String pageToForward = request.getContextPath();
          response.sendRedirect(pageToForward+"/admin-reply.jsp");          
          //request.getRequestDispatcher("/admin-response.jsp").forward(request, response);
            }
        else if (sessionidd.equals(sessionId)){
        
   
            String id = request.getParameter("rowId2");
            
           String sql = "SELECT pdfdocument, filename from vendorInformation  where id = ?";
           System.out.println("id is "+id);
            
        try {
            stmt = con.prepareStatement(sql);
             stmt.setString(1, id);
           rst = stmt.executeQuery();
            if (rst.next()){
               Blob blob = rst.getBlob("pdfdocument");
               String filename = rst.getString("filename");
               InputStream is = blob.getBinaryStream();
                int fileLength = is.available();
             
              
                 
                System.out.println("fileLength = " + fileLength);
 
                ServletContext context = getServletContext();
 
                // sets MIME type for the file download
                String mimeType = context.getMimeType(filename);
                if (mimeType == null) {        
                    mimeType = "application/octet-stream";
                }              
                 
                // set content properties and header attributes for the response
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", filename);
                response.setHeader(headerKey, headerValue);
 
                // writes the file to the client
                OutputStream outStream = response.getOutputStream();
                 
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                 
                while ((bytesRead = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                 
                is.close();
                outStream.close();             
            } else {
                // no file found
                response.getWriter().print("File not found for the id: " + id);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
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

//protected void doGet(HttpServletRequest request, HttpServletResponse response) throw ServletException
//{
// response.setContentType("text/html");
// PrintWriter out = response.getWriter();
// String filename = "Test Pdf.pdf";
// String filePath = "C:\\New Folder\\";
// response.setContentType("APPLICATION/PDF");
// response.setHeader("ContentDisposition","attachment: fileName=\""+filename+"\"");
// FileInputStream fi = new FileInputStream()filePath+filename);
// int i;
// while((i=fi.read()) != 1)
//  out.write;
// fi.close();
// out.close();
//}