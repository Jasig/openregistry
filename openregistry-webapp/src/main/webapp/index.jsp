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
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Login - Registry - Rutgers University</title>

    <link rel="stylesheet" href="<spring:theme code='styleSheet1'/>" type="text/css"/>

    <style>
		#home p.login a:link, #home p.login a:visited {-moz-border-radius:10px; -webkit-border-radius:10px; background:#fff; border:1px solid #fea; color:#57c; display:block; margin:0 auto; padding:10px 0 !important; width:99%; text-align:center;}
		#home p.login a:hover, #home p.login a:focus, #home p.login a:active {background:#57c; border-color:#57c; color:#fff;}
		#home p.login a em {display:block; font-family:Georgia,'Times New Roman',Times,serif; font-size:2em;}
	</style>
	<!-- provide CSS to IE6 and above to fix rendering bugs; use conditional comments -->
	<!--[if gte IE 6]><style type="text/css" media="screen">@import 'css/ie.css';</style><![endif]-->
	<link rel="stylesheet" href="css/print.css" type="text/css" media="print" />
</head>

<%--<c:redirect url="/viewPersonDetail.htm"/>--%>

<body class="" id="home">
	<h1 id="app-name">Registry</h1>

	<div id="content">
		<div class="box">
			<h2>Registry Login</h2>
			<p class="login"><a href="main.htm">Continue to <em>NetID Login</em></a></p>

			<h3>About</h3>
			<p><a href="http://oit.rutgers.edu/" target="_blank">Office of Information Technology (OIT)</a> supports Registry</p>
			<p>Registry is Rutgers Person Registry System.  It stores data about people affiliated with the university.</p>

			<h3>Features</h3>
			<ul>
				<li>Person Registry Search</li>
				<li>Person Registry View Complete Person</li>
			</ul>
		</div>

		<div id="sidebar">
<!--			<h3>Registry Announcements</h3>
			<p>Schedule of Classes for Fall 2009 will be available on May 5, 2009.</p>

			<h3>Available Semesters</h3>
			<ul>
				<li><span style="color:#555;">Spring 2009</span></li>
				<li><span style="color:#555;">Summer 2009</span></li>
			</ul>
-->

			<h3>System Requirements</h3>
			<ul>
				<li><span style="color:#555;">Registry is best viewed in the latest versions of major browsers. This includes Firefox 3, Safari 3 and Internet Explorer 8. Please view our <a href="https://eas.rutgers.edu/eas-browser-recommendations.html">Browser Recommendations</a> page for more details.</span></li>
				<li><span style="color:#555;">JavaScript must be enabled in browser for Registry to function.</span></li>
			</ul>

			<h3>Registry Support</h3>
			<p>For questions, comments or suggestions contact <a href="mailto:idm_support@ess.rutgers.edu">idm_support@ess.rutgers.edu</a>.</p>
			<p>There are several <a href="known_issues.html">known issues</a> within Registry that our visitors should be aware of.</p>
			<!--p>Your input is very important. Please take a moment to fill out a short <a href="#">survey</a> and help us improve CSP.</p -->

			<h3>Security</h3>
			<p>For security reasons, we ask that all users log out of Registry after completing their tasks and close the browser. This will clear out any traces of sensitive information that may be used to compromise user's personal information.</p>

			<h3>Disclaimer</h3>
			<p>
				Do we need one?
			</p>

			<h3>Related Links</h3>
			<ul>
				<li><a target="_blank" href="https://dummy.rutgers.edu">RIAS Portal</a></li>
				<li><a target="_blank" href="https://dummy.rutgers.edu">NetID Look Up</a></li>
                <li><a target="_blank" href="https://dummy.rutgers.edu">Activation</a></li>
                <li><a target="_blank" href="https://dummy.rutgers.edu">Password Reset</a></li>
			</ul>
		</div>
	</div>

	<div id="footer">
		<p>For questions, comments or suggestions contact <a href="http://rucs.camden.rutgers.edu/">Camden Help Desk</a>, <a href="http://www.ncs.rutgers.edu/helpdesk/">Newark Help Desk</a>, or <a href="http://www.nbcs.rutgers.edu/helpdesk/hdnew/">New Brunswick/Piscataway Help Desk</a>.</p>
		<p>Visit web sites for <a href="http://www.camden.rutgers.edu/">Camden campus</a>, <a href="http://rutgers-newark.rutgers.edu/">Newark campus</a>, <a href="http://nbp.rutgers.edu/">New Brunswick/Piscataway campus</a>, or <a href="http://www.rutgers.edu">Rutgers University</a>.</p>
		<a id="logo" href="http://www.rutgers.edu" title="go to Rutgers University home page"><img src="images/ru_logo.gif" width="150" height="40" alt="Rutgers logo" /></a>
	</div>

</body>
</html>