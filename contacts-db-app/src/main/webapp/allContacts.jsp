<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form method="POST" action="./contacts">
  <label for="fname">Name:</label><br>
  <input type="text" id="name" name="name"><br>
  <label for="lname">Email:</label><br>
  <input type="text" id="email" name="email">
  <button>Click to add new entry.</button>
</form>
<b></b>


<form method="POST" action="./deleteContact">
  <label for="name">ID:</label><br>
  <input type="text" id="id" name="id"><br>
  <button>Click to delete entry.</button>
</form>
<b></b>

<table>
	<tr>
		<th>ID</th>	
		<th>Name</th>
		<th>Email</th>	
	</tr>
	<c:forEach items="${contacts}" var="c">
	<tr>
		<td><em>${c.id}</em></td>
		<td contenteditable='true'><em>${c.name}</em></td>
		<td contenteditable='true'><b>${c.email}</b></td>	
		<td><b>Delete</b></td>	
	</tr>
	</c:forEach>
</table>	

</body>
</html>