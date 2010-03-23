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

<div id="view-role">
    <h3><spring:message code="viewRolePage.heading"/></h3>

    <div class="padded"><strong><spring:message code="person.label" /></strong> <c:out value="${formattedName}" /></div>

    <div class="padded"> <strong><c:out value="${viewRoleTitle}" />: </strong><c:out value="${role.title}" />/<c:out value="${role.organizationalUnit.name}" />/<c:out value="${role.campus.name}" /></div>

    <div class="box">
    <h5><spring:message code="roleInformation.heading" /></h5>
    <table>
        <thead>
        <tr class="appHeadingRow">
            <th><spring:message code="personStatus.label" /></th>
            <th><spring:message code="startDate.label" /></th>
            <th><spring:message code="endDate.label" /></th>
            <th><spring:message code="sponsor.label" /></th>
            <th><spring:message code="pt.label" /></th>
            <th><spring:message code="roleTitle.label" /></th>
            <th><spring:message code="affiliation.label" /></th>
            <th><spring:message code="organizationalUnit.label" /></th>
            <th><spring:message code="campus.label" /></th>
            <th><spring:message code="terminationReason.label" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <td>${role.personStatus.description}</td>
        <td><fmt:formatDate value="${role.start}" dateStyle="short" /></td>
        <td><fmt:formatDate value="${role.end}" dateStyle="short" /></td>
        <td>${sponsorPerson.officialName.formattedName}</td>
        <td>${role.percentage}</td>
        <td>${role.title}</td>
        <td>${role.affiliationType.description}</td>
        <td>${role.organizationalUnit.name}</td>
        <td>${role.campus.name}</td>
        <td>${role.terminationReason.description}</td>
        </tbody>
    </table>
</div>
<div class="box">

    <h5><spring:message code="emailAddress.heading" /></h5>

    <table width="50%">
        <thead>
        <tr class="appHeadingRow">
            <th><spring:message code="type.label" /></th>
            <th><spring:message code="value.label" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <c:forEach var="emailAddress" items="${role.emailAddresses}" varStatus="sorPersonLoopStatus">
        <tr>
            <td>${emailAddress.addressType.description}</td>
            <td><a href="mailto:'${emailAddress.address}'">${emailAddress.address}</a></td>
            </c:forEach>
        </tbody>
    </table>
</div>

<div class="box">
    <h5><spring:message code="phones.heading" /></h5>

    <table width="50%">
        <thead>
        <tr class="appHeadingRow">
            <th><spring:message code="addressType.label" /></th>
            <th><spring:message code="phoneType.label" /></th>
            <th><spring:message code="number.label" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <c:forEach var="phone" items="${role.phones}" varStatus="sorPersonLoopStatus">
            <tr>
                <td>${phone.addressType.description}</td>
                <td>${phone.phoneType.description}</td>
                <td>${phone}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="box">

    <h5><spring:message code="address.heading" /></h5>
    <c:choose>
        <c:when test="${not empty role.addresses}">
            <table width="50%">
                <thead>
                <tr class="appHeadingRow">
                    <th><spring:message code="type.label" /></th>
                    <th><spring:message code="value.label" /></th>
                </tr>
                </thead>
                <tbody class="zebra">
                <c:forEach var="address" items="${role.addresses}" varStatus="sorPersonLoopStatus">
                    <tr>
                        <td>${address.type.description}</td>
                        <td>${address.singleLineAddress}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise><spring:message code="noAddressesDefined.label" /><br /><br /></c:otherwise>
    </c:choose>
</div>

<div class="box">

    <h5><spring:message code="urls.heading" /></h5>
    <c:choose>
        <c:when test="${not empty role.urls}">

            <table width="50%">
                <thead>
                <tr class="appHeadingRow">
                    <th><spring:message code="type.label" /></th>
                    <th><spring:message code="value.label" /></th>
                </tr>
                </thead>
                <tbody class="zebra">
                <c:forEach var="url" items="${role.urls}" varStatus="sorPersonLoopStatus">
                    <tr>
                        <td>${url.type.description}</td>
                        <td>${url.url}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:when>
        <c:otherwise><spring:message code="noUrlsDefined.label" /></c:otherwise>
    </c:choose>
</div>

<div class="box">

    <h5><spring:message code="leaves.heading" /></h5>
    <c:choose>
        <c:when test="${not empty role.leaves}">
            <table width="50%">
                <thead>
                <tr class="appHeadingRow">
                    <th><spring:message code="property.label" /></th>
                    <th><spring:message code="value.label" /></th>
                </tr>
                </thead>
                <tbody class="zebra">
                <c:forEach var="leave" items="${role.leaves}" varStatus="sorPersonLoopStatus">
                    <tr>
                        <td><spring:message code="startDate.label" /></td>
                        <td><fmt:formatDate value="${leave.start}" dateStyle="long" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="endDate.label" /></td>
                        <td><fmt:formatDate value="${leave.end}" dateStyle="long" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="reason.label" /></td>
                        <td>${leave.reason.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise><spring:message code="noLeavesDefined.label" /></c:otherwise>
    </c:choose>
</div>

</div>

<div><a href="${flowExecutionUrl}&_eventId=submitBack">
    <button>Back</button>
</a></div>

