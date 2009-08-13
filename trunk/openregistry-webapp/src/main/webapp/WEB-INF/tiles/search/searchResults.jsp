<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<c:choose>
    <c:when test="${searchResults eq null}">
        
    </c:when>
    <c:when test="${empty searchResults}">
        <h2><strong></strong><spring:message code="delete.person.headers.results" /><strong></strong></h2>
        <p><strong>Your search returned no results.</strong></p>        
    </c:when>
    <c:otherwise>
        <h2><spring:message code="delete.person.headers.results" /></h2>
        <display:table name="searchResults" id="personMatch">
            <display:column title="&nbsp;">${personMatch_rowNum}</display:column>
            <display:column title="Name"><a href="${flowExecutionUrl}&_eventId=display&searchId=${personMatch_rowNum-1}">${personMatch.person.preferredName}</a></display:column>
            <display:column property="person.gender" title="Gender" />

        </display:table>
    </c:otherwise>
</c:choose>
