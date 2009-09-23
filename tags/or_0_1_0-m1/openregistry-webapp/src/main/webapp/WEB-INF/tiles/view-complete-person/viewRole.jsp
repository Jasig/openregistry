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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form modelAttribute="role">
    <fieldset id="viewRole">
        <legend><span><spring:message code="viewRolePage.heading"/></span></legend>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <label class="desc2" for="c1_startdate">
                <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label" /></span><em2><c:out value="${sorPersonName}"/></em2>
                <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading" /></span>
                <em2><c:out value="${role.title}"/>/<c:out value="${role.organizationalUnit.name}"/>/<c:out value="${role.campus.name}"/></em2>
            </label>
                <br/><br/>

                <label class="desc" for="c1_startdate"><spring:message code="roleInformation.heading"/></label>
                <table class="data" cellspacing="0" width="80%">
                    <thead>
                        <tr class="appHeadingRow">
                            <th><spring:message code="property.label"/></th>
                            <th><spring:message code="value.label"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td><spring:message code="startDate.label"/></td><td><fmt:formatDate pattern="MM-dd-yyyy" value="${role.start}" /></td></tr>
                        <tr><td><spring:message code="endDate.label"/></td<td><fmt:formatDate pattern="MM-dd-yyyy" value="${role.end}"/></td></tr>
                        <tr><td><spring:message code="sponsor.label"/></td<td>${sponsorPerson.formattedNameAndID}</td></tr>
                        <tr><td><spring:message code="pt.label"/></td<td>${role.percentage}</td></tr>              
                    </tbody>
                </table>


             <label class="desc"><spring:message code="emailAddress.heading"/><input id="addEmailBtn" type="image" name="_eventId_submitAddEmail" src="images/add2.gif" title="add a new email address"/></label>

                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="value.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="emailAddress" items="${role.emailAddresses}" varStatus="loopStatus">
                            <tr>
                                <td>${emailAddress.addressType.description}</td>
                                <td>${emailAddress.address}</td>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="phone" items="${role.phones}" varStatus="loopStatus">
                            <tr>
                                <td>${phone.addressType.description}</td>
                                <td>${phone.phoneType.description}</td>
                                <td>${phone.countryCode}</td>
                                <td>${phone.areaCode}</td>
                                <td>${phone.number}</td>
                                <td>${phone.extension}</td>
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
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="address" items="${role.addresses}" varStatus="loopStatus">
                            <tr>
                                <td>${address.type.description}</td>
                                <td>${address.singleLineAddress}</td>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="url" items="${role.urls}" varStatus="loopStatus">
                            <tr>
                                <td>${url.type.description}</td>
                                <td>${url.url}</td>
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
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitBack" class="btn-submit" value="Back" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>