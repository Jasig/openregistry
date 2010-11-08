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

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form modelAttribute="reconciliationResult" >
	<fieldset id="update">
		<legend><span><spring:message code="personMatches.heading"/><span style="color:#000;"><c:out value="${personToAdd.names[0].family}" />, <c:out value="${personToAdd.names[0].given}" /></span></span></legend>
		<div>
			<table>
				<tr>
					<th><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="nameColumn.label"/></span></th>
					<th><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="genderColumn.label"/></span></th>
				</tr>
				<c:forEach var="personMatch" items="${reconciliationResult.matches}">
					<tr>
						<td valign="top"><a href="${flowExecutionUrl}&_eventId=submitAddRole&personId=${personMatch.person.id}">${personMatch.person.officialName.family},${personMatch.person.officialName.given}</a></td>
						<td valign="top"><c:out value="${personMatch.person.gender}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="row">
			<label class="desc2" for="c1_startdate"><span style="color:#000; font-size:1.2em;"><spring:message code="personMatches.addAnyway"/></span></label>
		</div>
	</fieldset>
	<br/>
	<div class="row fm-v" style="clear:both;">
		<input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitAddPerson" class="btn-submit" value="Add Person" tabindex="11"/>
		<input style="float:left;" type="submit" id="fm-search-cancel"  name="_eventId_cancelAddPerson" class="btn-cancel" value="Cancel" tabindex="11"/>
	</div>
</form:form>
