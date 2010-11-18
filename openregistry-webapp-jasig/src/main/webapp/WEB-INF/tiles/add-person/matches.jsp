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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<script type="text/javascript">
	$(function() {
		$("#fm-search-submit1").button();
		$("#fm-search-cancel").button();
	});
</script>

<form:form modelAttribute="reconciliationResult" >
	<div class="ui-widget ui-widget-content ui-corner-all">
		<h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding: 5px; margin-bottom:0;">
			<span class="or-dialog-title"><spring:message code="personMatches.title" /></span>
		</h4>
		<div style="padding: 5px;">
			<div class="or-instructions" style="margin-bottom: 5px;" >
				Possible matches were found in the system. If you think one of the following is the person you are adding,
				click on the name of the person or click "Create New Person" to add <c:out value="${personToAdd.names[0].family}" />,
				<c:out value="${personToAdd.names[0].given}" /> as a new person.
			</div>
			<fieldset id="update">
				<legend>Possible Matches</legend>
				<div class="or-result-table">
					<display:table name="reconciliationResult.matches" id="personMatch" style="width: 50%;">
						<display:setProperty name="css.tr.even" value="or-even-rows" />
						<display:column title="Name" style="width: 55%"><a href="${flowExecutionUrl}&_eventId=submitAddRole&personId=${personMatch.person.id}">${personMatch.person.officialName.family}, ${personMatch.person.officialName.given}</a></display:column>
						<display:column title="Gender" property="person.gender" style="width: 15%" />
						<display:column title="Birthday" style="width: 30%">?</display:column>
					</display:table>
				</div>
			</fieldset>
			<div style="margin-top: 5px;"></div>
			<fieldset>
				<div>
					<div>The person to be added does not match the existing records listed above, create a new person.</div>
					<input type="submit" id="fm-search-submit1" name="_eventId_submitAddPerson" class="btn-submit" value="Create New Person"/>
				</div>
			</fieldset>
			<div style="margin-top: 5px;"></div>
			<fieldset>
				<input type="submit" id="fm-search-cancel"  name="_eventId_cancelAddPerson" class="btn-cancel" value="Cancel"/>
			</fieldset>
		</div>
	</div>
</form:form>
<div class="or-form-name">Form: matches</div>
