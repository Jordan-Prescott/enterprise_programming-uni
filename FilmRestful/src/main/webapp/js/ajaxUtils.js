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
				var tableRow = $('<tr id="'+ film.id +'">');
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
		type: "DELETE",
		dataType: "text",
		data: JSON.stringify({id: id}),
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





