/**
 * ajaxUtils
 * Set of functions to handle AJAX requests to a REST API called FilmsAPI. 
 * This file serves as a centralized location for all CRUD (Create, Read, Update, Delete) 
 * operations related to the FilmsAPI. The module exports functions for 
 * making GET, POST, PUT and DELETE requests, as well as handling errors and 
 * parsing responses. These functions can be used by other parts of the application 
 * to interact with the FilmsAPI and perform various tasks, such as retrieving data, 
 * adding new records, updating existing records, and deleting records.
 * 
 * @author jordanprescott
 * @version 0.1
 * 
 */

/**
 * dataHeader
 * 
 * This function takes in String paramter of format thi is the format the user
 * has requested to send or receive the data from the API. This function takes
 * that requested format and returns the corresponding header needed in the 
 * AJAX API request.
 * 
 * @param {string} format - Chosen format user requesting data in 
 * @returns {string} - Restful header value needed in framework for requesting data 
 */
function dataHeader(format) {

	// format accept header on whats requested by user
	if (format == "xml") {
		return "application/xml"
	} else if (format == "text") {
		return "text/plain"
	} else {
		return "application/json"
	}
}

/**
 * loadPage
 * 
 * This function us used when the index page (main page) is loaded. This 
 * retrieves all the latest films and returns them in an array of film
 * objects.
 * 
 * @returns {object} AJAX - Array of films in JSON format
 */
function apiGetAllFilms() {

	// get all films
	return $.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json", // static data type for efficiency 
		success: function() {
			console.log("Get all films success");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
		},
		complete: function() {
			// Request complete
			console.log("Get all films complete.");
		}
	});

}

/**
 * deleteFilm
 * 
 * This function takes in two parameters, the first is film which is a
 * JSON object representation of a film. The second is content which is
 * the specified formatting the user would like the contents of the 
 * response from films to be in either JSON, XML, or TEXT. To make this 
 * fucntion dynamic it uses these variables to format the correct AJAX
 * reuqest and format the data in the correct way. The request will delete
 * a film and once the request is done the response from the API is then 
 * returned.
 * 
 * @param {object} film - JSON object representation of a film
 * @param {string} content - Data format requested by user
 * @returns {object} AJAX - Response from API
 * 
 */
function apiDeleteFilm(film, content) {

	// delete film
	return $.ajax({
		url: "FilmsAPI",
		type: "DELETE",
		dataType: "text",
		data: parseRequest(content, film), // format data on content requested
		contentType: dataHeader(content), // get correct header for request
		success: function(result) {
			console.log(result);
			setNotification(result); // set notification for load after redirect

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result); // set notification for load after redirect

		},
		complete: function() {
			// Request complete
			console.log("Delete film complete.");
		}
	});
}

/**
 * addFilm
 * 
 * This function takes in two parameters, the first is film which is a
 * JSON object representation of a film. The second is content which is
 * the specified formatting the user would like the contents of the 
 * response from films to be in either JSON, XML, or TEXT. To make this 
 * fucntion dynamic it uses these variables to format the correct AJAX
 * reuqest and format the data in the correct way. The request will add
 * a film and once the request is done the response from the API is then 
 * returned.
 * 
 * @param {object} film - JSON object representation of a film
 * @param {string} content - Data format requested by user
 * @returns {object} AJAX - Response from API
 * 
 */
function apiCreateFilm(film, content) {

	// add film 
	return $.ajax({
		url: "../FilmsAPI",
		type: "POST",
		dataType: "text",
		data: parseRequest(content, film), // format data on content requested
		contentType: dataHeader(content), // get correct header for request
		success: function(result) {
			console.log(result);
			setNotification(result); // set notification for load after redirect

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result); // Notify of error
			notify();

		},
		complete: function() {
			// Request complete
			console.log("Added film complete.");

		}
	});


}

/**
 * updateFilm
 * 
 * This function takes in two parameters, the first is film which is a
 * JSON object representation of a film. The second is content which is
 * the specified formatting the user would like the contents of the 
 * response from films to be in either JSON, XML, or TEXT. To make this 
 * fucntion dynamic it uses these variables to format the correct AJAX
 * reuqest and format the data in the correct way. The request will update
 * a film and once the request is done the response from the API is then 
 * returned.
 * 
 * @param {object} film - JSON object representation of a film
 * @param {string} content - Data format requested by user
 * @returns {object} AJAX - Response from API
 * 
 */
function apiUpdateFilm(film, content) {


	// update film
	return $.ajax({
		url: "../FilmsAPI",
		type: "PUT",
		dataType: "text",
		data: parseRequest(content, film), // format data on content requested
		contentType: dataHeader(content), // get correct header for request
		success: function(result) {
			console.log(result);
			setNotification(result); // set notification for load after redirect
 
			// redirect to index.html
			window.location.href = "../index.html";

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result); // Notify of error
			notify();

		},
		complete: function() {
			// Request complete
			console.log("Update film complete.");

		}
	});



}

/**
 * searchFilm
 * 
 * This function is used to search for films in the DB and return the results 
 * found. This takes in three parameters the first is content the chosen data
 * format the user would like to retrieve the contents of the response from 
 * the api in. The second is column which refers to database column e.g 'title' 
 * this is the column the search will query. Finally, the searchString parameter
 * is what the value the user would like to look for e.g. 'batman'. This request
 * would look for films with batman in the title and return the found films.
 * 
 * @param {string} content - Data format requested by user
 * @param {string} column - Column of database searching against
 * @param {string} searchString - Value searching for
 * @returns {object} AJAX - Response from API
 * 
 */
function apiSearchFilm(content, column = "", searchString = "") {

	// get films matching search criteria
	return $.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: content, // type of data request is expecting back
		data: {
			searchBy: column,
			searchString: searchString
		},
		headers: {
			"Accept": dataHeader(content) // get correct header for request
		},
		success: function(data) {
			console.log("Search films success.");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
		},
		complete: function() {
			// Request complete
			console.log("Load page complete");
		}
	});

}

/** 
 * parseResponse
 * 
 * This function is to parse the data that is returned from the ajax GET requests
 * used to get all film and search for films. The data is passed into this function
 * that will return a JSON array of JSON objects representing films. The first parameter
 * if the format of the data that is passed in either JSON, XML, or TEXT. The data is 
 * array of films in either one of those formats. The reason for parsing back to JSON 
 * is this is locally adopted in JS, quicker to parse and makes other functions more more
 * useable.
 * 
 * @param {string} format - Format of the data passed in 
 * @param {object} data - Array of films from an ajax request in either JSON, XML, or TEXT
 * @returns {object} parsedFilms - JSON array of films
 * 
 */
function parseResponse(format, data) {

	var parsedFilms = []

	if (format == "xml") { // XML
		$(data).find('film').each(function() { // loop through results 

			film = {
				id: $(this).find('id').text(),
				title: $(this).find('title').text(),
				year: $(this).find('year').text(),
				director: $(this).find('director').text(),
				stars: $(this).find('stars').text(),
				genre: $(this).find('genre').text(),
				rating: $(this).find('rating').text(),
				review: $(this).find('review').text()
			}

			parsedFilms.push(film); // format a film into table row
		});

	} else if (format == "text") { // TEXT

		// split whole text into list of films
		var rowStrings = data.split(/[\n\r]+/);

		// loop through list of films
		for (var i = 1; i < rowStrings.length - 1; i++) {
			row = rowStrings[i].split("#"); // split on # deliminator

			// format a film into table row
			film = {
				id: row[0],
				title: row[1],
				year: row[2],
				director: row[3],
				stars: row[4],
				genre: row[5],
				rating: row[6],
				review: row[7],
			}

			parsedFilms.push(film);
		}

	} else { // JSON
		parsedFilms = data; // data already formatted from api response
	}

	return parsedFilms
}

/**
 * parseRquest
 * 
 * This function is to format the data in the correct data formatting before being
 * sent to the API. This is used when Adding, Deleting, or Updating a film. The format
 * is chosen by the user and the film is then represented in that data type. This 
 * function returns the film in the format the user has chosen.
 * 
 * @param {string} format - Format of the data should be in when returned
 * @param {object} film - A film JSON object representation
 * @returns {object} parsedFilm - A film represntation in either JSON, XML, or TEXT
 * 
 */
function parseRequest(format, film) {

	var parsedFilm = "";

	if (format == "xml") { // XML

		parsedFilm = '<film>'; 
		
		for (var i in film) { // loop films formatting tags
			parsedFilm += '<' + i + '>' + film[i] + '</' + i + '>';
		}
		
		parsedFilm += '</film>'; 

	} else if (format == "text") { // TEXT
		
		if (film.id) { // add only for update and delete. Add doesnt need this.
			parsedFilm = film.id + "#"
		}
		
		parsedFilm += film.title + "#" 
		+ film.year + "#" 
		+ film.director + "#" 
		+ film.stars + "#" 
		+ film.review + "#"
		+ film.genre + "#" 
		+ film.rating;

	} else {
		parsedFilm = JSON.stringify(film); // JSON
	}
	
	return parsedFilm

}