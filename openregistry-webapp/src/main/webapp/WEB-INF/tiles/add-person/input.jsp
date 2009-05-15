<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form:form modelAttribute="personSearch">
			<fieldset id="addperson">
				<legend><span><spring:message code="addPersonPage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">

					<div class="row">
						<label for="c1_prefix" class="prefix"><spring:message code="prefix.label"/></label>
                        <div class="select prefix">
                            <form:select path="person.names[0].prefix" id="c1_prefix" size="1" tabindex="1">
                                <form:option value="" label=""/>
							    <form:option value="Mrs" label="Mrs."/>
                                <form:option value="Miss" label="Miss"/>
                                <form:option value="Ms" label="Ms."/>
                                <form:option value="Mr" label="Mr."/>
                                <form:option value="Dr" label="Dr."/>
							</form:select>
                        </div>

						<label for="c1_firstName" class="firstName"><spring:message code="firstName.label"/><em>*</em></label>
						<form:input path="person.names[0].given" id="c1_firstName" size="10" maxlength="30" tabindex="2" />

                        <label for="c1_middleName" class="middleName"><spring:message code="middleName.label" /></label>
                        <form:input path="person.names[0].middle" id="c1_middleName" size="10" maxlength="30" tabindex="3" />

                        <label for="c1_lastName" class="lastName"><spring:message code="lastName.label" /><em>*</em></label>
                        <form:input path="person.names[0].family" id="c1_lastName" size="10" maxlength="30" tabindex="4" />

                        <label for="c1_suffix" class="suffix"><spring:message code="suffix.label" /></label>
                        <form:input path="person.names[0].suffix" id="c1_suffix" size="5" maxlength="5" tabindex="5" />
                    </div>

                    <div class="row">

						<label for="c1_gender" class="gender"><spring:message code="gender.label" /> <em>*</em></label>
						<div class="select gender">
                            <form:select path="person.gender" id="c1_gender" size="1" tabindex="6">
                                <form:option value="" label=""/>
                                <form:option value="F" label="Female"/>
							    <form:option value="M" label="Male"/>
							</form:select>
                        </div>
                   </div>

                   <div class="row">
						<label for="c1_dateOfBirth" class="dateOfBirth"><spring:message code="dateOfBirth.label"/><em>*</em></label>
						<form:input path="person.dateOfBirth" id="c1_dateOfBirth" size="10" maxlength="10" tabindex="7" />
                   </div>

                    <div class="row">
                        <label for="c1_SSN"><spring:message code="ssn.label"/></label>
                        <form:input path="person.ssn" id="c1_ssn" size="9" maxlength="9" tabindex="8" />
                    </div>

                    <div class="row">
                        <label for="c1_email"><spring:message code="email.label"/></label>
                        <form:input path="emailAddress" id="c1_email" size="20" maxlength="30" tabindex="9" />
                    </div>

                    <div class="row">

						<label for="c1_phone" class="cccode"><spring:message code="phoneNumber.label"/></label>
						<form:input path="phoneNumber" id="c1_phone" size="15" maxlength="15" tabindex="10" />

					</div>

			</fieldset>
			</fieldset>
            <c:if test='${empty addSucceeded}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitAddPerson" class="btn-submit" value="Add Person" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>