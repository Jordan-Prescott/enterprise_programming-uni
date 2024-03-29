package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * FilmAPI
 * 
 * Servlet implementation class FilmsAPI
 * 
 * @author jordanprescott
 * 
 *         RESTful API with methods GET, POST, PUT, DELETE for interacting with
 *         films database (DB). Each method can handle data types of JSON, XML,
 *         or plain text. Plain text has its own data format see below.
 * 
 *         GET     : Returns all films in data format requested. 
 *         POST    : Creates film. 
 *         PUT     : Updates existing film using id to identify entry. 
 *         DELETE  : Deletes existing film using id to identify entry.
 * 
 * @apiNote Plain/ Text Data Format: # used as deliminator and each entry on new
 *          line. Example of 3 entries below.
 * 
 *          title#year#director#stars#review 
 *          title#year#director#stars#review
 *          title#year#director#stars#review
 * 
 * 
 * @version 1.1
 * @since 15/04/23
 * 
 */
@WebServlet("/FilmsAPI")
public class FilmsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FilmsAPI() { //constructor
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) // GET
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		// store parameters
		String format = request.getHeader("Accept");
		String searchString = request.getParameter("searchString");
		String searchBy = request.getParameter("searchBy");

		ArrayList<Film> allFilms = null;

		// query validation
		if (searchBy != null && !restfulAPIUtils.validateQuery(searchBy)) { // invalid request
			out.write("ERROR: The searchBy parameter was invalid, please check documentation for "
					+ "valid criteria and try your request again.");
			
		} else if (searchString != null) { // query valid 
			
			String preparedSearchString = restfulAPIUtils.prePrepareStatement(searchString, searchBy);
																
			allFilms = dao.searchFilmsBy(preparedSearchString, searchBy); // return films that fit query

		} else { // return all films 

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

			try { // map film to xml data using JAXB 
				context = JAXBContext.newInstance(FilmList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(fl, sw);
			} catch (JAXBException e) {
				System.out.println(e);
				e.printStackTrace();
			}

			data = sw.toString(); // data formatted to xml

		} else if (format.equals("text/plain")) { // TEXT
			String textOutput = "id#title#year#director#stars#genre#rating#review#\n"; // Headers

			for (Film f : allFilms) { // loop films and format output
				String row = String.format("%s#%s#%s#%s#%s#%s#%s#%s\n", String.valueOf(f.getId()), f.getTitle(),
						String.valueOf(f.getYear()), f.getDirector(), f.getStars(), f.getGenre(), f.getRating(),
						f.getReview());
				textOutput += row;
			}

			data = textOutput; // data formatted to text
		}

		if (allFilms.isEmpty()) {
			out.write("No results found.");
		} else {
			out.write(data); // return content to client
		}
		out.close(); // close writer
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) // POST
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		// store headers
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null;

		try { // parse data sent depending on type specified in dataFormat
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
				String values[] = data.split("#"); // # delimiter 

				f = new Film();
				
				f.setTitle(values[0]);
				f.setYear(Integer.parseInt(values[1].replaceAll("[^0-9]", ""))); // data sanitization
				f.setDirector(values[2]);
				f.setStars(values[3]);
				f.setReview(values[4]);
				f.setGenre(values[5]);
				f.setRating(values[6]);

				System.out.println(f);

			}

		} catch (JsonSyntaxException | JAXBException | NumberFormatException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("ERROR: 400 Bad Request. The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again.");
		}

		try { // insert film
			dao.insertFilm(f);
			out.write("film inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
			out.write("[ERROR] film not inserted please check the data and try again.");
		}
		out.close(); // close writer
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
	protected void doPut(HttpServletRequest request, HttpServletResponse response) // PUT
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		// store headers
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null;

		try { // parse data sent depending on type specified in dataFormat
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
				String values[] = data.split("#"); // # delimiter

				f = new Film();

				f.setId(Integer.parseInt(values[0]));
				f.setTitle(values[1]);
				f.setYear(Integer.parseInt(values[2].replaceAll("[^0-9]", ""))); // data sanitization
				f.setDirector(values[3]);
				f.setStars(values[4]);
				f.setReview(values[5]);
				f.setGenre(values[6]);
				f.setRating(values[7]);
				
				System.out.println(f);
			}
		} catch (JsonSyntaxException | JAXBException | NumberFormatException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("ERROR: 400 Bad Request. The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again."); // Better formatted message for client
		}

		// update film
		try { 
			dao.updateFilm(f);
			out.write("film updated.");
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			out.write("ERROR: film not udpated please check the data and try again.");
		}
		out.close(); // close writer
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
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) // DELETE
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();

		// store headers 
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null; // used for conversion

		try { // parse data sent depending on type specified in dataFormat
			if (dataFormat.equals("application/json")) { // JSON
				Gson gson = new Gson();
				f = gson.fromJson(data, Film.class);

				System.out.println(f.getId());

			} else if (dataFormat.equals("application/xml")) { // XML
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

				System.out.println(f.getId());

			} else if (dataFormat.equals("text/plain")) { // TEXT
				String values[] = data.split("#");
				f = new Film(); // Film object created for program consistency

				f.setId(Integer.parseInt(values[0].replaceAll("[^0-9]", "")));
				
				System.out.println(f.getId());
			}
		} catch (JsonSyntaxException | NumberFormatException | JAXBException e) { // format errors: client data sent
																					// incorrectly
			System.out.println(e);
			e.printStackTrace();
			out.write("ERROR: 400 Bad Request. The data you sent was incorrectly formatted and cannot be processed. "
					+ "Please check the data and try again.");
		}

		try { // delete film
			dao.deleteFilm(f.getId());
			out.write("film deleted.");
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			out.write("ERROR: film not deleted please check the data and try again.");
		}
		out.close(); // close writer
	}

}
