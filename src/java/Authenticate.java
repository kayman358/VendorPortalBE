/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
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
import java.text.ParseException;

/**
 *
 * @author TN041502
 */
@WebServlet(urlPatterns = {"/Authenticate"})
public class Authenticate extends HttpServlet {

    @Resource(name = "jdbc/SupplierCapture")

    private DataSource ds;
    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    String user = "";
    String lastLoginDate = null;
    int counter = 0;
    String SolID = "";
    int RoleID;
    int flag;
    //int active;
    String last = null;
    int blocked;
    Date eight;
    Date seventeen;
    String myname = null;
    String sessionId = null;
    boolean result;
    String inactive = null;
    Date d1;
    Date d2;
    long diffDays = 0;

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        //session.setMaxInactiveInterval(15 * 60);

        // Date date = new Date();
        //String check = (adAuthenticator(username, password)); 
//        try {
//            System.out.println("I got here too try block");
//            con = ds.getConnection();
//            System.out.println("I got here too try block after connection");
//        } catch (SQLException ex) {
//            System.out.println("I got here too catch block");
//            ex.getMessage();
//            System.out.println("ex is "+ex);
//            Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
//        }
//         System.out.println("I got here too");
        String sql = "SELECT username, SolID, RoleID, LastLoginDate, maxAttempts, flag from UserProfile where username = ?";
        String query = "Update UserProfile SET Lastlogindate = ?, sessionId =? WHERE username = ?";

        try {
            con = ds.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            rst = stmt.executeQuery();
            while (rst.next()) {
                user = rst.getString("username");
                SolID = rst.getString("SolID");
                RoleID = rst.getInt("RoleID");
                last = rst.getString("LastLoginDate");
                flag = rst.getInt("flag");
                blocked = rst.getInt("maxAttempts");
                System.out.println("RoleID is " + RoleID);

                //String ip = getClientIp(request);
                //System.out.println("Ip address is " + ip);
                //String macaddress = getMACAddress(ip);
                // System.out.println("Mac Address is "+macaddress);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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
        Date date = new Date();
        String newDate = date.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            d1 = format.parse(last);
            d2 = format.parse(getCurrentTimeStamp());

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays >= 90) {
                con = ds.getConnection();
                String dormant = "Update UserProfile set userAccountDormant = ? where UserName = ?";
                stmt = con.prepareStatement(dormant);
                stmt.setString(1, "Yes");
                stmt.setString(2, user);
                stmt.executeUpdate();
                request.setAttribute("Blocked", "This account has been locked temporarily after it has been inactive for three months, please contact your system Administrator for assistance.");
                request.getRequestDispatcher("/admin-response.jsp").forward(request, response);
            }

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String name = adValidateUser(username, password);
        String[] splitUsername = name.split("\\|");
        myname = splitUsername[1];

        String output = (adAuthenticator(username, password));
        System.out.println("output " + output);        //Authenticate Method on AD confirming if a user exists

        //AD is down Block
//        boolean res = output.contentEquals("login error:the server is not operational.");
//        if (res){
//             request.setAttribute("errorMessage", "Login failed: AD down");
//            request.getRequestDispatcher("/index.jsp").forward(request, response);
//        }
        result = output.contains("error");

        if (result == true && flag != 2) {
            counter += 1;
            if (!user.equals("")) {
                try {
                    con = ds.getConnection();
                    String failedLogin = "Update UserProfile set LastFailedLoginDate = ? where UserName = ?";
                    String timefailed = getCurrentTimeStamp();
                    stmt = con.prepareStatement(failedLogin);
                    stmt.setString(1, timefailed);
                    stmt.setString(2, user);
                    stmt.executeUpdate();

                    String auditTrail = "Insert into AuditTrail (UserID, ActionDate, AuditAction) Values (?,?,?)";
                    stmt = con.prepareStatement(auditTrail);
                    stmt.setString(1, user);
                    stmt.setString(2, getCurrentTimeStamp());
                    // stmt.setString(3, myname);
                    stmt.setString(3, "Failed Login");
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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
            request.setAttribute("errorMessage", "Login failed: Invalid username or bad password");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

        System.out.println("print counter " + counter);
        if (counter == blocked) {
            String disable = "Update UserProfile set flag = 2 where username = ?";
            try {
                if (con == null){
                con = ds.getConnection();
                }
                stmt = con.prepareStatement(disable);
                stmt.setString(1, username);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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
        //System.out.println("inactive = " + inactive);
//        if (inactive.equals("1")){
//             request.setAttribute("errorMessage", "Account is inactive and already in dormant mode, contact system your Administrator");
//            request.getRequestDispatcher("/index.jsp").forward(request, response);
//        }

//        if (active == 1){
//           // request.setAttribute("Blocked", "This account is currently active please log off from your previous session.");
//            String pageToForward = request.getContextPath();
//            response.sendRedirect(pageToForward+"/admin-reply.jsp");
//           // request.getRequestDispatcher("/admin-response.jsp").forward(request, response);
//        } 
        //Login for BDM
        if (!result && username.equalsIgnoreCase(user) && RoleID == 1 && flag != 2) {

            try {
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

                try {
                    eight = parser.parse("8:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    seventeen = parser.parse("17:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    Date userDate = parser.parse(getTimeStamp());
                    if (userDate.after(eight) && userDate.before(seventeen)) {
                        System.out.println("On track man");
                    } else {
                        System.out.println("Don't login outside work hour again Bro");
                        String work = "Insert into workHour (userName, timeLoggedIn) values (?,?)";
                        con = ds.getConnection();
                        stmt = con.prepareStatement(work);
                        stmt.setString(1, user);
                        stmt.setString(2, getCurrentTimeStamp());
                        stmt.executeUpdate();
                    }
                } catch (ParseException e) {
                    // Invalid date was entered
                }
                con = ds.getConnection();
                sessionId = session.getId();
                lastLoginDate = getCurrentTimeStamp();
                stmt = con.prepareStatement(query);
                stmt.setString(1, lastLoginDate);
                stmt.setString(2, sessionId);
                stmt.setString(3, user);
                stmt.executeUpdate();

                String auditTrail = "Insert into AuditTrail (UserID, ActionDate, ClientName, AuditAction) Values (?,?,?,?)";
                con = ds.getConnection();
                stmt = con.prepareStatement(auditTrail);
                stmt.setString(1, user);
                stmt.setString(2, lastLoginDate);
                stmt.setString(3, myname);
                stmt.setString(4, "LoggedIn");
                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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

            session.setAttribute("username", username);
            session.setAttribute("myname", myname);
            session.setAttribute("SolID", SolID);
            session.setAttribute("RoleID", RoleID);
            session.setAttribute("Lastlogin", last);
            session.setAttribute("presentlogintime", lastLoginDate);
            if (diffDays < 90) {
                response.sendRedirect("view.jsp");
            }
            session.setAttribute("sessionId", sessionId);
            System.out.println("session id is " + sessionId);
            //Login for BDM ends         

        }
        //Login for Group Head Begins
        if (!result && username.equalsIgnoreCase(user) && RoleID == 2 && flag != 2) {
            try {

                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

                try {
                    eight = parser.parse("8:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    seventeen = parser.parse("17:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    Date userDate = parser.parse(getTimeStamp());
                    // System.out.println("Print time "+getTimeStamp());
                    if (userDate.after(eight) && userDate.before(seventeen)) {
                        System.out.println("On track man");
                    } else {
                        System.out.println("Don't login outside work hour again Bro");
                        String work = "Insert into workHour (userName, timeLoggedIn) values (?,?)";
                        con = ds.getConnection();
                        stmt = con.prepareStatement(work);
                        stmt.setString(1, user);
                        stmt.setString(2, getCurrentTimeStamp());
                        stmt.executeUpdate();
                    }
                } catch (ParseException e) {
                    // Invalid date was entered
                }
                con = ds.getConnection();
                sessionId = session.getId();
                lastLoginDate = getCurrentTimeStamp();
                stmt = con.prepareStatement(query);
                stmt.setString(1, lastLoginDate);
                stmt.setString(2, sessionId);
                stmt.setString(3, user);
                stmt.executeUpdate();

                String auditTrail = "Insert into AuditTrail (UserID, ActionDate, ClientName, AuditAction) Values (?,?,?,?)";
                stmt = con.prepareStatement(auditTrail);
                stmt.setString(1, user);
                stmt.setString(2, lastLoginDate);
                stmt.setString(3, myname);
                stmt.setString(4, "LoggedIn");
                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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

            session.setAttribute("username", username);
            session.setAttribute("myname", myname);
            session.setAttribute("SolID", SolID);
            session.setAttribute("RoleID", RoleID);
            session.setAttribute("Lastlogin", last);
            session.setAttribute("presentlogintime", lastLoginDate);
            if (diffDays < 90) {
                response.sendRedirect("views.jsp");
            }
            session.setAttribute("sessionId", sessionId);
            System.out.println("session id is " + sessionId);
        }

        //Login for GH Ends
        //Login for RoleID three Begins (Admin)
        if (!result && username.equalsIgnoreCase(user) && RoleID == 3 && flag != 2) {
            try {

                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

                try {
                    eight = parser.parse("8:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    seventeen = parser.parse("17:00");
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    Date userDate = parser.parse(getTimeStamp());
                    // System.out.println("Print time "+getTimeStamp());
                    if (userDate.after(eight) && userDate.before(seventeen)) {
                        System.out.println("On track man");
                    } else {
                        System.out.println("Don't login outside work hour again Bro");
                        String work = "Insert into workHour (userName, timeLoggedIn) values (?,?)";
                        con = ds.getConnection();
                        stmt = con.prepareStatement(work);
                        stmt.setString(1, user);
                        stmt.setString(2, getCurrentTimeStamp());
                        stmt.executeUpdate();
                    }
                } catch (ParseException e) {
                    // Invalid date was entered
                }
                con = ds.getConnection();
                sessionId = session.getId();
                lastLoginDate = getCurrentTimeStamp();
                stmt = con.prepareStatement(query);
                stmt.setString(1, lastLoginDate);
                stmt.setString(2, sessionId);
                stmt.setString(3, user);
                stmt.executeUpdate();

                String auditTrail = "Insert into AuditTrail (UserID, ActionDate, ClientName, AuditAction) Values (?,?,?,?)";
                stmt = con.prepareStatement(auditTrail);
                stmt.setString(1, user);
                stmt.setString(2, lastLoginDate);
                stmt.setString(3, myname);
                stmt.setString(4, "LoggedIn");
                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
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

            session.setAttribute("username", username);
            session.setAttribute("myname", myname);
            session.setAttribute("SolID", SolID);
            session.setAttribute("RoleID", RoleID);
            session.setAttribute("Lastlogin", last);
            session.setAttribute("presentlogintime", lastLoginDate);
            if (diffDays < 90) {
                response.sendRedirect("adminview.jsp");
            }
            session.setAttribute("sessionId", sessionId);
            System.out.println("session id is " + sessionId);
        }

        //Login for RoleID three ends. (Admin)
        if (flag == 2) {
            request.setAttribute("Blocked", "This account has been locked temporarily after three(3) incorrect attempts, please contact your system Administrator for assistance.");
            request.getRequestDispatcher("/admin-response.jsp").forward(request, response);
        }

    }

    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private static String adAuthenticator(java.lang.String username, java.lang.String password) {
        com.Auth.Service service = new com.Auth.Service();
        com.Auth.ServiceSoap port = service.getServiceSoap();
        return port.adAuthenticator(username, password);
    }

    private static String adUserDetails(java.lang.String username) {
        com.Auth.Service service = new com.Auth.Service();
        com.Auth.ServiceSoap port = service.getServiceSoap();
        return port.adUserDetails(username);
    }

    private static String adValidateUser(java.lang.String username, java.lang.String password) {
        com.Auth.Service service = new com.Auth.Service();
        com.Auth.ServiceSoap port = service.getServiceSoap();
        return port.adValidateUser(username, password);
    }

    private static String getGroups(java.lang.String username, java.lang.String password) {
        com.Auth.Service service = new com.Auth.Service();
        com.Auth.ServiceSoap port = service.getServiceSoap();
        return port.getGroups(username, password);
    }

//    public int updateBlocked() {
//
//        blocked = blocked + 1;
//
//        return blocked;
//    }
}
