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
            <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label" /></span><em2><c:out value="${sorPerson.formattedNameAndID}"/></em2></label>
                <br/><br/>
                <label class="desc"><spring:message code="role.heading"/></label>
                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="affiliationTitle.label"/></th>
                                <th><spring:message code="organization.label"/></th>
                                <th><spring:message code="campus.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${role.affiliationType.description}"/>/<c:out value="${role.title}"/></td>
                                <td><c:out value="${role.organizationalUnit.name}"/></td>
                                <td><c:out value="${role.campus.name}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <label class="desc" for="c1_startdate"><spring:message code="specifyRoleInfo.heading"/></label>

                <div class="row">
                    <label for="c1_startdate" class="startdate"><spring:message code="startDate.label"/><em>*</em></label>
                    <form:input path="start" id="c1_startdate" size="10" maxlength="10" tabindex="1"/>

                    <label for="c1_enddate" class="enddate"><spring:message code="endDate.label"/></label>
                    <form:input path="end" id="c1_enddate" size="10" maxlength="10" tabindex="2" />

                    <label for="c1_sponsor" class="sponsor"><spring:message code="sponsor.label" /><em>*</em></label>
                    <div class="select sponsor">
					    <form:select path="sponsor.sponsorId" id="c1_sponsor" items="${sponsorList}" itemValue="id" itemLabel= "formattedNameAndID" size="1" tabindex="3" />
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

                <label class="desc"><spring:message code="emailAddress.heading"/></label>
                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="value.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="emailAddress" items="${role.emailAddressList}" varStatus="loopStatus">
                            <tr>
                                <td><c:out value="${emailAddress.addressType.description}"/></td>
                                <td><form:input path="emailAddressList[${loopStatus.index}].address" /></</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <label class="desc"><spring:message code="phones.heading"/></label>
                <div>
                    <table class="data" cellspacing="0" width="50%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="type.label"/></th>
                                <th><spring:message code="ccCode.label"/></th>
                                <th><spring:message code="areaCode.label"/></th>
                                <th><spring:message code="number.label"/></th>
                                <th><spring:message code="ext.label"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="phone" items="${role.phoneList}" varStatus="loopStatus">
                            <tr>
                                <td><c:out value="${phone.phoneType.description}"/></td>
                                <td><form:input path="phoneList[${loopStatus.index}].countryCode" /></</td>
                                <td><form:input path="phoneList[${loopStatus.index}].areaCode" /></</td>
                                <td><form:input path="phoneList[${loopStatus.index}].number" /></</td>
                                <td><form:input path="phoneList[${loopStatus.index}].extension" /></</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <label class="desc"><spring:message code="addresses.heading"/></label>
                <div>
                    <c:forEach var="address" items="${role.addressList}" varStatus="loopStatus">
                        <div class="row">
                            <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="addressType.heading"/></span><em2><c:out value="${address.type.description}"/></em2></label>  
                        </div>
                        <div class="row">
						    <label for="c1_address1" class="address1"><spring:message code="addressLine1.label"/></label>
						    <form:input path="addressList[${loopStatus.index}].line1" id="c1_address1" size="30" maxlength="30" tabindex="8" />
					    </div>
                        <div class="row">
						    <label for="c1_address1" class="address1"><spring:message code="addressLine2.label"/></label>
						    <form:input path="addressList[${loopStatus.index}].line2" id="c1_address1" size="30" maxlength="30" tabindex="9" />
					    </div>
                        <div class="row">
						    <label for="c1_address1" class="address1"><spring:message code="addressLine3.label"/></label>
						    <form:input path="addressList[${loopStatus.index}].line3" id="c1_address1" size="30" maxlength="30" tabindex="10" />
					    </div>

					    <div class="row">
						    <label for="c1_city"><spring:message code="city.label"/></label>
						    <form:input path="addressList[${loopStatus.index}].city" id="c1_city" size="30" maxlength="30" tabindex="11" />

                            <label for="c1_state" class="state"><spring:message code="region.label"/></label>
					        <form:input path="addressList[${loopStatus.index}].region" id="c1_state" size="10" maxlength="10" tabindex="12" />

						    <label for="c1_zip" class="zip"><spring:message code="postalCode.label"/></label>
						    <form:input path="addressList[${loopStatus.index}].postalCode" id="c1_zip" size="10" maxlength="10" tabindex="13" />
					    </div>
                    </c:forEach>
                    <br/><br/>
                </div>

			</fieldset>
			</fieldset>
            <c:if test='${empty infoModel}'>
			    <div class="row fm-v" style="clear:both;">
				    <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdateRole" class="btn-submit" value="Update Role" tabindex="11"/>
			    </div>
            </c:if>

		</form:form>