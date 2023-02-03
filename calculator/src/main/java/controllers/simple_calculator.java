package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class simple_calculator
 */
@WebServlet("/simple_calculator")
public class simple_calculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public simple_calculator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		request.setAttribute("total", 0);
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num1 = Integer.valueOf(request.getParameter("num1"));
		int num2 = Integer.valueOf(request.getParameter("num2"));
		String operation = request.getParameter("operation");
		int ans = 0;
		
		switch(operation) {
		case "+":
			ans = num1 + num2;
			break;
		case "-":
			ans = num1 - num2;
			break;
		case "*":
			ans = num1 * num2;
			break;
		case "/":
			ans = num1 / num2;
			break;
		default:
			break;
		}
		
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		request.setAttribute("total", ans);
		view.forward(request, response);
	}

}
