package database;

import java.util.ArrayList;

import model.Film;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author jordanprescott
 * TODO: Comment here
 */
public class FilmDAO {
	
	Film oneFilm = null;
	Connection conn = null;
	Statement stmt = null;
	
	String user = "prescotj";
	String password = "tramkerL4";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public FilmDAO() {}
 
	/**
	 * Get Database Connection
	 * 
	 * @return Statement object
 	 */
	private Statement getConnection() {

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
	
	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			// connection string for demos database, username demos, password demos
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	// TODO: Why is this used????
	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisFilm;
	}

	/**
	 * Get all films
	 *  
	 * @return Collection of all contacts found in the dqlite database 
	 */
	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> filmsArray = new ArrayList<Film>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "select * from films";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
				filmsArray.add(oneFilm);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return filmsArray;
	}

	/**
	 * Get Film by ID
	 * 
	 * @param id: Unique identifier of film record
	 * @return Film matching with matching ID
	 */
	public Film getFilmByID(int id) {

		openConnection();
		oneFilm = null;
		// Create select statement and execute it
		try {
			String selectSQL = "select * from films where id=" + id + ";";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneFilm;
	}

	public boolean insertFilm(Film f) throws SQLException {

		boolean b = false;
		try {
			String sql = "insert into films (Name, Email) values ('" 
		+ f.getId() + "','" 
		+ f.getTitle() + "','" 
		+ f.getYear() + "','" 
		+ f.getDirector() + "','" 
		+ f.getStars() + "','"
		+ f.getReview() + 
		"');";
			
			System.out.println(sql);
			b = getConnection().execute(sql);
			closeConnection();
			b = true;
		} catch (SQLException s) {
			throw new SQLException("Film Not Added.");
		}
		return b;
	}

	public boolean deleteFilm(Film f) throws SQLException {
		boolean b = false;
		try {
			String sql = "delete from films where id= " + f.getId() + ";";
			System.out.println(sql);	
			b = getConnection().execute(sql);
			closeConnection();
			b = true;
		} catch (SQLException s) {
			throw new SQLException("Film Not deleted.");
		}
		return b;
	}

	public boolean updateFilm(Film f) throws SQLException {
		boolean b = false;
		try {
			String sql = "update films set "
					+ "title= '" + f.getTitle() + "','" 
					+ "year = '" + f.getYear() + "','" 
					+ "director = '" + f.getDirector() + "','"
					+ "stars = '" + f.getStars() + "','"
					+ "review = '" + f.getReview()
					+ ";";
			
			System.out.println(sql);
			b = getConnection().execute(sql);
			closeConnection();
			b = true;
		} catch (SQLException s) {
			throw new SQLException("Film Not updated");
		}
		return b;
	}

}
