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
  User: nmond
  Date: May 18, 2009
  Time: 4:37:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form modelAttribute="identifier">
    <fieldset id="activateNetID">
        <legend><span><spring:message code="generateActivationKeyPage.heading"/></span></legend>
        <p style="margin-bottom:0;">
            <spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
        </p>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <div class="row">
                <label for="c1_IdentifierType" class="identifierType"><spring:message code="identifier.label"/><em>*</em></label>
                <div class="select identifierType">
                    <form:select path="type" items="${identifierTypes}" itemLabel="name" itemValue="name" id="c1_IdentifierType" tabindex="1" />
                </div>

                <label for="c1_IdentifierValue" class="identifierValue"><spring:message code="identifierValue.label"/><em>*</em></label>
                <form:input path="value" id="c1_IdentifierValue" size="10" maxlength="9" tabindex="2"/>
            </div>
        </fieldset>
    </fieldset>

    <div class="row fm-v" style="clear:both;">
        <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitNewActivationKey" class="btn-submit" value="Generate New Key" tabindex="3"/>
    </div>


</form:form>