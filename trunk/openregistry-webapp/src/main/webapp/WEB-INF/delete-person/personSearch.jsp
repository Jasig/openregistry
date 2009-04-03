<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Delete a Person</title>
        <style type="text/css">
            .formerror {background-color: red;}
        </style>
    </head>
    <h1>Delete a Person</h1>

    <c:if test="${errorMessage != null}"><p>${errorMessage}</p></c:if>
    <c:if test="${successMessage != null}"><p><strong>${successMessage}</strong></p></c:if>
    <body>
       <form:form commandName="searchCriteria" method="POST">
           <h2>Find Person by...</h2>

           <form:errors path="*" element="p" id="globalErrors" />
           
           <fieldset>
               <form:label path="preferredName.prefix">Prefix</form:label>
               <form:select path="preferredName.prefix" cssErrorClass="formerror">
                   <form:option value="Mr." label="Mr." />
                   <form:option value="Mrs." label="Mrs." />
                   <form:option value="Ms." label="Ms." />
                   <form:option value="Miss" label="Miss" />
                   <form:option value="Dr." label="Dr." />
               </form:select> <form:errors path="preferredName.prefix" />

               <form:label path="preferredName.given">Given</form:label>
               <form:input path="preferredName.given" cssErrorClass="formerror" />

               <form:label path="preferredName.middle">Middle</form:label>
               <form:input path="preferredName.middle" cssErrorClass="formerror" />

               <form:label path="preferredName.family">Family</form:label>
               <form:input path="preferredName.family" cssErrorClass="formerror" />

               <form:label path="preferredName.suffix">Suffix</form:label>
               <form:input path="preferredName.suffix" cssErrorClass="formerror" />
           </fieldset>

           <fieldset>
               <form:label path="gender">Gender</form:label>
               <form:select path="gender" cssErrorClass="formerror">
                   <form:option value="" label="" />
                   <form:option value="M" label="Male" />
                   <form:option value="F" label="Female" />
               </form:select>
               
               <form:label path="dateOfBirth">Date of Birth</form:label>
               <form:input path="dateOfBirth" cssErrorClass="formerror" />
           </fieldset>

           <fieldset>
               <form:label path="identifiers[0].type">Identifier Type:</form:label>
               <form:select path="identifiers[0].type" cssErrorClass="formerror" itemLabel="name" itemValue="name" items="${identifierTypes}" />

               <form:label path="identifiers[0].value">Value</form:label>
               <form:input path="identifiers[0].value" cssErrorClass="formerror" />
           </fieldset>

           <input type="submit" value="Search -&gt;" name="_eventId_submit" />
       </form:form>
    
        <c:if test="${searchResults != null and fn:length(searchResults) gt 0}">
           <h2>Search Results</h2>
           <ul>
           <c:forEach items="${searchResults}" varStatus="status" var="result">
                <li><a href="${flowExecutionUrl}&_eventId=display&searchId=${status.index}">${result.person.preferredName}</a></li>           
           </c:forEach>
           </ul>
        </c:if>
    </body>
</html>