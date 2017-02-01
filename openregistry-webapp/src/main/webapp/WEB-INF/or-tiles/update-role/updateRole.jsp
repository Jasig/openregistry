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
<fieldset id="updateRole">
<legend><span><spring:message code="updateRolePage.heading"/></span></legend>
<p style="margin-bottom:0;"><spring:message code="requiredFields.heading"/><span style="color:#b00;">*</span>.
</p>
<br/>

<fieldset class="fm-h" id="ecn1">
<label class="desc2" for="c1_startdate">
    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label"/></span>
    <em2><c:out value="${sorPerson.formattedName}"/></em2>
    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading"/></span>
    <em2><c:out value="${role.affiliationType.description}"/></em2>
</label>
<br/><br/>

<label class="desc" for="c1_startdate"><spring:message code="roleInformation.heading"/></label>

<div class="row">
    <div style="float:left; margin-right:1em;">
    <label for="c1_startdate" class="startdate"><spring:message code="startDate.input.label"/><em>*</em></label>
    <form:input path="start" id="c1_startdate" size="10" maxlength="10" />
    </div>
    <div style="float:left;">
    <label for="c1_enddate" class="enddate"><spring:message code="endDate.input.label"/>
        <c:if test="${role.affiliationType.description == 'GUEST'}"><em>*</em></c:if></label>
    <form:input path="end" id="c1_enddate" size="10" maxlength="10" />
    </div>
</div>
<div class="row">
    <label for="c1_title" class="title"><spring:message code="roleTitle.label"/></label>
    <form:input path="title" id="c1_title" size="10" maxlength="50" />

    <%--<a href="${flowExecutionUrl}&_eventId=submitRenewRoleStandardRenewal"><img src="images/renew2.jpg"--%>
                                                                               <%--title="Renew for standard renewal period."/></a>--%>
    <%--<a href="${flowExecutionUrl}&_eventId=submitExpireRoleToday"><img src="images/expire.jpg"--%>
                                                                      <%--title="Expire today."/></a>--%>

</div>

<%--<div class="row">--%>
    <%--<label for="c1_UpdateRoleSponsor" class="updateRoleSponsor"><spring:message--%>
            <%--code="sponsor.label"/><em>*</em></label>--%>

    <%--<div class="select sponsor">--%>
        <%--<form:select path="sponsor.sponsorId" id="c1_UpdateRoleSponsor" items="${sponsorList}"--%>
                     <%--itemValue="id" itemLabel="formattedName" size="1" />--%>


    <%--</div>--%>

<div class="row">
    <label for="c1_organizationalUnit" class="organizationalUnit">
        <spring:message code="organizational.label"/><em>*</em>
    </label>
                    <%--ideally this code should work but it is not<form:select id="c1_organizationalUnit" cssClass="identType" path="organizationalUnit" title="">--%>
                           <%--<form:options  cssClass="ident"   items="${orgUnits}"  itemValue="id" itemLabel="name"/>--%>
                         <%--</form:select>--%>
    <div class="select">
        <select class="identType" id="c1_organizationalUnit"  name="organizationalUnit">
            <c:forEach var="orgUnit" items="${orgUnits}">
                <option value="${orgUnit.id}" <c:if test="${orgUnit.id eq role.organizationalUnit.id}"> selected="selected" </c:if>>
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
        </select>
    </div>
</div>

<c:if test="${role.affiliationType.description == 'GUEST'}">
<div class="row accountReason">
    <label for="c1_accountReason"><spring:message code="accountReason.label"/><c:if test="${level == 'rbhsGuest'}"><em>*</em></c:if></label>

    <div class="select">
        <SELECT class="c1_accountReason" name="accountReason" id="c1_accountReason">
            <option value=""></option>
            <c:forEach var="reason" items="${accountReasons}">
                <OPTION value="${reason.description}"
                        <c:if test="${reason.description == currentAccountReason}">selected</c:if>>${reason.description}</OPTION>
            </c:forEach>
        </SELECT>
    </div>
</div>
</c:if>

    <%--<label for="c1_pt" class="updateRolePt"><spring:message code="pt.label"/></label>--%>

    <%--<div class="select pt">--%>
        <%--<form:select path="percentage" id="c1_pt" size="1" >--%>
            <%--<form:option value="100" label="100"/>--%>
            <%--<form:option value="75" label="75"/>--%>
            <%--<form:option value="50" label="50"/>--%>
            <%--<form:option value="25" label="25"/>--%>
        <%--</form:select>--%>
    <%--</div>--%>

<!-- start Herd  -->
<label class="desc"><spring:message code="herd.heading"/>
    <input id="addHerdBtn" type="image" name="_eventId_submitAddHerd" src="images/add2.gif" title="add a herd"/>
</label>

<div>
    <table class="data" cellspacing="0" width="60%">
        <thead>
        <tr class="appHeadingRow">
            <th><spring:message code="herd.name.label"/></th>
            <th><spring:message code="herd.createDate.label"/></th>
            <th><spring:message code="herd.expDate.label"/></th>
            <th><spring:message code="herd.sponsor.label"/></th>
            <th><spring:message code="actions.label"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="herd" items="${herds}">
            <tr>
                <td>${herd.NETWORK_HERD_NM}</td>
                <td><fmt:formatDate pattern="MM/dd/yyyy" value="${herd.CREATE_DT}"/></td>
                <td><fmt:formatDate pattern="MM/dd/yyyy" value="${herd.EXPIRATION_DT}"/></td>
                <td><c:out value="${herd.NETID}"/></td>
                <td>
                    <a href="${flowExecutionUrl}&_eventId=submitUpdateHerd&rcpId=${herd.RCP_ID}&herdName=${herd.NETWORK_HERD_NM}">
                        <spring:message
                            code="herd.update.label"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- end herd -->

<label class="desc"><spring:message code="emailAddress.heading"/>
    <input id="addEmailBtn" type="image" name="_eventId_submitAddEmail" src="images/add2.gif" title="add a new email address"/>
</label>

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
                <td valign="center">

                        <%--<form:select path="emailAddresses[${loopStatus.index}].addressType" id="c1_type"--%>
                                     <%--items="${addressTypes}" itemValue="id" itemLabel="description"/>--%>
                         <!-- only Home email for Retiree -->
                        <c:choose>
                            <c:when test="${role.affiliationType.description == 'RETIREE'}">
                                <select  id="c1_type" name="emailAddresses[<c:out value="${loopStatus.index}"/>].addressType">
                            </c:when>
                            <c:otherwise>
                                <select  id="c1_type" name="emailAddresses[<c:out value="${loopStatus.index}"/>].addressType">
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="addressType" items="${addressTypes}" varStatus="innerLoopStatus">
                            <c:if test="${addressType.description != 'OFFICE'}">
                            <option value="${addressType.id}"<c:if test="${addressType.id==role.emailAddresses[loopStatus.index].addressType.id}">selected="selected"</c:if>><c:out value="${addressType.description}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>

                </td>
                <td><form:input size="60" maxlength="60" class="title" path="emailAddresses[${loopStatus.index}].address"/></td>
                <td>
                    <a href="${flowExecutionUrl}&_eventId=submitRemoveEmailAddress&emailId=${loopStatus.index}"><img
                            src="images/trash.jpg" title="remove email address"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<label class="desc"><spring:message code="phones.heading"/>
    <input id="addPhoneBtn" type="image"  name="_eventId_submitAddPhone"  src="images/add2.gif"  title="add a new phone number"/>
</label>

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
                        <%--<form:select path="phones[${loopStatus.index}].addressType" id="c1_type"--%>
                                     <%--items="${addressTypes}" itemValue="id" itemLabel="description"/>--%>
                        <!-- only Home phone for Retiree -->
                        <c:choose>
                            <c:when test="${role.affiliationType.description == 'RETIREE'}">
                                <select  id="phone_type" name="phones[<c:out value="${loopStatus.index}"/>].addressType" >
                            </c:when>
                            <c:otherwise>
                                <select  id="phone_type" name="phones[<c:out value="${loopStatus.index}"/>].addressType">
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="addressType" items="${addressTypes}" varStatus="innerLoopStatus">
                            <option value="${addressType.id}"<c:if test="${addressType.id==role.phones[loopStatus.index].addressType.id}">selected="selected"</c:if>><c:out value="${addressType.description}"/></option>
                        </c:forEach>
                    </select>


                    </div>
                </td>
                <td>
                    <div>
                        <%--<form:select path="phones[${loopStatus.index}].phoneType" id="c1_type"--%>
                                     <%--items="${phoneTypes}" itemValue="id" itemLabel="description"/>--%>
                        <select  id="line_type" name="phones[<c:out value="${loopStatus.index}"/>].phoneType">
                        <c:forEach var="phoneType" items="${phoneTypes}" varStatus="innerLoopStatus">
                            <option value="${phoneType.id}"<c:if test="${phoneType.id==role.phones[loopStatus.index].phoneType.id}">selected="selected"</c:if>><c:out value="${phoneType.description}"/></option>
                        </c:forEach>
                    </select>
                    </div>
                </td>
                <td><form:input path="phones[${loopStatus.index}].countryCode" size="7"/></td>
                <td><form:input path="phones[${loopStatus.index}].areaCode" size="9"/></td>
                <td><form:input path="phones[${loopStatus.index}].number" size="20"/></td>
                <td><form:input path="phones[${loopStatus.index}].extension" size="7"/></td>
                <td><a href="${flowExecutionUrl}&_eventId=submitRemovePhone&phoneId=${loopStatus.index}"><img
                        src="images/trash.jpg" title="remove phone number"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${role.affiliationType.description == 'RETIREE'}">
    <div>
    <label class="desc">
        <spring:message code="hidePhoneNumber.input.label"/>
        <input type="checkbox" name="hidePhoneNumber" id="hidePhoneNumber" <c:out value="${hidePhoneNumber?'checked':''}"/> />
    </label>
    </div>
    <br>
</c:if>
<label class="desc"><spring:message code="address.heading"/>
    <input id="addAddressBtn" type="image" name="_eventId_submitAddAddress" src="images/add2.gif"  title="add a new address"/>
</label>
    <%--<input id="addAddressBtn" type="image" name="_eventId_submitAddAddress" src="images/add2.gif"  title="add a new address"/></label>--%>
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
                                <%--<form:select path="addresses[${loopStatus.index}].type" id="c1_type"--%>
                                             <%--items="${addressTypes}" itemValue="id"--%>
                                             <%--itemLabel="description"/>--%>
                                 <!-- only Home address for Retiree -->
                                <c:choose>
                                    <c:when test="${role.affiliationType.description == 'RETIREE'}">
                                        <select id="mail_address_type"name="addresses[<c:out value="${loopStatus.index}"/>].type">
                                    </c:when>
                                    <c:otherwise>
                                        <select id="mail_address_type" name="addresses[<c:out value="${loopStatus.index}"/>].type">
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="addressType" items="${addressTypes}" varStatus="innerLoopStatus">
                                        <option value="${addressType.id}"<c:if test="${addressType.id==role.addresses[loopStatus.index].type.id}">selected="selected"</c:if>><c:out value="${addressType.description}"/></option>
                                 </c:forEach>
                                </select>

                            </div>
                        </td>
                        <td>
                            <a href="${flowExecutionUrl}&_eventId=submitUpdateAddress&addressId=${loopStatus.index}">
                                <c:choose>
                                    <c:when test="${address.singleLineAddress =='null'}">
                                        click here to update address
                                    </c:when>
                                    <c:otherwise>${address.singleLineAddress}</c:otherwise>
                                </c:choose>
                            </a>
                        </td>
                        <td>
                            <a href="${flowExecutionUrl}&_eventId=submitRemoveAddress&addressId=${loopStatus.index}"><img
                                    src="images/trash.jpg" title="remove address"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise><spring:message code="noAddressesDefined.label"/><br/><br/></c:otherwise>
</c:choose>

 <c:if test="${role.affiliationType.description == 'RETIREE'}">
     <div>
     <label class="desc">
        <spring:message code="hideHomeAddress.input.label"/>
        <input type="checkbox" name="hideHomeAddress" id="hideHomeAddress"  <c:out value="${hideHomeAddress?'checked':''}"/>/>
     </label>
     </div>
     <br>
 </c:if>

<c:if test="${role.affiliationType.description == 'GUEST'}">
    <!-- disclosure for guest-->
    <label class="desc"><spring:message code="hidePersonInfo.input.label"/>
                <input type="checkbox" name="hidePersonInfo" id="hidePersonInfo"  <c:out value="${hidePersonInfo?'checked':''}"/>/>
    </label>
    <!-- start Sponsor Guest Role -->
    <c:choose>
        <c:when test="${isSponsorGuestRole}">
             <label class="desc"><spring:message code="sponsorGuestRole.yes.heading"/>
                <input id="addBtn" type="submit" name="_eventId_submitCloseSGRole" value="Close"/></label>
        </c:when>
        <c:otherwise>
              <label class="desc"><spring:message code="sponsorGuestRole.no.heading"/>
                <input id="addBtn" type="submit" name="_eventId_submitCreateSGRole" value="Create"/></label>
        </c:otherwise>
    </c:choose>
    <!-- end Sponsor Guest Role -->
    <!-- start Guest Mail Distribution -->
    <c:choose>
        <c:when test="${mailDist != null}">
            <label class="desc"><spring:message code="mailDist.heading"/>
                <input id="addBtn" type="image" name="_eventId_submitUpdateMailDist" src="images/edit_icon.jpg" title="Update Mail Distribution"/></label>
                <div>
                    <table>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.campus.label"/></td>
                            <td><em2><c:out value="${mailDist.campus}"/></em2><td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.division.label"/></td>
                            <td><em2><c:out value="${mailDist.division}"/></em2><td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.dept.label"/></td>
                            <td><em2><c:out value="${mailDist.dept}"/></em2><td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.building.label"/></td>
                            <td><em2><c:out value="${mailDist.building}"/></em2><td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.roomNo.label"/></td>
                            <td><em2><c:out value="${mailDist.roomNo}"/></em2><td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align:right;"><spring:message code="mailDist.mailCode.label"/></td>
                            <td><em2><c:out value="${mailDist.mailCode}"/></em2><td>
                        </tr>
                    </table>
                </div>
        </c:when>
        <c:otherwise>
             <label class="desc"><spring:message code="mailDist.heading"/>
                <input id="addBtn" type="image" name="_eventId_submitAddMailDist" src="images/add2.gif" title="Add Mail Distribution"/></label>
             <span><spring:message code="mailDist.noMailDist.info"/></span>
        </c:otherwise>
    </c:choose>
    <!-- end Guest Mail Distribution -->
</c:if>

<label class="desc"><spring:message code="urls.heading"/>
    <%--<input id="addURLBtn" type="image" name="_eventId_submitAddURL"  src="images/add2.gif" title="add a new URL"/></label>--%>
<c:choose>
    <c:when test="${not empty role.urls}">
        <div>
            <table class="data" cellspacing="0" width="50%">
                <thead>
                <tr class="appHeadingRow">
                    <th><spring:message code="type.label"/></th>
                    <th><spring:message code="value.label"/></th>
                    <%--<th><spring:message code="action.label"/></th>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="url" items="${role.urls}" varStatus="loopStatus">
                    <tr>
                        <td valign="center">
                            <div>
                                <form:select path="urls[${loopStatus.index}].type" id="c1_type"
                                             items="${urlTypes}" itemValue="id" itemLabel="description"/>
                            </div>
                        </td>
                        <td><form:input path="urls[${loopStatus.index}].url" size="60"/></td>
                        <%--<td><a href="${flowExecutionUrl}&_eventId=submitRemoveURL&urlId=${loopStatus.index}"><img--%>
                                <%--src="images/trash.jpg" title="remove URL"/></a></td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise><spring:message code="noUrlsDefined.label"/><br/><br/></c:otherwise>
    </c:choose>
    <hr>
    <div class="row comments">
        <label for="changeComments" class="reason"><spring:message code="updatePerson.comment.label"/></label>
        <input type="text" name="changeComments"  id="changeComments" size="90" maxlength="100"/>
    </div>
</fieldset>

<c:if test='${empty infoModel}'><br>
    <div class="row fm-v" style="clear:both;">
        <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdateRole"
               class="btn-submit" value="Update Role" onclick="return window.confirm('Save the update?');"/>
    </div>
</c:if>

</fieldset>
</form:form>