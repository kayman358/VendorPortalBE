/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

/**
 *
 * @author Kolade
 */
@WebServlet(urlPatterns = {"/NewServlet"})
@MultipartConfig
public class NewServlet extends HttpServlet {

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
    String sq;
    int vendorId;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileName = null;
        String tin="";
        String tin2 = "";
        boolean validation = true;
        String suppliername = null, tax = null, suppliertype = null, businessclass = null,
                certifications = null, officelocation = null, sitename = null, country = null,
                address1 = null, address2 = null, address3 = null, cityname = null, state = null, lga = null,
                operatingunit = null, sitename2 = null, country2 = null,
                address1_2 = null, address2_2 = null, address3_2 = null,
                cityname2 = null, state2 = null, lga2 = null, operatingunit2 = null,
                salutation = null, laname = null, fname = null, department = null,
                contactnumber = null, emailaddress = null, fbndepartment = null,
                staffname = null, paymentmethod = null,
                deliverymethod = null, deliveryemailaddress = null,
                invoicecurrency = null, paymentterms = null, bankname = null,
                branchname = null, accountnumber = null, accountname = null,
                accountcurrency = null, contactnumber2 = null,
                emailaddress2 = null, salutation2 = null, fname2 = null,
                lname2 = null, department2 = null, company_type = null,
                otherbusiness = null, othersupplier = null, otherbanks = null,
                invoicecurrency2 = null, invoicecurrency3 = null,
                invoicecurrency4 = null, invoicecurrency5 = null, iban = null, swiftcode = null, createddate = null, tax2 = null;
        
        
        
//        
//        PrintWriter out = response.getWriter();
//                    
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Error</title>"); 
//            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script> ");
//            
//            out.println("<link rel=\"stylesheet\" href=\"js/css/bootstrap.css\">");
//            out.println("<link rel=\"stylesheet\" href=\"js/css/bootstrap.min.css\">");
//            out.println("<script src=\"js/js/bootstrap.min.js\" type=\"text/javascript\"></script>");
//            out.println(" <script src=\"script.js\" type=\"text/javascript\"></script>");
//            out.println("<nav class=\"navbar navbar-inverse navbar-fixed-top\">");
//            
//out.println("<div class=\"container-fluid\">");
//out.println("<div class=\"navbar-header\">");
//out.println("<a class=\"navbar-brand\" href=\"#\">");
//out.println("<img alt=\"Brand\" src=\"js/firstbank.png\">");
//out.print("</a>");
//out.println("</div>");
//out.println("</div>");
//out.print("</nav\">");
//
//           
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<br />");
//            out.println("<br />");
//            out.println("<br />");
//            out.println("<br />");
//            
//        
//        out.println("<div class=\"container\">");
        
        if (con != null) {
            //System.out.println("connection is "+con);
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory();

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                try {
                    // Parse the request
                    List items = upload.parseRequest(request);
                    Iterator iterator = items.iterator();
                    while (iterator.hasNext()) {
                        FileItem item = (FileItem) iterator.next();

                        if (item.isFormField()) {
                            if (item.getFieldName().equals("suppliername")) {
                                suppliername = item.getString();
                                if(suppliername.equals("") ||(!suppliername.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
//                                    out.println("<h5>Please enter correct supplier name</h5>");
                                }
                              //  System.out.println("Validation1 is "+validation);
                            }

                            if (item.getFieldName().equals("tax")) {
                                tax = item.getString();
                                if (!tax.equals("")){
                                if ((!tax.matches("^\\d{8}-0001$"))){
                                    validation = false;
                                  }
                                }
//                                System.out.println("Validation2 is "+validation);
//                                System.out.println("tax is "+tax);
                            }
                            if (item.getFieldName().equals("checkedstate")) {
                                officelocation = item.getString();
                                
                            }
                            if (item.getFieldName().equals("site")) {
                                sitename = item.getString();
                                if(sitename.equals("") || (!sitename.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation3 is "+validation);
                            }
                            if (item.getFieldName().equals("country")) {
                                country = item.getString();
                                if(country.equals("") || (!country.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation4 is "+validation);
                            }
                            if (item.getFieldName().equals("selectsup")) {
                                suppliertype = item.getString();
                                if (suppliertype.equals("") || (!suppliertype.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation5 is "+validation);
                            }
                            if (item.getFieldName().equals("business")) {
                                businessclass = item.getString();
                                if (businessclass.equals("") || (!businessclass.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation6 is "+validation);
                            }
                            if (item.getFieldName().equals("certifications")) {
                                certifications = item.getString();
                                if (!certifications.equals("")){
                                if(!certifications.matches("^[a-zA-Z0-9 ,.-]+$")){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("addressline1")) {
                                address1 = item.getString();
                                if (address1.equals("") || (!address1.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation7 is "+validation);
                            }
                            if (item.getFieldName().equals("addressline2")) {
                                address2 = item.getString();
                                if (!address2.equals("")){
                                if (!address2.matches("^[a-zA-Z0-9 ,.-]+$")){
                                validation = false;
                            }
                                }
                            }
                            if (item.getFieldName().equals("addressline3")) {
                                address3 = item.getString();
                                if (!address3.equals("")){
                                 if (!address3.matches("^[a-zA-Z0-9 ,.-]+$")){
                                validation = false;
                            }
                                }
                            }
                            if (item.getFieldName().equals("city")) {
                                cityname = item.getString();
                                if (cityname.equals("") || (!cityname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation8 is "+validation);
                            }
                            if (item.getFieldName().equals("customerResidState")) {
                                state = item.getString();
                                if (!state.equals("")){
                                 if ((!state.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("customerResidCity")) {
                                lga = item.getString();
                                if (!lga.equals("")){
                                     if ((!lga.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("unit")) {
                                operatingunit = item.getString();
                            }
                            if (item.getFieldName().equals("site2")) {
                                sitename2 = item.getString();
                                 if (!sitename2.equals("")){
                                     if ((!sitename2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("country2")) {
                                country2 = item.getString();
                                 if (!country2.equals("")){
                                     if ((!country2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("addressline1_2")) {
                                address1_2 = item.getString();
                                  if (!address1_2.equals("")){
                                     if ((!address1_2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("addressline2_2")) {
                                address2_2 = item.getString();
                                if (!address2_2.equals("")){
                                     if ((!address2_2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("addressline3_2")) {
                                address3_2 = item.getString();
                                if (!address3_2.equals("")){
                                     if ((!address3_2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("city2")) {
                                cityname2 = item.getString();
                                if (!cityname2.equals("")){
                                     if ((!cityname2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("customerResidState2")) {
                                state2 = item.getString();
                                if (!state2.equals("")){
                                     if ((!state2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("customerResidCity2")) {
                                lga2 = item.getString();
                                 if (!lga2.equals("")){
                                     if ((!lga2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("unit2")) {
                                operatingunit2 = item.getString();
                                if (!operatingunit2.equals("")){
                                     if ((!operatingunit2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("salutation")) {
                                salutation = item.getString();
                                if (salutation.equals("") || (!salutation.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation9 is "+validation);
                            }
                            if (item.getFieldName().equals("lname")) {
                                laname = item.getString();
                                if(laname.equals("") || (!laname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation10 is "+validation);
                            }
                            if (item.getFieldName().equals("fname")) {
                                fname = item.getString();
                                if (fname.equals("") || (!fname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation11 is "+validation);
                            }
                            if (item.getFieldName().equals("dept")) {
                                department = item.getString();
                                if (department.equals("") || (!department.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation12 is "+validation);
                            }
                            if (item.getFieldName().equals("child_1")) {
                                contactnumber = item.getString();
                                if (contactnumber.equals("") || (!contactnumber.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation13 is "+validation);
                            }
                            if (item.getFieldName().equals("chil_1")) {
                                emailaddress = item.getString();
                                if (emailaddress.equals("")|| (!emailaddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation14 is "+validation);
                            }
                            if (item.getFieldName().equals("fbndepartment")) {
                                fbndepartment = item.getString();
                                if(fbndepartment.equals("") || (!fbndepartment.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation15 is "+validation);
                            }
                            if (item.getFieldName().equals("staffname")) {
                                staffname = item.getString();
                                if (staffname.equals("")|| (!staffname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation16 is "+validation);
                            }
                            if (item.getFieldName().equals("payment")) {
                                paymentmethod = item.getString();
                                if (paymentmethod.equals("") || (!paymentmethod.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation17 is "+validation);
                            }
                            if (item.getFieldName().equals("deliveryMethod")) {
                                deliverymethod = item.getString();
                                if (deliverymethod.equals("") || (!deliverymethod.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation18 is "+validation);
                            }
                            if (item.getFieldName().equals("eftemail")) {
                                deliveryemailaddress = item.getString();
                                if (deliveryemailaddress.equals("") || (!deliveryemailaddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation19 is "+validation);
                            }
                            if (item.getFieldName().equals("swiftcode")) {
                                swiftcode = item.getString();
                                if (!swiftcode.equals("")){
                                     if ((!swiftcode.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("iban")) {
                                iban = item.getString();
                                if (!iban.equals("")){
                                     if ((!iban.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("Currencies")) {
                                invoicecurrency = item.getString();
                                if (invoicecurrency.equals("") || (!invoicecurrency.matches("^[a-zA-Z0-9 ,.-]+$"))){ 
                                    validation = false;
                                }
//                                System.out.println("Validation20 is "+validation);
                            }
                            if (item.getFieldName().equals("paymentterms")) {
                                paymentterms = item.getString();
                                if (paymentterms.equals("") || (!paymentterms.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation21 is "+validation);
                            }
                            if (item.getFieldName().equals("bank_name")) {
                                bankname = item.getString();
                                if (bankname.equals("") || (!bankname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation22 is "+validation);
                            }
                            if (item.getFieldName().equals("bankbranchname")) {
                                branchname = item.getString();
                                if (branchname.equals("") || (!branchname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation23 is "+validation);
                            }
                            if (item.getFieldName().equals("accountnumber")) {
                                accountnumber = item.getString();
                                if (accountnumber.equals("") || (!accountnumber.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation24 is "+validation);
                            }
                            if (item.getFieldName().equals("accountname")) {
                                accountname = item.getString();
                                if (accountname.equals("") || (!accountname.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation25 is "+validation);
                            }
                            if (item.getFieldName().equals("currency")) {
                                accountcurrency = item.getString();
                                if(accountcurrency.equals("") || (!accountcurrency.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
//                                System.out.println("Validation26 is "+validation);
                            }

                            if (item.getFieldName().equals("child_1_2")) {
                                contactnumber2 = item.getString();
                                 if (!contactnumber2.equals("")){
                                     if ((!contactnumber2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("salutation2")) {
                                salutation2 = item.getString();
                                if (!salutation2.equals("")){
                                     if ((!salutation2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("fname2")) {
                                fname2 = item.getString();
                                 if (!fname2.equals("")){
                                     if ((!fname2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("lname2")) {
                                lname2 = item.getString();
                                if (!lname2.equals("")){
                                     if ((!lname2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("dept2")) {
                                department2 = item.getString();
                                if (!department2.equals("")){
                                     if ((!department2.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("others1")) {
                                othersupplier = item.getString();
                                 if (!othersupplier.equals("")){
                                     if ((!othersupplier.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("others2")) {
                                otherbusiness = item.getString();
                                 if (!otherbusiness.equals("")){
                                     if ((!otherbusiness.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("others4")) {
                                otherbanks = item.getString();
                                if (!otherbanks.equals("")){
                                     if ((!otherbanks.matches("^[a-zA-Z0-9 ,.-]+$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("chil_1_2")) {
                                emailaddress2 = item.getString();
                                if (!emailaddress2.equals("")){
                                     if ((!emailaddress2.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))){
                                    validation = false;
                                }
                                }
                            }
                            if (item.getFieldName().equals("currency2")) {
                                invoicecurrency2 = item.getString();
                                
                            }
                            if (item.getFieldName().equals("currency3")) {
                                invoicecurrency3 = item.getString();
                            }
                            if (item.getFieldName().equals("currency4")) {
                                invoicecurrency4 = item.getString();
                            }
                            if (item.getFieldName().equals("currency5")) {
                                invoicecurrency5 = item.getString();
                            }
                            if (item.getFieldName().equals("tax2")) {
                                tax2 = item.getString();
//                                 if (!tax2.equals("")){
//                                if ((!tax2.matches("^\\d{8}-0001$"))){
//                                    validation = false;
//                                  }
//                                }
                            }
                            if (item.getFieldName().equals("checkedcompany")) {
                                company_type = item.getString();
                            }
                            Date date = new Date();
                            createddate = date.toString();
                        }
//                         out.println("</div>");
//            out.println("</body>");
//            out.println("</html>");
                        if (!item.isFormField()) {
                            fileName = item.getName();
                            String root = getServletContext().getRealPath("/");
                            File path = new File(root + "/Users");
                            if (!path.exists()) {
                                boolean status = path.mkdirs();
                            }

                            File uploadedFile = new File(path + "/" + suppliername + " " + fbndepartment + "-" + fileName);
//                            System.out.println(uploadedFile.getAbsolutePath());
                            // String path25 = uploadedFile.getAbsolutePath();
                            item.write(uploadedFile);
                        }
                    }
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String[] offices = officelocation.split(" ");
           
            //System.out.println("Validation is "+validation);
//            try {
//                rst=stmt.executeQuery("IF EXISTS (SELECT * FROM vendorInformation WHERE tax= "+tax +")" + "SELECT tax FROM vendorInformation WHERE tax= "+tax);  
//                rst.next();
//                 tin = rst.getString("tax");
//            } catch (SQLException ex) {
//                Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);           
//            }
//            
//             try {
//                rst=stmt.executeQuery("select multinational_tax_id from vendorInformation where multinational_tax_id = "+tax2);  
//                rst.next();
//                 tin2 = rst.getString("multinational_tax_id");
//            } catch (SQLException ex) {
//                Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);           
//            }
//            
            
            if (validation){
             
              
            
              
            sq = "insert into vendorInformation (suppliername, "
                    + "supplier_type_id, business_class_id, certifications, tax, "
                    + "sitename, country, address1, "
                    + "address2, address3, cityname, state, lga, operatingunit, salutation, "
                    + "lastname,firstname, department, contactnumber, "
                    + "emailaddress, costcenter_id, staffname, "
                    + "paymentmethod, deliverymethod, deliveryemailaddress,"
                    + " invoicecurrency, paymentterms, bankname, "
                    + "bankbranchname, accountnumber, accountname, "
                    + "accountcurrency, sitename2, country2, address1_2, "
                    + "address2_2, address3_2, cityname2, state2, lga2, "
                    + "operatingunit2, emailaddress2, "
                    + "contactnumber2, salutation2, firstname2, "
                    + "lastname2, department2, invoicecurrency2, "
                    + "invoicecurrency3, invoicecurrency4, invoicecurrency5, "
                    + "company_type, otherbusinessclasses, othersuppliertypes, "
                    + "other_bank_name, swiftcode, iban, createddate, "
                    + "multinational_tax_id, filename) "
                    + "values('" + suppliername + "','" + suppliertype + "','"
                    + "" + businessclass + "','" + certifications + "','" + tax + "','"
                    + "" + sitename + "','" + country + "','"
                    + "" + address1 + "','" + address2 + "','" + address3 + "','"
                    + "" + cityname + "','" + state + "','" + lga + "','" + operatingunit + "','"
                    + "" + salutation + "','" + laname + "','" + fname + "','" + department + "','"
                    + "" + contactnumber + "','" + emailaddress + "','" + fbndepartment + "','"
                    + "" + staffname + "','" + paymentmethod + "','" + deliverymethod + "','"
                    + "" + deliveryemailaddress + "','" + invoicecurrency + "','"
                    + "" + paymentterms + "','" + bankname + "','" + branchname + "','"
                    + "" + accountnumber + "','" + accountname + "','"
                    + "" + accountcurrency + "','" + sitename2 + "','" + country2 + "','"
                    + "" + address1_2 + "','" + address2_2 + "','" + address3_2 + "','"
                    + "" + cityname2 + "','" + state2 + "','" + lga2 + "','"
                    + "" + operatingunit2 + "','" + emailaddress2 + "','" 
                    + "" + contactnumber2 + "','" + salutation2 + "','"
                    + "" + fname2 + "','" + lname2 + "','" + department2 + "','"
                    + "" + invoicecurrency2 + "','" + invoicecurrency3 + "','"
                    + "" + invoicecurrency4 + "','" + invoicecurrency5 + "','"
                    + "" + company_type + "','" + otherbusiness + "','" 
                    + "" + othersupplier + "','" + otherbanks + "','" 
                    + "" + swiftcode + "','" + iban + "','" + createddate + "','" + tax2 + "','"
                    + "" + fileName + "')";
            
         
          
            try {
                stmt = con.prepareStatement(sq, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                rst = stmt.getGeneratedKeys();
                rst.next();
                //     System.out.println("The generated key is "+rst.toString());
                vendorId = rst.getInt(1);
//                System.out.println("The generated key is " + vendorId);

                String qryPart = "";
                
                    
                for (int i = 0; i < offices.length; i++) {
                    if (i == offices.length - 1) {
                        qryPart += "(" + vendorId + "," + offices[i] + ")";
                    } else {
                        qryPart += "(" + vendorId + "," + offices[i] + "),";
                    }
                }
                
                
                    
                String sql;
                sql = "INSERT INTO officelocation (vendor_id, state_id) values " + qryPart;

                stmt = con.prepareStatement(sql);
//                System.out.println("length of string "+offices.length);
//                System.out.println("index 1 is "+offices[0]);
                if (offices[0].trim().length() != 0){
                stmt.executeUpdate();
                }
                
              

            } catch (SQLException ex) {
                 response.sendError(400);
               Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
              
                }
            
            response.sendRedirect("welcome.jsp");
            
            }
            
            else {
                
            response.sendError(400);
        }
        
        
        }
        
        
        
        

    

        }




    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
