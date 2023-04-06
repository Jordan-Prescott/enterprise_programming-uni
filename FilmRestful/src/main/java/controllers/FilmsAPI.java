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

import model.Film;
import model.FilmList;
import database.FilmDAOEnum;

/**
 * Servlet implementation class FilmsAPI
 * 
 * @author jordanprescott
 * 
 *         DESCRIPTION HERE!!!!1
 * 
 *         Format followed JSON > XML > TEXT
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		ArrayList<Film> allFilms = dao.getAllFilms();
		String format = request.getHeader("Accept");

		// JSON default output
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
			String textOutput = "id#title#year#director#stars#review#\n"; //Headers 

			for (Film f : allFilms) {
				String row = String.format("%s#%s#%s#%s#%s#%s\n", 
						String.valueOf(f.getId()), 
						f.getTitle(),
						String.valueOf(f.getYear()), 
						f.getDirector(), 
						f.getStars(), 
						f.getReview());
				textOutput += row;
			}

			data = textOutput;
		}

		out.write(data); // return content to client
	}

	/**
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

		if (dataFormat.equals("application/json")) { //JSON
			Gson gson = new Gson();
			f = gson.fromJson(data, Film.class);
			
			System.out.println(f);
			
		} else if (dataFormat.equals("application/xml")) { //XML
			JAXBContext jaxbContext;
			
			try {
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

				System.out.println(f);
			} catch (JAXBException e) {
				System.out.println(e);
				e.printStackTrace();
			}

		} else if (dataFormat.equals("text/plain")) { //TEXT
			String values[] = data.split("#");
			
			f = new Film();
			
			f.setTitle(values[0]);
			f.setYear(Integer.parseInt(values[1].replace(" ", ""))); // sanitation of date 
			f.setDirector(values[2]);
			f.setStars(values[3]);
			f.setReview(values[4]);

			System.out.println(f);
		}

		try {
			dao.insertFilm(f);
			out.write("contact inserted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("[ERROR] film not inserted.");
		}
		out.close();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");

		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		if (dataFormat.equals("application/xml")) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				Film f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));
				dao.updateFilm(f);
				out.write("contact updated");
			} catch (JAXBException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (dataFormat.equals("application/json")) {

			Gson gson = new Gson();
			Film f = gson.fromJson(data, Film.class);
			try {
				dao.updateFilm(f);
				out.write("contact updated");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");

		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		Film f = null;

		if (dataFormat.equals("application/xml")) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (dataFormat.equals("application/json")) {
			Gson gson = new Gson();
			f = gson.fromJson(data, Film.class);
		}

		try {
			dao.deleteFilm(1);
			out.write("contact deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("ERROR");
		}
		out.close();
	}

}
