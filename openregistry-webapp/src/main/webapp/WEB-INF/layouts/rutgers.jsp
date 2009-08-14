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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css" media="screen">
        /* prevent IE < 6 from seeing CSS */
        @import '<c:url value="/css/common_0.1.css" />';
        @import '<c:url value="/css/openregistry_0.1.css" />'/**/;
    </style>
    <tiles:useAttribute id="key" name="titleCode"/>
    <title>OpenRegistry <spring:message code="${key}" /> </title>
    <!-- provide CSS to IE6 and 7 to fix rendering bugs; use conditional comments -->
    <!--[if gte IE 6]><style type="text/css" media="screen">@import 'css/ie_0.1.css';</style><![endif]-->
</head>
<body class="openregistry" id="update">

	<h1 id="app-name">OpenRegistry</h1>
    	<ul id="nav-system">
		<li id="home"><a href="." title="home page"><spring:message code="home.label" /></a></li>
		<li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li>
	</ul>

	<div id="content">
   		<c:set var="command" value="${command}" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/errors.jsp" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />
        <tiles:insertAttribute name="body" />
	</div>
    <div id="footer">
		<p>For questions, comments or suggestions contact <a href="http://rucs.camden.rutgers.edu/">Camden Help Desk</a>, <a href="http://www.ncs.rutgers.edu/helpdesk/">Newark Help Desk</a>, or <a href="http://www.nbcs.rutgers.edu/helpdesk/hdnew/">New Brunswick/Piscataway Help Desk</a>.</p>
		<p>Visit web sites for <a href="http://camden-www.rutgers.edu/">Camden campus</a>, <a href="http://rutgers-newark.rutgers.edu/">Newark campus</a>, <a href="http://nbp.rutgers.edu/">New Brunswick/Piscataway campus</a>, or <a href="http://www.rutgers.edu">Rutgers University</a>.</p>
		<a id="logo" href="http://www.rutgers.edu" title="go to Rutgers University home page"><img src="images/RU_logo.gif" width="150" height="40" alt="Rutgers logo" /></a>
	    <p>&copy; 2009 Rutgers, The State University of New Jersey. All rights reserved.</p>
    </div>
</body>
</html>
