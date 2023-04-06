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

import com.google.gson.Gson;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import database.ContactsDAO;
import models.Contact;
import models.ContactList;

/**
 * Servlet implementation class ContactsAPIController
 */
@WebServlet("/contacts-api")
public class ContactsAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactsAPIController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ContactsDAO dao = new ContactsDAO();
		ArrayList<Contact> allContacts = dao.selectAllContacts();

		String dataFormat = request.getHeader("Content-Type");
		String format = request.getHeader("Accept");

		String data = gson.toJson(allContacts);

		if (format.equals("application/xml")) {
			response.setContentType("application/xml");
			ContactList cl = new ContactList(allContacts);
			StringWriter sw = new StringWriter();
			JAXBContext context;
			try {
				context = JAXBContext.newInstance(ContactList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(cl, sw);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			data = sw.toString();
		}

		out.write(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ContactsDAO dao = new ContactsDAO();
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
	
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
		Contact c = null;
		
		if (dataFormat.equals("application/xml")) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Contact.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				c = (Contact) jaxbUnmarshaller.unmarshal(new StringReader(data));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (dataFormat.equals("application/json")){
			Gson gson = new Gson();
			c = gson.fromJson(data, Contact.class);
		}
		
		try {
			dao.insertContact(c);
			out.write("contact inserted.");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("[ERROR] film not added.");	
		}
		out.close();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ContactsDAO dao = new ContactsDAO();
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
		
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
		if (dataFormat.equals("application/xml")) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Contact.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				Contact c = (Contact) jaxbUnmarshaller.unmarshal(new StringReader(data));
				dao.updateContact(c);
				out.write("contact updated.");	
			} catch (JAXBException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (dataFormat.equals("application/json")){
			
			Gson gson = new Gson();
			Contact c = gson.fromJson(data, Contact.class);
			try {
				dao.updateContact(c);
				out.write("contact updated.");	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ContactsDAO dao = new ContactsDAO();
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
	
		String data = request.getReader().lines().reduce("",
				(accumulator, actual) -> accumulator + actual);
		
		Contact c = null;
		
		if (dataFormat.equals("application/xml")) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Contact.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				c = (Contact) jaxbUnmarshaller.unmarshal(new StringReader(data));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (dataFormat.equals("application/json")){
			Gson gson = new Gson();
			c = gson.fromJson(data, Contact.class);
		}
		
		try {
			dao.deleteContact(c);
			out.write("contact deleted.");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("[ERROR] film not deleted.");	
		}
		out.close();
	}

}
