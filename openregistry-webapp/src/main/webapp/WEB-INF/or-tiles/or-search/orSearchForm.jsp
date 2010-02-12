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
  User: rosey
  Date: Feb 2, 2010
  Time: 1:51:52 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<spring:theme code='findPersonSheet'/>" type="text/css" />

<h2>Find a Person</h2>

<p class="instructions">Enter your search criteria in the fields below and click <strong>Find Person</strong>.

<div id="search_form">
    <form:form commandName="searchCriteria" method="post" id="orForm">
        <form:errors path="*" element="p" id="globalErrors" />
        <fieldset>
            <legend>Find a Person</legend>
            <div class="field_set group">
                <div>
                     <form:label class="above" for="person_identifier" path="identifierValue">Identifier</form:label>
                    <form:input path="identifierValue" cssErrorClass="formerror" name="person_identifier" id="person_identifier"/>
                </div>
                <div>
                    <form:label class="above" for="person_name" path="name"><spring:message code="person.biodem.names.label" /></form:label>
                    <form:input path="name" cssErrorClass="formerror" name="person_name" id="person_name" />
                </div>
                <div>
                    <form:label class="above" for="person_dob" path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
                    <form:input path="dateOfBirth" cssErrorClass="formerror" name="person_dob" id="person_dob" placeholder="yyyy-mm-dd"/>
                </div>
                <div id="find_submit_container">
                    <input type="hidden" name="_eventId" value="submit" />
                    <button id="submitButton">Find Person</button>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>