package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAOEnum;
import model.Film;

/**
 * home
 * 
 * Servlet implementation class home
 *
 * @author jordanprescott
 *
 *         The 'home' servlet is a Java class that is designed to retrieve all
 *         the entries from a database and then forward this data to the
 *         'Index.jsp' file for display. Its main purpose is to serve as a
 *         controller between the database and the user interface.
 * 
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
 *      response)
 * 
 * @version 1.0
 * @since 08/04/23
 * 
 */
@WebServlet("/home")
public class home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
    public home() { // constructor
        super();
    }

	/**
	 * doGet
	 * 
	 * This method retrieves film objects from a database using a DAO object. It
	 * then stores these objects in an array and passes it to the index.jsp file for
	 * rendering. The servlet separates data retrieval and presentation logic,
	 * making it a more efficient way to handle web requests. This is the home view
	 * for the application and renders all film entries.
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		ArrayList<Film> allFilms = dao.getAllFilms(); // get films and store array

		request.setAttribute("films", allFilms);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); // set dispatcher location
		rd.include(request, response); // send values to index

	}
	
	/**
	 * doPost
	 * 
	 * This method retrieves film objects from a database using a DAO object. It
	 * then stores these objects in an array and passes it to the index.jsp file for
	 * rendering. This is used from in the search bar on index.jsp.
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		
		String searchString = request.getParameter("searchString"); 
		ArrayList<Film> Films = dao.searchFilms(searchString); // get all films that match this query 
		
		request.setAttribute("films", Films);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); // set dispatcher location
		rd.include(request, response); // send values to index
		
	}

}
