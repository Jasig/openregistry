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
<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
    <style type="text/css" media="screen">
        /* prevent IE < 6 from seeing CSS */
        @import '<c:url value="/css/common_0.1.css" />';
        @import '<c:url value="/css/openregistry_0.1.css" />'/**/;
    </style>
    <link href="<c:url value="/resources/dojo/resources/dojo.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/dijit/themes/nihilo/nihilo.css" />" rel="stylesheet" />
    <link href="<c:url value="/css/or/or.forms.css" />" rel="stylesheet" />

	<c:choose>
        <c:when test="${not empty taskTitleCode}">
            <title>OpenRegistry <spring:message code="${taskTitleCode}" /> </title>
        </c:when>
            <c:otherwise>
                <tiles:useAttribute id="key" name="titleCode"/>
                <title>OpenRegistry <spring:message code="${key}" /> </title>
            </c:otherwise>
    </c:choose>
    <tiles:insertAttribute name="head" />
    </head>
     <body class="openregistry nihilo" >
	    <h1 id="app-name">OpenRegistry</h1>
        <ul id="nav-system">
            <li id="home"><a href="." title="home page"><spring:message code="home.label" /></a></li>
            <li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li>
        </ul>
        <c:choose>
            <c:when test="${not empty taskTitleCode}">
                <h2><span style="display:block; font-size:1em; font-weight:800; color:#d21033; text-transform:uppercase;margin:1.5em 0; padding:10px;" /><spring:message code="${taskTitleCode}" /></h2>
            </c:when>
            <c:otherwise>
                <tiles:useAttribute id="key" name="titleCode"/><h2><spring:message code="${key}" /></h2>
            </c:otherwise>
        </c:choose>
        <tiles:insertAttribute name="content" />
		
        <div class="footer">
            <p><spring:message code="footer.copyright.text" /></p>
        </div>
    </body>
</html>