<%-- 
    Document   : login.jsp
    Created on : May 25, 2017, 9:25:45 AM
    Author     : TN041502
--%>


<%@page import="java.sql.*"%>
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
    <body  onunload="">

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

   
 

           
           <form action="Authenticate" method="POST">
                 <div class="row">


                        <div class="col-md-4 required inner-addon left-addon">
                            <i class="glyphicon glyphicon-user"></i>
                            <label for="username">Username</label>
                            <input class="form-control" id = "username" name="username" placeholder="Username" type="text" maxlength="50" required="true">
                       
                 </div>
                 </div>
                 <div class="row">
                        <div class="col-md-4 required">
                            <label for="password">Password</label>
                            <input class="form-control" id = "password" name="password" placeholder="Password" type="Password" required="true">
                        </div>
                    </div><br />
                    <button type="submit" class="btn btn-primary">Login</button>&nbsp&nbsp&nbsp<button type="reset" class="btn btn-primary">Reset</button>
                    
            </form>
            
            
<div style="color:red; font-weight: bolder">${errorMessage}</div>
<div style="color:red; font-weight: bolder">${concurrent}</div>          
           
            
                 <div id="timeout">
                     <label for="timeout" style="color: #d00">Your Session has expired please login again!!!</label>
            </div>
            
           
            <br />
            <br />
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