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
        <style type="text/css">
            .formerror {background-color: #f00;}
        </style>
    </head>
    <h1><spring:message code="delete.person.title" /></h1>

    <c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>
    <c:if test="${successMessage != null}"><p><strong>${successMessage}</strong></p></c:if>
    <body>
       <form:form commandName="searchCriteria" method="POST">
           <h2><spring:message code="delete.person.headers.find" /></h2>

           <form:errors path="*" element="p" id="globalErrors" />
           
           <fieldset>
               <form:label path="preferredName.prefix"><spring:message code="person.biodem.names.prefix.label" /></form:label>
               <form:select path="preferredName.prefix" cssErrorClass="formerror">
                   <form:option value="Mr." label="Mr." />
                   <form:option value="Mrs." label="Mrs." />
                   <form:option value="Ms." label="Ms." />
                   <form:option value="Miss" label="Miss" />
                   <form:option value="Dr." label="Dr." />
               </form:select> <form:errors path="preferredName.prefix" />

               <form:label path="preferredName.given"><spring:message code="person.biodem.names.given.label" /></form:label>
               <form:input path="preferredName.given" cssErrorClass="formerror" />

               <form:label path="preferredName.middle"><spring:message code="person.biodem.names.middle.label" /></form:label>
               <form:input path="preferredName.middle" cssErrorClass="formerror" />

               <form:label path="preferredName.family"><spring:message code="person.diodem.names.family.label" /></form:label>
               <form:input path="preferredName.family" cssErrorClass="formerror" />

               <form:label path="preferredName.suffix"><spring:message code="person.biodem.names.suffix.label" /></form:label>
               <form:input path="preferredName.suffix" cssErrorClass="formerror" />
           </fieldset>

           <fieldset>
               <form:label path="gender"><spring:message code="person.biodem.gender.label" /></form:label>
               <form:select path="gender" cssErrorClass="formerror">
                   <form:option value="" label="" />
                   <form:option value="M"><spring:message code="person.biodem.gender.values.male" /></form:option>
                   <form:option value="F"><spring:message code="person.biodem.gender.values.female" /></form:option>
               </form:select>
               
               <form:label path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
               <form:input path="dateOfBirth" cssErrorClass="formerror" />
           </fieldset>

           <fieldset>
               <c:forEach items="${identifierTypes}" var="identifierType" varStatus="status">
                   <input type="hidden" value="${identifierType.name}" name="identifiers[${status.index}].type" />
                   <form:label path="identifiers[${status.index}].value">${identifierType.name}</form:label> <form:input path="identifiers[${status.index}].value" cssErrorClass="formerror" /><br/>
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
    </body>
</html>