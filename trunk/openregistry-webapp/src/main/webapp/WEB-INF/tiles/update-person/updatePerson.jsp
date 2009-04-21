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

<form:form modelAttribute="personSearch">
			<fieldset id="updateperson">
				<legend><span><spring:message code="updatePersonPage.heading"/></span></legend>
				<p style="margin-bottom:0;">
					<spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">
                    <label class="desc" for="c1_prefix"><spring:message code="name.heading"/></label>
					<div class="row">
                        <table class="data" cellspacing="0" width="80%">
                            <thead>
                                <tr class="appHeadingRow">
                                    <th><spring:message code="prefix.label"/></th>
                                    <th><spring:message code="firstName.label"/></th>
                                    <th><spring:message code="middleName.label" /></th>
                                    <th><spring:message code="lastName.label" /></th>
                                    <th><spring:message code="suffix.label" /></th>
                                    <th><spring:message code="type.label" /></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="name" items="${person.names}" >
                            <tr>
                                <td>
                                     <c:out value="${name.prefix}"/>
                                </td>
                                <td>
                                     <c:out value="${name.first}"/>
                                </td>
                                <td>
                                    <c:out value="${name.middle}"/>
                                </td>
                                <td>
                                    <c:out value="${name.family}"/>
                                </td>
                                <td>
                                    <c:out value="${name.suffix}"/>
                                </td>
                                <td>
                                    Official
                                </td>
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="addEmploy">
                <input id="addNameBtn" class="button" type="submit" name="_eventId_submitAddName" value="Add Name" title="add a new name" />
            </div>

                    <label class="desc" for="c1_prefix"><spring:message code="identifiers.heading"/></label>
					<div class="row">
                        <table class="data" cellspacing="0" width="50%">
                            <thead>
                                <tr class="appHeadingRow">
                                    <th><spring:message code="type.label"/></th>
                                    <th><spring:message code="value.label"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                  SSN
                                </td>
                                <td>
                                    <form:input path="person.ssn" id="c1_firstName" size="10" maxlength="30" />
                                </td>
                            </tr>
                            </tbody>
                        </table>
                </div>
            
                <label class="desc" for="c1_prefix"><spring:message code="roles.heading"/></label>
                <div class="row">              
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="affiliationTitle.label"/></th>
                                <th><spring:message code="startDate.label"/></th>
                                <th><spring:message code="endDate.label"/></th>
                            </tr>
                            </thead>
                            <tbody>
                             <c:forEach var="role" items="${person.roles}">
                            <tr>
                                <td>
                                    <c:out value="${role.affiliationType.description}"/>/<c:out value="${role.title}"/>
                                </td>
                                <td>
                                    <c:out value="${role.start}"/>
                                </td>
                                <td>
                                    <c:out value="${role.end}"/>
                                </td>
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="addEmploy">
                <input id="addRoleBtn" class="button" type="submit" name="_eventId_submitAddRole" value="Add Role" title="add a new role" />
            </div>

            <div class="row">
                <label for="c1_dateOfBirthUpdate" class="dateOfBirthUpdate"><spring:message code="dateOfBirth.label"/><em>*</em></label>
				<form:input path="person.dateOfBirth" id="c1_dateOfBirthUpdate" size="10" maxlength="10" tabindex="7" />
            </div>
            <div class="row">
                <label for="c1_genderUpdate" class="genderUpdate"><spring:message code="gender.label" /> <em>*</em></label>
				<div class="select gender">
                    <form:select path="person.gender" id="c1_genderUpdate" size="1" tabindex="6">
                        <form:option value="" label=""/>
                        <form:option value="F" label="Female"/>
						<form:option value="M" label="Male"/>
					</form:select>
                </div>
            </div>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdatePerson" class="btn-submit" value="Update Person" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>