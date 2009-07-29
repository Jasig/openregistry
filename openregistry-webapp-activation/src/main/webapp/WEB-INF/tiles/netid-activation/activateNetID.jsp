<%--
  Created by IntelliJ IDEA.
  User: nmond
  Date: May 18, 2009
  Time: 4:37:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form modelAttribute="identifier">
    <fieldset id="activateNetID">
        <legend><span><spring:message code="netIDActivationPage.heading"/></span></legend>
        <p style="margin-bottom:0;">
            <spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
        </p>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <div class="row">
                <label for="c1_netID" class="netid"><spring:message code="netID.label"/><em>*</em></label>
                <form:input path="value" id="c1_netID" size="10" maxlength="8" tabindex="1"/>
            </div>
            <div class="row">
                <label for="c1_activationKey" class="activationKey"><spring:message code="activationKey.label"/><em>*</em></label>
                <input type="activatationKey" id="c1_activationKey" value="" name="activationKey" size="10" />
            </div>
                <div class="row">
                <label for="c1_password" class="password"><spring:message code="password.label"/><em>*</em></label>
                <input type="password" id="c1_password"value="" name="password" size="10" class="password"/>
            </div>
        </fieldset>
    </fieldset>

    <c:if test='${empty addSucceeded}'>
        <div class="row fm-v" style="clear:both;">
            <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitNetIDActivation" class="btn-submit" value="Activate" tabindex="19"/>
        </div>
    </c:if>

</form:form>