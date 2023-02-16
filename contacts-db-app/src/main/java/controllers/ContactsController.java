package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ContactsDAO;
import models.Contact;

/**
 * Dr Mohammed Kaleem
 * Lab Demo
 */
@WebServlet("/contacts")
public class ContactsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ContactsDAO dao = new ContactsDAO();
		ArrayList<Contact> allCons = dao.selectAllContacts();
		request.setAttribute("contacts", allCons);
		RequestDispatcher rd = request.getRequestDispatcher("allContacts.jsp");
		rd.include(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ContactsDAO dao = new ContactsDAO();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Contact con = new Contact(name, email);
		
		try {
			dao.insertContact(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.print(name);
		
		response.sendRedirect("./contacts");
	}

}


