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
<form:form modelAttribute="sorPerson">
			<fieldset id="selectRole">
				<legend><span><spring:message code="selectRoleToAddPage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">

					<div class="row">
						<label for="c1_affiliation" class="affiliation"><spring:message code="role.label"/><em>*</em></label>
                        <div class="select affiliation">
                            <SELECT name="roleAffiliation">
                                <c:forEach var="affiliationItem" items="${affiliationList}">
                                    <OPTION value="${affiliationItem}">${affiliationItem.description}</OPTION>
                                </c:forEach>
                            </SELECT>
                        </div>
                    </div>
			</fieldset>
			</fieldset>
			<div class="row fm-v" style="clear:both;">
				<input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitSelectRole" class="btn-submit" value="Add Role" tabindex="3"/>
			</div>
        <fieldset class="fm-h" id="ecn1">
            <p style="margin-bottom:0;">${sorPerson.formattedName} <spring:message code="existingRoles.heading" /></p>
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
                            <td><a href="${flowExecutionUrl}&_eventId=submitUpdateRole&roleId=${role.id}">${role.affiliationType.description}/${role.title}/${role.organizationalUnit.name}</a></td>
                            <td>${role.campus.name}</td>
                            <td><fmt:formatDate pattern="MM/dd/yyyy" value="${role.start}"/></td>
                            <td><fmt:formatDate pattern="MM/dd/yyyy" value="${role.end}"/></td>
                        </tr>
                        </c:forEach>
                </tbody>
            </table>
        </div>
        </c:when>
            <c:otherwise><spring:message code="noRolesDefined.label"/><br/><br/></c:otherwise>
        </c:choose>
            
        </fieldset>

		</form:form>