<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>Delete Person</h1>

<c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>

Name: ${person.preferredName}<br />
Gender: ${person.gender}<br/>
Birth Date: <fmt:formatDate pattern="MM-dd-yyyy" value="${person.dateOfBirth}"/><br/>

<form action="${flowExecutionUrl}" method="post">
    <input type="submit" name="_eventId_deletePerson" value="Delete Person" />
</form>

<p>Which role would you like to remove/expire?</p>

<c:forEach items="${person.roles}" var="role" varStatus="status">
    <form action="${flowExecutionUrl}" method="post">
    <h3>${role.title} <select name="terminationType"><c:forEach items="${terminationTypes}" var="type"><option value="${type.description}">${type.description}</option></c:forEach></select> <input type="submit" value="Delete" name="_eventId_delete" />
     </h3>
    Valid: <fmt:formatDate pattern="MM-dd-yyyy" value="${role.start}"/> - <fmt:formatDate value="${role.end}" pattern="MM-dd-yyyy" />
    <input type="hidden" value="${status.index}" name="roleId" />
    </form>
    <hr />
</c:forEach>

<a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
