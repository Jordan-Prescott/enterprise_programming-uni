package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAOEnum;
import model.Film;

/**
 * Servlet implementation class deleteFilm
 */
@WebServlet("/deleteFilm")

/**
 * deleteFilm
 * 
 * @author jordanprescott
 * 
 *         The deleteFilm servlet handles requests to delete a film from a
 *         database. When invoked with a GET request from an a href, it calls
 *         the doPost method to adhere to industry standards. The doPost method
 *         then deletes a film record in the db. The deleteFilm servlet is a
 *         secure and efficient way to perform the deletion operation in web
 *         applications.
 * 
 *
 * @version 1.0
 * @since 08/04/23
 * 
 */
public class deleteFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deleteFilm() { // constructor
		super();
	}

	/**
	 * doGet
	 * 
	 * ONLY USED TO INVOKE doPost()
	 * 
  	 * Href used in JSP meaning by default the request is GET. This method invokes
	 * doPost to follow industry standards. When passing data to a server this is
	 * handled by POST requests.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response); // pass to doPost()

	}

	/**
	 * doPost
	 * 
	 * 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;

		Integer id = Integer.parseInt(request.getParameter("id")); // get id param

		try {
			dao.deleteFilm(id); // delete film from DB
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./home");

	}

}
