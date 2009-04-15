<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Test Title</title>
        <tiles:insertAttribute name="head" />
    </head>
    <body class="nihilo">
        <div id="header"><h1>OpenRegistry</h1><h2><spring:message code="delete.person.title" /></h2></div>
        <div id="content">
            <tiles:insertAttribute name="content" />
        </div>
        <div class="footer">
            <p><spring:message code="footer.copyright.text" /></p>
        </div>
    </body>
</html>