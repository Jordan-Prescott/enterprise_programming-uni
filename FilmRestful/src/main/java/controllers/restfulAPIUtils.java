package controllers;

import java.util.Arrays;
import java.util.List;

/**
 * restfulAPIUtils
 * 
 * Utils class used for functions such as data validation and string formatting for the FilmsAPI.
 * 
 * @author jordanprescott
 *
 * @version 1.0
 * @since 15/04/23
 */
public class restfulAPIUtils {
	
	
	/**
	 * validateQuery
	 * 
	 * This method validates a users input by checking if what they have sent is in an array of valid search criteria. 
	 * If the data is in the array the method will return true else it will return false.
	 * 
	 * @param column Column the user wants to search by.
	 * @return Boolean True is the data is valid and false if the not.
	 */
	public static Boolean validateQuery(String column) {
		
		List<String> validSearchCriteria = Arrays.asList("id", "title", "year", "director", "stars", "genre", "rating");
		
		if (validSearchCriteria.contains(column)) {
		    return true;
		}
		 return false;
		
	}
	
	/**
	 * prePrepareStatement
	 * 
	 * This method prepares the search string before being passed to DAO. This will wildcard the 
	 * value if in the array. The reason for this is it gives better results from the database 
	 * for example searching title = batman will return one value but searching title = %batman%
	 * will return multiple.
	 * 
	 * @param searchString The value the user is searching for.
	 * @param column The column in which they are looking for the searchString.
	 * @return ss Formatted searchString ready to be passed to DAO.
	 */
	public static String prePrepareStatement(String searchString, String column) {
		
		List<String> wildCardcolumns = Arrays.asList("title", "director", "stars");
		
		String ss = searchString.toLowerCase(); // sanitize data 
		
		if (wildCardcolumns.contains(column)) {
			ss = "%" + searchString + "%";
		}
		
		return ss;
		
	}
	

}
