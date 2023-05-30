package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAOEnum;
import model.Film;

/**
 * createFilm
 * 
 * Servlet implementation class createFilm
 * 
 * @author jordanprescott
 * 
 *         createFilm is a Java servlet that only has a doPost method used to
 *         create a film entry in a database. The doPost method collects
 *         parameters from a form, inserts them into the database.
 * 
 * @version 1.0
 * @since 09/04/23
 * 
 */
@WebServlet("/createFilm")
public class createFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createFilm() { // constructor
		super();
	}

	/**
	 * doGet
	 * 
	 * Passes request directly to the addFilm.jsp to add an entry.
	 * 
	 * 	@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// redirect instantly
		RequestDispatcher rd = request.getRequestDispatcher("./pages/addFilm.jsp"); // set dispatcher location
		rd.include(request, response); // send values to updateFilm jsp

	}

	/**
	 * doPost
	 * 
	 * The doPost method is used to add a film entry to a database. It collecting
	 * parameters from a form on index.jsp and uses them to build a film object
	 * which is then passed into createFilm(f). This adds the entry to the database
	 * and the user is then redirected back to ./home.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;

		Film f = new Film(); // constructor sets all variables to null

		// build film from parameters
		f.setTitle(request.getParameter("title"));
		f.setYear(Integer.parseInt(request.getParameter("year")));
		f.setDirector(request.getParameter("director"));
		f.setStars(request.getParameter("stars"));
		f.setReview(request.getParameter("review"));
		f.setGenre(request.getParameter("genre"));
		f.setRating(request.getParameter("rating"));

		try { // insert film
			dao.insertFilm(f);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./home");

	}

}
