<%--
  Created by IntelliJ IDEA.
  User: sheliu
  Date: 11/19/12
  Time: 9:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form modelAttribute="auditAttributes">
    <fieldset id="AuditAttributes">

        <legend><span><spring:message code="audit.viewAudit.title"/></span></legend>

        <c:if test="${auditLevel == 'person'}" >
            <span class="label first"><spring:message code="audit.heading.label"/>&nbsp;<em><c:out value="${person.officialName.longFormattedName}"/></em>&nbsp;&nbsp;
        </c:if>
        <c:if test="${auditLevel != 'person'}" >
            <span class="label first"><spring:message code="audit.heading.sor.label"/>&nbsp;<em><c:out value="${person.officialName.longFormattedName}"/></em>&nbsp;&nbsp;
            <spring:message code="audit.source.label"/>&nbsp;<em><c:out value="${auditLevel}"/></em>
        </c:if>

        </span>
        <br><br>
        <p><spring:message code="audit.selectAttr.Info"/><span style="float: right"><spring:message code="audit.selectAttr.Info2"/></span></p>

        <div class="row" style="padding: -0.5">
            <div class="q first auditPeriod">
                <label for="c1_auditPeriod"><spring:message code="audit.auditPeriod.label"/></label>
                    <div class="select">
                        <form:select id="c1_auditPeriod" path="auditPeriod">
                            <form:option value="0">All</form:option>
                            <form:option value="1">Past 1 Month</form:option>
                            <form:option value="2">Past 2 Month</form:option>
                            <form:option value="3">Past 3 Month</form:option>
                            <form:option value="4">Past 4 Month</form:option>
                            <form:option value="5">Past 5 Month</form:option>
                            <form:option value="6">Past 6 Month</form:option>
                            <form:option value="12">Past 1 Year</form:option>
                            <form:option value="24">Past 2 Year</form:option>
                            <%--<form:option value="36">Past 3 Year</form:option>--%>
                            <%--<form:option value="48">Past 4 Year</form:option>--%>
                            <%--<form:option value="60">Past 5 Year</form:option>--%>
                        </form:select>
                    </div>
            </div>
        </div>

        <div class="row" style="padding: 0">
            <div class="q first">
                <label for="c1_personInfo"><spring:message code="audit.personInfo.label"/>
                    <form:checkbox path="auditPersonInfo" id="c1_personInfo" /></label>

            </div>
        </div>

        <div class="row">
            <div class="q first">
                <label for="c1_roles"><spring:message code="audit.roles.label"/>
                    <form:checkbox path="auditRoles" id="c1_roles" /></label>

            </div>
        </div>

        <div class="row">
            <div class="q first">
                <label for="c1_names"><spring:message code="audit.names.label"/>
                    <form:checkbox path="auditNames" id="c1_names" /></label>

            </div>
        </div>

        <c:if test="${auditLevel == 'person'}" >
        <div class="row">
            <div class="q first">
                <label for="c1_identifiers"><spring:message code="audit.identifiers.label"/>
                    <form:checkbox path="auditIdentifiers" id="c1_identifiers" /></label>

            </div>
        </div>
        </c:if>

        <div class="row">
            <div class="q first">
                <label for="c1_addresses"><spring:message code="audit.addresses.label"/>
                    <form:checkbox path="auditAddresses" id="c1_addresses" /></label>
            </div>
        </div>

        <div class="row">
            <div class="q first">
                <label for="c1_emails"><spring:message code="audit.emails.label"/>
                    <form:checkbox path="auditEmails" id="c1_emails"  /></label>
            </div>
        </div>

        <div class="row">
            <div class="q first">
                <label for="c1_phones"><spring:message code="audit.phones.label"/>
                    <form:checkbox path="auditPhones" id="c1_phones"  /></label>
            </div>
        </div>

        <div class="row">
            <div class="q first">
                <label for="c1_iDCard"><spring:message code="audit.IdCard.label"/>
                     <form:checkbox path="auditIdCard" id="c1_iDCard"  /></label>
            </div>
        </div>

        <br><br>
        <div class="row">
            <c:if test="${auditLevel == 'person'}" >
                <input type="submit" id="fm-submit1" name="_eventId_submitAuditPerson" class="btn-submit" value="View Audit"/>
            </c:if>
            <c:if test="${auditLevel != 'person'}" >
                <input type="submit" id="fm-submit1" name="_eventId_submitAuditSorPerson" class="btn-submit" value="View Audit"/>
            </c:if>
        </div>
    </fieldset>

</form:form>