<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form modelAttribute="sorPerson">
			<fieldset id="managePersonRoles">
				<legend><span><spring:message code="managePersonRolesPage.heading"/></span></legend>
                <br/>

                <fieldset class="fm-h" id="ecn1">
                <label class="desc2"><span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="managePersonRoles.label" /></span><em2><c:out value="${sorPerson.formattedNameAndID}"/></em2></label>
                </br></br>

                <label class="desc"><spring:message code="roles.heading"/></label>
                <c:choose>
                <c:when test="${not empty sorPerson.roles}">
                <div>
                    <table class="data" cellspacing="0" width="60%">
                        <thead>
                            <tr class="appHeadingRow">
                                <th><spring:message code="affiliationTitle.label"/></th>
                                <th><spring:message code="organization.label"/></th>
                                <th><spring:message code="campus.label"/></th>
                                <th><spring:message code="startDate.label"/></th>
                                <th><spring:message code="endDate.label"/></th>
                                <th><spring:message code="action.label"/></th>
                            </tr>
                            </thead>
                            <tbody>
                             <c:forEach var="role" items="${sorPerson.roles}">
                            <tr>
                                <td>
                                    <a href="${flowExecutionUrl}&_eventId=submitUpdateRole&roleId=${role.id}">${role.affiliationType.description}/${role.title}</a>
                                </td>
                                <td>
                                    ${role.organizationalUnit.name}
                                </td>
                                <td>
                                    ${role.campus.name}
                                </td>
                                <td>
                                    <fmt:formatDate pattern="MM/dd/yyyy" value="${role.start}"/>
                                </td>
                                <td>
                                    <fmt:formatDate pattern="MM/dd/yyyy" value="${role.end}"/>
                                </td>
                                <td><a href="${flowExecutionUrl}&_eventId=submitRemoveRole&roleId=${role.id}"><spring:message code="remove.label"/></a></td>
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:when>
                <c:otherwise><spring:message code="noRolesDefined.label"/><br/><br/></c:otherwise>
            </c:choose>

            <div>
                <input id="addRoleBtn" class="button" type="submit" name="_eventId_submitAddRole" value="Add Role" title="add a new role" />
            </div>

			</fieldset>
			</fieldset>
          

		</form:form>