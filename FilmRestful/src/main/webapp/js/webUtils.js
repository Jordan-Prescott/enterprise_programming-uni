/**
 * webUtils
 * 
 * WebUtils is to help store and parse data that can be used in a web application's user 
 * experience as well as other functions. The file contains functions that allow the 
 * application to easily store and parse data from localStorage or other sources. It 
 * also provides methods for parsing data in different formats, such as JSON or XML. 
 * The parsed data can be used to dynamically create elements on the page, update the 
 * user interface, or pass on to other functions within the application.
 * 
 * @author jordanprescott
 * @version 0.1
 * 
 */

/**
 * storeID
 * 
 * The storeID function takes in an id parameter and stores this value to localStorage 
 * with the key of filmID. The purpose of this function is to allow the application to 
 * remember the id of the film that the user wants to update, so that it can be retrieved 
 * later on the updateFilm.html page. By storing this value in localStorage, the 
 * application can ensure that it persists across different browser sessions and page 
 * reloads. This function is called when the user clicks on a "Update" button or link on 
 * the index page, and it stores the id of the corresponding film in localStorage using 
 * the key of filmID. Later, when the user navigates to the updateFilm.html page, the 
 * application can retrieve the id from localStorage and use it to pre-populate the 
 * update form with the details of the selected film.
 * 
 * @param {string} id - Unique identifier of a film.
 */
function storeID(id) {
	localStorage.setItem("filmID", id);
}

/**
 * getBody
 * 
 * The getBody function takes in two parameters - format and data. The format parameter 
 * specifies the format of the data that is being passed in, which can be one of 
 * JSON, XML, or TEXT. This indicates to the function how it should parse the data 
 * parameter that is being passed in. The purpose of this function is to format the 
 * data which is results from an API call, in order to make it suitable for display 
 * in a table. The formatted data is then added to the filmTableBody element in the 
 * HTML, which is used to populate the table body with the results. The getBody function 
 * typically forms part of a larger AJAX request function, which retrieves data from an 
 * API and then passes it to the getBody function for formatting and display. By formatting 
 * the data in a standardized way, the function ensures that it is displayed correctly and 
 * consistently in the table, regardless of the specific format of the API response.
 * 
 * @param {string} format - Format of the data passed in either json, xml, or text.
 * @param {Array} data - Results returned from api request.
 */
function getBody(format, data) {

	// result location
	var body = $('#filmTableBody');
	
	film = { // film object
		id: null,
		title: null,
		year: null, 
		director: null,
		stars: null,
		genre: null,
		rating: null,
		review: null
	}

	if (format == "xml") { // XML
		$(data).find('film').each(function() { // loop through results 
			film.id = $(this).find('id').text();
			film.title = $(this).find('title').text();
			film.year = $(this).find('year').text();
			film.director = $(this).find('director').text();
			film.stars = $(this).find('stars').text();
			film.genre = $(this).find('genre').text();
			film.rating = $(this).find('rating').text();
			film.review = $(this).find('review').text();

			body.append(formatRow(film)); // format a film into table row
		});

	} else if (format == "text") { // TEXT

		// split whole text into list of films
		var rowStrings = data.split(/[\n\r]+/);

		// loop through list of films
		for (var i = 1; i < rowStrings.length - 1; i++) {
			row = rowStrings[i].split("#"); // split on # deliminator
			
			// format a film into table row
			film.id = row[0];
			film.title = row[1];
			film.year = row[2];
			film.director = row[3];
			film.stars = row[4];
			film.genre = row[5];
			film.rating = row[6];
			film.review = row[7]; 

			body.append(formatRow(film));
		}

	} else { // JSON

		// loop through film objects
		$.each(data, function(i, film) {
			body.append(formatRow(film)); // format a film into table row
		})
	}

}

/**
 * formatRow
 * 
 * This function takes in a JSON object of a single film and maps the object to a 
 * table row. The row has all values of the film as well as formatted buttons for
 * updating and deleting a film in the user interface. The correct functions with
 * the film ids are formatted so that they can be called and pass in variables to 
 * make the user experience seemless. 
 * 
 * @param {JSON} film - JSON object of Film.
 */
function formatRow(film) {
	var tableRow = $('<tr id="' + film.id + '">');
	tableRow.append($('<td>').text(film.title));
	tableRow.append($('<td>').text(film.year));
	tableRow.append($('<td>').text(film.director));
	tableRow.append($('<td>').text(film.stars));
	tableRow.append($('<td>').text(film.genre));
	tableRow.append($('<td>').text(film.rating));
	tableRow.append($('<td>').text(film.review));
	tableRow.append($('<td><a href="./pages/updateFilm.html"><button type="button" onclick="storeID(' + film.id + ')" class="btn btn-light">Update</button></a></td>'));
	tableRow.append($('<td><button type="button" class="btn btn-danger" onclick="deleteFilm(' + film.id + ')">Delete</button></td>'));

	return tableRow
}