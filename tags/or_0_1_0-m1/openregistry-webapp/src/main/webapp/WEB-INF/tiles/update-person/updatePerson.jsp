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
<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form modelAttribute="sorPerson">
			<fieldset id="updateperson">
				<legend><span><spring:message code="updatePersonPage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">
                    <label class="desc"><spring:message code="name.heading"/><input id="addNameBtn" type="image" name="_eventId_submitAddName" src="images/add2.gif" title="add a name"/></label>

                    <div>
                        <table class="data" cellspacing="0" width="80%">
                            <thead>
                                <tr class="appHeadingRow">
                                    <th><spring:message code="prefix.label"/></th>
                                    <th><spring:message code="firstName.label"/></th>
                                    <th><spring:message code="middleName.label" /></th>
                                    <th><spring:message code="lastName.label" /></th>
                                    <th><spring:message code="suffix.label" /></th>
                                    <th><spring:message code="type.label" /></th>
                                    <th><spring:message code="action.label" /></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="sorName" items="${sorPerson.names}" varStatus="loopStatus">
                            <tr>
                                <td><form:select path="names[${loopStatus.index}].prefix">
                                        <form:option value="Empty" label=""/>
							            <form:option value="Mrs" label="Mrs."/>
                                        <form:option value="Miss" label="Miss"/>
                                        <form:option value="Ms" label="Ms."/>
                                        <form:option value="Mr" label="Mr."/>
                                        <form:option value="Dr" label="Dr."/>
                                    </form:select>
                                </td>
                                <td><form:input path="names[${loopStatus.index}].given" /></</td>
                                <td><form:input path="names[${loopStatus.index}].middle" /></td>
                                <td><form:input path="names[${loopStatus.index}].family" /></td>
                                <td><form:input path="names[${loopStatus.index}].suffix" /></td>
                                <td> </td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemoveName&nameId=${sorName.id}"><img src="images/trash.jpg" title="remove name"/></a></td>   
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>

                <label class="desc"><spring:message code="roles.heading"/><input id="addRoleBtn" type="image" name="_eventId_submitAddRole" src="images/add2.gif" title="add a role"/></label>
                <div>
                    <table class="data" cellspacing="0" width="60%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="titleOrg.label"/></th>
                                <th><spring:message code="campus.label"/></th>
                                <th><spring:message code="startDate.label"/></th>
                                <th><spring:message code="endDate.label"/></th>
                                <th><spring:message code="actions.label"/></th>
                            </tr>
                            </thead>
                            <tbody>
                             <c:forEach var="role" items="${sorPerson.roles}" >
                            <tr>
                                <td><a href="${flowExecutionUrl}&_eventId=submitUpdateRole&roleId=${role.id}">${role.title}/${role.organizationalUnit.name}</a></td>
                                <td>${role.campus.name}</td>
                                <td><fmt:formatDate pattern="MM/dd/yyyy" value="${role.start}"/></td>
                                <td><fmt:formatDate pattern="MM/dd/yyyy" value="${role.end}"/></td>
                                <td>
                                    <a href="${flowExecutionUrl}&_eventId=submitUpdateRole&roleId=${role.id}"><spring:message code="edit.label"/>
                                </td>
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>

            <label class="desc"><spring:message code="biodem.heading"/></label>
            <div>
                <table class="data" cellspacing="0" width="50%">
                    <thead>
                        <tr class="appHeadingRow">
                            <th><spring:message code="type.label"/></th>
                            <th><spring:message code="value.label"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><spring:message code="dateOfBirth.label"/><em>*</em></td>
                            <td><form:input path="dateOfBirth" size="12" maxlength="10"/></td>
                        </tr>
                        <tr>
                            <td><spring:message code="gender.label" /> <em>*</em></td>
                            <td><form:select path="gender">
                                    <form:option value="" label=""/>
                                    <form:option value="F" label="Female"/>
						            <form:option value="M" label="Male"/>
					             </form:select></td>
                        </tr>

                    </tbody>
                </table>
            </div>
                    <label class="desc"><spring:message code="identifiers.heading"/></label>
					<div>
                        <table class="data" cellspacing="0" width="50%">
                            <thead>
                                <tr class="appHeadingRow">
                                    <th><spring:message code="type.label"/></th>
                                    <th><spring:message code="value.label"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><spring:message code="ssn.label"/></td>
                                <td><form:input path="ssn" size="11" maxlength="9" /></td>
                            </tr>
                                <c:forEach var="identifier" items="${identifiers}">
                                    <tr>
                                        <td>${identifier.type.name}</td>
                                        <td>${identifier.value}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                </div>

			</fieldset>
			</fieldset>

			<div class="row fm-v" style="clear:both;">
				<input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdatePerson" class="btn-submit" value="Update Person" tabindex="11"/>
			</div>


		</form:form>