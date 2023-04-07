package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Film;
import model.FilmList;
import database.FilmDAOEnum;

/**
 * Servlet implementation class FilmsAPI
 * 
 * @author jordanprescott
 * 
 *         RESTful API with methods GET, POST, PUT, DELETE for interacting with
 *         films database (DB). Each method can handle data types of JSON, XML,
 *         or plain text. Plain text has its own data format see below.
 * 
 *         GET    : Returns all films in data format requested. 
 *         POST   : Creates film. 
 *         PUT    : Updates existing film using id to identify entry. 
 *         DELETE : Deletes existing film using id to identify entry.
 * 
 * @apiNote Plain/ Text Data Format: # used as deliminator and each entry on new
 *          line. Example of 3 entries below.
 * 
 *          title#year#director#stars#review 
 *          title#year#director#stars#review
 *          title#year#director#stars#review
 * 
 * 
 * @version 1.0
 * @since 06/04/23
 * 
 */
@WebServlet("/FilmsAPI")
public class FilmsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FilmsAPI() {
		super();
	}

	/**
	 * doGet
	 * 
	 * This method handles GET requests in the FilmsAPI servlet. It gets all films
	 * from the FilmDAOEnum, then checks the Accept header of the request to
	 * determine the desired output format. It then converts the array of Films to
	 * the desired format. Finally, it writes the formatted data to the response
	 * object and sends it back to the client.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		
		String format = request.getHeader("Accept");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		
		ArrayList<Film> allFilms = null;
		Film f = null;
		
		// collect entries for output depending on what is requested.
		if(id != null) { // return single film
			f = new Film();
			
			f.setId(Integer.parseInt(id));
			allFilms = dao.getFilm(f);
			
		} else if (title != null) { // return single or multiple films
			f = new Film();
			
			f.setTitle(title);
			allFilms = dao.getFilm(f);
			
		} else { // return all films:
			
			allFilms = dao.getAllFilms();
			
		}
		
		// JSON: default output
		Gson gson = new Gson();
		response.setContentType("application/json");
		String data = gson.toJson(allFilms);
		
		if (format.equals("application/xml")) { // XML
			FilmList fl = new FilmList(allFilms);
			StringWriter sw = new StringWriter();
			JAXBContext context;
			
			try {
				context = JAXBContext.newInstance(FilmList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(fl, sw);
			} catch (JAXBException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			data = sw.toString();
			
		} else if (format.equals("text/plain")) { // TEXT
			String textOutput = "id#title#year#director#stars#review#\n"; // Headers
			
			for (Film f2 : allFilms) {
				String row = String.format("%s#%s#%s#%s#%s#%s\n", String.valueOf(f2.getId()), f2.getTitle(),
						String.valueOf(f2.getYear()), f2.getDirector(), f2.getStars(), f2.getReview());
				textOutput += row;
			}
			
			data = textOutput;
		}
		
		if (allFilms.isEmpty()) {
		    out.write("No results found.");
		} else {			
			out.write(data); // return content to client
		}
	}

	/**
	 * doPost
	 * 
	 * This method is responsible for handling HTTP POST requests to insert data
	 * into the database. It reads the request body and checks the content type. It
	 * then converts the data to a Film object using JSON, XML, or plain text. If
	 * the data format is incorrect, it returns an error message to the client.
	 * Finally, it inserts the Film object into the database using the FilmDAOEnum
	 * and returns a success message or an error message to the client.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null;

		try {
			if (dataFormat.equals("application/json")) { // JSON
				Gson gson = new Gson();
				f = gson.fromJson(data, Film.class);

				System.out.println(f);

			} else if (dataFormat.equals("application/xml")) { // XML
				JAXBContext jaxbContext;

				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

				System.out.println(f);

			} else if (dataFormat.equals("text/plain")) { // TEXT
				String values[] = data.split("#");

				f = new Film();

				f.setTitle(values[0]);
				f.setYear(Integer.parseInt(values[1].replace(" ", ""))); // data sanitization
				f.setDirector(values[2]);
				f.setStars(values[3]);
				f.setReview(values[4]);

				System.out.println(f);

			}

		} catch (JsonSyntaxException | JAXBException | NumberFormatException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("[ERROR: 400 Bad Request] The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again.");
		}

		try { // insert film
			dao.insertFilm(f);
			out.write("film inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
			out.write("[ERROR] film not inserted please check the data and try again.");
		}
		out.close();
	}

	/**
	 * doPut
	 * 
	 * This method handles HTTP PUT requests to update a Film object in a database.
	 * It retrieves the data sent by the client, checks the data format, and
	 * converts the data to a Film object using JSON, XML, or plain text. It also
	 * includes data sanitization and error handling for format errors. Once the
	 * Film object is created, it updates the database using the DAO (Data Access
	 * Object) class and sends a response to the client.
	 * 
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null;

		try {
			if (dataFormat.equals("application/json")) { // JSON
				Gson gson = new Gson();
				f = gson.fromJson(data, Film.class);

				System.out.println(f);

			} else if (dataFormat.equals("application/xml")) { // XML
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

				System.out.println(f);

			} else if (dataFormat.equals("text/plain")) { // TEXT
				String values[] = data.split("#");

				f = new Film();

				f.setId(Integer.parseInt(values[0]));
				f.setTitle(values[1]);
				f.setYear(Integer.parseInt(values[2].replace(" ", ""))); // data sanitization
				f.setDirector(values[3]);
				f.setStars(values[4]);
				f.setReview(values[5]);

				System.out.println(f);
			}
		} catch (JsonSyntaxException | JAXBException | NumberFormatException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("[ERROR: 400 Bad Request] The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again."); // Better formatted message for client
		}

		try { // update film
			dao.updateFilm(f);
			out.write("film updated.");
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			out.write("[ERROR] film not udpated please check the data and try again.");
		}

	}

	/**
	 * doDelete
	 * 
	 * This method handles DELETE requests in a servlet and extracts the data sent
	 * by the client. It parses the data using JSON, XML, or plain text, and
	 * extracts an ID value to identify which entry in the database to delete. If
	 * the data format or the data is invalid, an error message is returned to the
	 * client. If the delete operation is successful, a confirmation message is sent
	 * back, otherwise an error message is returned.
	 * 
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null; // used for conversion
		Integer id = null; // value extracted after conversation and used in deleteFilm(id);

		try {
			if (dataFormat.equals("application/json")) { // JSON
				Gson gson = new Gson();
				f = gson.fromJson(data, Film.class);

				id = f.getId();

				System.out.println(id);

			} else if (dataFormat.equals("application/xml")) { // XML
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

				id = f.getId();

				System.out.println(id);

			} else if (dataFormat.equals("text/plain")) { // TEXT

				f = new Film(); // Film object created for program consistency

				f.setId(Integer.parseInt(data.replace(" ", "").replace("#", ""))); // data sanitization

				id = f.getId();
				System.out.println(id);
			}
		} catch (JsonSyntaxException | NumberFormatException | JAXBException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("[ERROR: 400 Bad Request] The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again.");
		}

		try { // delete film
			dao.deleteFilm(id);
			out.write("contact deleted.");
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			out.write("[ERROR] film not deleted please check the data and try again.");
		}
		out.close();
	}

}
