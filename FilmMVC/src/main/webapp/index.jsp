<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./styles/bootstrap.css">
<meta charset="UTF-8">
<title>Film DB</title>
</head>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">Film DB</a>
		<form class="d-flex">
			<input class="form-control me-sm-2" type="search"
				placeholder="Search">
			<button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
		</form>
	</div>
</nav>
<!-- NAV -->

<body>

	<!-- Insert new film. -->
	<div class="list-group">
		<form method="POST" action="./createFilm">
			<fieldset>
				<div class="form-group">
					<label for="fTitle" class="mt-4">Title</label> <input
						type="text" class="form-control" name="title" id="title"
						placeholder="The Dark Knight" required>
				</div>
				<div class="form-group">
					<label for="fYear" class="mt-4">Year</label> <input
						type="number" min="1888" class="form-control" name="year" id="year"
						placeholder="2008" required>
				</div>
				<div class="form-group">
					<label for="fDirector" class="mt-4">Director</label> <input
						type="text" class="form-control" name="director" id="director"
						placeholder="Christopher Nolan" required>
				</div>
				<div class="form-group">
					<label for="fStars" class="mt-4">Stars</label> <input
						type="text" class="form-control" name="stars" id="stars"
						placeholder="Christian Bale, Heith Ledger, Aaron Eckhart" required>
				</div>
				<div class="form-group">
					<label for="fReview" class="mt-4">Review</label>
					<textarea class="form-control" name="review" id="review" rows="3" required></textarea>
				</div>
				<br>
				<button class="btn btn-primary">Add Film.</button>
				<br>
			</fieldset>
		</form>
	</div>
	<b></b>

	<!-- Display database output. -->
	<div class="">
		<table class="table table-hover">
			<tr>
				<th>Title</th>
				<th>Year</th>
				<th>Director</th>
				<th>Stars</th>
				<th>Review</th>
			</tr>

			<c:forEach items="${films}" var="f">
				<tr>
					<td><em>${f.title}</em></td>
					<td><em>${f.year}</em></td>
					<td><em>${f.director}</em></td>
					<td><em>${f.stars}</em></td>
					<td><em>${f.review}</em></td>

					<td><a href="./deleteFilm?id=${f.id}">Delete</a></td>
					<td><a href="./updateFilm?id=${f.id}">Update</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
<!-- BODY -->
</html> 