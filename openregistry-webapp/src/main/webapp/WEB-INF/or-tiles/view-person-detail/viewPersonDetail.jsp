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

<div id="calculated-person">

<div class="padded"><strong><spring:message code="officialName.heading" /></strong> ${person.officialName.longFormattedName}</div>

    <strong><spring:message code="viewCalculatedPerson.heading" /></strong>
    <table>
        <thead>
        <tr>
            <th><spring:message code="property.label" /></th>
            <th colspan="5"><spring:message code="value.label" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <c:forEach var="name" items="${person.names}" varStatus="sorPersonLoopStatus">
            <tr>
                <td><spring:message code="nameColumn.label" /> <b>(${name.type.description})</b></td>
                <td><spring:message code="vcpFirst.label" /> <b>${name.given}</b></td>
                <td><spring:message code="vcpLast.label" /> <b>${name.family}</b></td>
                <td><spring:message code="vcpMiddle.label" /> <b>${name.middle}</b></td>
                <td><spring:message code="vcpPrefix.label" /> <b>${name.prefix}</b></td>
                <td><spring:message code="vcpSuffix.label" /> <b>${name.suffix}</b></td>
            </tr>
        </c:forEach>
        <tr>
            <td><spring:message code="dateOfBirth.label" /></td>
            <td colspan="5"><fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></td>
        </tr>
        <tr>
            <td><spring:message code="gender.label" /></td>
            <td colspan="5"><spring:message code="${person.gender}.genderDisplayValue" /></td>
        </tr>
        <c:forEach var="identifier" items="${person.identifiers}" varStatus="sorPersonLoopStatus">
            <tr>
                <td><spring:message code="identifier.heading" /><b>${identifier.type.name}</b></td>
                <td colspan="5">${identifier.value}
                    <c:if test="${identifier.primary}"> <b>(<spring:message code="primary.label" />)</b> </c:if>
                    <c:if test="${identifier.deleted}"> <b>(<spring:message code="deleted.label" />)</b> </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:forEach var="role" items="${person.roles}">
            <tr>
                <td><spring:message code="role.label" /> (<b>${role.personStatus.description}</b>)</td>
                <td colspan="3">
                    <a href="${flowExecutionUrl}&_eventId=submitViewRole&roleCode=${role.code}&formattedName=${person.officialName.formattedName}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a>
                </td>
                <td colspan="2"><fmt:formatDate value="${role.start}" /> <spring:message code="vcpTo.label" />
                    <fmt:formatDate value="${role.end}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:forEach var="sorPerson" items="${sorPersons}" varStatus="sorPersonLoopStatus">
    <div class="sor-person">
    <strong><spring:message code="source.heading" /></strong> <c:out value="${sorPerson.sourceSor}" />
    <table>
        <thead>
        <tr>
            <th><spring:message code="property.label" /></th>
            <th colspan="5"><spring:message code="value.label" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <tr>
            <td><spring:message code="sorId.label" /></td>
            <td colspan="5">${sorPerson.sorId}</td>
        </tr>
        <c:forEach var="sorName" items="${sorPerson.names}" varStatus="nameLoopStatus">
            <tr>
                <td><spring:message code="nameColumn.label" /> <b>(${sorName.type.description})</b></td>
                <td><spring:message code="vcpFirst.label" /> <b>${sorName.given}</b></td>
                <td><spring:message code="vcpLast.label" /> <b>${sorName.family}</b></td>
                <td><spring:message code="vcpMiddle.label" /> <b>${sorName.middle}</b></td>
                <td><spring:message code="vcpPrefix.label" /> <b>${sorName.prefix}</b></td>
                <td><spring:message code="vcpSuffix.label" /> <b>${sorName.suffix}</b></td>
            </tr>
        </c:forEach>
        <tr>
            <td><spring:message code="dateOfBirth.label" /></td>
            <td colspan="5"><fmt:formatDate value="${sorPerson.dateOfBirth}" /></td>
        </tr>
        <tr>
            <td><spring:message code="gender.label" /></td>
            <td colspan="5"><spring:message code="${sorPerson.gender}.genderDisplayValue" /></td>

        </tr>
        <tr>
            <td><spring:message code="ssn.label" /></td>
            <td colspan="5">${sorPerson.ssn}</td>
        </tr>
        <c:forEach var="role" items="${sorPerson.roles}">
            <tr>
                <td><spring:message code="role.label" /> <b>(${role.personStatus.description})</b></td>
                <td colspan="3">
                    <a href="${flowExecutionUrl}&_eventId=submitViewSoRRole&sorSource=${sorPerson.sourceSor}&roleCode=${role.code}&formattedName=${sorPerson.formattedName}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a>
                </td>
                <td colspan="2"><fmt:formatDate value="${role.start}" /> <spring:message code="vcpTo.label" />
                    <fmt:formatDate value="${role.end}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--pass the index of the iteration so that we can grab the correct sorPerson when passing to the update sorPerson flow--%>
    <div><a href="${flowExecutionUrl}&_eventId=updateSorPerson&sorPersonIndex=${sorPersonLoopStatus.index}"><button>Edit</button></a></div>
</c:forEach>
</div>

<div class="center"><a href="${flowExecutionUrl}&_eventId=submitNewSearch"><button>New Search</button></a></div>
<%--<div class="row fm-v" style="clear:both;">--%>
    <%--<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11" />--%>
<%--</div>--%>

