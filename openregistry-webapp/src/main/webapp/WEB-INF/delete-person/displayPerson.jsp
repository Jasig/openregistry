<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Delete Person</title>
    </head>
    <body>
        <h1>Delete Person</h1>
        <p>Which components of this person would you like to remove?</p>

        <c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>

        <form:form method="POST" commandName="deletionCriteria">
            <fieldset>
                <form:label path="entirePerson">The Entire Person, including System of Record Records.</form:label> <form:checkbox path="entirePerson" />
            </fieldset>
            <input type="submit" name="_eventId_submit" value="Delete" /> <input type="submit" name="_eventId_cancel" value="Cancel" />
        </form:form>

    </body>
</html>