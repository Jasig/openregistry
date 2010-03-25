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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    });

    $('.accordion').accordion({collapsible: true});
    $('#content span[title]').qtip({content: {text: false}, position: {
	            corner: {
		            target: 'topMiddle',
		            tooltip: 'bottomLeft'
	            },
	            adjust: {
		            screen: true
	            }
            },
            style: {tip: 'bottomLeft', name: 'dark'}});

    <c:forEach items="${person.identifiersByType}" var="entry">
        $('#${entry.key}-content').hide();
        $('#${entry.key}').qtip({content: $('#${entry.key}-content'),
            position: {
	            corner: {
		            target: 'topMiddle',
		            tooltip: 'bottomLeft'
	            },
	            adjust: {
		            screen: true
	            }
            },
            style: {tip: 'bottomLeft', name: 'dark'}});
    </c:forEach>
});
</script>

<div id="calculated-person">

    <c:set var="officialName" value="${person.officialName}"  />
    <c:set var="preferredName" value="${person.preferredName}" />
        <h2><strong><c:if test="${not empty officialName.prefix}"><span title="Prefix">${officialName.prefix}</span></c:if>
    <c:if test="${not empty officialName.given}"><span title="Given">${officialName.given}</span></c:if>
    <c:if test="${not empty officialName.middle}"><span title="Middle">${officialName.middle}</span></c:if>
    <c:if test="${not empty officialName.family}"><span title="Family">${officialName.family}</span></c:if><c:if test="${not empty officialName.suffix}">, <span title="Suffix">${officialName.suffix}</span></c:if></strong></h2>
    <div align="left" style="text-transform: lowercase;">official name</div>


    <!-- TODO what about other names? -->
    <c:if test="${officialName ne preferredName}">
    <p><strong>Preferred Name</strong>: <c:if test="${not empty preferredName.prefix}"><span title="Prefix">${preferredName.prefix}</span></c:if>
    <c:if test="${not empty preferredName.given}"><span title="Given">${preferredName.given}</span></c:if>
    <c:if test="${not empty preferredName.middle}"><span title="Middle">${preferredName.middle}</span></c:if>
    <c:if test="${not empty preferredName.family}"><span title="Family">${preferredName.family}</span></c:if><c:if test="${not empty preferredName.suffix}">, <span title="Suffix">${preferredName.suffix}</span></c:if>
    </p>
    </c:if>

    <p><strong><spring:message code="dateOfBirth.label" />:</strong> <fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></p>
    <p><strong><spring:message code="gender.label" />:</strong> <spring:message code="${person.gender}.genderDisplayValue" /></p>
    <p><strong>Contact Email: </strong></p>
    <p><strong>Contact Phone: </strong></p>

    <div style="text-align: right;"><a href="#" id="activationKeyLink" class="button"><button>Generate New Activation Key</button></a></div>
    <div id="activationKeyDialog" title="Activation Key"></div>



    <h2>Identifiers</h2>
    <c:forEach var="identifierEntry" items="${person.identifiersByType}">
        <c:forEach var="identifier" items="${identifierEntry.value}" varStatus="status">
            <c:choose>
                <c:when test="${status.first}">
                <a class="tooltip" id="${identifier.type.name}">${identifier.type.name}: ${identifier.value}</a>
                <div id="${identifier.type.name}-content">
                    <p><strong>Creation Date: </strong> <fmt:formatDate value="${identifier.creationDate}" dateStyle="long" /></p>

                    <p><strong>Other Values:</strong></p>
                    <ul>
                    <c:if test="${fn:length(identifierEntry.value) eq 1}">
                        <li>None</li>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <li>${identifier.value}</li>
                </c:otherwise>
            </c:choose>
            <c:if test="${status.last}">
                    </ul>
                </div>
            </c:if>
        </c:forEach>
    </c:forEach>


    <h2>Roles</h2>
    <div class="accordion">
    <c:forEach var="role" items="${person.roles}">
        <h3><a href="#" class="${role.active ? 'active' : role.terminated ? 'terminated' : 'NotYetActive'}">${role.title} - <fmt:formatDate value="${role.start}" dateStyle="long" /> - <c:choose>
            <c:when test="${not empty role.end}">
                <fmt:formatDate value="${role.end}" dateStyle="long" />
            </c:when>
            <c:otherwise>
                Present
            </c:otherwise>
        </c:choose> </a></h3>
        <div>
            Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
            Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
            Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
        </div>
    </c:forEach>
    </div>


</div>

<div class="center"><a href="${flowExecutionUrl}&_eventId=submitNewSearch"><button>New Search</button></a></div>
<%--<div class="row fm-v" style="clear:both;">--%>
    <%--<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11" />--%>
<%--</div>--%>

