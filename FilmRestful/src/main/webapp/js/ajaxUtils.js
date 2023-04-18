/**
 * 
 */

$(function() { // testing
	$("#update").click(updateFilm);
	$("#delete").click(deleteFilm);
	$("#filmDB").click(loadPage);
});

function loadPage() {
	$.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json",
		success: function(data) {
			var table = $('#filmTable');

			$.each(data, function(i, film) {
				var tableRow = $('<tr>');
				tableRow.append($('<td>').text(film.title));
				tableRow.append($('<td>').text(film.year));
				tableRow.append($('<td>').text(film.director));
				tableRow.append($('<td>').text(film.stars));
				tableRow.append($('<td>').text(film.genre));
				tableRow.append($('<td>').text(film.rating));
				tableRow.append($('<td>').text(film.review));
				tableRow.append($('<td><a href="./pages/updateFilm.html"><button type="button" class="btn btn-light">Update</button></a></td>'));
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

function deleteFilm(id){
	$.ajax({
		url: "FilmsAPI",
		type: "GET",
		dataType: "json",
		data: {
			searchString: id,
			searchBy: "id"
		},
		success: notify("You deleted a film."),
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





