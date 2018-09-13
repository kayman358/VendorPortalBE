<%-- 
    Document   : login.jsp
    Created on : May 25, 2017, 9:25:45 AM
    Author     : TN041502
--%>


<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@include file="dataSource.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script> 
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> -->
<script src="script.js" type="text/javascript"></script>
<link rel="stylesheet" href="js/css/bootstrap.min.css">
<link rel="stylesheet" href="js/css/bootstrap.css">
<link rel="stylesheet" href="js/css/intlTelInput.css">

<script src="js/js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/js/intlTelInput.js" type="text/javascript"></script>
<script src="js/js/utils.js" type="text/javascript"></script>

<!DOCTYPE html>

<html>
    <head>
        
        <!--  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>-->
        <script type="text/javascript" src="js/jquery.chained.mini.js"></script>
        <script type="text/javascript" src="js/test.js"></script>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FBN Vendor Portal</title>
        
    </head>
    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                        <img alt="Brand" src="js/firstbank.png">
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">


            <br />
            <br />
            <br />
            <br />
            <h1 class="page-header">Supplier Capture Portal (Back-Office)</h1>

   
            <h3>Create a new Back-Office user</h3><br />

           
           <form action="Create" method="POST">
                <!-- <div class="row">


                        <div class="col-md-6 required inner-addon left-addon">
                            <i class="glyphicon glyphicon-user"></i>
                            <label for="username">Username</label>
                            <input class="form-control" id = "newusername" name="newusername" placeholder="SN012345" type="text" maxlength="50" required="true">
                       
                 </div>
                
                        <div class="col-md-6 required">
                            <label for="email">Email</label>
                            <input class="form-control" id = "email" name="email" placeholder="SN012345@FIRSTBANKNIGERIA.COM" type="text" required="true">
                        </div>
                    </div><br />
                    
                    
                    
                    
                     <div class="row">


                        <div class="col-md-4 required inner-addon left-addon">
                            
                            <label for="firstname">First Name</label>
                            <input class="form-control" id = "firstname" name="firstname" placeholder="First Name" type="text" maxlength="50" required="true">
                       
                 </div>
                          <div class="col-md-4 inner-addon left-addon">
                            
                            <label for="firstname">Middle Name</label>
                            <input class="form-control" id = "middlename" name="middlename" placeholder="Middle Name" type="text" maxlength="50">
                       
                 </div>
                
                        <div class="col-md-4 required">
                            <label for="lastname">Last Name</label>
                            <input class="form-control" id = "lastname" name="lastname" placeholder="Last Name" type="text" required="true">
                        </div>
                    </div><br />-->
                    
                    <div class="row">
                    
                    <div class="col-md-6 required inner-addon left-addon">
                            <i class="glyphicon glyphicon-user"></i>
                            <label for="username">Username</label>
                            <input class="form-control" id = "newusername" name="newusername" placeholder="SN012345" type="text" maxlength="50" required="true">
                       
                    </div></div>
                    
                    <br/>
                    
                        <div class="row">
                        <div class="col-md-6 required">
                            <label for="role">Select Role</label>
                            <select class="form-control" id="role" name="role">
                            <option value="" selected>--Select--</option>
                            <option value="1">Maker</option>
                            <option value="2">Checker</option>
                            <option value="3">Super User</option>
                            </select>
                        </div>
                            
                             <div class="col-md-6 required">
                             <label for="role">Branch/Head Office Department</label>
                             <select class="form-control" id="fbndept" name="fbndept">
                              <option value="" selected="selected">--Choose--</option>
                            <c:forEach var="row" items="${result2.rows}">
                            <option value="${row.id}">${row.costcenters}</option>
                       </c:forEach>      
                        </select>
                             </div>    
                            
                    </div><br />
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    <button type="submit" class="btn btn-primary">Create User</button>&nbsp&nbsp&nbsp<button type="reset" class="btn btn-primary">Reset</button>
                    
            </form>
   
        </div>

        <footer>
            <div class="container">
                <div class="row">

                    <strong style="color: #fff">&copy 2017. First Bank of Nigeria Ltd. All Rights Reserved</strong><br /><br />
                    <ul class="footer-links">
                         <p style="color:white;float:right;font-weight: 100;font-size: 28px;margin-top: 10px;font-style:italic;">
                    <span style="color: #FFD700">-</span>You First<span style="color: #FFD700">-</span>
                </p>
                        <li id="fb"><a href="http://www.firstbanknigeria.com/" target="_blank">Privacy Policy</a></li>
                        <li id="fb"><a href="http://www.firstbanknigeria.com/" target="_blank">Terms of Use</a></li>
                        <li id="fb"><a href="http://www.firstbanknigeria.com/" target="_blank">Cookie Policy</a></li>
                        <li id="fb"><a href="http://www.firstbanknigeria.com/" target="_blank">Accessibility</a></li>
                    </ul>


                    <div >

                       
                    </div>
                </div>
            </div>


        </footer>
    </body>
</html>