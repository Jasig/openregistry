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

<script type="text/javascript">
jQuery(document).ready(function() {
    $('#activationKeyLink').click(function() {
        $.post('<c:url value="/api/v1/people/${preferredPersonIdentifierType}/${person.primaryIdentifiersByType[preferredPersonIdentifierType].value}/activation" />', {}, function(data, textStatus, XMLHttpRequest) {
            var location = XMLHttpRequest.getResponseHeader('Location');
            var activationKey = location.substring(location.lastIndexOf("/")+1);

            $('#activationKeyDialog').html(activationKey);
            $('#activationKeyDialog').dialog({
                show: 'slide',
			    modal: true,
			    buttons: {
				    Ok: function() {
					    $(this).dialog('close');
				    }
			    }
		    });
        })
    })
});
</script>

<div id="calculated-person">
    <div style="text-align:center;"><a href="#" id="activationKeyLink" class="button"><button>Generate New Activation Key</button></a></div>
    <div id="activationKeyDialog" title="Activation Key"></div>

    <h2>Summary</h2>
    <c:set var="officialName" value="${person.officialName}"  />
    <c:set var="preferredName" value="${person.preferredName}" />
    <p><strong><spring:message code="officialName.heading" /></strong>: <c:if test="${not empty officialName.prefix}"><span title="Prefix">${officialName.prefix}</span></c:if>
    <c:if test="${not empty officialName.given}"><span title="Given">${officialName.given}</span></c:if>
    <c:if test="${not empty officialName.middle}"><span title="Middle">${officialName.middle}</span></c:if>
    <c:if test="${not empty officialName.family}"><span title="Family">${officialName.family}</span></c:if><c:if test="${not empty officialName.suffix}">, <span title="Suffix">${officialName.suffix}</span></c:if>
    </p>

    <!-- TODO what about other names? -->

    <p><strong>Preferred Name</strong>: <c:if test="${not empty preferredName.prefix}"><span title="Prefix">${preferredName.prefix}</span></c:if>
    <c:if test="${not empty preferredName.given}"><span title="Given">${preferredName.given}</span></c:if>
    <c:if test="${not empty preferredName.middle}"><span title="Middle">${preferredName.middle}</span></c:if>
    <c:if test="${not empty preferredName.family}"><span title="Family">${preferredName.family}</span></c:if><c:if test="${not empty preferredName.suffix}">, <span title="Suffix">${preferredName.suffix}</span></c:if>
    </p>

    <p><strong><spring:message code="dateOfBirth.label" />:</strong> <fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></p>
    <p><strong><spring:message code="gender.label" />:</strong> <spring:message code="${person.gender}.genderDisplayValue" /></p>
    <p><strong>Contact Email: </strong></p>
    <p><strong>Contact Phone: </strong></p>

    <h2>Identifiers</h2>
    
    <h2>Active Roles</h2>

    <h2>Expired Roles</h2>
    

    <table>
        <thead>
        <tr>
            <th colspan="2"><spring:message code="viewCalculatedPerson.heading" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <c:forEach var="name" items="${person.names}" varStatus="sorPersonLoopStatus">
            <tr>
                <td>${name.type.description} <spring:message code="nameColumn.label" /></td>
                <td>${name.longFormattedName}</td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
        </tr>
        <c:forEach var="identifier" items="${person.identifiers}" varStatus="sorPersonLoopStatus">
            <tr>
                <td>${identifier.type.name}</td>
                <td>${identifier.value}
                    <c:if test="${identifier.primary}"> (<spring:message code="primary.label" />)</c:if>
                    <c:if test="${identifier.deleted}"> (<spring:message code="deleted.label" />)</c:if>
                </td>
            </tr>
        </c:forEach>
        <c:forEach var="role" items="${person.roles}">
            <tr>
                <td><spring:message code="role.label" /> (<b>${role.personStatus.description}</b>)</td>
                <td>
                    <a href="${flowExecutionUrl}&_eventId=submitViewRole&roleCode=${role.code}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a>
                     <fmt:formatDate value="${role.start}" /> <spring:message code="vcpTo.label" /> <fmt:formatDate value="${role.end}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="center"><a href="${flowExecutionUrl}&_eventId=submitNewSearch"><button>New Search</button></a></div>
<%--<div class="row fm-v" style="clear:both;">--%>
    <%--<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11" />--%>
<%--</div>--%>

