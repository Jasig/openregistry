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
    <fieldset id="addrole">

        <legend>
            <span>
                <spring:message code="addRolePage.heading"/>: <em><c:out value="${role.affiliationType.description}"/></em>&nbsp;&nbsp;
                <spring:message code="title.label"/> <em><c:out value="${role.title}"/></em>&nbsp;&nbsp;
                <spring:message code="to.label"/> <em><c:out value="${sorPerson.formattedName}"/></em>
            </span>
        </legend>

        <p style="margin-bottom:0;"><spring:message code="requiredFields.heading"/> <span style="color:#b00;">*</span>.</p>

        <p class="label first"><spring:message code="specifyRoleInfo.heading"/></p>

        <div class="row">
            <div class="q first startdate">
                <label for="c1_startdate"><spring:message code="startDate.input.label"/> <em>*</em></label>
                <form:input path="start" id="c1_startdate" size="17" maxlength="10"  />
            </div>
            <div class="q"><br /><br />-</div>
            <div class="q enddate">
                <label for="c1_enddate"><spring:message code="endDate.input.label"/>
                    <c:if test="${role.affiliationType.description == 'GUEST'}"><em>*</em></c:if></label>
                <form:input path="end" id="c1_enddate" size="17" maxlength="10"  />
            </div>
        </div>

        <c:if test="${!(role.affiliationType.description=='RETIREE')}">
            <div class="row title">
                <label for="c1_title"><spring:message code="roleTitle.label"/></label>
                <form:input path="title" id="c1_title" size="90" maxlength="50" />
            </div>

            <div class="row organizationalUnit">
                <label for="c1_organizationalUnit"><spring:message code="organizational.label"/><em>*</em></label>

                <div class="select">
                    <form:select id="c1_organizationalUnit" path="organizationalUnit" >
                        <c:forEach items="${orgUnits}" var="orgUnit">
                            <option value="${orgUnit.id}" <c:if test="${orgUnit.id == role.organizationalUnit.id}">selected </c:if>>
                                ${orgUnit.name}

                                <%--<c:if test="${orgUnit.parentOrganizationalUnit != null && orgUnit.parentOrganizationalUnit.parentOrganizationalUnit == null}">--%>
                                    <%---- [RBHS]--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${orgUnit.parentOrganizationalUnit != null && orgUnit.parentOrganizationalUnit.parentOrganizationalUnit != null}">--%>
                                    <%---- [${orgUnit.parentOrganizationalUnit.name} / RBHS]--%>
                                <%--</c:if>--%>

                                    <c:if test="${orgUnit.RBHS != null && orgUnit.RBHS == 'Y'}">
                                        <c:if test="${orgUnit.parentOrganizationalUnit != null}">
                                                -- [${orgUnit.parentOrganizationalUnit.name} / RBHS]
                                        </c:if>
                                        <c:if test="${orgUnit.parentOrganizationalUnit == null}">
                                                -- [RBHS]
                                        </c:if>
                                    </c:if>

                                    <c:if test="${orgUnit.campus != null}">
                                    (${orgUnit.campus.name})
                                </c:if>
                            </option>
                        </c:forEach>
                        <%--<form:options items="${orgUnits}" itemValue="id" itemLabel="name"/>--%>
                    </form:select>
                </div>
            </div>
        </c:if>
        <c:if test="${role.affiliationType.description == 'GUEST'}">
            <div class="row accountReason">
                <label for="c1_accountReason"><spring:message code="accountReason.label"/><c:if test="${level == 'rbhsGuest'}"><em>*</em></c:if></label>
                <div class="select">
                    <SELECT class="c1_accountReason" name="accountReason" id="c1_accountReason">
                        <option value=""></option>
                        <c:forEach var="accountReason" items="${accountReasons}">
                            <OPTION value="${accountReason.description}">${accountReason.description}</OPTION>
                        </c:forEach>
                    </SELECT>
                </div>
            </div>
            <div class="row ">
                <div class="q first">
                <label for="sponsorGuest"><span><spring:message
                        code="sponsorGuest.input.label"/></span>
                    <input type="checkbox" name="sponsorGuest" id="sponsorGuest" /></label>
                </div>
                <div class="q">
                <label for="hidePersonInfo"><span><spring:message
                            code="hidePersonInfo.input.label"/></span>
                        <input type="checkbox" name="hidePersonInfo" id="hidePersonInfo" /></label>
                </div>
            </div>
        </c:if>
        <div class="row">
            <%--<label for="c1_email"><spring:message code="email.label"/> (${role.emailAddresses[0].addressType.description})</label>--%>
            <div class="q first">
                <c:if test="${role.affiliationType.description != 'GUEST'  &&  level != 'rbhsHouseStaff'}">
                    <label for="c1_email"><spring:message code="email.label"/></label>
                </c:if>
                <c:if test="${role.affiliationType.description == 'GUEST'  || level =='rbhsHouseStaff'}">
                    <label for="c1_email"><spring:message code="email.label"/><em>*</em></label>
                 </c:if>
                 <form:input path="emailAddresses[0].address" id="c1_email" size="60" maxlength="60" />
            </div>

            <div class="q">
                <c:if test="${role.affiliationType.description == 'GUEST'}">
                    <label for="disableEmail"><span><spring:message
                                  code="guest.disableEmail.input.label"/></span>
                    <input type="checkbox" name="disableEmail" id="disableEmail" /></label>
                </c:if>
            </div>
        </div>

        <c:if test="${!isEarlyFS}">
            <c:choose>
                <c:when test="${role.affiliationType.description == 'RETIREE'}">
                    <p class="label"><spring:message code="homeAddressInfo.heading"/>
                    <input type="checkbox" name="hideHomeAddress" id="hideHomeAddress"/>
                    <label for="hideHomeAddress"><span><spring:message
                        code="hideHomeAddress.input.label"/></span></label></p>
                </c:when>
                <c:otherwise>
                    <p class="label"><spring:message code="addressInfo.heading"/> (${role.addresses[0].type.description})</p>
                </c:otherwise>
            </c:choose>
            <div class="row">
                <div class="q address1 first">
                    <label for="c1_address1"><spring:message code="addressLine1.label"/></label>
                    <form:input path="addresses[0].line1" id="c1_address1" size="39" maxlength="30" />
                </div>
                <div class="q address2">
                    <label for="c1_address2"><spring:message code="addressLine2.label"/></label>
                    <form:input path="addresses[0].line2" id="c1_address2" size="39" maxlength="30" />
                </div>
                <div class="q address3">
                    <label for="c1_address3"><spring:message code="addressLine3.label"/></label>
                    <form:input path="addresses[0].line3" id="c1_address3" size="39" maxlength="30" />
                </div>
            </div>

            <div class="row">
                <div class="q first">
                    <label for="c1_city"><spring:message code="city.label"/></label>
                    <form:input path="addresses[0].city" id="c1_city" size="39" maxlength="30" />
                </div>
                <div class="q region">
                <label for="c1_state" class="state"><spring:message code="region.label"/></label>
                    <div class="select region">
                        <form:select path="addresses[0].region" id="c1_state" items="${regions}" size="1" />
                    </div>
                </div>
                <div class="q zip">
                    <label for="c1_zip"><spring:message code="postalCode.label"/></label>
                    <form:input path="addresses[0].postalCode" id="c1_zip" size="17" maxlength="10" />
                </div>
            </div>

            <%--
                            <div class="row">
                                <label for="c1_country"><spring:message code="country.label"/></label>
                                <div class="select country">
                                    <form:select path="addresses[0].country" id="c1_country" items="${countries}" itemValue="id">
                                    <itemLabel="name" size="1" />
                                </div>
                            </div>
            --%>


            <p class="label"><spring:message code="phoneNumber.heading" />
                <c:if test="${role.affiliationType.description == 'RETIREE'}">
                    <input type="checkbox" name="hidePhoneNumber" id="hidePhoneNumber"/>
                    <label for="hidePhoneNumber"><span><spring:message
                            code="hidePhoneNumber.input.label"/></span></label>
                </c:if></p>

            <c:forEach var="phone" items="${role.phones}" varStatus="loopStatus">
                <div class="row">
                    <div class="q phone type">
                        ${phone.addressType.description} - ${phone.phoneType.description}:
                    </div>
                </div>
                <div class="row">
                    <div class="q first areacode">
                        <label for="c1_areacode"><spring:message code="areaCode.label"/></label>
                        <form:input path="phones[${loopStatus.index}].areaCode" id="c1_areacode" size="3"
                                    maxlength="3" />
                    </div>

                    <div class="q"><br/><br/>-</div>

                    <div class="q number">
                        <label for="c1_number"><spring:message code="number.label"/></label>
                        <form:input path="phones[${loopStatus.index}].number" id="c1_number" size="7"
                                    maxlength="7" />
                    </div>

                    <div class="q ext">
                        <label for="c1_ext"><spring:message code="ext.label"/>.</label>
                        <form:input path="phones[${loopStatus.index}].extension" id="c1_ext" size="10"
                                    maxlength="10" />
                    </div>
                </div>
            </c:forEach>

        </c:if>
        <c:if test="${role.affiliationType.description == 'GUEST'}">
            <p class="label">Sponsor and Network Herd:</p>
            <div class="row ">
                <div class="q first identType">
                    <label for="identifierType"><spring:message code="sponsorIdType.label"/></label>

                    <div class="select">
                        <select name="identifierType" id="identifierType" >
                            <c:forEach var="identifierType" items="${identifierTypes}">
                                <option value="${identifierType.name}">${identifierType.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="q sponsor">
                    <label for="sponsorId"><spring:message code="sponsor.label"/> <em>*</em></label>
                    <c:if test="${wfNGRequest != null}">
                         <input id="sponsorId" size="17" name="identifier" type="text" value ="${wfNGRequest.sponsorNetID}"/>
                    </c:if>
                    <c:if test="${wfNGRequest == null}">
                         <input id="sponsorId" size="17" name="identifier" type="text"/>
                    </c:if>
                </div>
            </div>
            <div class="row ">
                <div class="q first herdName">
                    <label for="herdName"><spring:message code="herd.input.label"/>
                        <c:if test="${role.affiliationType.description == 'GUEST'}"><em>*</em></c:if></label>

                    <div class="select">
                        <SELECT class="herdName" name="herdName" id="herdName" >
                            <c:if test="${empty herdNames}">
                                <OPTION value="ACS_MISC" selected="selected">ACS_MISC</OPTION>
                            </c:if>
                            <c:if test="${wfNGRequest != null}">
                                <OPTION value="ACS_MISC" selected="selected">ACS_MISC</OPTION>
                            </c:if>
                            <c:if test="${not empty herdNames}">
                                <c:forEach var="herdNm" items="${herdNames}">
                                    <c:choose>
                                        <c:when test="${herdNm=='ACS_MISC'}">
                                            <OPTION value="${herdNm}" selected="selected">${herdNm}</OPTION>
                                        </c:when>
                                        <c:otherwise>
                                            <OPTION value="${herdNm}" >${herdNm}</OPTION>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </SELECT>
                    </div>
                </div>
                <div class="q herdExpDate">
                    <label for="herdExpDate"><span><spring:message
                            code="herdExpDate.input.label"/></span>
                        <c:if test="${role.affiliationType.description == 'GUEST'}"><em>*</em></c:if>
                    </label>
                    <c:if test="${wfNGRequest == null}">
                        <input name="herdExpDate" id="herdExpDate" size="10" maxlength="10"/>
                    </c:if>
                    <c:if test="${wfNGRequest != null}">
                        <input name="herdExpDate" id="herdExpDate" size="10" maxlength="10" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${role.end}'/>" />
                    </c:if>

                </div>
            </div>
        </c:if>
        <c:if test="${earlyFSRequest != null}">
            <hr></br>
            <legend><span><spring:message code="earlyFS.requestProcessed.heading"/></span></legend>
            <div class="row comments">
                <p class="instructions"><spring:message code="earlyFS.approved.instruction"/></p>
                <label for="reason" class="reason"><spring:message code="earlyFS.requestProcessed.comment.label"/></label>
                <input id="reason" size="100" name="reason" type="text"
                    value="<spring:message code='fs.approved.defaultStatusReason'/>"/>
            </div>
        </c:if>
        <c:if test="${wfNGRequest != null}">
            <hr>
            </br>
            <legend><span><spring:message code="guest.wfNG.requestProcessed.heading"/></span></legend>
            <div class="row comments">
                <label for="reason" class="reason"><spring:message
                        code="guest.wfNG.requestProcessed.comment.label"/></label>
                <input id="reason" size="100" name="reason" type="text"
                       value="<spring:message code='guest.wfNG.requestProcessed.defaultStatusReason'/>"/>
            </div>
        </c:if>
        <br><br>
        <c:if test='${empty infoModel}'>
            <div class="row">
                <input type="submit" id="fm-search-submit1" name="_eventId_submitAddRole" class="btn-submit" value="Add Role" onclick="return window.confirm('Save the new role?');"/>
                <%--&nbsp;or&nbsp;--%>

                <%--<c:choose>--%>
                    <%--<c:when test="${earlyFSRequest != null}">--%>
                        <%--&lt;%&ndash;<input type="submit" id="fm-search-cancel" name="_eventId_cancelAddFsRole" class="btn-cancel" value="Cancel" />&ndash;%&gt;--%>
                        <%--<input type="submit" id="fm-search-back" name="_eventId_backAddFsRole" class="btn-cancel" value="Back" />--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--<input type="submit" id="fm-search-back" name="_eventId_backAddRole" class="btn-cancel" value="Back" />--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>
            </div>
        </c:if>
    </fieldset>

</form:form>