<%--
  Created by IntelliJ IDEA.
  User: nmond
  Date: May 28, 2009
  Time: 5:13:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css" media="screen">
        /* prevent IE < 6 from seeing CSS */
        @import '<c:url value="css/common_0.1.css" />';
        @import '<c:url value="css/openregistry_0.1.css" />'/**/;
    </style>
</head>
<body class="openregistry" id="update">
	<h1 id="app-name">Open Registry</h1>
    <div id="content">
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />
	</div>
	<div id="content">
		<div id="sidebar">
            <h2>Manage People</h2>
			<ul>
	            <li><a href="addPerson.htm">Add Person</a></li>
	            <li><a href="updatePerson.htm">Update Person</a></li>
                <li><a href="splitPerson.htm">Move Sor Person Record</a></li>
            </ul>
			<h2>Important Notices</h2>
			<p>
				Notices go here.
			</p>
		</div>
	</div>
</body>
</html>