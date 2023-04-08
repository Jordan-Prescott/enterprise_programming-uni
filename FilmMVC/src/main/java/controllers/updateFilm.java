package controllers;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class updateFilm
 * 
 * @author jordanprescott
 * 
 *         This servlet updates a film entry in a database. The doGet method
 *         directs the user to an updateFilm.jsp page with a form to update the
 *         film entry. The doPost method updates the film entry in the database
 *         after the user submits the form, and then either redirects the user
 *         back to the home view.
 *
 * @version 1.0
 * @since 08/04/23
 *
 */
@WebServlet("/updateFilm")
public class updateFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updateFilm() { // constructor
		super();
	}

	/**
	 * doGet
	 * 
	 * This doGet method retrieves a film object's details to be updated, sets the
	 * attributes as request attributes, and forwards the request to the
	 * updateFilm.jsp page with a pre-filled form for the user to update.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;

		// get films details user wants to update
		Film f = new Film();
		f.setId(Integer.parseInt(request.getParameter("id")));

		ArrayList<Film> thisFilm = dao.searchFilms(f); // returns only one value

		request.setAttribute("film", thisFilm);
		RequestDispatcher rd = request.getRequestDispatcher("./jsp_views/updateFilm.jsp"); // set dispatcher location
		rd.include(request, response); // send values to updateFilm jsp

	}

	/**
	 * doPost
	 * 
	 *  This doPost method retrieves the updated film data from a form, it 
	 *  gets the parameters and creates a film object for updateFilm().
	 *	When the film has been updated it then redirects the user to ./home.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;

		// create film for updateFilm() param
		Film f = new Film();

		f.setId(Integer.parseInt(request.getParameter("id")));
		f.setTitle(request.getParameter("title"));
		f.setYear(Integer.parseInt(request.getParameter("year")));
		f.setDirector(request.getParameter("director"));
		f.setStars(request.getParameter("stars"));
		f.setReview(request.getParameter("review"));

		try {
			dao.updateFilm(f); // update film in db
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect("./home");

	}

}
