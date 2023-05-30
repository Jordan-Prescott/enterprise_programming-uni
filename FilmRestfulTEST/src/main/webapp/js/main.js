/**
 * main
 * 
 * main is the main JS file used this in this program used throughout the 
 * pages. This file is to manage the core functionality and behaviour 
 * and call other functions when needed such as using ajaxUtils when needing
 * to interact with the api.
 * 
 * @author jordanprescott
 * @version 0.1
 * 
 */

// globals
var allFilms = null; // updated in load
var body = $('#filmTableBody');

/**
 * loadIndex
 * 
 * This fucntion is used when the index page is loaded. It first gets the notifications
 * if there is any stored and displays that to the user. Then it uses ajaxUtils to get
 * all the latest films and display this in the body of the index page. allFilms is 
 * stored as a local variable to make use of in program for efficiency.
 * 
 */
function loadIndex() {

	// display notifications if any
	notify();

	// get all latest films 
	apiGetAllFilms().done(function(data) {
		allFilms = data;
		localStorage.setItem("allFilms", JSON.stringify(allFilms)); // store locally

		// display table
		$.each(allFilms, function(i, film) {
			body.append(getRow(film));
		})

	})

}

/**
 * loadUpdate
 * 
 * This fucntion is used when the update page is loaded. It first gets the id
 * stored in localStorage stored when the user selects the update button. Next, it 
 * collects the allFilms variable and loops through them to find the corresponding
 * film that matches the id and saves that to film variable. This is then used to 
 * populate the update film table.
 * 
 */
function loadUpdate() {

	var id = parseInt(localStorage.getItem("filmID"));
	allFilms = JSON.parse(localStorage.getItem("allFilms"));

	//	get film details from local var
	var film = $.grep(allFilms, function(obj) {
		console.log(obj.id, id)
		return obj.id === id;
	})[0];

	// populate form fields
	$("#title").val(film.title);
	$("#director").val(film.director);
	$("#year").val(film.year);
	$("#stars").val(film.stars);
	$("#genre").val(film.genre);
	$("#rating").val(film.rating);
	$("#review").val(film.review);

}

/**
 * deleteFilm
 * 
 * This function is used to delete a film. It first builds a film JSON object
 * with the id passed to it when called. The film object has all fields but only
 * id is used this for later when the data is parsed before being sent to the API.
 * It then alerts the user and asks if they are sure they would like to delete the
 * film. If the user confirms ajaxUtils will be used to delete the film.
 * 
 */
function deleteFilm(id) { // PAUSED 

	film = { // only need id for deleting film
		id: id,
		title: "",
		year: 0,  
		director: "",
		stars: "",
		review: "",
		genre: "",
		rating: ""
	}

	if (confirm("Are you sure you want to delete this item?")) { //ask for confirmation

		apiDeleteFilm(film, $('#format').val()).done(function(data) { // call api and delete film
			// remove entry in table
			$('#' + id).remove();

			notify(); // page doesnt reload so notify is called
		})

	}

}

/**
 * addFilm
 * 
 * This function is used to add a film. It first builds a JSON object getting the details 
 * from the DOM. It then checks that the user has entered data for all fields and if not
 * it will not send the request and notify the user to fill in the data. If all fields 
 * are entered the function will use ajaxUtils to add the film.
 * 
 */
function addFilm(){
	
	// get data entered and build film
	var film = {
		title: $('#title').val(),
		year: $('#year').val(),
		director: $('#director').val(),
		stars: $('#stars').val(),
		review: $('#review').val(),
		genre: $('#genre').val(),
		rating: $('#rating').val()
	}
	
	// check all fields are not null or ""
	const allFieldsHaveValue = Object.values(film).every(val => val !== null && val !== undefined && val !== "");
	
	// if all fields entered add film
	if (allFieldsHaveValue) {
		apiCreateFilm(film, $('#format').val()).done(function() {
				//redirect to index
				window.location.href = "../index.html";
		})
	} else { // notify user to fill in all fields
		setNotification("Please ensure that all fields have a value.");
		notify();
	}
	

}

/**
 * updateFilm
 * 
 * This function is used to update a film. It first builds a JSON object getting the details 
 * from the DOM. It then checks that the user has entered data for all fields and if not
 * it will not send the request and notify the user to fill in the data. If all fields 
 * are entered the function will use ajaxUtils to update the film. 
 * 
 */
function updateFilm() {
	
	// get data entered and build film
	var film = {
		id: localStorage.getItem("filmID"),
		title: $('#title').val(),
		year: $('#year').val(),
		director: $('#director').val(),
		stars: $('#stars').val(),
		genre: $('#genre').val(),
		rating: $('#rating').val(),
		review: $('#review').val()
	}
	
	// check all fields are not null or ""
	const allFieldsHaveValue = Object.values(film).every(val => val !== null && val !== undefined && val !== "");
	
	// if all fields entered update film
	if (allFieldsHaveValue) {
		apiUpdateFilm(film, $('#format').val()).done(function() {
			//redirect to index
			window.location.href = "../index.html";
		})
	} else { // notify user to fill in all fields
 		setNotification("Please ensure that all fields have a value.");
		notify();
	}

	
}

/**
 * searchFilms
 * 
 * This function is used to search for films and display the found films in the
 * index page. It first empties the table for the search results to be added. Next
 * it gets the details needed to search from the DOM they are format, searchBy, and 
 * searchString. If they are not present and the user has just hit the search icon 
 * a message will be displayed stating that all will be returned. If the user has
 * specified what they are searching for then this function uses ajaxUtils to
 * query the API and return the found films.
 * 
 */
function searchFilms() {
	
	// empty table ready for new entries
	$("#filmTableBody").empty();

	// get search criteria
	var format = $("#format").val();
	var searchBy = $("#searchBy").val();
	var searchString = $("#searchString").val();
	

	if (searchString) { // user has typed something they are looking for
		apiSearchFilm(format, searchBy, searchString).done(function(data) {
			getBody(parseResponse(format, data));
		})
	} else { // search is empty
		setNotification("Not sure what you want? I'll give you them all.");
		notify();
			// get all latest films 
		apiGetAllFilms().done(function(data) {
			allFilms = data;
			localStorage.setItem("allFilms", JSON.stringify(allFilms)); // store locally
	
			// display table
			$.each(allFilms, function(i, film) {
				body.append(getRow(film));
			})
	
		})
	}
	
}

