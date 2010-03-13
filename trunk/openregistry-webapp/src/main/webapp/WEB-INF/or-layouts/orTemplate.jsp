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
    <link rel="stylesheet" href="<c:url value="/css/or_base.css" />" type="text/css"/>
    <tiles:useAttribute name="additionalCssFile" id="customCssFile" ignore="true" />
    <c:if test="${not empty customCssFile}">
        <link rel="stylesheet" href="${customCssFile}" type="text/css" />
    </c:if>
    <!--[if IE]><link rel="stylesheet" type="text/css" href="<c:url value="/css/or_ie.css" />" media="all"/><![endif]-->
    <link rel="stylesheet" href="<c:url value="/css/jquery-ui-1.8rc3.custom.css" />" />

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <tiles:useAttribute id="key" name="titleCode" classname="java.lang.String" />
    <title>OpenRegistry &raquo; <spring:message code="${key}" text="OpenRegistry"/> </title>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.4.1.js" /> "></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8rc3.custom.min.js" />"></script>

    <style type="text/css">
        #<%=key.replace(".", "")%> a {
            font-weight: bold;
        }
    </style>
</head>
<body>

    <%--insert the generic header tile--%>
    <%--<tiles:insertAttribute name="header" />--%>
    <tiles:insertTemplate template="/WEB-INF/or-tiles/base-template/orHeader.jsp"/>

    <div id="main">

        <tiles:insertAttribute name="content"/>

        <%--insert the generic footer tile--%>
        <%--<tiles:insertAttribute name="footer" />--%>
        <tiles:insertTemplate template="/WEB-INF/or-tiles/base-template/orFooter.jsp"/>
    </div>
</body>
</html>
