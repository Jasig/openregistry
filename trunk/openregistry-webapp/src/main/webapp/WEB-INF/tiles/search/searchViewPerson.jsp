<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>Delete Person</h1>

<c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>

Name: ${person.preferredName}<br />
Gender: ${person.gender}<br/>
Birth Date: <fmt:formatDate pattern="MM-dd-yyyy" value="${person.dateOfBirth}"/><br/>

<p>Which role would you like to remove/expire?</p>

<c:forEach items="${person.roles}" var="role" varStatus="status">
    <h3><a href="${flowExecutionUrl}&_eventId=deleteRole&roleId=${status.index}">${role.title}</a></h3>
    Valid: <fmt:formatDate pattern="MM-dd-yyyy" value="${role.start}"/> - <fmt:formatDate value="${role.end}" pattern="MM-dd-yyyy" />
    <hr />
</c:forEach>

<a href="${flowExecutionUrl}&eventId=cancel">Cancel</a>
