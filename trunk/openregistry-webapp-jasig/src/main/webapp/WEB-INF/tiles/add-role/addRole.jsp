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
        $("#fm-search-submit1").button();
		$("#cancel.button").button();
    });
</script>

<form:form modelAttribute="role" commandName="role" method="post">
	<h2><spring:message code="addRolePage.heading" /></h2>

	<p class="or-instructions">
		<spring:message code="requiredFields.heading" /><span class="or-required-field-marker">*</span>.
	</p>

	<form:errors path="*" element="div" id="or-message" cssClass="or-error-message ui-state-error ui-corner-all" htmlEscape="false"/>
	<fieldset class="fm-h" id="ecn1">
		<label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="addRole.label"/></span><em2><c:out value="${role.affiliationType.description}"/></em2></label>
		<label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="title.label" /></span><em2><c:out value="${role.title}"/></em2></label>
		<label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="to.label" /></span><em2><c:out value="${sorPerson.formattedName}"/></em2></label>

		<h3><spring:message code="specifyRoleInfo.heading"/></h3>

		<div class="row">
			<label for="c1_startdate" class="startdate"><spring:message code="startDate.label"/><span class="or-required-field-marker">*</span></label>
			<form:input path="start" id="c1_startdate" size="10" maxlength="10" tabindex="1"/>

			<label for="c1_enddate" class="enddate"><spring:message code="endDate.label"/></label>
			<form:input path="end" id="c1_enddate" size="10" maxlength="10" tabindex="2" />
		</div>
		<div class="row">
			<label for="c1_sponsor"><spring:message code="sponsor.label" /><span class="or-required-field-marker">*</span></label>
			<div class="select sponsor"><form:select path="sponsorId" id="c1_sponsor" items="${sponsorList}" itemValue="id" itemLabel="formattedName" size="1" tabindex="3" /></div>
		</div>
		<div class="row">
			<form:label cssClass="above"  path="emailAddresses[0].address" cssErrorClass="labelError"><spring:message code="campusEmail.label" /><span class="or-required-field-marker">*</span></form:label>
			<form:input path="emailAddresses[0].address" id="c1_email" size="20" maxlength="30" tabindex="6" cssErrorClass="fieldError"/>
		</div>
		<div class="row">
			<label for="c1_pt"><spring:message code="pt.label" /></label>
			<div class="select pt">
				<form:select path="percentage" id="c1_pt" size="1" tabindex="7">
					<form:option value="100" label="100"/>
					<form:option value="75" label="75"/>
					<form:option value="50" label="50"/>
					<form:option value="25" label="25"/>
				</form:select>
			</div>
		</div>
	</fieldset>

	<fieldset id="e_cell">
		<legend><spring:message code="localAddressInfo.heading"/></legend>
		<fieldset class="fm-h" style="margin-bottom:0;">
			<div class="row">
				<label for="c1_address1" class="address1"><spring:message code="addressLine1.label"/></label>
				<form:input path="addresses[0].line1" id="c1_address1" size="30" maxlength="30" tabindex="8" />
			</div>
			<div class="row">
				<label for="c1_address2" class="address1"><spring:message code="addressLine2.label"/></label>
				<form:input path="addresses[0].line2" id="c1_address2" size="30" maxlength="30" tabindex="9" />
			</div>
			<div class="row">
				<label for="c1_address3" class="address1"><spring:message code="addressLine3.label"/></label>
				<form:input path="addresses[0].line3" id="c1_address3" size="30" maxlength="30" tabindex="10" />
			</div>
			<div class="row">
				<label for="c1_city"><spring:message code="city.label"/></label>
				<form:input path="addresses[0].city" id="c1_city" size="30" maxlength="30" tabindex="11" />

				<label for="c1_state" class="state"><spring:message code="region.label"/></label>
				<form:input path="addresses[0].region" id="c1_state" size="10" maxlength="10" tabindex="12" />

				<label for="c1_zip" class="zip"><spring:message code="postalCode.label"/></label>
				<form:input path="addresses[0].postalCode" id="c1_zip" size="10" maxlength="10" tabindex="13" />
			</div>

			<div class="row">
				<label for="c1_country"><spring:message code="country.label"/></label>
				<div class="select country">
					<form:select path="addresses[0].country" id="c1_country" items="${countries}" itemValue="id" itemLabel="name" size="1" tabindex="14" />
				</div>
			</div>

			<div class="row">
				<label for="c1_cccode" class="cccode"><spring:message code="ccCode.label"/></label>
				<form:input path="phones[0].countryCode" id="c1_cccode" size="5" maxlength="5" tabindex="15" />

				<label for="c1_areacode" class="areacode"><spring:message code="areaCode.label"/></label>
				<form:input path="phones[0].areaCode" id="c1_areacode" size="5" maxlength="5" tabindex="16" />

				<label for="c1_number" class="number"><spring:message code="number.label"/></label>
				<form:input path="phones[0].number" id="c1_number" size="10" maxlength="10" tabindex="17" />

				<label for="c1_ext" class="ext"><spring:message code="ext.label"/></label>
				<form:input path="phones[0].extension" id="c1_ext" size="10" maxlength="10" tabindex="18" />
			</div>
		</fieldset>
	</fieldset>

	<input type="hidden" name="_eventId" value="submitAddRole" />
	<button id="fm-search-submit1"><spring:message code="addRole.button" /></button>

	<a href="${flowExecutionUrl}&_eventId=cancelAddRole"><button id="cancel.button"><spring:message code="cancel.button" /></button></a>

	<%--
	 <div class="row fm-v" style="clear:both;">
		 <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitAddRole" class="btn-submit" value="Add Role" tabindex="19"/>
		 <input style="float:left;" type="submit" id="fm-search-cancel"  name="_eventId_cancelAddRole" class="btn-cancel" value="Cancel" tabindex="20"/>
	 </div>
 --%>
</form:form>
