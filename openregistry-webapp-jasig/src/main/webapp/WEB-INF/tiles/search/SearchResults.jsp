<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<c:if test="${searchResults ne null}">
	<div class="or-result-table ui-widget ui-widget-content ui-corner-all">
		<h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding:5px;">
			<span class="ui-dialog-title"><spring:message code="find.person.headers.results" /></span>
		</h4>
		<div style="padding: 0;">
			<div class="table-status" style="padding:0 5px 5px 5px;">Records found: ${fn:length(searchResults)}</div>
			<c:if test="${fn:length(searchResults) > 0}">
				<display:table name="searchResults" id="personMatch" htmlId="find_person_results_table" requestURI="" style="margin-left:-1px; margin-right:0; ">
					<display:setProperty name="basic.msg.empty_list" value="Your search returned no results." />
					<display:setProperty name="css.tr.even" value="even-rows" />
					<display:column>${personMatch_rowNum}.</display:column>
					<display:column title="Name" sortable="true"><a href="${flowExecutionUrl}&_eventId=display&searchId=${personMatch_rowNum-1}"> ${personMatch.person.preferredName}</a></display:column>
					<display:column title="ID">${personMatch.person.primaryIdentifiersByType[preferredPersonIdentifierType].value}</display:column>
					<display:column title="Roles" sortable="true">
						<c:forEach var="role" items="${personMatch.person.roles}">
							${role.displayableName}
						</c:forEach>
					</display:column>
					<display:column property="person.gender" title="Gender" />
				</display:table>
			</c:if>
		</div>
	</div>
</c:if>
