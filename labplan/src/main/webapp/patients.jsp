<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="com.labplan.entities.Patient" %>
<%@ page import="com.labplan.services.PatientService" %>
<%@ page import="com.labplan.persistence.generic.PatientDao" %>
<%@ page import="com.labplan.persistence.sql.SqlPatientDao" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
	</head>
	
	<body>
		<%
		PatientDao patientDao = new SqlPatientDao();
		PatientService patientService = new PatientService(patientDao);
		
		int currentPage = 1;
		int entriesPerPage = 3;
		int pageCount = patientService.getPageCount(entriesPerPage);
		
		List<Patient> patients = patientService.getPage(currentPage, entriesPerPage);
		%>
		
		<aside>
			<header>
				<img class="logo" src="assets/logo.svg" alt="Platform logo" />
				
				<h1>LabTest</h1>
			</header>
		</aside>
		
		<main>
			<div class="content-wrapper">
				<h1>Patients</h1>
			
				<table class="patients">
					<thead>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Age</th>
							<th>Weight</th>
							<th>Height</th>
						</tr>
					</thead>
				
					<tbody>
						<% for (Patient patient : patients) { %>
							<tr>
								<td><%= patient.getFirstName() %></td>
								<td><%= patient.getLastName() %></td>
								<td><%= patient.getAge() %></td>
								<td><%= patient.getWeight() %></td>
								<td><%= patient.getHeight() %></td>
							</tr>
						<% } %>
					</tbody>
					
					<tfoot>
						<tr class="pagination">
							<td colspan="5">
								<table>
									<tr>
										<td><a href="#">First</a></td>
										<td><a href="#">Previous</a></td>
										<td class="page-indicator">Page <%= currentPage %> out of <%= pageCount %></td>
										<td><a href="#">Next</a></td>
										<td><a href="#">Last</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</main>
	</body>
</html>