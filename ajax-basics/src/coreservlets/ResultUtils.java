package coreservlets;

/** Some helper utilities for making Strings describing customers. */

public class ResultUtils {
  /** makeTextList returns a textual list describing the Customer,
   *  suitable for putting in a dialog box. 
   */
  
  public static String makeTextList(Customer c) {
    String list = "Richest Customer:\n" +
                  makeEntry("First name", c.getFirstName()) +
                  makeEntry("Last name", c.getLastName()) +
                  makeEntry("Balance", c.getFormattedBalance());   
    return(list);
  }
  
  private static String makeEntry(String prompt, 
                                   String value) {
    return(String.format("  - %s: %s\n",
                         prompt, value));
  }
  
  /** makeBulletedList returns an HTML ul list describing the Customer,
   *  suitable for inserting into the page. 
   */
  
  public static String makeBulletedList(Customer c) {
    String list =
      "<ul>\n" +
      makeBullet("First name", c.getFirstName()) +
      makeBullet("Last name", c.getLastName()) +
      makeBullet("Balance", c.getFormattedBalance()) +
      "</ul>\n";
    return(list);
  }
  
  private static String makeBullet(String prompt, 
                                   String value) {
    return(String.format("  <li>%s: %s</li>\n",
                         prompt, value));
  }
  
  public static String makeErrorMessage(String itemName) {
	    String message =
	      String.format("<h2 class='error'>Missing or Invalid %s</h2>",
	                    itemName);
	    return(message);
	  }
}
