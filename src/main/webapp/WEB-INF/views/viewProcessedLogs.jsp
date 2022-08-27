<%@page import="com.mvc.inventory.management.model.ProcessedLogs"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="com.mvc.inventory.management.model.RawLogs"%>
<%@page import="com.mvc.inventory.management.model.RawMaterial"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>View Processed Logs</title>
  </head>
  <body class="bg-light text-dark">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <!-- Navbar content -->
        <!-- <a class="navbar-brand" href="#">Navbar</a> -->
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="dashboard">Add Raw Material <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="viewRawMaterials">View Raw Materials</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="addProcessedItem">Add Processed Item</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="viewProcessedMaterials">View Processed Items</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="viewRawLogs">Raw Logs</a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="viewProcessedLogs">Processed Logs</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="logout">Logout</a>
      </li>
    </ul>
  </div>
    </nav>

    <div class="container">
        <div class="row mt-5">
            <div class="col-12">
                <div class="text-center">
                    <h2>Processed Item Logs</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">Log ID</th>
                    <th scope="col">Product ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Status</th>
                    <th scope="col">Time</th>
                    <th scope="col">Description</th>
                  </tr>
                </thead>
                <tbody>
                <%List<ProcessedLogs> allProcessedLogs = (List<ProcessedLogs>)request.getAttribute("allProcessedLogs");
                	Collections.reverse(allProcessedLogs);
                	for(ProcessedLogs processedLogs : allProcessedLogs){                		 
                %>
                  <tr>
                    <th scope="row"><%=processedLogs.getLogId() %></th>
                    <td><%=processedLogs.getId() %></td>
                    <td><%=processedLogs.getName() %></td>
                    <td><%=processedLogs.getQuantity() %></td>
                    <%if(processedLogs.getStatus().equals("ISSUED")){ %>
                    	<td style="color:#FEBE10;"><b><%=processedLogs.getStatus() %></b></td>
                    <%}else if(processedLogs.getStatus().equals("ADDED")){ %>
                    	<td style="color:green;"><b><%=processedLogs.getStatus() %></b></td>
                    <%}else if(processedLogs.getStatus().equals("DELETED")){ %>
                		<td style="color:red;"><b><%=processedLogs.getStatus() %></b></td>
                	<%}else{ %>
                    	<td style="color:blue;"><b><%=processedLogs.getStatus() %></b></td>
                    <%} %>
                    <td><%=processedLogs.getTimeStamp() %></td>
                    <%if(processedLogs.getStatus().equals("ISSUED")){ %>
                    	<td>Issued to <%=processedLogs.getIssuer() %></td>
                    <%}else if(processedLogs.getStatus().equals("ADDED")){ %>
                    	<td>New Item Added to Inventory</td>
                    <%}else if(processedLogs.getStatus().equals("DELETED")){ %>
                	<td>Item Deleted From Inventory</td>
                	<%}else{ %>
                    	<td>Quantity Updated</td>
                    <%} %>
                  </tr>
                  <%} %>
                </tbody>
              </table>
            </div>
        </div>
    </div>

    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </body>
</html>