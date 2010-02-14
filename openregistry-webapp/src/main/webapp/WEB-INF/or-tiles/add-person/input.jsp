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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="<spring:theme code='addPersonSheet'/>" type="text/css" />

<form:form modelAttribute="personSearch">
			<fieldset id="addperson">
				<legend><span><spring:message code="addPersonPage.heading"/></span></legend>
				<p class="instructions">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset>
					<div class="row">
						<form:label cssClass="above" for="prefix" path="sorPerson.names[0].prefix"><spring:message code="prefix.label"/></form:label>
                        <div class="select prefix">
                            <form:select path="sorPerson.names[0].prefix" id="prefix" size="1" tabindex="1">
                                <form:option value="" label=""/>
							    <form:option value="Mrs" label="Mrs."/>
                                <form:option value="Miss" label="Miss"/>
                                <form:option value="Ms" label="Ms."/>
                                <form:option value="Mr" label="Mr."/>
                                <form:option value="Dr" label="Dr."/>
							</form:select>
                        </div>

						<form:label cssClass="above" for="first_name" path="sorPerson.names[0].given"><spring:message code="firstName.label"/><em>*</em></form:label>
						<form:input path="sorPerson.names[0].given" id="first_name" size="10" maxlength="30" tabindex="2" />

                        <form:label cssClass="above" for="middle_name" path="sorPerson.names[0].middle"><spring:message code="middleName.label" /></form:label>
                        <form:input path="sorPerson.names[0].middle" id="middle_name" size="10" maxlength="30" tabindex="3" />

                        <form:label cssClass="above" for="last_name" path="sorPerson.names[0].family"><spring:message code="lastName.label" /><em>*</em></form:label>
                        <form:input path="sorPerson.names[0].family" id="last_name" size="10" maxlength="30" tabindex="4" />

                        <form:label cssClass="above" for="suffix" path="sorPerson.names[0].suffix"><spring:message code="suffix.label" /></form:label>
                        <form:input path="sorPerson.names[0].suffix" id="suffix" size="5" maxlength="5" tabindex="5" />
                    </div>

                    <div class="row">

						<form:label cssClass="above" for="gender" path="sorPerson.gender"><spring:message code="gender.label" /> <em>*</em></form:label>
						<div class="select gender">
                            <form:select path="sorPerson.gender" id="gender" size="1" tabindex="6">
                                <form:option value="" label=""/>
                                <form:option value="F" label="Female"/>
							    <form:option value="M" label="Male"/>
							</form:select>
                        </div>
                   </div>

                   <div class="row">
						<form:label for="dateOfBirth" cssClass="above" path="sorPerson.dateOfBirth"><spring:message code="dateOfBirth.label"/><em>*</em></form:label>
						<form:input path="sorPerson.dateOfBirth" id="dateOfBirth" size="10" maxlength="10" tabindex="7" />
                   </div>

                    <div class="row">
                        <form:label for="ssn" cssClass="above" path="sorPerson.ssn"><spring:message code="ssn.label"/></form:label>
                        <form:input path="sorPerson.ssn" id="ssn" size="9" maxlength="9" tabindex="8" />
                    </div>

                    <div class="row">
                        <form:label for="email" cssClass="above" path="emailAddress"><spring:message code="email.label"/></form:label>
                        <form:input path="emailAddress" id="email" size="20" maxlength="30" tabindex="9" />
                    </div>

                    <div class="row">

						<form:label for="phone" cssClass="above" path="phoneNumber"><spring:message code="phoneNumber.label"/></form:label>
						<form:input path="phoneNumber" id="phone" size="15" maxlength="15" tabindex="10" />

					</div>

			</fieldset>
			</fieldset>
            <c:if test='${empty addSucceeded}'>
                <div class="center"><a href="${flowExecutionUrl}&_eventId=submitAddPerson"><button>Add Person</button></a></div>
                            </c:if>

		</form:form>