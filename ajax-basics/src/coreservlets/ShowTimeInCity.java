package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ShowTimeInCity extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    PrintWriter out = response.getWriter();
    String cityName = request.getParameter("city");
    String message = CityUtils.getTime(cityName);
    if (message.startsWith("In")) { // Found city
      message = String.format("<hr/><h2>%s</h2><hr/>", message);
    } else { // No city or unknown city
      message = String.format("<h2 class='error'>%s</h2>", message);
    }
    out.print(message);
  }
  
  /** Support both POST and GET so that showTimeInCityPost works
   *  identically to the earlier showTimeInCity example.
   */
  
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
