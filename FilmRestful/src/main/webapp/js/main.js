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
			body.append(getRow(film));
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

		apiDeleteFilm(film, $('#format').val()).done(function(data) {
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
		review: $('#review').val(),
		genre: $('#genre').val(),
		rating: $('#rating').val()
	}
	
	const allFieldsHaveValue = Object.values(film).every(val => val !== null && val !== undefined && val !== "");
	
	if (allFieldsHaveValue) {
		apiCreateFilm(film, $('#format').val()).done(function() {
				//redirect to index
				window.location.href = "../index.html";
		})
	} else {
		setNotification("Please ensure that all fields have a value.");
		notify();
	}
	

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
		apiUpdateFilm(film, $('#format').val()).done(function() {
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
	

	if (searchString) {
		apiSearchFilm(format, searchBy, searchString).done(function(data) {
			getBody(parseResponse(format, data));
		})
	} else {
		setNotification("Not sure what you want? I'll give you them all.");
		notify();
		apiSearchFilm(format, searchBy, searchString).done(function(data) {
			getBody(data);
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
	
	body.append(getRow(film));
	
}
