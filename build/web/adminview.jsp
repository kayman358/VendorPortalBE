<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@include file="dataSource.jsp" %>


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="script.js" type="text/javascript"></script>
<link rel="stylesheet" href="js/css/bootstrap.min.css">
<link rel="stylesheet" href="js/css/bootstrap.css">
<link href="style.css" rel="stylesheet" type="text/css">
<link href="js/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="js/js/bootstrap.js" type="text/javascript"></script>
<script src="js/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" />
<script src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" type="text/css">
<script src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
<html>
    <head>
        <title>Vendor Information</title>
        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.chained.mini.js"></script>
        <script type="text/javascript" src="js/test.js"></script>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <script>
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>

    </head>
    <body>
         <%  String username = (String) session.getAttribute("username");
             if (session.getAttribute("username") == null) {
                response.sendRedirect("index.jsp");
            }
            // System.out.print("request is "+request.getQueryString());
           //  if (!request.getQueryString().equals(null)){
                
            //       if  (!request.getQueryString().equalsIgnoreCase("staffNo="+username)){
            //      response.sendRedirect("login.jsp");    
            //   }
         //  }
         //session.setMaxInactiveInterval(15*60);
         %>
         
         
           
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                        <img alt="Brand" src="js/firstbank.png">
                    </a>
                </div>
            </div>
        </nav>
        
        <br /><br /><br /><br />
        
        
        <div class="container">
            <h1 class="page-header">List of All Prospective Suppliers (iSupplier Module)</h1>
            <br /><br />
            <form action="Logout" method="Post">
              
                <button type="submit" name="submitbtn" class="btn btn-primary" style="margin-left: 1075px">Logout</button>
            </form>
            <a href="new-user.jsp" class="btn btn-primary">Create new User</a>
         <!--   <form action="Filter" method="Post">
                <div class="col-md-3">
            <label>Wish to Filter Approval Level?</label>
            <select class="form-control" name="filter" id='filter' onchange="filter()">
            <option>--Select--</option>
            <option value="Not Confirmed">Not Confirmed</option>
            <option value="Approved">Approved</option>
            <option value="Confirmed">Confirmed</option>
            </select></div>
       </form>-->
            
            <p style="text-align: right; color: #2e6da4">Welcome ${myname}</p><br />
            
            <% if (session.getAttribute("Lastlogin") != null) { %>
            <p style="text-align: right; color: #2e6da4">Last Login Date: ${Lastlogin}</p><br />
                <%    }%>

          
            
            <table id="mytable" class="table table-hover table-bordered table-striped">
                <thead>
                <tr>
                 <!--   <th>S/N</th>-->
                    <th>Supplier Name</th>
                    <th>Supplier Type</th>
                    <th>Business Class</th>
                    <th>FBN Branch/Department</th>
                    <th>Enrolled Date</th>
                    <th>Contact Person</th>
                    <!--<th>Confirm Vendor</th>-->
                    <th>Status</th>
                    <th>View Attachment</th>
                </tr>
               </thead> 
               <tbody id="myTableBody">
                <c:forEach var="row" items="${rest1.rows}">
                    <tr>
                   
                    <td><c:out value="${row.suppliername}"/></td>
                    <td><c:out value="${row.supplierType}"/></td>
                    <td><c:out value="${row.businessclass}"/></td>
                    <td><c:out value="${row.costcenters}"/></td>
                    <td><c:out value="${row.createddate}"/></td>
                    <td><c:out value="${row.staffname}"/></td>
                    
                    <!--<td>
                        
                            <input type="hidden" name="btnId" value="${row.id}"/>
                            <input name="confirm" id="confirm" class="confirm" type="checkbox"  value="${row.id}" onchange="confirmVendor()">


                       </td>-->
                       <td><c:out value="${row.status}"/></td>
                    
                    <td>
            <form action="Pdf" method="Post">
               <input type="hidden" name="rowId2" value="${row.id}"/>
               <button type="submit" name="submitbtn" class="btn btn-primary">Download</button>
            </form>
                    </td>
                    </tr>
                </c:forEach>
            </tbody>
                <tr>
                   <!--   <th>S/N</th>-->
                    <th>Supplier Name</th>
                    <th>Supplier Type</th>
                    <th>Business Class</th>
                    <th>FBN Branch/Department</th>
                    <th>Enrolled Date</th>
                     <th>Contact Person</th>
                   <!-- <th>Confirm Vendor</th>-->
                    <th>Status</th>
                     <th>View Attachment</th>
                </tr>
            </table>
          <!--  <form action="Approve" method="Post">
                <input type="hidden" name="confirmedIds" id="confirmedIds">
                <button type="submit" name="submitbtn" class="btn btn-danger" style="margin-left: 985px">Submit Confirmation</button>
            </form>-->
            <div class="pagination-container">
                <nav>
                    <ul class="pagination"></ul>
                </nav>
            </div>
        </div>
       
        <footer>
            
            <script src="js/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="js/js/jquery.dataTables.min.js" type="text/javascript"></script>
            <script src="js/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
            <script src="js/js/jquery.dataTables.js" type="text/javascript"></script>
            <script src="js/js/dataTables.bootstrap.js" type="text/javascript"></script>
            <!--    <script>window.onbeforeunload = function(){
    //Ajax request to update the database
    $.ajax({
        type: "POST",
        url: "BrowserCloseServlet"
    });
    return ("Goodbye");
};</script>-->
            <script>
            $(document).ready(function(){
    $('#mytable').DataTable( {
        
       /* "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false */
        });
});

        </script>
            
            <div class="container">
                <div class="row">

                    <strong style="color: #fff">&copy 2017. FirstBank of Nigeria PLC. All Rights Reserved</strong><br /><br />
                    <ul class="footer-links">
                         <p style="color:white;float:right;font-weight: 100;font-size: 28px;margin-top: 10px;font-style:italic;">
                    <span style="color: #FFD700">-</span>You First<span style="color: #FFD700">-</span>
                </p>
                        <li><a href="http://www.firstbanknigeria.com/" target="_blank">Privacy Policy</a></li>
                        <li><a href="http://www.firstbanknigeria.com/" target="_blank">Terms of Use</a></li>
                        <li><a href="http://www.firstbanknigeria.com/" target="_blank">Cookie Policy</a></li>
                        <li><a href="http://www.firstbanknigeria.com/" target="_blank">Accessibility</a></li>
                    </ul>


                 
                </div>
            </div>
            
            
            

        </footer>
    </body>
</div>
</html>