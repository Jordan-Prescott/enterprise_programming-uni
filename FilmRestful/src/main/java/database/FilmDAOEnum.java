package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import model.Film;

/**
 * 
 * @author jordanprescott
 *
 */
public enum FilmDAOEnum {

	INSTANCE;

	Film oneFilm = null;
	Connection conn = null;
	Statement stmt = null;

	String user = "prescotj";
	String password = "tramkerL4";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public Statement getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return stmt;
	}
	
	/**
	 * TODO: Think about if needed
	 */
	private void openConnection () {
		
	}
	
	private void closeConnection() {
		
	}
	
	
}
