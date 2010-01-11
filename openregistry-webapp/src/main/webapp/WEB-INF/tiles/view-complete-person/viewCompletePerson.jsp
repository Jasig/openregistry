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

<form:form modelAttribute="person">
    <fieldset id="selectSorPerson">
        <legend><span><spring:message code="viewCompletePersonPage.heading"/></span></legend>
        <br/>

    <fieldset class="fm-h" id="ecn1">
        <label class="desc">${person.officialName.formattedName}</label>
        <div>
            <label class="desc"><spring:message code="viewCalculatedPerson.heading"/></label>
            <table class="data" cellspacing="0" width="80%">
                <thead>
                    <tr class="appHeadingRow">
                        <th><spring:message code="property.label"/></th>
                        <th><spring:message code="value.label"/></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="name" items="${person.names}" varStatus="loopStatus">
                    <tr>
                        <td><spring:message code="nameColumn.label"/> (${name.type.description})</td>
                        <td>${name.longFormattedName}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><spring:message code="dateOfBirth.label"/></td>
                    <td><fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></td>
                </tr>
                <tr>
                    <td><spring:message code="gender.label" /></td>
                    <td>${person.gender}</td>
                </tr>
                <c:forEach var="identifier" items="${person.identifiers}" varStatus="loopStatus">
                    <tr>
                        <c:choose>
                            <c:when test="${identifier.isPrimary}">
                                <td><spring:message code="identifier.heading" />${identifier.type.name} (<spring:message code="primary.label" />)</td>
                            </c:when>
                            <c:when test="${identifier.isDeleted}">
                                <td><spring:message code="identifier.heading" />${identifier.type.name} (<spring:message code="deleted.label" />)</td>
                            </c:when>
                            <c:otherwise><td><spring:message code="identifier.heading" />${identifier.type.name}</td></c:otherwise>
                        </c:choose>
                        <td>${identifier.value}</td>
                    </tr>
                </c:forEach>
                <c:forEach var="role" items="${person.roles}" >
                    <tr>
                        <td><spring:message code="role.label" /> (${role.personStatus.description}) <fmt:formatDate value="${role.start}" dateStyle="long" />-<fmt:formatDate value="${role.end}" dateStyle="long" /></td>
                        <td><a href="${flowExecutionUrl}&_eventId=submitViewRole&roleCode=${role.code}&formattedName=${person.officialName.formattedName}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a></td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
        </div>        
        <c:forEach var="sorPerson" items="${sorPersons}" varStatus="loopStatus">
            <label class="desc"><spring:message code="source.heading"/> <c:out value="${sorPerson.sourceSor}" /></label>
                <div>
                    <table class="data" cellspacing="0" width="80%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="property.label"/></th>
                                <th><spring:message code="value.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><spring:message code="sorId.label" /></td>
                            <td>${sorPerson.sorId}</td>
                        </tr>
                        <c:forEach var="sorName" items="${sorPerson.names}" varStatus="loopStatus">
                            <tr>
                                <td><spring:message code="nameColumn.label"/> (${sorName.type.description})</td>
                                <td>${sorName.longFormattedName}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td><spring:message code="ssn.label" /></td>
                            <td>${sorPerson.ssn}</td>
                        </tr>
                        <tr>
                            <td><spring:message code="dateOfBirth.label"/></td>
                            <td><fmt:formatDate value="${sorPerson.dateOfBirth}" dateStyle="long"/></td>
                        </tr>
                        <tr>
                            <td><spring:message code="gender.label" /></td>
                            <td>${sorPerson.gender}</td>
                        </tr>
                        <c:forEach var="role" items="${sorPerson.roles}" >
                            <tr>
                                <td><spring:message code="role.label" /> (${role.personStatus.description}) <fmt:formatDate value="${role.start}" dateStyle="long" />-<fmt:formatDate value="${role.end}" dateStyle="long" /></td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitViewSoRRole&sorSource=${sorPerson.sourceSor}&roleCode=${role.code}&formattedName=${sorPerson.formattedName}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a></td>
                            </tr>
                         </c:forEach>
                        </tbody>
                    </table>
        </c:forEach>
        </div>

		</fieldset>
		</fieldset>

		<div class="row fm-v" style="clear:both;">
			<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11"/>
		</div>

</form:form>