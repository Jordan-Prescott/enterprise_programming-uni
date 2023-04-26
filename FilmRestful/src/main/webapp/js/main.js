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
	getAllFilms().done(function(data) {
		allFilms = data;
		localStorage.setItem("allFilms", JSON.stringify(allFilms)); // store locally

		// display table
		$.each(allFilms, function(i, film) {
			body.append(formatRow(film));
		})

	})

}

/**
 * populateUpdateForm
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
 * liveSearch
 * 
 * The code that enables live searching in a web application allows users 
 * to search for specific data within a table and see the results update 
 * live as they type their search query. 
 *
 */
$(document).ready(function() {
    $('#searchString').on('keyup', function() {
        var query = $(this).val().toLowerCase();
        $('#filmTable tbody tr').each(function() {
            var text = $(this).text().toLowerCase();
            if(text.indexOf(query) !== -1) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });
});