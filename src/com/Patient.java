package com;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testpaf", "root", "");
			// con =
			// DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_labs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
			// "root", "");

			// For testing
			System.out.println("Successfully connected to the DB");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB Connection Failed!!");
		}
		return con;
	}

	public String addPatient(String name, String pwd, String email, String pNumber, String addrs) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for adding";
			}

			// create a prepared statement
			String query = " insert into patient (`patientid`,`name`,`password`,`email`,`phonenumber`,`address`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, pwd);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, pNumber);
			preparedStmt.setString(6, addrs);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPatients = readPatients();
			output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";

			// output = "Patient added successfully";
			// System.out.println(output);
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the patient.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readPatients() {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Patient Name</th>"
					+ "<th>Password</th><th>Email</th><th>Phone Number</th>" + "<th>Address</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from patient";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String patientid = Integer.toString(rs.getInt("patientid"));
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");

				String phonenumber = Integer.toString(rs.getInt("phonenumber"));
				String address = rs.getString("address");

				// Add into the html table
				output += "<tr><td><input id='hidpatientidUpdate' name='hidpatientidUpdate' type='hidden' value='"
						+ patientid + "'>" + name + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + phonenumber + "</td>";
				output += "<td>" + address + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button'value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ " <td><input name='btnRemove' type='button'value='Remove'class='btnRemove btn btn-danger' data-patientid='"
						+ patientid + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
			System.out.println("Data Read Successfully");
		} catch (Exception e) {
			output = "Error while reading the data!";
			System.err.println(e.getMessage());
		}
		return output;

	}

	public String deletePatient(String patientid) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting the database for deleting";
			}

			// create a prepared statement
			String query = "delete from patient where patientid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(patientid));
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPatients = readPatients();
			output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";

			// output = "Patient Deleted successfully";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the patient.\"}";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String updatePatient(String pID, String name, String pwd, String email, String pNumber, String addrs) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating!";
			}

			// create a prepared statement
			String query = "UPDATE patient SET name=?,password=?,email=?,phonenumber=?,address=?" + "WHERE patientid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, pwd);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, pNumber);
			preparedStmt.setString(5, addrs);
			preparedStmt.setInt(6, Integer.parseInt(pID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPatients = readPatients();
			output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";

			// output = "Patient Updated successfully";
			// System.out.println(output);
		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while updating the patient.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}
	/*
	 * public String searchPatient(String search) { String output = "";
	 * 
	 * System.out.println(search);
	 * 
	 * try { Connection con = connect();
	 * 
	 * if (con == null) { return "Erroe while connecting to the database"; }
	 * 
	 * output = "<table class=\"table\" border =\"1\">" +
	 * "<tr><th>Patient Name</th><th>Password</th><th>Email</th><th>Phone Number</th><th>Address</th>"
	 * + "<th>Update</th><th>Remove</th></tr>";
	 * 
	 * String query = "select * from patient where name like ?"; PreparedStatement
	 * prepareStatement = con.prepareStatement(query); prepareStatement.setString(1,
	 * search);
	 * 
	 * ResultSet set = prepareStatement.executeQuery();
	 * 
	 * while (set.next()) {
	 * 
	 * String ID = Integer.toString(set.getInt("patientid")); String pName =
	 * set.getString("name"); String pwd = set.getString("password"); String Email =
	 * set.getString("email"); String pNumber = set.getString("phonenumber"); String
	 * Address = set.getString("address");
	 * 
	 * output += "<tr><th>" + ID + "</th>"; output += "<th>" + pName + "</th>";
	 * output += "<th>" + pwd + "</th>"; output += "<th>" + Email + "</th>"; output
	 * += "<th>" + pNumber + "</th>"; output += "<th>" + Address + "</th>";
	 * 
	 * output +=
	 * "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>" +
	 * "<td><form method=\"post\" action=\"patient.jsp\">" +
	 * "<input name=\"btnRemove\" value=\"remove\" type=\"submit\">" +
	 * "<input name=\"id\" type=\"hidden\" value=\"" + ID + "\">" +
	 * "</form></td></tr>"; }
	 * 
	 * con.close(); prepareStatement.close();
	 * 
	 * output += "</table>";
	 * System.out.println("successfully print search data...");
	 * 
	 * } catch (Exception e) {
	 * 
	 * output = "Cannot read the data"; System.err.println(e.getMessage());
	 * 
	 * } return output;
	 * 
	 * }
	 */
}
