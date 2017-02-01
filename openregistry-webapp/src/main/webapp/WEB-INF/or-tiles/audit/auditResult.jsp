<%--
  Created by IntelliJ IDEA.
  User: sheliu
  Date: 11/19/12
  Time: 11:42 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${error ne null}">
       ${error}
    </c:when>
    <c:when test="${auditResults eq null}">
    </c:when>
    <c:when test="${empty auditResults}">
        <label class="desc">No record found</label>
    </c:when>
    <c:otherwise>
        <fieldset class="fm-h">
            <legend><span><spring:message code="audit.result"/></span></legend>
        <div class="row">
            <a href="${flowExecutionUrl}&_eventId=submitListByTime"><spring:message code="audit.result.list.byTime"/></a>
            <a href="${flowExecutionUrl}&_eventId=submitListByTimeRevers"><spring:message code="audit.result.list.byTimeRevers"/></a>
            <a href="${flowExecutionUrl}&_eventId=submitListByEAttr"><spring:message code="audit.result.list.byAttr"/></a>
        </div>
        <%--<div class="row">--%>
            <%--<div class="q first">--%>
                <%--<label></label><input type="radio" name="sortBy" value="attribute" onclick="window.location.reload()"/>List By Attribute</label>--%>
            <%--</div>--%>
            <%--<div class="q ">--%>
                <%--<label></label><input type="radio" name="sortBy" value="time" onclick="window.location.reload()"/>List By Time</label>--%>
            <%--</div>--%>
        <%--</div>--%>


        <div id="auditResults">
            <c:if test="${sortBy == 'attribute'}">
            <c:if test="${auditResults.person != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.person.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.person[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.person}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.role != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.role.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.role[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.role}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.identifier != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.identifier.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.identifier[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.identifier}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.name != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.name.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.name[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.name}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.email != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.email.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.email[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.email}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.phone != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.phone.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.phone[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.phone}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${auditResults.address != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.address.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.address[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.address}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${auditResults.idCard != null}">
                <label class="desc" style="color:#000000;"><spring:message code="audit.idCard.result"/></label>

                <div class="scrollable">
                    <table>
                        <thead>
                        <th></th>
                        <c:forEach var="fieldName" items="${auditResults.idCard[0]}">
                            <th>${fieldName.key}</th>
                        </c:forEach>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${auditResults.idCard}" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index}</td>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus">
                                    <c:if test="${loopStatus.index == 0}">
                                        <td>
                                            <a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${field.value}"><em>${field.value}</em></a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loopStatus.index != 0}">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>


            </c:if>
            <c:if test="${sortBy == 'time'}">
                <div class="scrollable">
                    <c:forEach var="revList" items="${auditResultsByRev}" varStatus="loopStatus1">
                        <%--<label class="desc"> ${loopStatus1.index}  RevNo: ${revList.key} Time: ${revList.value[0].Time} User: ${revList.value[0].User} </label>--%>
                        <label class="desc">Time: <em>${revList.value[0].Time}</em> User: <em>${revList.value[0].User}</em>
                            RevNo: <em>${revList.key}</em> Reason: <em>${revList.value[0].Reason}</em>
                                <%--<a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${revList.key}"><em>${revList.key}</em></a>--%>
                        </label>
                        <table style="padding-left: 10%">
                            <c:forEach var="row" items="${revList.value}" varStatus="loopStatus3">
                                <thead>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                    <c:if test="${loopStatus4.index > 3 }">
                                        <th>${field.key}</th>
                                    </c:if>
                                </c:forEach>
                                </thead>
                                <tbody>
                                <tr>
                                    <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                        <c:if test="${loopStatus4.index > 3 }">
                                            <td>${field.value}</td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${sortBy == 'timeRevers'}">
                <div class="scrollable">
                    <c:forEach var="revList" items="${auditResultsByRevRevers}" varStatus="loopStatus1">
                        <%--<label class="desc"> ${loopStatus1.index}  RevNo: ${revList.key} Time: ${revList.value[0].Time} User: ${revList.value[0].User} </label>--%>
                        <label class="desc">Time: <em>${revList.value[0].Time}</em> User: <em>${revList.value[0].User}</em>
                            RevNo: <em>${revList.key}</em> Reason: <em>${revList.value[0].Reason}</em>
                                <%--<a href="${flowExecutionUrl}&_eventId=submitListForRev&rev=${revList.key}"><em>${revList.key}</em></a>--%>
                        </label>
                        <table style="padding-left: 10%">
                            <c:forEach var="row" items="${revList.value}" varStatus="loopStatus3">
                                <thead>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                    <c:if test="${loopStatus4.index > 3 }">
                                        <th>${field.key}</th>
                                    </c:if>
                                </c:forEach>
                                </thead>
                                <tbody>
                                <tr>
                                    <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                        <c:if test="${loopStatus4.index > 3 }">
                                            <td>${field.value}</td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${sortBy == 'singleRev'}">
                <div class="scrollable">
                    <label class="desc">Time: <em>${auditResultsForRev[0].Time}</em> User:
                        <em>${auditResultsForRev[0].User}</em>
                        RevNo: <em>${auditResultsForRev[0].RevNo}</em> Reason: <em>${auditResultsForRev[0].Reason}</em></label>
                    <table style="padding-left: 10%">
                        <c:forEach var="row" items="${auditResultsForRev}" varStatus="loopStatus3">
                            <thead>
                            <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                <c:if test="${loopStatus4.index > 3 }">
                                    <th>${field.key}</th>
                                </c:if>
                            </c:forEach>
                            </thead>
                            <tbody>
                            <tr>
                                <c:forEach var="field" items="${row}" varStatus="loopStatus4">
                                    <c:if test="${loopStatus4.index > 3 }">
                                        <td>${field.value}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
        <%--<form:form>--%>
        <div class="row">
            <%--<input type="submit" id="fm-submit1" name="_eventId_submitDone" class="btn-submit" value="Back to View Person"/>--%>
            <input type="submit" id="fm-submit1" class="btn-submit" value="Back" onclick="window.history.go(-1)"/>
        </div>
        <%--</form:form>--%>
        </fieldset>
    </c:otherwise>
</c:choose>