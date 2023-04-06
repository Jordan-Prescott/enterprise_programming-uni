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
import models.Contact;
import database.ContactsDAO;
import database.FilmDAO;
import database.FilmDAOEnum;



/**
 * Servlet implementation class FilmsAPI
 */
@WebServlet("/FilmsAPI")
public class FilmsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilmsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
//		FilmDAO doa = new FilmDAO();
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		ArrayList<Film> allFilms = dao.getAllFilms();

		String dataFormat = request.getHeader("Content-Type");
		String format = request.getHeader("Accept");

		String data = gson.toJson(allFilms);

		if (format.equals("application/xml")) {
			response.setContentType("application/xml");
			FilmList fl = new FilmList(allFilms);
			StringWriter sw = new StringWriter();
			JAXBContext context;
			try {
				context = JAXBContext.newInstance(FilmList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(fl, sw);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			data = sw.toString();
		}

		out.write(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		FilmDAO dao = new FilmDAO();
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
	
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
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
			
		} else if (dataFormat.equals("application/json")){
			Gson gson = new Gson();
			f = gson.fromJson(data, Film.class);
		}
		
		try {
			dao.insertFilm(f);
			out.write("contact inserted");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("ERROR");	
		}
		out.close();
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		FilmDAO dao = new FilmDAO();
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
		
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
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
			
		} else if (dataFormat.equals("application/json")){
			
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
		
//		FilmDAO dao = new FilmDAO();
		FilmDAOEnum dao = FilmDAOEnum.INSTANCE;
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
	
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
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
			
		} else if (dataFormat.equals("application/json")){
			Gson gson = new Gson();
			f = gson.fromJson(data, Film.class);
		}
		
		try {
			dao.deleteFilm(f);
			out.write("contact deleted");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("ERROR");	
		}
		out.close();
	}

}
