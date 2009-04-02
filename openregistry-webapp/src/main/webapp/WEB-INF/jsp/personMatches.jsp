<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Mar 23, 2009
  Time: 9:54:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.include file="/WEB-INF/jsp/includes/head.jsp" />

<head>
    <title><spring:message code="personMatches.title"/></title>
</head>
<body class="openregistry" id="update">

	<h1 id="app-name">Open Registry</h1>
	<ul id="nav-system"><li id="logout"><a href="logout.htm" title="log out of current session"><spring:message code="logout.label" /></a></li></ul>

	<div id="content">
   		<c:set var="command" value="serviceExecutionResult" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/errors.jsp" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />

		<form:form modelAttribute="serviceExecutionResult" >
			<fieldset id="update">
				<legend><span><spring:message code="personMatches.heading"/><span style="color:#000;"><c:out value="${personToAdd.firstAddedName.family}" />, <c:out value="${personToAdd.firstAddedName.given}" /></span></span></legend>

                <div class="row">
                    <label class="desc2" for="c1_startdate"><span style="color:#000; font-size:1.2em;"><spring:message code="personMatches.addAnyway"/></span></label>
                </div>
                <br/><br/>
                <div>
                    <table>
                        <tr>
                            <th><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="nameColumn.label"/></span></th>
                            <th><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="genderColumn.label"/></span></th>
                        </tr>
                            <c:forEach var="personMatch" items="${serviceExecutionResult.reconciliationResult.matches}">
                                <tr>
                                    <td valign="top"><c:out value="${personMatch.person.officialName.family}" />, <c:out value="${personMatch.person.officialName.given}" /></td>
                                    <td valign="top"><c:out value="${personMatch.person.gender}" /></td>
                                </tr>
                            </c:forEach>
                    </table>
                </div>

        </fieldset>
        <br/>
        <div class="row fm-v" style="clear:both;">
            <input style="float:left;" type="submit" id="fm-search-submit1" name="_eventId_submitAddPerson" class="btn-submit" value="Add This Person" tabindex="11"/>
            <input style="float:left;" type="submit" id="fm-search-cancel"  name="_eventId_cancelAddPerson" class="btn-cancel" value="Cancel" tabindex="11"/>
        </div>

    </form:form>
	</div>
	<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
</body>
</html>