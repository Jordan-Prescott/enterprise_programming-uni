<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./styles/bootstrap.css">
<meta charset="UTF-8">
<title>MVC Film DB</title>
</head>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">Film DB.</a>
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
				<legend>Legend</legend>
				<div class="form-group row">
					<label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
					<div class="col-sm-10">
						<input type="text" readonly="" class="form-control-plaintext"
							id="staticEmail" value="email@example.com">
					</div>
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1" class="form-label mt-4">Email
						address</label> <input type="email" class="form-control"
						id="exampleInputEmail1" aria-describedby="emailHelp"
						placeholder="Enter email"> <small id="emailHelp"
						class="form-text text-muted">We'll never share your email
						with anyone else.</small>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1" class="form-label mt-4">Password</label>
					<input type="password" class="form-control"
						id="exampleInputPassword1" placeholder="Password">
				</div>

				<label for="fTitle">Title:</label><br> <input type="text"
					id="title" name="title" required><br> <label
					for="fYear">Year:</label><br> <input type="number" min="1888"
					id="year" name="year" required><br> <label
					for="fDirector">Director:</label><br> <input type="text"
					id="director" name="director" required><br> <label
					for="fStars">Stars:</label><br> <input type="text" id="stars"
					name="stars" required><br> <label for="fReview">Review:</label><br>
				<input type="text" id="review" name="review" required><br>

				<br>
				<button>Add Film.</button>
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