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

<form:form modelAttribute="address">
    <fieldset id="updateAddress">
        <legend><span><spring:message code="updateAddress.heading"/></span></legend>
        <p style="margin-bottom:0;"><spring:message code="requiredFields.heading"/><span style="color:#b00;">*</span>.
        </p>
        <br/>

        <fieldset id="update-1">
            <label class="desc2" for="c1_startdate"><span
                    style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label"/></span>
                <em2><c:out value="${sorPerson.formattedName}"/></em2>
            </label>
            <label class="desc2" for="c1_startdate"><span
                    style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading"/></span>
                <em2><c:out value="${role.affiliationType.description}"/>/<c:out value="${role.title}"/></em2>
            </label>
            <br/><br/>

            <label class="desc"><spring:message code="address.heading"/></label>

            <div class="row">
                <label for="c1_type"><spring:message code="addressType.label"/></label>

                <div class="select addressType">
                    <form:select path="type" id="c1_type" items="${addressTypes}" itemValue="id" itemLabel="description"
                                 size="1" />
                </div>
            </div>
            <div class="row">
                <label for="c1_address1" class="address1"><spring:message code="addressLine1.label"/></label>
                <form:input path="line1" id="c1_address1" size="30" maxlength="30" />
            </div>
            <div class="row">
                <label for="c1_address1" class="address1"><spring:message code="addressLine2.label"/></label>
                <form:input path="line2" id="c1_address1" size="30" maxlength="30" />
            </div>
            <div class="row">
                <label for="c1_address1" class="address1"><spring:message code="addressLine3.label"/></label>
                <form:input path="line3" id="c1_address1" size="30" maxlength="30" />
            </div>

            <div class="row">
                <label for="c1_city"><spring:message code="city.label"/></label>
                <form:input path="city" id="c1_city" size="30" maxlength="30" />
            </div>

            <div class="row">
                <label for="c1_state" class="state"><spring:message code="region.label"/></label>
                <%--<form:input path="region" id="c1_state" size="10" maxlength="10" />--%>
                <div class="select region">
                    <form:select path="region" id="c1_state" items="${regions}" size="1" />
                </div>
            </div>

            <div class="row">
                <label for="c1_zip" class="zip"><spring:message code="postalCode.label"/></label>
                <form:input path="postalCode" id="c1_zip" size="10" maxlength="10" />
            </div>
            <%--<div class="row">--%>
                <%--<label for="c1_country"><spring:message code="country.label"/></label>--%>

                <%--<div class="select country">--%>
                    <%--<form:select path="country" id="c1_country" items="${countries}" itemValue="id" itemLabel="name"--%>
                                 <%--size="1" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<hr>--%>
            <%--<div class="row comments">--%>
                <%--<label for="changeComments" class="reason"><spring:message code="updatePerson.comment.label"/></label>--%>
                <%--<input type="text" name="changeComments"  id="changeComments" size="90" maxlength="100"/>--%>
            <%--</div>--%>
            <%--<br/><br/>--%>
        </fieldset>

    <c:if test='${empty infoModel}'>
        <div class="row">
            <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitUpdateAddressInfo"
                   class="btn-submit" value="Update Address" />
        &nbsp;or&nbsp;
        <input type="submit" id="fm-cancel" name="_eventId_submitCancelUpdateAddress"
               class="btn-cancel" value="Cancel" />
        </div>
    </c:if>
    </fieldset>

</form:form>