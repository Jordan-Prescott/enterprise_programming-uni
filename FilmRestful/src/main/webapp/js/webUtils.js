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
 * 
 * @param {string} id - Unique identifier of a film.
 */
function storeID(id) {
	localStorage.setItem("filmID", id);
}

/**
 * getBody
 * 
 * 
 * @param {string} format - Format of the data passed in either json, xml, or text.
 * @param {Array} data - Results returned from api request.
 */
function getBody(data) {

	// result location
	var body = $('#filmTableBody');

	$.each(data, function(i, film) {
			body.append(getRow(film)); // format a film into table row
		})
}

/**
 * getRow
 * 
 * This function takes in a JSON object of a single film and maps the object to a 
 * table row. The row has all values of the film as well as formatted buttons for
 * updating and deleting a film in the user interface. The correct functions with
 * the film ids are formatted so that they can be called and pass in variables to 
 * make the user experience seemless. 
 * 
 * @param {JSON} film - JSON object of Film.
 */
function getRow(film) {
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


