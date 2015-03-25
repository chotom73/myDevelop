<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Active Users</h1>
<ul>
	<c:forEach items="${activeUsers}" var="uinfo">
		<li>
			<strong>${uinfo.key.username}</strong>
			/ last Active : <strong>${uinfo.value}</strong>
		</li>
	</c:forEach>
</ul>