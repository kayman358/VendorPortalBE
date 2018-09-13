<%-- 
    Document   : dataSource
    Created on : May 8, 2017, 10:52:05 AM
    Author     : TN041502
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>Vendor Information</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->

    </head>  
    <body>

           <sql:query dataSource="jdbc/SupplierCapture" var="result2">
                                SELECT * from branches order by costcenters ASC;
                            </sql:query>
       

        <sql:query dataSource="jdbc/SupplierCapture" var="result">
            <%
                String SolID = (String) session.getAttribute("SolID");

            %>
            SELECT v.[id],[suppliername],[supplierType],[businessclass],[costcenters],[createddate],[staffname]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[CostCenters] c
            where v.[status] = 'Not Confirmed' AND  v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id]  AND
            costcenter_id in (
            SELECT [id]
            FROM [SupplierCapture].[dbo].[CostCenters] 
            Where bdo_id = (
            SELECT b.[id] as [bdo_id]
            FROM [SupplierCapture].[dbo].[Bdo] b join 
            [SupplierCapture].[dbo].[CostCenters] c on b.id =  c.bdo_id
            where c.id ='<%=SolID%>'
            )
            ) 
        </sql:query>





        <sql:query dataSource="jdbc/SupplierCapture" var="result6">
            <%
                String costcenterId = (String) session.getAttribute("SolID");

            %>
            SELECT v.[id],[suppliername],[supplierType] AS supplierType,[businessclass] AS businessclass,[costcenters] AS costcenters,[createddate],[staffname]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[CostCenters] c
            where v.[status] = 'Approved' AND v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id] AND costcenter_id in (
            SELECT [id]
            FROM [SupplierCapture].[dbo].[CostCenters] 
            Where bdo_id = (
            SELECT b.[id]
            FROM  [SupplierCapture].[dbo].[Bdo] b join
            [SupplierCapture].[dbo].[groupHeads] g on b.groupHeadId = g.id join 
            [SupplierCapture].[dbo].[CostCenters] c on b.id =  c.bdo_id    
            where c.id ='<%=costcenterId%>' 
            )
            )

        </sql:query>


        <sql:query dataSource="jdbc/SupplierCapture" var="rest1">
          
            SELECT v.[id],[suppliername],[supplierType],[businessclass],[costcenters],[createddate],[staffname],[status]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[branches] c
            where v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id]
        </sql:query>
            
            
            
            
            
            
            
            
            
            
         <sql:query dataSource="jdbc/SupplierCapture" var="rest2">
          
            SELECT v.[id],[suppliername],[supplierType],[businessclass],[costcenters],[createddate],[staffname]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[CostCenters] c
            where v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id]  AND v.[status] = 'Approved'
        </sql:query>
            
        <sql:query dataSource="jdbc/SupplierCapture" var="rest3">
          
            SELECT v.[id],[suppliername],[supplierType],[businessclass],[costcenters],[createddate],[staffname]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[CostCenters] c
            where v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id]  AND v.[status] = 'Not Confirmed'
        </sql:query>
            
        <sql:query dataSource="jdbc/SupplierCapture" var="rest4">
          
            SELECT v.[id],[suppliername],[supplierType],[businessclass],[costcenters],[createddate],[staffname]
            FROM [SupplierCapture].[dbo].[vendorInformation] v, [SupplierCapture].[dbo].[supplierType] s, [SupplierCapture].[dbo].[businessClass] bc, [SupplierCapture].[dbo].[CostCenters] c
            where v.[supplier_type_id] = s.[id] AND v.[business_class_id] = bc.[id] AND v.[costcenter_id] = c.[id]  AND v.[status] = 'Confirmed'
        </sql:query>



       
    </body>
</html>