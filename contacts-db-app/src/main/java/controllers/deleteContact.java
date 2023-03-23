package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ContactsDAO;
import models.Contact;

/**
 * Servlet implementation class deleteContact
 */
@WebServlet("/deleteContact")
public class deleteContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteContact() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		ContactsDAO dao = new ContactsDAO();
		String id = request.getParameter("id");
		
		Contact c = dao.selectContacts(Integer.valueOf(id));
		
		try {
			dao.deleteContact(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.print(id);
		
		response.sendRedirect("./contacts");
	}

}
