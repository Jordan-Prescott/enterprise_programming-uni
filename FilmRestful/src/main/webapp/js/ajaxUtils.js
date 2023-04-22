/**
 * ajaxUtils
 * 
 * 
 */

/**
 * loadPage
 * 
 */
function loadPage() {
	$.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json",
		success: function(data) {
			var table = $('#filmTableBody');

			$.each(data, function(i, film) {
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
				table.append(tableRow);
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
 */
function deleteFilm(id) {

	if (confirm("Are you sure you want to delete this item?")) { //ask for confirmation

		$.ajax({
			url: "FilmsAPI",
			type: "DELETE",
			dataType: "text",
			data: JSON.stringify({ id: id }),
			contentType: "application/json",
			success: function(result) {
				console.log(result);
				$('#' + id).remove();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				// Handle any errors that occur during the request
				console.error("Error: " + textStatus, errorThrown);
			},
			complete: function(result) {
				// Request complete
				console.log("Delete film complete.");
				notify(result.responseText);
			}
		});
	}
}

/**
 * addFilm
 * 
 */
function addFilm() {

	var title = $('#title').val();
	var year = $('#year').val();
	var director = $('#director').val();
	var stars = $('#stars').val();
	var genre = $('#genre').val();
	var rating = $('#rating').val();
	var review = $('#review').val();

	if (title && year && director && stars && genre && rating && review) {

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
 * 
 * 
 */
function updateFilm() {

	var id = localStorage.getItem("filmID");
	var title = $('#title').val();
	var year = $('#year').val();
	var director = $('#director').val();
	var stars = $('#stars').val();
	var genre = $('#genre').val();
	var rating = $('#rating').val();
	var review = $('#review').val();

	if (title && year && director && stars && genre && rating && review) {

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
				window.location.href = '../index.html';

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
 */
function populateUpdateForm() {

	id = localStorage.getItem("filmID");

	$.ajax({
		url: "../FilmsAPI",
		type: "GET",
		dataType: "json",
		data: {
			searchBy: "id",
			searchString: id
		},
		success: function(result) {

			var film = result[0];
			console.log(film);
			$("#title").val(film.title);
			$("#director").val(film.director);
			$("#year").val(film.year);
			$("#stars").val(film.stars);
			$("#genre").val(film.genre);
			$("#rating").val(film.rating);
			$("#review").val(film.review);

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


function searchFilm() {

	$("#filmTableBody").empty();

	var format = $("#format").val();
	var searchBy = $("#searchBy").val();
	var searchString = $("#searchString").val();
	
	// 
	if(format == "xml") {
		accept = "application/xml"
	} else if(format == "text") {
		accept = "text/plain"
	} else {
		accept = "application/json"
	}

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
