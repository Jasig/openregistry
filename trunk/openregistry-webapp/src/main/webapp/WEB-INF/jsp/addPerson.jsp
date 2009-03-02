<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.include file="/WEB-INF/jsp/includes/head.jsp" />

<head>
    <title><spring:message code="addPerson.title"/></title>
</head>
<body class="openregistry" id="update">

	<h1 id="app-name">Open Registry</h1>
	<ul id="nav-system"><li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li></ul>

	<div id="content">
   		<c:set var="command" value="person" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/errors.jsp" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />

		<form:form modelAttribute="person" id="fm-search" method="post" action="addPerson.htm">
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
                            <form:select path="officialName.prefix" id="c1_prefix" size="1" tabindex="1">
                                <form:option value="Empty" label=""/>
							    <form:option value="Mrs" label="Mrs."/>
                                <form:option value="Miss" label="Miss"/>
                                <form:option value="Ms" label="Ms."/>
                                <form:option value="Mr" label="Mr."/>
                                <form:option value="Dr" label="Dr."/>
							</form:select>
                        </div>


						<label for="c1_firstName" class="firstName"><spring:message code="firstName.label"/><em>*</em></label>
						<form:input path="officialName.given" id="c1_firstName" size="10" maxlength="30" tabindex="2" />

                        <label for="c1_middleName" class="middleName"><spring:message code="middleName.label" /></label>
                        <form:input path="officialName.middle" id="c1_middleName" size="10" maxlength="30" tabindex="4" />
						    
                        <label for="c1_lastName" class="lastName"><spring:message code="lastName.label" /><em>*</em></label>
                        <form:input path="officialName.family" id="c1_lastName" size="10" maxlength="30" tabindex="3" />

                        <label for="c1_suffix" class="suffix"><spring:message code="suffix.label" /></label>
                        <form:input path="officialName.suffix" id="c1_suffix" size="5" maxlength="5" tabindex="5" />
                    </div>

                    <div class="row">
                        <label for="c1_preferredName" class="preferredName"><spring:message code="preferredName.label"/></label>
						<form:input path="preferredName.given" id="c1_preferredName" size="10" maxlength="30" tabindex="2" />
                    </div>

                    <div class="row">

						<label for="c1_gender" class="gender"><spring:message code="gender.label" /> <em>*</em></label>
						<div class="select gender">
                            <form:select path="gender" id="c1_gender" size="1" tabindex="6">
                                <form:option value="" label=""/>
                                <form:option value="F" label="Female"/>
							    <form:option value="M" label="Male"/>
							</form:select>
                        </div>
                   </div>

                   <!--
                   <div >
                            <form:radiobutton path="gender" value="Female" label="Female"/>
                            <form:radiobutton path="gender" value="Male" label="Male" />
                        </div>
                    <div class="row">
                        <label for="c1_gender" class="gender"><spring:message code="gender.label" /> <em>*</em></label>
                    </div>
                    -->
                   <div class="row">
						<label for="c1_dateOfBirth" class="dateOfBirth"><spring:message code="dateOfBirth.label"/><em>*</em></label>
						<form:input path="dateOfBirth" id="c1_dateOfBirth" size="10" maxlength="10" tabindex="7" />
                   </div>

                    <div class="row">
                        <label for="c1_SSN"><spring:message code="ssn.label"/></label>
                        <form:input path="identifiers[0].value" id="c1_ssn" size="9" maxlength="9" tabindex="8" />
                    </div>

                    <div class="row">
						<label for="c1_RUID" class="ruid"><spring:message code="ruid.label" /></label>
                        <form:input path="identifiers[1].value" id="c1_ruid" size="9" maxlength="9" tabindex="9" />
					</div>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="fm-search-submit" class="btn-submit" value="Add Person" tabindex="19"/>
			    </div>
            </c:if>

		</form:form>
	</div>

	<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
</body>
</html>