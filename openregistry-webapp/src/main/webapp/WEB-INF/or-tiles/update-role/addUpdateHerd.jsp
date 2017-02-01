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

<form:form modelAttribute="herd">
      <%--<c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">--%>
      <%--<div class="error-msg">${message.text}</div>--%>
  <%--</c:forEach>--%>
    <fieldset id="addUpdateHerd">
        <c:if test="${herdCommand == 'add'}">
            <legend><span><spring:message code="herd.add.heading"/></span></legend>
        </c:if>
        <c:if test="${herdCommand == 'update'}">
            <legend><span><spring:message code="herd.update.heading"/></span></legend>
        </c:if>

        <p><spring:message code="requiredFields.heading"/><span style="color:#b00;">*</span>.</p>

        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="person.label"/></span>
        <em2><c:out value="${sorPerson.formattedName}"/></em2> <br>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="role.heading"/></span>
        <em2><c:out value="${role.affiliationType.description}"/></em2>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.role.start.label"/></span>
        <em2><fmt:formatDate pattern="MM/dd/yyyy" value="${role.start}"/></em2>
        <span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.role.end.label"/></span>
        <em2><fmt:formatDate pattern="MM/dd/yyyy" value="${role.end}"/></em2> <br><br><br>

        <%--<c:if test='${herdCommand == "update"}'>--%>
            <%--<span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.name.label"/>:</span>--%>
            <%--<em2><c:out value="${herd.NETWORK_HERD_NM}"/></em2> <br>--%>
            <%--<span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.sponsor.label"/>:</span>--%>
            <%--<em2><c:out value="${herd.SPONSOR_RCP_ID}"/></em2> <br>--%>
            <%--<span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.createDate.label"/>:</span>--%>
            <%--<em2><fmt:formatDate pattern="MM/dd/yyyy" value="${herd.CREATE_DT}"/></em2> <br>--%>
            <%--<span style="color:#000; font-weight:bold;font-size:1.2em;"><spring:message code="herd.expDate.label"/><span style="color:#b00;">*</span>:</span>--%>
            <%--<input name="herdExpDate" id="c1_enddate" size="10" maxlength="10" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${herd.EXPIRATION_DT}'/>"/> <br><br>--%>
        <%--</c:if>--%>
        <%--<c:if test='${herdCommand == "add"}'>--%>

            <div class="row">

                <div class="row">
                    <label for="herdName" class="herdName"><spring:message code="herd.input.label"/><span style="color:#b00;">*</span>: </label>
                    <div class="select">
                        <select class="herdName"  name="herdName" id="herdName"/>
                            <c:if test="${herdCommand != 'update'}">
                                <c:if test="${empty herdNames}">
                                    <OPTION value="ACS_MISC" selected="selected">ACS_MISC</OPTION>
                                </c:if>
                                <c:if test="${not empty herdNames}">
                                    <c:forEach var="herdNm" items="${herdNames}" >
                                        <c:choose>
                                            <c:when test="${herdNm=='ACS_MISC'}">
                                                <OPTION value="${herdNm}" selected="selected">${herdNm}</OPTION>
                                            </c:when>
                                            <c:otherwise>
                                                <OPTION value="${herdNm}" >${herdNm}</OPTION>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                            <c:if test="${herdCommand == 'update'}">
                                    <option value="${herd.NETWORK_HERD_NM}">${herd.NETWORK_HERD_NM}</option>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div  style="float:left; margin-right:1em;">
                    <label for="identifierType">Sponsor ID Type</label>
                    <div class="select">
                        <select class="identType" name="identifierType" id="identifierType">
                            <c:forEach var="identifierType" items="${identifierTypes}">
                                <option value="${identifierType.name}" <c:if test="${identifierType == 'RCPID'}"> selected</c:if>>${identifierType.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div style="float:left;">
                    <label for="sponsorId" class="sponsor"><spring:message code="sponsor.label"/><span style="color:#b00;">*</span>:</label>
                    <input id="sponsorId" name="sponsorId" type="text" value="${herd.SPONSOR_RCP_ID}" />
                </div>

            </div>



            <div class="row">
                <label for="herdExpDate"><spring:message code="herdExpDate.input.label"/><span style="color:#b00;">*</span>:</label>
                <input name="herdExpDate" id="herdExpDate" size="10" maxlength="10" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${herd.EXPIRATION_DT}'/>" />
            </div>

        <%--</c:if>--%>

        <div class="row">
           <c:if test='${herdCommand == "add"}'>
                <input  type="submit" id="fm-submit1" name="_eventId_submitAddHerd" class="btn-submit" value="Add Herd" />
            </c:if>
            <c:if test='${herdCommand == "update"}'>
                <input  type="submit" id="fm-submit1" name="_eventId_submitUpdateHerd" class="btn-submit" value="Update Herd" />
            </c:if>
            &nbsp;or&nbsp;
            <input type="submit" id="fm-cancel" name="_eventId_submitCancelHerd" class="btn-cancel" value="Cancel" />
        </div>
    </fieldset>
</form:form>