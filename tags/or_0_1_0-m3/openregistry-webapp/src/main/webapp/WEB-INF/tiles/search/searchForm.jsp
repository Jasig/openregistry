<%--

    Copyright (C) 2009 Jasig, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${errorMessage != null}"><div id="status" class="box"><p>${errorMessage}</p></div><script type="text/javascript">setTimeout("createFadeOutEffect()", 5000);</script></c:if>
<c:if test="${success != null}"><div id="status" class="box"><p><strong><spring:message code="deleteSuccess" /></strong></p></div><script type="text/javascript">setTimeout("createFadeOutEffect()", 5000);</script></c:if>

<div>
   <h2><strong><span style="padding:10px;"/><spring:message code="delete.person.headers.find" /></strong></h2>
   <form:form commandName="searchCriteria" method="post" id="orForm">
       <form:errors path="*" element="p" id="globalErrors" />
       <ul>
           <li class="container">
               <form:label path="givenName"><spring:message code="person.biodem.names.label" /></form:label>
               <span>
               <form:input path="givenName" cssErrorClass="formerror" />
               <form:label path="givenName"><spring:message code="person.biodem.names.given.label" /></form:label>
               </span>

               <span>
               <form:input path="familyName" cssErrorClass="formerror" />
                <form:label path="familyName"><spring:message code="person.diodem.names.family.label" /></form:label>
               </span>
           </li>
           <li class="container">
               <form:label path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
              <span>
               <form:input path="dateOfBirth" cssErrorClass="formerror" />
              </span>
           </li>
           <li class="container">
               <form:label path="identifierType" for="identifierType">Identifier</form:label>
               <span>
               <form:select path="identifierType" items="${identifierTypes}" itemLabel="name" itemValue="name" />
               <form:input path="identifierValue" cssErrorClass="formerror" />
              </span>
           </li>
           <li>
               <input type="hidden" name="_eventId" value="submit" />
               <button id="submitButton">Search</button></li>
       </ul>
   </form:form>
</div>