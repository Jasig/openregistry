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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form modelAttribute="defaultMailDist">
<fieldset id="addUpdateMailDist">
    <c:if test="${mailDistCommand == 'add'}">
        <legend><span><spring:message code="mailDist.add.heading"/></span></legend>
    </c:if>
    <c:if test="${mailDistCommand == 'update'}">
        <legend><span><spring:message code="mailDist.update.heading"/></span></legend>
    </c:if>

<br/>


    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label"/></span>
    <em2><c:out value="${sorPerson.formattedName}"/></em2> <br><br>
    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading"/></span>
    <em2><c:out value="${role.affiliationType.description}"/></em2>
    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.role.start.label"/></span>
    <em2><fmt:formatDate pattern="MM/dd/yyyy" value="${role.start}"/></em2>
    <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.role.end.label"/></span>
    <em2><fmt:formatDate pattern="MM/dd/yyyy" value="${role.end}"/></em2> <br><br>

    <c:if test="${sponsorMailDist.name != null}" >
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.sponsorName.label"/></span>
        <em2><c:out value="${sponsorMailDist.name}"/></em2><br><br><Br>
    </c:if>

    <c:if test='${mailDistCommand == "update"}'>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.heading"/></span>
    <!--
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.campus.label"/></span>
        <em2><c:out value="${mailDist.campus}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.division.label"/></span>
        <em2><c:out value="${mailDist.division}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.dept.label"/></span>
        <em2><c:out value="${mailDist.dept}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.building.label"/></span>
        <em2><c:out value="${mailDist.building}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.roomNo.label"/></span>
        <em2><c:out value="${mailDist.roomNo}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="mailDist.mailCode.label"/></span>
        <em2><c:out value="${mailDist.mailCode}"/></em2> <br>  -->
                    <table>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.campus.label"/></td>
                        <td><em2><c:out value="${mailDist.campus}"/></em2><td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.division.label"/></td>
                        <td><em2><c:out value="${mailDist.division}"/></em2><td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.dept.label"/></td>
                        <td><em2><c:out value="${mailDist.dept}"/></em2><td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.building.label"/></td>
                        <td><em2><c:out value="${mailDist.building}"/></em2><td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.roomNo.label"/></td>
                        <td><em2><c:out value="${mailDist.roomNo}"/></em2><td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:right;"><spring:message code="mailDist.mailCode.label"/></td>
                        <td><em2><c:out value="${mailDist.mailCode}"/></em2><td>
                    </tr>
                </table>

    </c:if>
    <hr>
        <div class="row">
            <label for="campus" class="campus"><spring:message code="mailDist.campus.label"/><c:if test="${sponsorMailDist.name != null}" ><span style="color:#b00;">*</span></c:if>:</label>
            <div class="select">
                <form:select id="campus" name="campus" path="campus" onchange="document.location='${flowExecutionUrl}&_eventId=submitRefSelections&campus=' + this.value">
                    <form:options items="${selections.campus}" />
                </form:select>
            </div>
        </div>

        <div class="row">
            <label for="division" class="division"><spring:message code="mailDist.division.label"/><c:if test="${sponsorMailDist.name != null}" ><span style="color:#b00;">*</span></c:if>:</label>
            <div class="select">
                <form:select id="division" name="division" path="division">
                    <form:options items="${selections.division}" />
                </form:select>
            </div>
        </div>

        <div class="row">
            <label for="dept" class="dept"><spring:message code="mailDist.dept.label"/><c:if test="${sponsorMailDist.name != null}" ><span style="color:#b00;">*</span></c:if>:</label>
            <div class="select">
                <form:select id="dept" name="campus" path="dept">
                    <form:options items="${selections.dept}" />
                </form:select>
            </div>
        </div>

        <div class="row">
            <label for="building" class="building"><spring:message code="mailDist.building.label"/><c:if test="${sponsorMailDist.name != null}" ><span style="color:#b00;">*</span></c:if>:</label>
            <div class="select">
                <form:select id="building" name="building" path="building">
                    <form:options items="${selections.building}" />
                </form:select>
            </div>
        </div>

        <div class="row">
            <label for="roomNo" class="roomNo" style="width:100em;"><spring:message code="mailDist.roomNo.label"/><c:if test="${sponsorMailDist.name != null}" ><span style="color:#b00;">*</span></c:if>:</label>
                <form:input name="roomNo" id="roomNo" path="roomNo" size="10" maxlength="10" />
        </div>

        <div class="row">
            <label for="mailCode" class="mailCode" style="width:100em;"><spring:message code="mailDist.mailCode.label"/><span style="color:#b00;">**</span>:</label>
            <%--<form:input name="mailCode" id="mailCode" path="mailCode" size="10" maxlength="10" disabled="true"/>--%>
            <div class="select">
                <form:select id="mailCode" name="mailCode" path="mailCode">
                        <form:option value="A3"/>
                        <form:option value="F2"/>
                </form:select>
            </div>
        </div>

    <hr>
    <div class="row fm-v">
    <table>
        <c:if test="${sponsorMailDist.name != null}" >
            <tr><td><span style="color:#b00;">*</span><spring:message  code="mailDist.mailCode.info1"/></td></tr>
        </c:if>
        <tr><td><span style="color:#b00;">**</span><spring:message  code="mailDist.mailCode.info2"/></td></tr>
    </table>
    </div>

    <div class="row">
       <c:if test='${mailDistCommand == "add"}'>
            <input type="submit" id="fm-submit1" name="_eventId_submitAddMailDist" class="btn-submit" value="Add Mail Distribution" />
        </c:if>
        <c:if test='${mailDistCommand == "update"}'>
            <input type="submit" id="fm-submit1" name="_eventId_submitUpdateMailDist" class="btn-submit" value="Update Mail Distribution"/>
        </c:if>
        &nbsp;or&nbsp;
        <input type="submit" id="fm-cancel" name="_eventId_submitCancelMailDist" class="btn-cancel" value="Cancel" />
    </div>
</fieldset>
</form:form>