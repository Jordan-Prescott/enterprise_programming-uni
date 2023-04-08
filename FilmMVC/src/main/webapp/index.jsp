<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC Film DB</title>
</head>
<body>

<!-- Insert new film. -->

<form method="POST" action="./createFilm">

  <label for="fTitle">Title:</label><br>
  <input type="text" id="title" name="title"><br>
  
  <label for="fYear">Year:</label><br>
  <input type="text" id="year" name="year">
  
  <label for="fDirector">Director:</label><br>
  <input type="text" id="director" name="director">
  
  <label for="fStars">Stars:</label><br>
  <input type="text" id="stars" name="stars">
  
  <label for="fReview">Review:</label><br>
  <input type="text" id="review" name="review">
  
  <button>Add New Entry.</button>
</form>
<b></b>


<!-- Display database output. -->
<table>
	<tr>
		<th>Title</th>	
		<th>Year</th>
		<th>Director</th>
		<th>Stars</th>
		<th>Review</th>	
	</tr>
	
	<c:forEach items="${films}" var="c"> 
	<tr>
		<td><em></em></td>
		<td><em>${c.title}</em></td>
		<td><em>${c.year}</em></td>	
		<td><em>${c.director}</em></td>	
		<td><em>${c.stars}</em></td>	
		<td><em>${c.review}</em></td>	

		<td><a href="./deleteFilm?id=${c.id}">Delete</a></td>
		<td><a href="./updateFilm?id=${c.}">Update</a></td>	
	</tr>
	</c:forEach>
</table>	

</body>
</html>