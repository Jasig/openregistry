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

<form:form modelAttribute="address">
    <fieldset id="updateAddress">
        <legend><span><spring:message code="updateAddress.heading"/></span></legend>
        <p style="margin-bottom:0;"><spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.</p>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label" /></span><em2><c:out value="${sorPerson.formattedNameAndID}"/></em2></label>
            <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading" /></span><em2><c:out value="${role.affiliationType.description}"/>/<c:out value="${role.title}"/></em2></label>
                <br/><br/>

                <label class="desc"><spring:message code="address.heading"/></label>

                <div class="row">
                    <label for="c1_type"><spring:message code="addressType.label"/></label>
                    <div class="select addressType">
                        <form:select path="type" id="c1_type" items="${addressTypeList}" itemValue="id" itemLabel="description" size="1" tabindex="7" />
                    </div>
                </div>
                <div class="row">
                    <label for="c1_address1" class="address1"><spring:message code="addressLine1.label"/></label>
                    <form:input path="line1" id="c1_address1" size="30" maxlength="30" tabindex="8" />
                </div>
                <div class="row">
                    <label for="c1_address1" class="address1"><spring:message code="addressLine2.label"/></label>
                    <form:input path="line2" id="c1_address1" size="30" maxlength="30" tabindex="9" />
                </div>
                <div class="row">
                    <label for="c1_address1" class="address1"><spring:message code="addressLine3.label"/></label>
                    <form:input path="line3" id="c1_address1" size="30" maxlength="30" tabindex="10" />
                </div>

                <div class="row">
                    <label for="c1_city"><spring:message code="city.label"/></label>
                    <form:input path="city" id="c1_city" size="30" maxlength="30" tabindex="11" />

                    <label for="c1_state" class="state"><spring:message code="region.label"/></label>
                    <form:input path="region" id="c1_state" size="10" maxlength="10" tabindex="12" />

                    <label for="c1_zip" class="zip"><spring:message code="postalCode.label"/></label>
                    <form:input path="postalCode" id="c1_zip" size="10" maxlength="10" tabindex="13" />
                </div>
                <div class="row">
                    <label for="c1_country"><spring:message code="country.label"/></label>
                    <div class="select country">
                        <form:select path="country" id="c1_country" items="${countryList}" itemValue="id" itemLabel="name" size="1" tabindex="14" />
                    </div>
                </div>
                <br/><br/>
			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdateAddressInfo" class="btn-submit" value="Update Address" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>