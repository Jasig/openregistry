<%--

    Copyright (C) 2009 Jasig, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<c:choose>
    <c:when test="${searchResults eq null}">

    </c:when>
    <c:otherwise>
        <fieldset>
        <legend><span><spring:message code="find.person.headers.results"/></span></legend>
        </fieldset>
        <div id="search_results">
            <p class="instructions">${resultsSize} results were found for your query:</p>
        <display:table name="searchResults" id="personMatch" htmlId="find_person_results_table" requestURI="" >
            <display:setProperty name="basic.msg.empty_list" value="Your search returned no results." />
            <display:setProperty name="css.tr.even" value="even-rows" />
            <display:column title="Name" sortable="true" ><a href="${flowExecutionUrl}&_eventId=display&searchId=${personMatch_rowNum-1}"><c:out value="${personMatch.person.preferredName}"/></a></display:column>
            <display:column title="ID">
                ${personMatch.person.primaryIdentifiersByType[preferredPersonIdentifierType].value}
            </display:column>
            <display:column title="Roles" sortable="true">
                <c:forEach var="role" items="${personMatch.person.roles}">
                    ${role.displayableName}<br/>
                </c:forEach>
            </display:column>
            <display:column property="person.gender" title="Gender" />
        </display:table>
    </c:otherwise>
</c:choose>