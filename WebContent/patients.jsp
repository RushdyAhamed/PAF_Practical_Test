<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.Patient"%>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.3.1.min.js"></script>
<script src="Components/patients.js"></script>
<meata charset="ISO-8859-1">
<title>Patients Management</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Patients Management</h1>

				<form id="formPatient" name="formPatient" method="post"
					action="patients.jsp">
					Name: <input id="name" name="name" type="text"
						class="form-control form-control-sm"> <br> Password:
					<input id="password" name="password" type="password"
						class="form-control form-control-sm"> <br> Email: <input
						id="email" name="email" type="text"
						class="form-control form-control-sm"> <br> Phone
					Number: <input id="phonenumber" name="phonenumber" type="text"
						class="form-control form-control-sm"> <br> Address: <input
						id="address" name="address" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidpatientidSave" name="hidpatientidSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divPatientsGrid">
					<%
						Patient pObj = new Patient();
						out.print(pObj.readPatients());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>