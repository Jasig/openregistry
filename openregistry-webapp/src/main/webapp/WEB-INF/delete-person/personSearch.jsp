<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><spring:message code="delete.person.title" /></title>
        <link href="css/fluid/0.8/fluid.reset.css" rel="stylesheet" />
        <link href="css/fluid/0.8/fluid.layout.css" rel="stylesheet" />
        <link href="css/fluid/0.8/fluid.text.css" rel="stylesheet" />
        <link href="css/fluid/0.8/fluid.states.css" rel="stylesheet" />
        <link href="css/fluid/0.8/fluid.theme.rust.css" rel="stylesheet" />
        <link href="css/or/or.forms.css" rel="stylesheet" />

        <style type="text/css">
            .formerror {background-color: #f00;}
        </style>
    </head>
    <h1></h1>
    <c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>
    <c:if test="${successMessage != null}"><p><strong>${successMessage}</strong></p></c:if>
    <body  class="fl-theme-rust fl-font-sans">
        <div class="fl-container-750 fl-centered">
            <div class="fl-container-flex header">
                <h1><spring:message code="delete.person.title" /></h1>
            </div>
            <div class="fl-container-flex content">
               <form:form commandName="searchCriteria" method="POST" >
                   <h2><spring:message code="delete.person.headers.find" /></h2>

                   <form:errors path="*" element="p" id="globalErrors" />

                   <fieldset>
                       <legend>Name</legend>
                       <form:label path="preferredName.prefix"><spring:message code="person.biodem.names.prefix.label" /></form:label>
                       <form:select path="preferredName.prefix" cssErrorClass="formerror" cssClass="fl-textfield">
                           <form:option value="Mr." label="Mr." />
                           <form:option value="Mrs." label="Mrs." />
                           <form:option value="Ms." label="Ms." />
                           <form:option value="Miss" label="Miss" />
                           <form:option value="Dr." label="Dr." />
                       </form:select> <form:errors path="preferredName.prefix" />

                       <form:label path="preferredName.given"><spring:message code="person.biodem.names.given.label" /></form:label>
                       <form:input path="preferredName.given" cssErrorClass="formerror" cssClass="fl-textfield" />

                       <form:label path="preferredName.middle"><spring:message code="person.biodem.names.middle.label" /></form:label>
                       <form:input path="preferredName.middle" cssErrorClass="formerror" cssClass="fl-textfield" />

                       <form:label path="preferredName.family"><spring:message code="person.diodem.names.family.label" /></form:label>
                       <form:input path="preferredName.family" cssErrorClass="formerror" cssClass="fl-textfield" />

                       <form:label path="preferredName.suffix"><spring:message code="person.biodem.names.suffix.label" /></form:label>
                       <form:input path="preferredName.suffix" cssErrorClass="formerror" cssClass="fl-textfield" />
                   </fieldset>

                   <fieldset>
                        <legend>Identifiers</legend>
                       <form:label path="gender"><spring:message code="person.biodem.gender.label" /></form:label>
                       <form:select path="gender" cssErrorClass="formerror" cssClass="fl-textfield">
                           <form:option value="" label="" />
                           <form:option value="M"><spring:message code="person.biodem.gender.values.male" /></form:option>
                           <form:option value="F"><spring:message code="person.biodem.gender.values.female" /></form:option>
                       </form:select>

                       <form:label path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
                       <form:input path="dateOfBirth" cssErrorClass="formerror" cssClass="fl-textfield" />
                   </fieldset>

                   <fieldset>
                       <c:forEach items="${identifierTypes}" var="identifierType" varStatus="status">
                           <input type="hidden" value="${identifierType.name}" name="identifiers[${status.index}].type" />
                           <form:label path="identifiers[${status.index}].value">${identifierType.name}</form:label> <form:input path="identifiers[${status.index}].value" cssErrorClass="formerror" cssClass="fl-textfield" /><br/>
                       </c:forEach>
                   </fieldset>

                   <input type="submit" value="Search -&gt;" name="_eventId_submit" />
               </form:form>

                <c:if test="${searchResults != null and fn:length(searchResults) gt 0}">
                   <h2><spring:message code="delete.person.headers.results" /></h2>
                   <ul>
                   <c:forEach items="${searchResults}" varStatus="status" var="result">
                        <li><a href="${flowExecutionUrl}&_eventId=display&searchId=${status.index}">${result.person.preferredName}</a></li>
                   </c:forEach>
                   </ul>
                </c:if>
            </div>
            <div class="fl-container-flex footer">
                <p><spring:message code="footer.copyright.text" /></p>
            </div>
        </div>
    </body>
</html>