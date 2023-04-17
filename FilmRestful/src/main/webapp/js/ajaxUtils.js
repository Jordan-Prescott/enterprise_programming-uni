/**
 * 
 */

$(function() {
	$("#updateFilm").click(updateFilm);
	$("#deleteFilm").click(deleteFilm);
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
				tableRow.append($('<td type="button" class="btn btn-link" id="updateFilm">Update</td>'));
				tableRow.append($('<td type="button" class="btn btn-link" id="deleteFilm">Delete</td>'));
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

function updateFilm() {
	console.log("get the fuck out of here.")
}