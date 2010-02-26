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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Manage People</h2>

<div class="instructions">
    <ul>
        <li>Enter your search criteria in the fields below and click <strong>Find Person</strong>.</li>
        <li>Matching persons will appear below.</li>
        <li>Select a person to perform any of the following actions:
            <ul>
                <li>View a person's complete details</li>
                <li>Update the Person</li>
                <li>Move an Sor Person record to another calculated person</li>
                <li>Move all Sor Person Records to another calculated person</li>
            </ul>
        </li>
    </ul>
</div>


<div id="search_form">
    <form:form commandName="${commandName}" method="post" id="orForm">
        <form:errors path="*" element="div" id="message" cssClass="error" htmlEscape="false"/>
        <fieldset>
            <legend>Find a Person</legend>
            <div class="field_set group">
                <div>
                     <form:label cssClass="above" path="identifierValue">Identifier</form:label>
                    <form:input path="identifierValue" cssErrorClass="formerror" />
                </div>
                <div>
                    <form:label cssClass="above" path="name"><spring:message code="person.biodem.names.label" /></form:label>
                    <form:input path="name" cssErrorClass="formerror" />
                    <form:errors path="name" />
                </div>
                <div>
                    <form:label cssClass="above" path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
                    <form:input path="dateOfBirth" cssErrorClass="formerror" placeholder="yyyy-mm-dd"/>
                </div>
                <div id="find_submit_container">
                    <input type="hidden" name="_eventId" value="submit" />
                    <button id="submitButton">Find Person</button>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>