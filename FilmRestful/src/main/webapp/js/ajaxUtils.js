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
 * The loadPage function iis executed when the index.html page is loaded. 
 * The purpose of this function is to populate the index page with all 
 * the entries from a database by making an AJAX request to an API that 
 * serves as an interface for the database. The function uses AJAX to 
 * make a request to the API, which responds with a list of all the films 
 * in the database. The function then processes the response and apps the 
 * elements to the HTML elements to display the films on the index page.
 * 
 */
function loadPage() {
	
	// get all films
	$.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json",
		success: function(result) {
			var table = $('#filmTableBody');
			
			//loop over results and format each film for a row in table
			$.each(result, function(i, film) {
				table.append(formatRow(film));
			})
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
 * deleteFilm
 * 
 * The deleteFilm function takes a single parameter, id, which represents the unique 
 * identifier of the film to be deleted from the database. The purpose of this function 
 * is to delete a film from the database by making an AJAX request to the API using 
 * the DELETE HTTP method. The function constructs the appropriate API endpoint 
 * URL for the specific film to be deleted and sends the AJAX request to the API. 
 * If the request is successful, the function updates the table to remove the 
 * deleted film and notifies the user that the film has been deleted.
 * 
 * @param {string} id - Unique identifier of a film.
 */
function deleteFilm(id) {

	if (confirm("Are you sure you want to delete this item?")) { //ask for confirmation

		// delete film
		$.ajax({ 
			url: "FilmsAPI",
			type: "DELETE",
			dataType: "text",
			data: JSON.stringify({ id: id }),
			contentType: "application/json",
			success: function(result) {
				console.log(result);
				
				// remove entry in table
				$('#' + id).remove();
				notify(result);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				// Handle any errors that occur during the request
				console.error("Error: " + textStatus, errorThrown);
				
				// notify the film was deleted.
				// TODO: refactor 
				notify(result);
			},
			complete: function(result) {
				// Request complete
				console.log("Delete film complete.");
			}
		});
	}
}

/**
 * addFilm
 * 
 * The addFilm function is used on the addFilm.html page to collect 
 * information about a new film from the user and add it to the database. 
 * The function collects the entries that the user has inputted in the form, 
 * and checks whether all required fields have been filled in. If all required 
 * fields have been completed, the function constructs an AJAX POST request 
 * with the entered data, and sends it to the API to add the new film to 
 * the database. If the request is successful, the function notifies the user 
 * that the film has been added and redirects them to the index page. 
 * 
 * TODO: Notify and redirtect the user to index
 */
function addFilm() {
	
	// get values in form entered by user
	var title = $('#title').val();
	var year = $('#year').val();
	var director = $('#director').val();
	var stars = $('#stars').val();
	var genre = $('#genre').val();
	var rating = $('#rating').val();
	var review = $('#review').val();

	// make sure all feilds are filled in
	if (title && year && director && stars && genre && rating && review) {

		// add film 
		$.ajax({
			url: "../FilmsAPI",
			type: "POST",
			dataType: "text",
			data: JSON.stringify(
				{
					title: title,
					year: year,
					director: director,
					stars: stars,
					genre: genre,
					rating: rating,
					review: review
				}
			),
			contentType: "application/json",
			success: function(result) {
				console.log(result);

			},
			error: function(jqXHR, textStatus, errorThrown) {
				// Handle any errors that occur during the request
				console.error("Error: " + textStatus, errorThrown);

			},
			complete: function(result) {
				// Request complete
				console.log("Added film complete.");
			}
		});

	}

}

/**
 * updateFilm
 * 
 * The updateFilm function is used on the updateFilm.html page to collect 
 * updated information about a film from the user and update the corresponding 
 * record in the database. The function collects all the fields entered by 
 * the user in the update form, and checks whether all required fields have 
 * been completed. If all required fields have been filled in, the function 
 * constructs an AJAX PUT request with the updated data, and sends it to the 
 * FilmsAPI to update the corresponding record in the database. If the request 
 * is successful, the function notifies the user that the film has been updated 
 * and redirects them to the index page. 
 * 
 * TODO: notify and redirect user 
 */
function updateFilm() {
	
	// get details of film from form
	var id = localStorage.getItem("filmID");
	var title = $('#title').val();
	var year = $('#year').val();
	var director = $('#director').val();
	var stars = $('#stars').val();
	var genre = $('#genre').val();
	var rating = $('#rating').val();
	var review = $('#review').val();

	// make sure all feilds are filled in
	if (title && year && director && stars && genre && rating && review) {

		// update film
		$.ajax({
			url: "../FilmsAPI",
			type: "PUT",
			dataType: "text",
			data: JSON.stringify(
				{
					id: id,
					title: title,
					year: year,
					director: director,
					stars: stars,
					genre: genre,
					rating: rating,
					review: review
				}
			),
			contentType: "application/json",
			success: function(result) {
				console.log(result.responseText);
				window.location.href = 'index.html';

			},
			error: function(jqXHR, textStatus, errorThrown) {
				// Handle any errors that occur during the request
				console.error("Error: " + textStatus, errorThrown);

			},
			complete: function(result) {
				// Request complete
				console.log("Update film complete.");

			}
		});

	}

}

/**
 * populateUpdateForm
 * 
 * 
 * 
 */
function populateUpdateForm() {

	// get id stored in local storage
	id = localStorage.getItem("filmID");

	// get film
	$.ajax({
		url: "../FilmsAPI",
		type: "GET",
		dataType: "json",
		data: {
			searchBy: "id",
			searchString: id
		},
		success: function(result) {

			// list returned so get the only entry
			var film = result[0];
			
			// fill in the form with current film details			
			$("#title").val(film.title);
			$("#director").val(film.director);
			$("#year").val(film.year);
			$("#stars").val(film.stars);
			$("#genre").val(film.genre);
			$("#rating").val(film.rating);
			$("#review").val(film.review);
			
			console.log(film);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			// Handle any errors that occur during the request
			console.error("Error: " + textStatus, errorThrown);
		},
		complete: function(result) {
			// Request complete
			console.log("Get Film for update complete.");
			notify(result.responseText);
		}
	});
}


/**
 * searchFilm
 * 
 * 
 * 
 */
function searchFilm() {

	// empty table ready for new entries
	$("#filmTableBody").empty();

	// get search criteria
	var format = $("#format").val();
	var searchBy = $("#searchBy").val();
	var searchString = $("#searchString").val();
	
	// format accept header on whats requested by user
	if(format == "xml") {
		accept = "application/xml"
	} else if(format == "text") {
		accept = "text/plain"
	} else {
		accept = "application/json"
	}

	// get films matching search criteria
	$.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: format,
		data: {
			searchBy: searchBy,
			searchString: searchString
		},
		headers: {
			"Accept": accept
		},
		success: function(data) {
			getBody(format, data);			
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
