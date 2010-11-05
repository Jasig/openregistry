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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/themes/jasig/jasig-theme.css" />" type="text/css" />
    <link rel="stylesheet" href="<c:url value="/themes/jasig/jquery-ui-1.8.6.custom.css" />" />
	<link rel="stylesheet" href="<c:url value="/fluid/fss/css/fss-layout.css" />" type="text/css" />
    <tiles:useAttribute name="additionalCssFile" id="customCssFile" ignore="true" />
    <!--[if IE]><link rel="stylesheet" type="text/css" href="<c:url value="/css/or_ie.css" />" media="all"/><![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <tiles:useAttribute id="key" name="titleCode" classname="java.lang.String" />
    <title>OpenRegistry &raquo; <spring:message code="${key}" text="OpenRegistry"/> </title>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.4.2.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.6.custom.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.qtip-1.0.0-rc3.min.js" />"></script>

    <style type="text/css">
        #<%=key.replace(".", "")%> a {
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="fl-container-950 fl-centered">
	<div class="fl-container-flex header">
		<tiles:insertTemplate template="/WEB-INF/tiles/base-template/Header.jsp"/>
	</div>

	<div id="or-status-bar" class="or-status-bar status-bar">
		<tiles:insertTemplate template="/WEB-INF/tiles/base-template/StatusBar.jsp" />
	</div>

	<div id="or-content" class="fl-container-flex content">
		<tiles:insertAttribute name="content"/>
	</div>

	<div class="fl-container-flex footer">
		<tiles:insertTemplate template="/WEB-INF/tiles/base-template/Footer.jsp"/>
	</div>
</div>
</body>
</html>
