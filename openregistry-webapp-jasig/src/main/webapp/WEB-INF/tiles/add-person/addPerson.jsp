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

<script type="text/javascript">
	$(function() {
		$("#addPersonButton").button();
	});
</script>

<style type="text/css">
	.field-width-1 { width: 90px; }
	.field-width-2 { width: 150px; }
	.dropdown-menu-width { width: 80px; }
</style>
<spring:message code="date.placeholder" var="datePlaceHolder" />
<spring:message code="phoneNumber.placeholder" var="phoneNumberPlaceHolder" />

<form:form modelAttribute="reconciliationCriteria" commandName="reconciliationCriteria" method="post">
	<div class="ui-widget ui-widget-content ui-corner-all">
		<h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding: 5px; margin-bottom:0;">
			<span class="ui-dialog-title"><spring:message code="addPersonPage.heading" /></span>
		</h4>
		<div style="padding: 5px;">
			<p class="or-instructions">
				<spring:message code="requiredFields.heading" /> <span class="or-required-field-marker">*</span>.
			</p>

			<form:errors path="*" element="div" id="or-message" cssClass="or-error-message ui-state-error ui-corner-all" htmlEscape="false"/>
			<div class="field_set group" style="margin-bottom: 5px;">
				<div class="or-field-container field-width-1">
					<form:label cssClass="or-field-label-above" path="sorPerson.names[0].prefix" cssErrorClass="or-field-label-error-above">
						<spring:message code="prefix.label" />
					</form:label>
					<form:select path="sorPerson.names[0].prefix" size="1" tabindex="1" cssClass="or-field-content dropdown-menu-width">
						<form:option value="" label="" />
						<form:option value="Mrs" label="Mrs." />
						<form:option value="Miss" label="Miss" />
						<form:option value="Ms" label="Ms." />
						<form:option value="Mr" label="Mr." />
						<form:option value="Dr" label="Dr." />
					</form:select>
				</div>
				<div class="or-field-container field-width-2">
					<form:label  path="sorPerson.names[0].given" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="firstName.label" /><span class="or-required-field-marker">*</span></form:label>
					<form:input path="sorPerson.names[0].given" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="30" tabindex="2"/>
				</div>
				<div class="or-field-container field-width-2">
					<form:label  path="sorPerson.names[0].middle" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="middleName.label" /></form:label>
					<form:input path="sorPerson.names[0].middle" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="30" tabindex="3"/>
				</div>
				<div class="or-field-container field-width-2">
					<form:label  path="sorPerson.names[0].family" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="lastName.label" /><span class="or-required-field-marker">*</span></form:label>
					<form:input path="sorPerson.names[0].family" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="30" tabindex="4"/>
				</div>
				<div class="or-field-container field-width-2">
					<form:label path="sorPerson.names[0].suffix" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="suffix.label" /></form:label>
					<form:input path="sorPerson.names[0].suffix" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="30" tabindex="5"/>
				</div>
			</div>
			<div class="field_set group" style="margin-bottom: 5px;">
				<div class="or-field-container field-width-1">
					<form:label path="sorPerson.gender" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="gender.label" />
						<span class="or-required-field-marker">*</span></form:label>
					<form:select path="sorPerson.gender" cssClass="or-field-content dropdown-menu-width" size="1" tabindex="6">
						<form:option value="" label="" />
						<form:option value="F" label="Female" />
						<form:option value="M" label="Male" />
					</form:select>
				</div>

				<div class="or-field-container field-width-2">
					<form:label path="sorPerson.dateOfBirth" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="dateOfBirth.label" /><span class="or-required-field-marker">*</span></form:label>
					<form:input path="sorPerson.dateOfBirth" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="10" maxlength="10" tabindex="7" placeholder="${datePlaceHolder}"/>
				</div>

				<div class="or-field-container field-width-2">
					<form:label path="sorPerson.ssn" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="ssn.label" /></form:label>
					<form:input path="sorPerson.ssn" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="9" maxlength="9" tabindex="8"/>
				</div>

				<div class="or-field-container field-width-2">
					<form:label path="emailAddress" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="email.label" /></form:label>
					<form:input path="emailAddress" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="20" tabindex="9"/>
				</div>

				<div class="or-field-container field-width-2">
					<form:label path="phoneNumber" cssClass="or-field-label-above" cssErrorClass="or-field-label-error-above"><spring:message code="phoneNumber.label" /></form:label>
					<form:input path="phoneNumber" cssClass="or-field-content" cssErrorClass="or-field-content-error" size="15" maxlength="15" tabindex="10" placeholder="${phoneNumberPlaceHolder}"/>
				</div>
			</div>

			<c:if test='${empty addSucceeded}'>
				<input type="hidden" name="_eventId" value="submitAddPerson" />
				<button id="addPersonButton"><spring:message code="addPerson.button" /></button>
			</c:if>
		</div>
	</div>
</form:form>
<div class="or-form-name">Form: add-person</div>
