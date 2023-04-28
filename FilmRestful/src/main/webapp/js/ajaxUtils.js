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
 * contentHeader
 * 
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
 * 
 */
function apiGetAllFilms() {

	// get all films
	return $.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json",
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
 * 
 * @param {string} id - Unique identifier of a film.
 */
function apiDeleteFilm(film, content) {

	// delete film
	return $.ajax({
		url: "FilmsAPI",
		type: "DELETE",
		dataType: "text",
		data: parseRequest(content, film),
		contentType: dataHeader(content),
		success: function(result) {
			console.log(result);
			setNotification(result);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result);

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
 * 
 */
function apiCreateFilm(film, content) {

	// add film 
	return $.ajax({
		url: "../FilmsAPI",
		type: "POST",
		dataType: "text",
		data: parseRequest(content, film),
		contentType: dataHeader(content),
		success: function(result) {
			console.log(result);

			// set notifcation to alert the user
			setNotification(result);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result);

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
 * 
 */
function apiUpdateFilm(film, content) {


	// update film
	return $.ajax({
		url: "../FilmsAPI",
		type: "PUT",
		dataType: "text",
		data: parseRequest(content, film),
		contentType: dataHeader(content),
		success: function(result) {
			console.log(result);

			// set notification to alert user
			setNotification(result);

			// redirect to index.html
			window.location.href = "../index.html";

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
			setNotification(result);
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
 * 
 */
function apiSearchFilm(format, column = "", searchString = "") {

	// get films matching search criteria
	return $.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: format,
		data: {
			searchBy: column,
			searchString: searchString
		},
		headers: {
			"Accept": dataHeader(format)
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
		parsedFilms = data;
	}

	return parsedFilms
}


/**
 * parseRquest
 * 
 */
function parseRequest(format, film) {

	var parsedFilm = "";

	if (format == "xml") {

		parsedFilm = '<film>';
		for (var i in film) {
			parsedFilm += '<' + i + '>' + film[i] + '</' + i + '>';
		}
		parsedFilm += '</film>';

	} else if (format == "text") {
		
		parsedFilm = film.id + "#" 
		+ film.title + "#" 
		+ film.year + "#" 
		+ film.director + "#" 
		+ film.stars + "#" 
		+ film.review + "#"
		+ film.genre + "#" 
		+ film.rating;
 		 

	} else {
		parsedFilm = JSON.stringify(film);
	}
	
	return parsedFilm

}