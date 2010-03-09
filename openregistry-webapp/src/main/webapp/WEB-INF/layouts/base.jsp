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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <link rel="stylesheet" href="<spring:theme code='styleSheet1'/>" type="text/css"/>
    <link rel="stylesheet" href="<spring:theme code='styleSheet2'/>" type="text/css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <tiles:useAttribute id="key" name="titleCode"/>
    <title>OpenRegistry <spring:message code="${key}" /> </title>
</head>
<body class="openregistry" id="update">

	<h1 id="app-name">OpenRegistry</h1>
    	<ul id="nav-system">
		<li id="home"><a href="main.htm" title="home page"><spring:message code="home.label" /></a></li>
		<li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li>
	</ul>

	<div id="content">
   		<c:set var="command" value="${command}" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/errors.jsp" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="body2" ignore="true" />
	</div>
    <div id="footer">
		<p><spring:message code="footer.copyright.text" /></p>
    </div>
</body>
</html>
