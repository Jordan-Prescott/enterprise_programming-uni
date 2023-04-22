/**
 * 
 */

function storeID(id) {
	localStorage.setItem("filmID", id);
}

/**
 * 
 */
function getBody(format, data) {

	var body = $('#filmTableBody');

	if (format == "xml") {
		$(data).find('film').each(function() {
			var id = $(this).find('id').text();
			var title = $(this).find('title').text();
			var director = $(this).find('director').text();
			var genre = $(this).find('genre').text();
			var year = $(this).find('year').text();
			var stars = $(this).find('stars').text();
			var rating = $(this).find('rating').text();
			var review = $(this).find('review').text();

			body.append(formatRow(id, title, year, director, stars, genre, rating, review));
		});

	} else if (format == "text") {
		var rowStrings = data.split(/[\n\r]+/);
		
		
		for (var i = 1; i < rowStrings.length -1; i++) {
			row = rowStrings[i].split("#");
			body.append(formatRow(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
		}

	} else {
		$.each(data, function(i, film) {
			body.append(formatRow(film.id, film.title, film.year, film.director, film.stars, film.genre, film.rating, film.review));
			})
	}

}

function formatRow(id, title, year, director, stars, genre, rating, review) {
	var tableRow = $('<tr id="' + id + '">');
	tableRow.append($('<td>').text(title));
	tableRow.append($('<td>').text(year));
	tableRow.append($('<td>').text(director));
	tableRow.append($('<td>').text(stars));
	tableRow.append($('<td>').text(genre));
	tableRow.append($('<td>').text(rating));
	tableRow.append($('<td>').text(review));
	tableRow.append($('<td><a href="./pages/updateFilm.html"><button type="button" onclick="storeID(' + id + ')" class="btn btn-light">Update</button></a></td>'));
	tableRow.append($('<td><button type="button" class="btn btn-danger" onclick="deleteFilm(' + id + ')">Delete</button></td>'));

	return tableRow
}