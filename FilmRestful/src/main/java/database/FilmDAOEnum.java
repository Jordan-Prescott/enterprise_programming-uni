package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Film;

/**
 * FilmDAOEnum
 * 
 * @author jordanprescott
 * 
 *         Singleton design pattern this Device Access Object (DAO) is used for
 *         interaction with the Database (DB). The structure follows the Create,
 *         Read, Update, Delete (CRUD) operations performed on a database and
 *         each method has been tagged with //{OPERATION} at the start to
 *         identify.
 * 
 *         Create : insertFilm() 
 *         Read   : getNextFilm(), getAllFilms(), getFilm(), searchFilmsBy
 *         Update : updateFilm() 
 *         Delete : deleteFilm
 * 
 * @version 1.0
 * @since 10/04/23
 *
 */
public enum FilmDAOEnum {

	/**
	 * Singleton pattern.
	 */
	INSTANCE;

	Film oneFilm = null;
	Connection conn = null;
	PreparedStatement prepStmt = null;

	String user = "prescotj";
	String password = "tramkerL4";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;
	

	/**
	 * openConnection
	 * 
	 * Takes a String parameter of an SQL query and a Prepared Statement is then
	 * created and set as the global variable (prepStmt). This also loads the driver
	 * needed and creates the connection to the DB for the prepStmt to be executed
	 * in each method.
	 * 
	 * @param query SQL query used in each method set as global variable prepStmt.
	 */
	private void openConnection(String query) {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		// connecting to database
		try {
			conn = DriverManager.getConnection(url, user, password);
			prepStmt = conn.prepareStatement(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * closeConnecton
	 * 
	 * Closes the opened connection to the DB.
	 */
	private void closeConnection() {
		try {
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * insertFilm
	 * 
	 * Takes parameter of Film object and uses details of Film to format an SQL
	 * query and then execute the query against the DB. This creates a single film
	 * entry in DB.
	 * 
	 * @param f Film object 
	 * @return Return Boolean true to indicate completion
	 * @throws SQLException Throws error if connection to DB can not be established.
	 */
	public boolean insertFilm(Film f) throws SQLException { // CREATE

		String insertSQL = "INSERT INTO films (title, year, director, stars, review, genre, rating) VALUES (?, ?, ?, ?, ?, ?, ?);";
		Boolean b = false; 

		try {
			openConnection(insertSQL);

			// format query 
			prepStmt.setString(1, f.getTitle());
			prepStmt.setInt(2, f.getYear());
			prepStmt.setString(3, f.getDirector());
			prepStmt.setString(4, f.getStars());
			prepStmt.setString(5, f.getReview());
			prepStmt.setString(6, f.getGenre());
			prepStmt.setString(7, f.getRating());
			System.out.println(prepStmt.toString());

			int insertFilmResult = prepStmt.executeUpdate();

			b = true;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Film Not Added.");
		} finally {
			//regardless of error close conn
			closeConnection();			
		}

		return b;

	}

	/**
	 * getNextFilm
	 * 
	 * Takes result set as parameter from executed query against DB and converts
	 * entry to Film object and returns object.
	 * 
	 * @param rs ResultSet of query sent from DB.
	 * @return thisFilm Film object constructed from entry in DB.
	 */
	private Film getNextFilm(ResultSet rs) { // READ
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"), rs.getString("genre"), rs.getString("rating"));
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return thisFilm;
	}

	/**
	 * getAllFilms
	 * 
	 * Queries DB for all films and then loops through each entry creating local
	 * Film object and appending this to the array. The completed array of Film
	 * objects is then returned.
	 * 
	 * @return filmsArray Array of Film objects built on DB response.
	 */
	public ArrayList<Film> getAllFilms() { // READ

		ArrayList<Film> filmsArray = new ArrayList<Film>();
		String selectSQL = "SELECT * FROM films;";

		try {
			openConnection(selectSQL);
			System.out.println(selectSQL);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) { // loop through request results
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			//regardless of error close conn
			closeConnection();			
		}

		return filmsArray;
	}

	/**
	 * searchFilmsBy 
	 *
	 * Takes in two parameters the first is String s this is the search string the 
	 * user is looking for e.g. 'Batman'. The second parameter is String column this
	 * is the column the user is searching against e.g. 'Title'. This request would
	 * look for films with the title of battman.
	 * 
	 * @param s Value use is looking for such as 'Batman'
	 * @param column The column which the user wants to search such as title
	 * @return filmsArray Array of film objects returned from database
	 */	
	public ArrayList<Film> searchFilmsBy(String s, String column) { // READ
		
		ArrayList<Film> filmsArray = new ArrayList<Film>();
		
		// format query depending what is passed in
		String selectSQL = "SELECT * FROM films WHERE " + column + " LIKE ?;";
		
		try {
			openConnection(selectSQL);
			
			// format query
			prepStmt.setString(1, s);
		
			System.out.println(prepStmt);
			
			ResultSet rs = prepStmt.executeQuery();
			
			while (rs.next()) { // loop through films returned in request
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			//regardless of error close conn
			closeConnection();			
		}
		
		return filmsArray;
	}

	/**
	 * updateFilm
	 * 
	 * Takes parameter of Film f object and uses details of Film to format an SQL
	 * query and then execute the query against the DB. This updates a film entry in
	 * DB. It first creates a string of the query and then uses prepared statements 
	 * to format the query securely before executing it.
	 * 
	 * @param f Film object that will be updated in DB.
	 * @return b Boolean of true to indicate completion.
	 * @throws SQLException Throws error if connection to DB can not be established.
	 */
	public boolean updateFilm(Film f) throws SQLException { // UPDATE
		String insertSQL = "UPDATE films SET title=?, year=?, director=?, stars=?, review=?, genre=?, rating=? WHERE id=?;";
		Boolean b = false; 

		try {
			openConnection(insertSQL);

			// format query
			prepStmt.setString(1, f.getTitle());
			prepStmt.setInt(2, f.getYear());
			prepStmt.setString(3, f.getDirector());
			prepStmt.setString(4, f.getStars());
			prepStmt.setString(5, f.getReview());
			prepStmt.setString(6, f.getGenre());
			prepStmt.setString(7, f.getRating());
			prepStmt.setInt(8, f.getId());
			System.out.println(prepStmt.toString());

			int udpateFilmResult = prepStmt.executeUpdate(); // execute prepared statement

			b = true;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Film Not Updated.");
		} finally {
			//regardless of error close conn
			closeConnection();			
		}
		
		return b;
	}

	/**
	 * deleteFilm
	 * 
	 * Takes parameter of id an Integer and uses details of Film to format an SQL
	 * query and then execute the query against the DB. This deletes a film entry in
	 * DB. It first creates a string of the query and then uses prepared statements 
	 * to format the query securely before executing it.
	 * 
	 * @param id Film object that will be deleted from DB.
	 * @return b Boolean of true to indicate completion.
	 * @throws SQLException Throws error if connection to DB can not be established.
	 */
	public boolean deleteFilm(int id) throws SQLException { // DELETE

		String deleteSQL = "DELETE FROM films WHERE id = ?;";
		Boolean b = false; 

		try {
			openConnection(deleteSQL);

			// format SQL
			prepStmt.setInt(1, id);
			System.out.println(prepStmt.toString());

			int deleteFilmResult = prepStmt.executeUpdate(); // execute prepared statement

			b = true;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Film Not Deleted.");
		} finally {
			//regardless of error close conn
			closeConnection();			
		}

		return b;
	}
}
