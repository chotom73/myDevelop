<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${exception ne null}">
<div class="error">${exception.message}</div>
</c:if>
<form action="<c:url value="/login"/>" method="post">
	<fieldset>
		<legend>Login</legend>
	</fieldset>
	<table>
		<tr>
			<td>UserName</td>
			<td>
				<input type="text" id="username" name="username" placeholder="Username"/>
		</tr>
		<tr>
			<td>Password</td>
			<td>
				<input type="password" id="password" name="password" placeholder="Password"/>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button id="login">Login</button>
			</td>
		</tr>
	</table>
</form>

</body>
</html>