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

<%--insert the generic navigation tile--%>
<%--<tiles:insertAttribute name="nav" />--%>
<tiles:insertDefinition name="openregistry.navigation"/>

<%--insert the errors and info files--%>
<div id="content">
	<%--<c:set var="command" value="${command}" />--%>
	<%--<jsp:directive.include file="/WEB-INF/jsp/or-includes/errors.jsp" />--%>
	<%--<jsp:directive.include file="/WEB-INF/jsp/or-includes/info.jsp" />--%>

	<%--insert the main content of this page--%>
	<tiles:insertAttribute name="body"/>
</div>
