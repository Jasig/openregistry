<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="<c:url value="/resources/dojo/resources/dojo.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/dijit/themes/nihilo/nihilo.css" />" rel="stylesheet" />
<link href="<c:url value="/css/or/or.forms.css" />" rel="stylesheet" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
	
    <style type="text/css" media="screen">
        /* prevent IE < 6 from seeing CSS */
        @import '<c:url value="/css/common_0.1.css" />';
        @import '<c:url value="/css/openregistry_0.1.css" />'/**/;
    </style>
	<c:choose>
        <c:when test="${not empty taskTitleCode}">
            <title>OpenRegistry <spring:message code="${taskTitleCode}" /> </title>
        </c:when>
            <c:otherwise>
                <tiles:useAttribute id="key" name="titleCode"/>
                <title>OpenRegistry <spring:message code="${key}" /> </title>
            </c:otherwise>
    </c:choose>
     
    <ul id="nav-system">
		<li id="home"><a href="." title="home page"><spring:message code="home.label" /></a></li>
		<li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li>
	</ul>
    <tiles:insertAttribute name="head" />
    </head>
     <body class="nihilo" > 
	<h1 id="app-name">OpenRegistry</h1>

        <c:choose>
            <c:when test="${not empty taskTitleCode}">
                <div id="searchheader"> <h2><span style="display:block; font-size:1.3em; font-weight:900; color:#d21033; text-transform:uppercase;" /><spring:message code="${taskTitleCode}" /></h2></div>
            </c:when>
            <c:otherwise>
                <tiles:useAttribute id="key" name="titleCode"/>
                <div id="header"><h1>OpenRegistry</h1><h2><spring:message code="${key}" /></h2></div>
            </c:otherwise>
        </c:choose>

        <tiles:insertAttribute name="content" />
		
        <div class="footer">
            <p><spring:message code="footer.copyright.text" /></p>
        </div>
    </body>
</html>