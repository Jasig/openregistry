<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="searchResults">
<c:if test="${searchResults != null and fn:length(searchResults) gt 0}">
   <h2><spring:message code="delete.person.headers.results" /></h2>
   <ul>
   <c:forEach items="${searchResults}" varStatus="status" var="result">
        <li><a href="${flowExecutionUrl}&_eventId=display&searchId=${status.index}">${result.person.preferredName}</a></li>
   </c:forEach>
   </ul>
</c:if>
</div>
