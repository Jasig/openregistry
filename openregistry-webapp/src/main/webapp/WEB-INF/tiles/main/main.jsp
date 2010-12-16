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

<%--
  Created by IntelliJ IDEA.
  User: nmond
  Date: May 28, 2009
  Time: 5:13:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form:form modelAttribute="person">

	<div id="content">
		<div id="sidebar">
            <h2>Manage People</h2>
			<ul>
  <!--
	            <li><a href="addPerson.htm">Add Person</a></li>
	            <li><a href="updatePerson.htm">Update Person</a></li>
                <li><a href="splitPerson.htm">Move Sor Person Record</a></li>
                <li><a href="joinPerson.htm">Move All Sor Person Records</a></li>
                <li><a href="newActivationKey.htm">Request New Activation Key</a></li>
  -->
                <!-- Only the principal who is authorize to access the view complete person url
                can see this link on the main page -->
                <!-- Commenting sec:authorize on url viewCompletePerson.htm for now  -->

                   <li><a href="viewCompletePerson.htm">View Complete Person</a></li>

            </ul>
			<h2>Important Notices</h2>
			<p>
				Notices go here.
			</p>
		</div>
	</div>
</form:form>