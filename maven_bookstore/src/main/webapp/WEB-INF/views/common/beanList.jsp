<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>

<%
	ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
	String[] beanNames = context.getBeanDefinitionNames();
	ArrayList<Class> beanClasses = new ArrayList<Class>();
	for (String beanName: beanNames) {
	    beanClasses.add(context.getBean(beanName).getClass());
}
%>

<c:set var="context" value="<%=context%>" />
<c:set var="beanNames" value="<%=beanNames%>" />
<c:set var="beanClasses" value="<%=beanClasses%>" />

<html>
<head>
    <title>Spring</title>
</head>
<body>
<h1>Spring Context</h1>
<ul>
    <li>ApplicationContext: ${context.displayName}</li>
    <ul>
<li>${context.class.name}</li>
</ul>
</ul>
<h1>Beans</h1>
<ul>
<c:forEach var="item" items="${beanNames}" varStatus="status">
<li><b>${item}</b></li>
<ul>
<li>${beanClasses[status.index].name}</li>
</ul>
</c:forEach>
</ul>
</body>
</html>