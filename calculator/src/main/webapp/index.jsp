<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="./simple_calculator">
	<input type="number" name="num1" placeholder="first_number">
	<select name="operation">
		<option>+</option>
		<option>-</option>
		<option>*</option>
		<option>/</option>
	</select>
	<input type="number" name="num2" placeholder="second_number">
	<input type="submit" value="Calculate">
</form>
Answer: ${total}
</body>
</html>