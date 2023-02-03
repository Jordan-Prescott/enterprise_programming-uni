package controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
		
		Contact contact = dao.selectContacts(1);
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		out.write("<b>"+contact.getName() + "</b> | " + contact.getEmail() + "<br>");
		out.close();

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// add contact code here
		
	}

}
