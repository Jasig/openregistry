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
<%--<link rel="stylesheet" href="<spring:theme code='addPersonSheet'/>" type="text/css" />--%>

<form:form modelAttribute="reconciliationCriteria" commandName="reconciliationCriteria" method="post">
    <fieldset>
        <legend><span><spring:message code="addPersonPage.heading" /></span></legend>
        <p class="instructions">
            <spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
        </p>

        <form:errors path="*" element="div" id="message" cssClass="error" htmlEscape="false"/>
        <fieldset>
            <div class="field_set group">
                <div>
                    <form:label cssClass="above" path="sorPerson.names[0].prefix" cssErrorClass="labelError">
                        <spring:message code="prefix.label" />
                    </form:label>
                    <form:select path="sorPerson.names[0].prefix" size="1" tabindex="1">
                        <form:option value="" label="" />
                        <form:option value="Mrs" label="Mrs." />
                        <form:option value="Miss" label="Miss" />
                        <form:option value="Ms" label="Ms." />
                        <form:option value="Mr" label="Mr." />
                        <form:option value="Dr" label="Dr." />
                    </form:select>
                </div>
                <div>

                    <form:label cssClass="above"  path="sorPerson.names[0].given" cssErrorClass="labelError"><spring:message code="firstName.label" /><em>*</em></form:label>
                    <form:input path="sorPerson.names[0].given"  size="10" maxlength="30" tabindex="2" cssErrorClass="fieldError"/>
                </div>
                <div>

                    <form:label cssClass="above"  path="sorPerson.names[0].middle" cssErrorClass="labelError"><spring:message code="middleName.label" /></form:label>
                    <form:input path="sorPerson.names[0].middle" size="10" maxlength="30" tabindex="3" cssErrorClass="fieldError"/>
                </div>
                <div>

                    <form:label cssClass="above"  path="sorPerson.names[0].family" cssErrorClass="labelError"><spring:message code="lastName.label" /><em>*</em></form:label>
                    <form:input path="sorPerson.names[0].family" size="10" maxlength="30" tabindex="4" cssErrorClass="fieldError" />
                </div>
                <div>

                    <form:label cssClass="above"  path="sorPerson.names[0].suffix" cssErrorClass="labelError"><spring:message code="suffix.label" /></form:label>
                    <form:input path="sorPerson.names[0].suffix" size="10" maxlength="30" tabindex="4" cssErrorClass="fieldError" />
                </div>


            </div>
            <div style="clear:both">

                <div class="padded">

                    <form:label cssClass="above" path="sorPerson.gender" cssErrorClass="labelError"><spring:message code="gender.label" />
                        <em>*</em></form:label>
                    <form:select path="sorPerson.gender" size="1" tabindex="6">
                        <form:option value="" label="" />
                        <form:option value="F" label="Female" />
                        <form:option value="M" label="Male" />
                    </form:select>
                </div>

                <div class="padded">
                    <form:label cssClass="above" path="sorPerson.dateOfBirth" cssErrorClass="labelError"><spring:message code="dateOfBirth.label" /><em>*</em></form:label>
                    <form:input path="sorPerson.dateOfBirth" size="10" maxlength="10" tabindex="7" cssErrorClass="fieldError" placeholder="yyyy-mm-dd"/>
                </div>

                <div class="padded">
                    <form:label  cssClass="above" path="sorPerson.ssn" cssErrorClass="labelError"><spring:message code="ssn.label" /></form:label>
                    <form:input path="sorPerson.ssn"  size="9" maxlength="9" tabindex="8" cssErrorClass="fieldError"/>
                </div>

                <div class="padded">
                    <form:label cssClass="above" path="emailAddress" cssErrorClass="labelError"><spring:message code="email.label" /></form:label>
                    <form:input path="emailAddress" size="20" maxlength="30" tabindex="9" cssErrorClass="fieldError"/>
                </div>

                <div class="padded">

                    <form:label cssClass="above" path="phoneNumber" cssErrorClass="labelError"><spring:message code="phoneNumber.label" /></form:label>
                    <form:input path="phoneNumber" size="15" maxlength="15" tabindex="10" cssErrorClass="fieldError"/>

                </div>
            </div>

        </fieldset>
    </fieldset>
    <c:if test='${empty addSucceeded}'>

        <input type="hidden" name="_eventId" value="submitAddPerson" />
        <button id="submitButton">Add Person</button>

    </c:if>

</form:form>