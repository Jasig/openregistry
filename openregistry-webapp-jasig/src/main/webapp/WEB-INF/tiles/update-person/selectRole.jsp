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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	$(function() {
		$("#fm-search-submit1").button();
	});
</script>

<form:form modelAttribute="sorPerson">
	<div class="ui-widget ui-widget-content ui-corner-all">
		<h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding: 5px; margin-bottom:0;">
			<span class="or-dialog-title"><spring:message code="selectRole.title" /></span>
		</h4>
		<div style="padding:5px;">
			<div class="or-banner">
					${sorPerson.formattedName}
			</div>

			<fieldset>
				<legend>Select a Role</legend>
				<div class="or-fieldgroup group" style="margin-bottom: 5px;">
					<div class="or-field-container" style="min-width: 250px;">
						<label for="c1_affiliation" class="or-field-label-above"><spring:message code="role.label"/></label>
						<select name="roleInfoCode" id="c1_affiliation" class="or-field-content">
							<c:forEach var="roleInfoItem" items="${roleInfos}">
								<option value="${roleInfoItem.code}">${roleInfoItem.displayableName}</option>
							</c:forEach>
						</select>
					</div>

					<input type="hidden" name="_eventId" value="submitSelectRole"/>
					<button id="fm-search-submit1"><spring:message code="addRole.button" /></button>
				</div>
			</fieldset>
			<div style="margin-bottom:5px;"></div>
			<fieldset>
				<legend>Current Roles</legend>
				<p style="margin-bottom:0;">${sorPerson.formattedName} <spring:message code="existingRoles.heading"/></p>
				<c:choose>
					<c:when test="${not empty sorPerson.roles}">
						<div>
							<table class="data" cellspacing="0" width="60%">
								<thead>
								<tr class="appHeadingRow">
									<th><spring:message code="titleOrg.label"/></th>
									<th><spring:message code="campus.label"/></th>
									<th><spring:message code="startDate.label"/></th>
									<th><spring:message code="endDate.label"/></th>
								</tr>
								</thead>
								<tbody>
								<c:forEach var="role" items="${sorPerson.roles}">
									<tr>
										<td>
											<a href="${flowExecutionUrl}&_eventId=submitUpdateRole&roleId=${role.id}">${role.title}/${role.organizationalUnit.name}</a>
										</td>
										<td>${role.campus.name}</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${role.start}"/></td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${role.end}"/></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
					<c:otherwise><spring:message code="noRolesDefined.label"/><br/><br/></c:otherwise>
				</c:choose>
			</fieldset>
		</div>
	</div>
</form:form>
<div class="or-form-name">Form: selectRole</div>
