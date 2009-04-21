<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form:form modelAttribute="sorName">
			<fieldset id="addname">
				<legend><span><spring:message code="addNamePage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">

					<div class="row">
						<label for="c1_prefix" class="prefix"><spring:message code="prefix.label"/></label>
                        <div class="select prefix">
                            <form:select path="prefix" id="c1_prefix" size="1" tabindex="1">
                                <form:option value="Empty" label=""/>
							    <form:option value="Mrs" label="Mrs."/>
                                <form:option value="Miss" label="Miss"/>
                                <form:option value="Ms" label="Ms."/>
                                <form:option value="Mr" label="Mr."/>
                                <form:option value="Dr" label="Dr."/>
							</form:select>
                        </div>

						<label for="c1_firstName" class="firstName"><spring:message code="firstName.label"/><em>*</em></label>
						<form:input path="given" id="c1_firstName" size="10" maxlength="30" tabindex="2" />

                        <label for="c1_middleName" class="middleName"><spring:message code="middleName.label" /></label>
                        <form:input path="middle" id="c1_middleName" size="10" maxlength="30" tabindex="3" />

                        <label for="c1_lastName" class="lastName"><spring:message code="lastName.label" /><em>*</em></label>
                        <form:input path="family" id="c1_lastName" size="10" maxlength="30" tabindex="4" />

                        <label for="c1_suffix" class="suffix"><spring:message code="suffix.label" /></label>
                        <form:input path="suffix" id="c1_suffix" size="5" maxlength="5" tabindex="5" />
                    </div>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitPerformAddName" class="btn-submit" value="Add Name" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>