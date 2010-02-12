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
    <link rel="stylesheet" href="<spring:theme code='baseSheet'/>" type="text/css"/>
    <!--[if IE]><link rel="stylesheet" type="text/css" href="<spring:theme code='ieSheet'/>" media="all"/><![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <tiles:useAttribute id="key" name="titleCode"/>
    <title>OpenRegistry &raquo; <spring:message code="${key}" /> </title>
</head>
<body>

    <%--insert the generic header tile--%>
    <%--<tiles:insertAttribute name="header" />--%>
    <tiles:insertTemplate template="/WEB-INF/or-tiles/base-template/orHeader.jsp"/>


    <div id="main">

        You aren't really logged out, this is just a placeholder page until logout is properly implemented

        </div>


        <%--insert the generic footer tile--%>
        <%--<tiles:insertAttribute name="footer" />--%>
        <tiles:insertTemplate template="/WEB-INF/or-tiles/base-template/orFooter.jsp"/>

</body>
</html>
