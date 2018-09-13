
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class UploadData extends HttpServlet {
    @Resource(name="jdbc/SupplierCapture")
	private DataSource ds;
    Connection con = null;
    ResultSet rst = null;
    PreparedStatement stmt = null;
    String fileName = null;
    String fileType = null;
    String sq;
    int vendorId;
   

    String  suppliername = null, tax = null, suppliertype = null, businessclass = null,
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

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
         boolean validation = true;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
        }
        // gets values of text fields
        suppliername = request.getParameter("suppliername");
        suppliertype = request.getParameter("selectsup");
        businessclass = request.getParameter("business");
        certifications = request.getParameter("certifications");
        tax = request.getParameter("tax");
        officelocation = request.getParameter("checkedstate");
        sitename = request.getParameter("site");
        country = request.getParameter("country");
        address1 = request.getParameter("addressline1");
        address2 = request.getParameter("addressline2");
        address3 = request.getParameter("addressline3");
        cityname = request.getParameter("city");
        state = request.getParameter("customerResidState");
        lga = request.getParameter("customerResidCity");
        operatingunit = request.getParameter("unit");
        salutation = request.getParameter("salutation");
        laname = request.getParameter("lname");
        fname = request.getParameter("fname");
        department = request.getParameter("dept");
        contactnumber = request.getParameter("child_1");
        emailaddress = request.getParameter("chil_1");
        fbndepartment = request.getParameter("fbndepartment");
        staffname = request.getParameter("staffname");
        paymentmethod = request.getParameter("payment");
        deliverymethod = request.getParameter("deliveryMethod");
        deliveryemailaddress = request.getParameter("eftemail");
        invoicecurrency = request.getParameter("Currencies");
        paymentterms = request.getParameter("paymentterms");
        bankname = request.getParameter("bank_name");
        branchname = request.getParameter("bankbranchname");
        accountnumber = request.getParameter("accountnumber");
        accountname = request.getParameter("accountname");
        accountcurrency = request.getParameter("currency");
        sitename2 = request.getParameter("site2");
        country2 = request.getParameter("country2");
        address1_2 = request.getParameter("addressline1_2");
        address2_2 = request.getParameter("addressline2_2");
        address3_2 = request.getParameter("addressline3_2");
        cityname2 = request.getParameter("city2");
        state2 = request.getParameter("customerResidState2");
        lga2 = request.getParameter("customerResidCity2");
        operatingunit2 = request.getParameter("unit2");
        salutation2 = request.getParameter("salutation2");
        lname2 = request.getParameter("lname2");
        fname2 = request.getParameter("fname2");
        department2 = request.getParameter("dept2");
        contactnumber2 = request.getParameter("child_1_2");
        emailaddress2 = request.getParameter("chil_1_2");
        company_type = request.getParameter("checkedcompany");
        otherbusiness = request.getParameter("others2");
        othersupplier = request.getParameter("others1");
        otherbanks = request.getParameter("others4");
        invoicecurrency2 = request.getParameter("currency2");
        invoicecurrency3 = request.getParameter("currency3");
        invoicecurrency4 = request.getParameter("currency4");
        invoicecurrency5 = request.getParameter("currency5");
        iban = request.getParameter("iban");
        swiftcode = request.getParameter("swiftcode");
        tax2 = request.getParameter("tax2");

        Date date = new Date();
        createddate = date.toString();

        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("attachment");
        if (filePart != null) {
           // fileName = filePart.getName();
            fileType = filePart.getContentType();
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
           // System.out.println("File name is "+fnamed);
            // prints out some information for debugging
          //  System.out.println(filePart.getName());
          //  System.out.println(filePart.getSize());
          //  System.out.println(filePart.getContentType());

            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        if (suppliername.equals("") || (!suppliername.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (!tax.equals("")) {
            if ((!tax.matches("^\\d{8}-0001$"))) {
                validation = false;
            }
        }
        if (sitename.equals("") || (!sitename.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (country.equals("") || (!country.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (suppliertype.equals("") || (!suppliertype.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (businessclass.equals("") || (!businessclass.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (!certifications.equals("")) {
            if (!certifications.matches("^[a-zA-Z0-9 ,.-]+$")) {
                validation = false;
            }
        }
        if (address1.equals("") || (!address1.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (!address2.equals("")) {
            if (!address2.matches("^[a-zA-Z0-9 ,.-]+$")) {
                validation = false;
            }
        }
        if (!address3.equals("")) {
            if (!address3.matches("^[a-zA-Z0-9 ,.-]+$")) {
                validation = false;
            }
        }
        if (cityname.equals("") || (!cityname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (!state.equals("")) {
            if ((!state.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!lga.equals("")) {
            if ((!lga.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!sitename2.equals("")) {
            if ((!sitename2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!country2.equals("")) {
            if ((!country2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!address1_2.equals("")) {
            if ((!address1_2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!address2_2.equals("")) {
            if ((!address2_2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!address3_2.equals("")) {
            if ((!address3_2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!cityname2.equals("")) {
            if ((!cityname2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!tax2.equals("")) {
            if ((!tax2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!state2.equals("")) {
            if ((!state2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!lga2.equals("")) {
            if ((!lga2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!operatingunit2.equals("")) {
            if ((!operatingunit2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (salutation.equals("") || (!salutation.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (laname.equals("") || (!laname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (fname.equals("") || (!fname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (department.equals("") || (!department.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (contactnumber.equals("") || (!contactnumber.matches("^[a-zA-Z0-9 ()+,.-]+$"))) {
            validation = false;
        }
        if (emailaddress.equals("") || (!emailaddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
            validation = false;
        }
        if (fbndepartment.equals("") || (!fbndepartment.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (staffname.equals("") || (!staffname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (paymentmethod.equals("") || (!paymentmethod.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (deliverymethod.equals("") || (!deliverymethod.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (deliveryemailaddress.equals("") || (!deliveryemailaddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
            validation = false;
        }
        if (!swiftcode.equals("")) {
            if ((!swiftcode.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!iban.equals("")) {
            if ((!iban.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (invoicecurrency.equals("") || (!invoicecurrency.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (paymentterms.equals("") || (!paymentterms.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (bankname.equals("") || (!bankname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (branchname.equals("") || (!branchname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (accountnumber.equals("") || (!accountnumber.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (accountname.equals("") || (!accountname.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (accountcurrency.equals("") || (!accountcurrency.matches("^[a-zA-Z0-9 ,.-]+$"))) {
            validation = false;
        }
        if (!contactnumber2.equals("")) {
            if ((!contactnumber2.matches("^[a-zA-Z0-9 ()+,.-]+$"))) {
                validation = false;
            }
        }
        if (!salutation2.equals("")) {
            if ((!salutation2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!fname2.equals("")) {
            if ((!fname2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!lname2.equals("")) {
            if ((!lname2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!department2.equals("")) {
            if ((!department2.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!othersupplier.equals("")) {
            if ((!othersupplier.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!otherbusiness.equals("")) {
            if ((!otherbusiness.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!otherbanks.equals("")) {
            if ((!otherbanks.matches("^[a-zA-Z0-9 ,.-]+$"))) {
                validation = false;
            }
        }
        if (!emailaddress2.equals("")) {
            if ((!emailaddress2.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
                validation = false;
            }
        }
        
        if (tax.equals("")){
            tax = tax2;
        }

        String message = null;  // message will be sent back to client

        String[] offices = officelocation.split(" ");

        if (validation) {

            String sql = "INSERT INTO vendorInformation (suppliername, "
                    + "supplier_type_id, business_class_id, certifications, tax, "
                    + "sitename, country, address1, "
                    + "address2, address3, cityname, state, lga, operatingunit, salutation, "
                    + "lastname, firstname, department, contactnumber, "
                    + "emailaddress, costcenter_id, staffname, "
                    + "paymentmethod, deliverymethod, deliveryemailaddress, "
                    + "invoicecurrency, paymentterms, bankname, "
                    + "bankbranchname, accountnumber, accountname, "
                    + "accountcurrency, sitename2, country2, address1_2, "
                    + "address2_2, address3_2, cityname2, state2, lga2, "
                    + "operatingunit2, emailaddress2, "
                    + "contactnumber2, salutation2, firstname2, "
                    + "lastname2, department2, invoicecurrency2, "
                    + "invoicecurrency3, invoicecurrency4, invoicecurrency5, "
                    + "company_type, otherbusinessclasses, othersuppliertypes, "
                    + "other_bank_name, swiftcode, iban, createddate, "
                    + "multinational_tax_id, filename, filetype, pdfdocument) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                // connects to the database

                // constructs SQL statement
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                stmt.setString(1, suppliername);
                stmt.setString(2, suppliertype);
                stmt.setString(3, businessclass);
                stmt.setString(4, certifications);
                stmt.setString(5, tax);
                stmt.setString(6, sitename);
                stmt.setString(7, country);
                stmt.setString(8, address1);
                stmt.setString(9, address2);
                stmt.setString(10, address3);
                stmt.setString(11, cityname);
                stmt.setString(12, state);
                stmt.setString(13, lga);
                stmt.setString(14, operatingunit);
                stmt.setString(15, salutation);
                stmt.setString(16, laname);
                stmt.setString(17, fname);
                stmt.setString(18, department);
                stmt.setString(19, contactnumber);
                stmt.setString(20, emailaddress);
                stmt.setString(21, fbndepartment);
                stmt.setString(22, staffname);
                stmt.setString(23, paymentmethod);
                stmt.setString(24, deliverymethod);
                stmt.setString(25, deliveryemailaddress);
                stmt.setString(26, invoicecurrency);
                stmt.setString(27, paymentterms);
                stmt.setString(28, bankname);
                stmt.setString(29, branchname);
                stmt.setString(30, accountnumber);
                stmt.setString(31, accountname);
                stmt.setString(32, accountcurrency);
                stmt.setString(33, sitename2);
                stmt.setString(34, country2);
                stmt.setString(35, address1_2);
                stmt.setString(36, address2_2);
                stmt.setString(37, address3_2);
                stmt.setString(38, cityname2);
                stmt.setString(39, state2);
                stmt.setString(40, lga2);
                stmt.setString(41, operatingunit2);
                stmt.setString(42, emailaddress2);
                stmt.setString(43, contactnumber2);
                stmt.setString(44, salutation2);
                stmt.setString(45, fname2);
                stmt.setString(46, lname2);
                stmt.setString(47, department2);
                stmt.setString(48, invoicecurrency2);
                stmt.setString(49, invoicecurrency3);
                stmt.setString(50, invoicecurrency4);
                stmt.setString(51, invoicecurrency5);
                stmt.setString(52, company_type);
                stmt.setString(53, otherbusiness);
                stmt.setString(54, othersupplier);
                stmt.setString(55, otherbanks);
                stmt.setString(56, swiftcode);
                stmt.setString(57, iban);
                stmt.setString(58, createddate);
                stmt.setString(59, tax2);
                stmt.setString(60, fileName);
                stmt.setString(61, fileType);
                if (inputStream != null) {
                    // fetches input stream of the upload file for the blob column
                    stmt.setBlob(62, inputStream);
                }

                // sends the statement to the database server
                //    int row = stmt.executeUpdate();
//            if (row > 0) {
//                message = "File uploaded and saved into database";
//            }
                int row = stmt.executeUpdate();
                if (row > 0) {
                    message = "Record Uploaded";
                }
                rst = stmt.getGeneratedKeys();
                rst.next();
                //     System.out.println("The generated key is "+rst.toString());
                vendorId = rst.getInt(1);
              //  System.out.println("The generated key is " + vendorId);

                String qryPart = "";

                for (int i = 0; i < offices.length; i++) {
                    if (i == offices.length - 1) {
                        qryPart += "(" + vendorId + "," + offices[i] + ")";
                    } else {
                        qryPart += "(" + vendorId + "," + offices[i] + "),";
                    }
                }

                String sql2;
                sql2 = "INSERT INTO officelocation (vendor_id, state_id) values " + qryPart;

                stmt = con.prepareStatement(sql2);
//                System.out.println("length of string "+offices.length);
//                System.out.println("index 1 is "+offices[0]);
                if (offices[0].trim().length() != 0) {
                    stmt.executeUpdate();
                }

            } catch (SQLException ex) {
                response.sendRedirect("error.jsp");
                Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
                message = "ERROR: " + ex.getMessage();
                request.setAttribute("Message", message);

                //  ex.printStackTrace();
            } finally {
                if (con != null) {
                    // closes the database connection
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            request.setAttribute("Message", message);
           response.sendRedirect("welcome.jsp");
        } else if (!validation) {
            request.setAttribute("Message", message);
             response.sendRedirect("error-400.jsp");
            //  response.sendError(400);
        }

    }
}
