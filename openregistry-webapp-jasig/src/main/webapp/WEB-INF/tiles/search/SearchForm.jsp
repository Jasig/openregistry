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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	jQuery(document).ready(function() {
		var cache = {};
		$('#name').autocomplete({
			source: function(request, response) {
				if (cache.term == request.term && cache.content) {
					response(cache.content);
				}

				if (new RegExp(cache.term).test(request.term) && cache.content && cache.content.length < 13) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(cache.content, function(value) {
						return matcher.test(value.value)
					}));
				}

				$.ajax({
					url: "<c:url value="/nameSearch.json" />",
					dataType: "json",
					data: request,
					success: function(data) {
						cache.term = request.term;
						cache.content = data;
						response(data);
					}
				});
			},
			minLength: 3
		});
	});

	$(function() {
		$("#submitButton").button();
	});
</script>
<spring:message code="date.placeholder" var="datePlaceHolder" />

<div id="search_form" style="margin-bottom: 5px;">
	<form:form commandName="${commandName}" method="post" id="orForm">
		<form:errors path="*" element="div" id="or-message" cssClass="or-error-message ui-state-error ui-corner-all" htmlEscape="false" />
		<fieldset id="searchFields">
			<legend><spring:message code="find.person.filter.legend" /></legend>
			<div class="or-fieldgroup group">
				<div class="or-field-container">
					<form:label path="identifierValue" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="person.biodem.identifier.label" /></form:label>
					<form:input path="identifierValue" cssClass="or-field-content" cssErrorClass="or-field-content-error" placeholder="id" />
				</div>
				<div class="or-field-container">
					<form:label path="name" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="person.biodem.names.label" /></form:label>
					<form:input path="name" cssClass="or-field-content" cssErrorClass="or-field-content-error" autocomplete="false" placeholder="first/last name"/>
					<form:errors path="name" />
				</div>
				<div class="or-field-container">
					<form:label path="dateOfBirth" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
					<form:input path="dateOfBirth" cssClass="or-field-content" cssErrorClass="or-field-content-error" placeholder="${datePlaceHolder}"/>
				</div>
				<div id="find_submit_container" class="clearfix">
					<input type="hidden" name="_eventId" value="submit" />
					<button id="submitButton"><spring:message code="find.person.button" /></button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>
