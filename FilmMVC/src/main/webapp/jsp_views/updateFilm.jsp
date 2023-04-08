<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Film</title>
</head>
<body>

<c:forEach items="${film}" var="f"> <!-- only 1 entry --> 
<form method="POST" action="./updateFilm?id=${f.id}">

  <label for="fTitle">Title:</label><br>
  <input type="text" id="title" name="title" value="${f.title}" required><br>
  
  <label for="fYear">Year:</label><br>
  <input type="number" min="1888" id="year" name="year" value="${f.year}" required><br>
  
  <label for="fDirector">Director:</label><br>
  <input type="text" id="director" name="director" value="${f.director}" required><br>
  
  <label for="fStars">Stars:</label><br>
  <input type="text" id="stars" name="stars" value="${f.stars}" required><br>
  
  <label for="fReview">Review:</label><br>
  <input type="text" id="review" name="review" value="${f.review}" required><br>
  
  <button>Update Film.</button>
  
</form>
</c:forEach>

</body>
</html>