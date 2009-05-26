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
    <fieldset id="netIDPassword">
        <legend><span><spring:message code="netIDActivationPage.heading"/></span></legend>
        <p style="margin-bottom:0;">
            <spring:message code="requiredFields.heading" /><span style="color:#b00;">*</span>.
        </p>
        <br/>

        <fieldset class="fm-h" id="ecn1">
            <label class="desc" ><spring:message code="password.heading"/><c:out value="${identifier.value}"/></label>
                <div class="row">
                    <label class="password"><spring:message code="password.label"/><em>*</em></label>
                    <input type="password" value="" name="password" size="10"/>
                </div>

        </fieldset>
    </fieldset>

    <c:if test='${empty addSucceeded}'>
        <div class="row fm-v" style="clear:both;">
            <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitNetIDPassword" class="btn-submit" value="Enter" tabindex="19"/>
        </div>
    </c:if>

</form:form>