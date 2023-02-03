package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connect_to_db {

	public static void main(String[] args) {
		Connection c = null;
		Statement s = null;
		ResultSet r = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:" + "/Users/jordanprescott/Developer/enterprise_programming_eclipse_workspace/contacts-db-app/src/main/webapp/WEB-INF/classes/" + "contactsdb.sqlite");
			s = c.createStatement();
			System.out.println(s);
			System.out.println("Success");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException c1) {
			c1.printStackTrace();
		}

	}

}
