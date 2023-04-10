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

<!-- NAV START -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
	
	  	<!-- FILMDB LOGO + ADD BUTTON -->
		<a class="navbar-brand" href="./home">Film DB</a>
		<ul class="navbar-nav me-auto">
			<li class="nav-item"><a class="btn btn-success"
				href="./createFilm">Add Film</a></li>
		</ul>
		
		<!-- SEARCH -->
		<form method="POST" action="./searchFilms" class="d-flex">
			<input class="form-control me-sm-2" type="search"
				placeholder="e.g. ROBERT DE NIRO" name="querySQL" id="querySQL">
			<button class="btn btn-secondary my-2 my-sm-0">Search</button>
		</form>
		
	</div>
</nav>
<!-- NAV END -->

<!-- BODY START -->
<body>
	<!-- FILMS TABLE -->
	<div class="">
		<table class="table table-hover">
			<tr>
				<th>Title</th>
				<th>Year</th>
				<th>Director</th>
				<th>Stars</th>
				<th>Genre</th>
				<th>Rating</th>
				<th>Review</th>
			</tr>

			<c:forEach items="${films}" var="f"> <!-- LOOPS THROUGH ARRAY OF FILMS GENERATING TABLE -->
				<tr>
					<td><em>${f.title}</em></td>
					<td><em>${f.year}</em></td>
					<td><em>${f.director}</em></td>
					<td><em>${f.stars}</em></td>
					<td><em>${f.genre}</em></td>
					<td><em>${f.rating}</em></td>
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
