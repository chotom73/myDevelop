<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form method="GET" modelAttribute="bookSearchCriteria">
    <fieldset>
        <legend>searchcriteria</legend>
        <table>
            <tr>
                <td><form:label path="title">title</form:label></td>
                <td><form:input path="title" /></td>
            </tr>
            <tr>
                <td><form:label path="category">category</form:label></td>
                <td><form:select path="category" items="${categories}" itemValue="id" itemLabel="name"/></td></tr>
        </table>
    </fieldset>
    <button id="search">search</button>
</form:form>

<c:if test="${not empty bookList}">
    <table>
        <tr>
            <th>title</th>
            <th>description</th>
            <th>price</th>
        </tr>
        <c:forEach items="${bookList}" var="book">
            <tr>
                <td><a href="<c:url value="/book/detail/${book.id}"/>">${book.title}</a></td>
                <td>${book.description}</td>
                <td>${book.price}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>