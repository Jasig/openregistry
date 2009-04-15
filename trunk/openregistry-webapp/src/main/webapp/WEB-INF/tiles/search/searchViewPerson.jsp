<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Delete Person</h1>
<p>Which components of this person would you like to remove?</p>

<c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>

<form:form method="POST" commandName="deletionCriteria">
    <fieldset>
        <form:label path="entirePerson">The Entire Person, including System of Record Records.</form:label> <form:checkbox path="entirePerson" />
    </fieldset>
    <input type="submit" name="_eventId_submit" value="Delete" /> <input type="submit" name="_eventId_cancel" value="Cancel" />
</form:form>