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

<form:form modelAttribute="role">
    <fieldset id="updateRole">
        <legend><span><spring:message code="updateRolePage.heading"/></span></legend>
        <p style="margin-bottom:0;"><spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.</p>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <label class="desc2" for="c1_startdate">
                <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label" /></span><em2><c:out value="${sorPerson.formattedNameAndID}"/></em2>
                <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading" /></span>
                <em2><c:out value="${role.title}"/>/<c:out value="${role.organizationalUnit.name}"/>/<c:out value="${role.campus.name}"/></em2>
            </label>
                <br/><br/>

                <label class="desc" for="c1_startdate"><spring:message code="roleInformation.heading"/></label>

                <div class="row">
                    <label for="c1_startdate" class="startdate"><spring:message code="startDate.label"/><em>*</em></label>
                    <form:input path="start" id="c1_startdate" size="10" maxlength="10" tabindex="1"/>

                    <label for="c1_enddate" class="enddate"><spring:message code="endDate.label"/></label>
                    <form:input path="end" id="c1_enddate" size="10" maxlength="10" tabindex="2" />

                    <a href="${flowExecutionUrl}&_eventId=submitRenewRoleStandardRenewal"><img src="images/renew2.jpg" title="Renew for standard renewal period."/></a>
                    <a href="${flowExecutionUrl}&_eventId=submitExpireRoleToday"><img src="images/expire.jpg" title="Expire today."/></a>

                </div>

                <div class="row">
                    <label for="c1_UpdateRoleSponsor" class="updateRoleSponsor"><spring:message code="sponsor.label" /><em>*</em></label>
                    <div class="select sponsor">
					    <form:select path="sponsor.sponsorId" id="c1_UpdateRoleSponsor" items="${sponsorList}" itemValue="id" itemLabel= "formattedNameAndID" size="1" tabindex="3" />
                    </div>

                    <label for="c1_pt" class="updateRolePt"><spring:message code="pt.label" /></label>
                       <div class="select pt">
							<form:select path="percentage" id="c1_pt" size="1" tabindex="4">
                            <form:option value="100" label="100"/>
							<form:option value="75" label="75"/>
                            <form:option value="50" label="50"/>
                            <form:option value="25" label="25"/>
							</form:select>
					    </div>

                </div>

             <label class="desc"><spring:message code="emailAddress.heading"/><input id="addEmailBtn" type="image" name="_eventId_submitAddEmail" src="images/add2.gif" title="add a new email address"/></label>

                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="value.label"/></th>
                                <th><spring:message code="action.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="emailAddress" items="${role.emailAddresses}" varStatus="loopStatus">
                            <tr>
                                <td valign="center" >
                                    <div>
                                        <form:select path="emailAddresses[${loopStatus.index}].addressType" id="c1_type" items="${emailTypes}" itemValue="id" itemLabel="description" />
                                    </div>
                                </td>
                                <td><form:input size="60" path="emailAddresses[${loopStatus.index}].address" /></td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemoveEmailAddress&emailId=${emailAddress.id}"><img src="images/trash.jpg" title="remove email address"/></a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


                <label class="desc"><spring:message code="phones.heading"/><input id="addPhoneBtn" type="image" name="_eventId_submitAddPhone" src="images/add2.gif" title="add a new phone number"/></label>
                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="addressType.label"/></th>
                                <th><spring:message code="phoneType.label"/></th>
                                <th><spring:message code="ccCode.label"/></th>
                                <th><spring:message code="areaCode.label"/></th>
                                <th><spring:message code="number.label"/></th>
                                <th><spring:message code="ext.label"/></th>
                                <th><spring:message code="action.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="phone" items="${role.phones}" varStatus="loopStatus">
                            <tr>
                                <td>
                                    <div>
                                        <form:select path="phones[${loopStatus.index}].addressType" id="c1_type" items="${addressTypes}" itemValue="id" itemLabel="description" />
                                </div>
                                </td>
                                <td>
                                    <div>
                                        <form:select path="phones[${loopStatus.index}].phoneType" id="c1_type" items="${phoneTypes}" itemValue="id" itemLabel="description" />
                                </div>
                                </td>
                                <td><form:input path="phones[${loopStatus.index}].countryCode" size="7"/></td>
                                <td><form:input path="phones[${loopStatus.index}].areaCode" size="9"/></td>
                                <td><form:input path="phones[${loopStatus.index}].number" size="20"/></td>
                                <td><form:input path="phones[${loopStatus.index}].extension" size="7"/></td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemovePhone&phoneId=${phone.id}"><img src="images/trash.jpg" title="remove phone number"/></a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <label class="desc"><spring:message code="address.heading"/><input id="addAddressBtn" type="image" name="_eventId_submitAddAddress" src="images/add2.gif" title="add a new address"/></label>
                <c:choose>
                <c:when test="${not empty role.addresses}">
                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="value.label"/></th>
                                <th><spring:message code="action.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="address" items="${role.addresses}" varStatus="loopStatus">
                            <tr>
                                <td>
                                    <div>
                                        <form:select path="addresses[${loopStatus.index}].type" id="c1_type" items="${addressTypes}" itemValue="id" itemLabel="description" />
                                    </div>
                                </td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitUpdateAddress&addressId=${address.id}">${address.singleLineAddress}</a></td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemoveAddress&addressId=${address.id}"><img src="images/trash.jpg" title="remove address"/></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </c:when>
                    <c:otherwise><spring:message code="noAddressesDefined.label"/><br/><br/></c:otherwise>
                </c:choose>

                <label class="desc"><spring:message code="urls.heading"/><input id="addURLBtn" type="image" name="_eventId_submitAddURL" src="images/add2.gif" title="add a new URL"/></label>
                <c:choose>
                <c:when test="${not empty role.urls}">

                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="value.label"/></th>
                                <th><spring:message code="action.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="url" items="${role.urls}" varStatus="loopStatus">
                            <tr>
                                <td valign="center" >
                                    <div>
                                        <form:select path="urls[${loopStatus.index}].type" id="c1_type" items="${urlTypes}" itemValue="id" itemLabel="description" />
                                    </div>
                                </td>
                                <td><form:input path="urls[${loopStatus.index}].url" size="60" /></td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemoveURL&urlId=${url.id}"><img src="images/trash.jpg" title="remove URL"/></a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                </c:when>
                    <c:otherwise><spring:message code="noUrlsDefined.label"/><br/><br/></c:otherwise>
                </c:choose>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdateRole" class="btn-submit" value="Update Role" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>