<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./styles/bootstrap.css">
<meta charset="UTF-8">
<title>Update Film</title>
</head>

<!-- NAV START -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="./home">Film DB</a>
	</div>
</nav>
<!-- NAV END -->

<!-- BODY START -->
<body>

	<!-- PAGE HEADER FOR INDICATION -->
	<h1>Update Film</h1>


	<!-- FORM TO ADD FILM -->
	<!-- NOTE: All fields required for as one way of data validation -->
	<!-- NOTE: Although this loops through array only one entry will be returned: Back-end searches by unique id -->
	<c:forEach items="${film}" var="f">
		<form method="POST" action="./updateFilm?id=${f.id}">
			<fieldset class="fieldset-updateFilm">
				<div class="form-box">
					<div class="form-group">
						<label for="fTitle" class="mt-4">Title</label> <input type="text"
							class="form-control" name="title" id="title" value="${f.title}"
							required>
					</div>
					<div class="form-group">
						<label for="fYear" class="mt-4">Year</label> <input type="number"
							min="1888" class="form-control" name="year" id="year"
							value="${f.year}" required>
					</div>
					<div class="form-group">
						<label for="fDirector" class="mt-4">Director</label> <input
							type="text" class="form-control" name="director" id="director"
							value="${f.director}" required>
					</div>
					<div class="form-group">
						<label for="fStars" class="mt-4">Stars</label> <input type="text"
							class="form-control" name="stars" id="stars" value="${f.stars}"
							required>
					</div>
					<div class="form-group">
						<label for="fReview" class="mt-4">Review</label>
						<textarea class="form-control" name="review" id="review" rows="3"
							required>${f.review}</textarea>
					</div>
					<br>
					<button class="btn btn-primary">Update Film.</button>
					<br>
				</div>
			</fieldset>
		</form>
	</c:forEach>

</body>
<!-- BODY END -->

</html>