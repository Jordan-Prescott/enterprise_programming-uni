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
 *         Read : getNextFilm(), getAllFilms(), getFilm()
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
			System.out.println(se);
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
		} catch (SQLException e) {
			e.printStackTrace();
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

			prepStmt.setString(1, f.getTitle());
			prepStmt.setInt(2, f.getYear());
			prepStmt.setString(3, f.getDirector());
			prepStmt.setString(4, f.getStars());
			prepStmt.setString(5, f.getReview());
			prepStmt.setString(6, f.getGenre());
			prepStmt.setString(7, f.getRating());
			System.out.println(prepStmt.toString());

			int insertFilmResult = prepStmt.executeUpdate();

			closeConnection();
			b = true;
		} catch (SQLException se) {
			System.out.println(se);
			throw new SQLException("Film Not Added.");
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
		} catch (SQLException e) {
			e.printStackTrace();
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

			while (rs.next()) {
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}

			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return filmsArray;
	}

	/**
	 * getFilm
	 * 
	 * This method gets a single film from the DB by querying the DB by ID. Each ID is 
	 * unique and the database if it finds a result will only return one value. Array is
	 * returned for efficiency in FilmAPI.
	 * 
	 * @param f Film object
	 * @return filmsArray Array of films but as the query is by id only one value returned
	 */
	public ArrayList<Film> getFilm(Film f) { // READ

		ArrayList<Film> filmsArray = new ArrayList<Film>();
		String selectSQL = "SELECT * FROM films WHERE id = ?;";

		try { // get film from DB
			openConnection(selectSQL);

			// format query 
			prepStmt.setInt(1, f.getId());
			System.out.println(prepStmt.toString());

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}

			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return filmsArray;
	}
	
	/**
	 * getFilms
	 * 
	 * This method takes in a Film object and uses the get methods to get 
	 * the values to query on. The query is formatted for what is passed in
	 * and returns all films that match that query. This method can return 
	 * multiple films as values such as film title can be used across multiple
	 * films e.g 'BATMAN' and 'CANDYMAN 2'.
	 * 
	 * @param f Film Object
	 * @return filmsArray Array of films returned from query to DB.
	 */
	public ArrayList<Film> getFilms(Film f) { // READ

		ArrayList<Film> filmsArray = new ArrayList<Film>();
		String selectSQL = "SELECT * FROM films WHERE 1 = 1"; // REMOVE THIS

		// format query
		if (f.getTitle() != null) {
			selectSQL += " AND title LIKE ?";
		} else if (f.getDirector() != null) {
			selectSQL += " AND Director = ?";
		} else if (f.getYear() != 0) {
			selectSQL += " AND year = ?";
		} else if (f.getStars() != null) {
			selectSQL += " AND stars LIKE ?";
		} else if (f.getGenre() != null) {
			selectSQL += " AND genre = ?";
		}else if (f.getRating() != null) {
			selectSQL += " AND rating = ?";
		}
		selectSQL += ";";
		
		try { // get film from DB
			openConnection(selectSQL);

			// format query - set the correct value in prepStmt
			if (f.getTitle() != null) {
				prepStmt.setString(1, "%" + f.getTitle() + "%");
			} else if (f.getDirector() != null) {
				prepStmt.setString(1, f.getDirector());
			} else if (f.getYear() != 0) {
				prepStmt.setInt(1, f.getYear());
			} else if (f.getStars() != null) {
				prepStmt.setString(1, "%" + f.getStars() + "%");
			} else if (f.getGenre() != null) {
				prepStmt.setString(1, f.getGenre());
			}else if (f.getRating() != null) {
				prepStmt.setString(1, f.getRating());
			}
			
			System.out.println(prepStmt.toString());

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}

			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return filmsArray;
	}

	/**
	 * searchFilms
	 * 
	 * Takes parameter of String s a sql query and uses SQL OR to make string a
	 * dynamic query and then execute the query against the DB. This retrieves any
	 * films the OR statements have triggered true. For example you can search for
	 * director name or number of stars or the year and this will return everything
	 * found without needing to heavily format the query.
	 * 
	 * @param f Film object that will be updated in DB.
	 * @return oneFilm Film object of returned film from DB.
	 */
	public ArrayList<Film> searchFilms(String s) { // READ

		ArrayList<Film> filmsArray = new ArrayList<Film>();

		// format query depending what is passed in
		String selectSQL = "SELECT * FROM films WHERE title LIKE ? OR year= ? OR director LIKE ? OR stars LIKE ? OR genre = ? OR rating = ?;";

		try {
			openConnection(selectSQL);

			// passes in string in all places and wildcards some for better results
			prepStmt.setString(1, "%" + s + "%");
			prepStmt.setString(2, s);
			prepStmt.setString(3, "%" + s + "%");
			prepStmt.setString(4, "%" + s + "%");
			prepStmt.setString(5, s);
			prepStmt.setString(6, s);
			System.out.println(prepStmt);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}

			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return filmsArray;
	}
	
	public ArrayList<Film> searchFilmsBy(String s, String column) { // READ
		
		ArrayList<Film> filmsArray = new ArrayList<Film>();
		
		// format query depending what is passed in
		String selectSQL = "SELECT * FROM films WHERE ? LIKE ?;";
		
		try {
			openConnection(selectSQL);
			
			// passes in string in all places and wildcards some for better results
			prepStmt.setString(1, "%" + s + "%");
			prepStmt.setString(2, s);
			prepStmt.setString(3, "%" + s + "%");
			prepStmt.setString(4, "%" + s + "%");
			prepStmt.setString(5, s);
			prepStmt.setString(6, s);
			System.out.println(prepStmt);
			
			ResultSet rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				oneFilm = getNextFilm(rs);
				filmsArray.add(oneFilm);
			}
			
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		
		return filmsArray;
	}

	/**
	 * updateFilm
	 * 
	 * Takes parameter of Film object and uses details of Film to format an SQL
	 * query and then execute the query against the DB. This updates a film entry in
	 * DB.
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

			// format SQL
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

			closeConnection();
			b = true;
		} catch (SQLException s) {
			System.out.println(s);
			throw new SQLException("Film Not Updated.");
		}
		return b;
	}

	/**
	 * deleteFilm
	 * 
	 * Takes parameter of id an Integer and uses details of Film to format an SQL
	 * query and then execute the query against the DB. This deletes a film entry in
	 * DB.
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

			closeConnection();
			b = true;
		} catch (SQLException s) {
			throw new SQLException("Film Not Deleted.");
		}

		return b;
	}
}
