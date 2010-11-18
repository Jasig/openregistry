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

<div class="ui-widget ui-widget-content ui-corner-all">
	<h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding: 5px;">
		<span class="or-dialog-title"><spring:message code="person.detail.title" /></span>
	</h4>
	<div style="padding:5px;">
		<c:set var="officialName" value="${person.officialName}"  />
		<c:set var="preferredName" value="${person.preferredName}" />
		<div class="group" style="margin-bottom:5px;">
			<div class="or-field-container" style="width:307px;">
				<div class="or-field-label-above"><spring:message code="officialName.heading" /></div>
				<div class="or-field-content">
					<c:if test="${not empty officialName.prefix}"><span title="Prefix">${officialName.prefix}</span></c:if>
					<c:if test="${not empty officialName.given}"><span title="Given">${officialName.given}</span></c:if>
					<c:if test="${not empty officialName.middle}"><span title="Middle">${officialName.middle}</span></c:if>
					<c:if test="${not empty officialName.family}"><span title="Family">${officialName.family}</span></c:if>
					<c:if test="${not empty officialName.suffix}">, <span title="Suffix">${officialName.suffix}</span></c:if>
				</div>
			</div>

			<!-- TODO what about other names? -->
			<c:if test="${officialName ne preferredName}">
				<div class="or-field-label-above" style="text-transform: lowercase;"><spring:message code="preferedName.heading" /></div>
				<div class="or-field-content or-section-title1">
					<c:if test="${not empty preferredName.prefix}"><span title="Prefix">${preferredName.prefix}</span></c:if>
					<c:if test="${not empty preferredName.given}"><span title="Given">${preferredName.given}</span></c:if>
					<c:if test="${not empty preferredName.middle}"><span title="Middle">${preferredName.middle}</span></c:if>
					<c:if test="${not empty preferredName.family}"><span title="Family">${preferredName.family}</span></c:if>
					<c:if test="${not empty preferredName.suffix}">, <span title="Suffix">${preferredName.suffix}</span></c:if>
				</div>
			</c:if>

			<div class="or-field-container" style="width: 150px;">
				<div class="or-field-label-above"><spring:message code="dateOfBirth.label" /></div>
				<div class="or-field-content"><fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></div>
			</div>
			<div class="or-field-container" style="width: 150px;">
				<div class="or-field-label-above"><spring:message code="gender.label" /></div>
				<div class="or-field-content"><spring:message code="${person.gender}.genderDisplayValue" /></div>
			</div>
		</div>

		<div class="group" style="margin-bottom:5px;">
			<div class="or-field-container" style="width: 150px;">
				<div class="or-field-label-above"><spring:message code="contact.email.label" /></div>
				<div class="or-field-content">&nbsp;</div>
			</div>
			<div class="or-field-container" style="width: 150px;">
				<div class="or-field-label-above"><spring:message code="contact.phone.label" /></div>
				<div class="or-field-content">&nbsp;</div>
			</div>
		</div>

		<div class="group">
			<h4 style="margin-top: 5px;"><spring:message code="identifiers.heading" /></h4>
			<c:forEach var="identifierEntry" items="${person.identifiersByType}">
				<div class="group" style="margin-bottom:5px;">
					<c:forEach var="identifier" items="${identifierEntry.value}" varStatus="status">
						<c:choose>
							<c:when test="${status.first}">
								<div class="or-field-container" style="width: 150px;">
									<div class="or-field-label-above"><a class="tooltip" id="${identifier.type.name}">${identifier.type.name}</a></div><div class="or-field-content">${identifier.value}</div>
								</div>
								<div id="${identifier.type.name}-content" class="or-field-container" style="width: 150px;">
									<div class="or-field-label-above"><spring:message code="creation.date.label" /></div>
									<div class="or-field-content"><fmt:formatDate value="${identifier.creationDate}" dateStyle="long" /></div>
								</div>
								<div class="or-field-container" style="width: 150px;">
								<div class="or-field-label-above"><spring:message code="other.values.label" /></div>
								<div class="or-field-content">
								<ul style="list-style:none;">
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
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</div>

		<h4 style="margin-top: 5px;"><spring:message code="roles.heading" /></h4>
		<div class="accordion" style="margin-bottom: 6px;">
			<c:forEach var="role" items="${person.roles}">
				<h3 style="margin-bottom: 0;">
					<a href="#" class="${role.active ? 'active' : role.terminated ? 'terminated' : 'NotYetActive'}">${role.title} - <fmt:formatDate value="${role.start}" dateStyle="long" /> -
						<c:choose>
							<c:when test="${not empty role.end}">
								<fmt:formatDate value="${role.end}" dateStyle="long" />
							</c:when>
							<c:otherwise>
								Present
							</c:otherwise>
						</c:choose>
					</a>
				</h3>
				<div>
					Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
					Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
					Some content about a role goes here.  It should be a lot so that we can justify using the accordion model.
				</div>
			</c:forEach>
		</div>

		<%--
	  <div><a href="#" id="activationKeyLink" class="button"><button>Generate New Activation Key</button></a></div>
	  <div id="activationKeyDialog" title="Activation Key"></div>
  --%>
	</div>
</div>

<%--<div class="center"><a href="${flowExecutionUrl}&_eventId=submitNewSearch"><button>New Search</button></a></div>--%>
<%--<div class="row fm-v" style="clear:both;">--%>
<%--	<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11" />--%>
<%--</div>--%>
<div class="or-form-name">Form: viewPersonDetail</div>
