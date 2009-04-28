<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form:form modelAttribute="role">
			<fieldset id="selectRole">
				<legend><span><spring:message code="selectRoleToAddPage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">

					<div class="row">
						<label for="c1_affiliation" class="affiliation"><spring:message code="selectAffiliation.label"/></label>
                        <div class="select affiliation">
                            <form:select path="affiliationType.description" id="c1_affiliation" items="${affiliationList}" itemValue="id" itemLabel="name" size="1" tabindex="1" />
                        </div>
                        <label for="c1_title" class="title"><spring:message code="selectTitle.label"/></label>
                        <form:input path="title" id="c1_title" size="40" maxlength="100" tabindex="2" />
                    </div>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitSelectRole" class="btn-submit" value="Select Role" tabindex="3"/>
			    </div>
            </c:if>

		</form:form>