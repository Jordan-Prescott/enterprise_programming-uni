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
 * searchFilms
 * 
 * Servlet implementation class searchFilms
 * 
 * @author jordanprescott
 * 
 * The searchFilms Servlet only has a doPost method that takes
 * a user's requested film title, queries a database for matching
 * films, and returns a list of films that match the search criteria. 
 * 
 * @version 1.0
 * @since 09/04/23
 */
@WebServlet("/searchFilms")
public class searchFilms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchFilms() { // constructor  
        super(); 
    }

	/**
	 * doPost
	 * 
	 * The doPost method takes the user's requested film title, queries a 
	 * database for the title, and retrieves a list of all entries found. 
	 * It then passes the list to the home.jsp page to render the output 
	 * by setting the data as an attribute in the request object and forwarding 
	 * it to the JSP page.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;

//		Film f = new Film(); // constructor sets all variables to null
//		f.setTitle(request.getParameter("title"));
		
		
		
		ArrayList<Film> allFilms = dao.searchFilms(request.getParameter("querySQL")); // get films and store array

		request.setAttribute("films", allFilms);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); // set dispatcher location
		rd.include(request, response); // send values to index
		
	}

}
