<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<div id="content">
	<h2>${titleCode}</h2>

	<c:if test='${not empty infoModel}'>
		<div id="or-message" class="highlight">
			<h2><c:out value="${infoModel}"/></h2>
		</div>
	</c:if>

	<c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
		<c:choose>
			<c:when test="${message.severity eq 'Error'}">
				<c:set var="cssClass" value="'error'"/>
			</c:when>
			<c:otherwise>
				<c:set var="cssClass" value="'highlight'"/>
			</c:otherwise>
		</c:choose>
		<div class="${cssClass}" id="or-message">
			<h2><c:out value="${message.text}"/></h2>
		</div>
	</c:forEach>
</div>
