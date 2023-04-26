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
function apiDeleteFilm(id) {

	// delete film
	return $.ajax({
		url: "FilmsAPI",
		type: "DELETE",
		dataType: "text",
		data: JSON.stringify({ id: id }),
		contentType: "application/json",
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
function apiCreateFilm(film) {

		// add film 
		return $.ajax({
			url: "../FilmsAPI",
			type: "POST",
			dataType: "text",
			data: JSON.stringify(film),
			contentType: "application/json",
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
function apiUpdateFilm(film) {


	// update film
	return $.ajax({
		url: "../FilmsAPI",
		type: "PUT",
		dataType: "text",
		data: JSON.stringify(film),
		contentType: "application/json",
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
function apiSearchFilm(acceptHeader, format, column="", searchString="") {

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
			"Accept": acceptHeader
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
