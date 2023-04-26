/**
 * 
 */

var allFilms = null; // updated in load
var body = $('#filmTableBody');

/**
 * loadIndex
 * 
 */
function loadIndex() {

	notify();

	// get all latest films 
	apiGetAllFilms().done(function(data) {
		allFilms = data;
		localStorage.setItem("allFilms", JSON.stringify(allFilms)); // store locally

		// display table
		$.each(allFilms, function(i, film) {
			body.append(formatRow(film));
		})

	})

}

/**
 * loadUpdate
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
 */
function deleteFilm(id) {

	if (confirm("Are you sure you want to delete this item?")) { //ask for confirmation

		apiDeleteFilm(id).done(function(data) {
			// remove entry in table
			$('#' + id).remove();

			notify(); // page doesnt reload so notify is called
		})

	}

}

/**
 * addFilm
 * 
 */
function addFilm(){
	
	var film = {
		title: $('#title').val(),
		year: $('#year').val(),
		director: $('#director').val(),
		stars: $('#stars').val(),
		genre: $('#genre').val(),
		rating: $('#rating').val(),
		review: $('#review').val()
	}
	
	const allFieldsHaveValue = Object.values(film).every(val => val !== null && val !== undefined && val !== "");
	
	if (allFieldsHaveValue) {
		apiCreateFilm(film).done(function() {
				//redirect to index
				window.location.href = "../index.html";
		})
	} else {
		setNotification("Please ensure that all fields have a value.");
	}
	
	notify();

}

/**
 * updateFilm
 * 
 * 
 */
function updateFilm() {
	
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
	
	const allFieldsHaveValue = Object.values(film).every(val => val !== null && val !== undefined && val !== "");
	

	if (allFieldsHaveValue) {
		apiUpdateFilm(film).done(function() {
			//redirect to index
			window.location.href = "../index.html";
		})
	} else {
		setNotification("Please ensure that all fields have a value.");
	}

	notify();
	
}

/**
 * searchFilms
 */
function searchFilms() {
	
	// empty table ready for new entries
	$("#filmTableBody").empty();

	// get search criteria
	var format = $("#format").val();
	var searchBy = $("#searchBy").val();
	var searchString = $("#searchString").val();

	// format accept header on whats requested by user
	if (format == "xml") {
		accept = "application/xml"
	} else if (format == "text") {
		accept = "text/plain"
	} else {
		accept = "application/json"
	}
	

	if (searchString) {
		apiSearchFilm(accept, format, searchBy, searchString).done(function(data) {
			getBody(format, data);
		})
	} else {
		setNotification("Not sure what you want? I'll just give you them all.");
		notify();
		apiSearchFilm(accept, format, searchBy, searchString).done(function(data) {
			getBody(format, data);
		})
	}
	
}

/**
 * randomFilm
 * 
 * 
 */
function randomFilm() {

	// empty table ready for new entries
	body.empty();
	
	const randomNumber = Math.floor(Math.random() * allFilms.length);
	const film = allFilms[randomNumber];
	
	body.append(formatRow(film));
	
}
