<%@ page language="java" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.include file="/WEB-INF/jsp/includes/head.jsp" />
<%-- $Id: $ --%>

<body class="openregistry" id="update">

	<h1 id="app-name">Open Registry</h1>
	<ul id="nav-system"
		><li id="logout"><a href="logout.htm" title="log out of current session">Log out</a></li
	></ul>
		
	<div id="content">
   		<c:set var="command" value="role" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/errors.jsp" />
   		<jsp:directive.include file="/WEB-INF/jsp/includes/info.jsp" />
		
		<form:form modelAttribute="role" id="fm-search" method="post" action="addRole.htm">
			<fieldset id="addrole">
				<legend><span>Add Role</span></legend>				
				<p style="margin-bottom:0;">
					Required fields are marked with <span style="color:#b00;">*</span>.
				</p>
                <br/>

                <fieldset class="fm-h" id="ecn1">


                    <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;">Add Role:</span><em2><c:out value="${affiliationType.description}"/></em2></label>
                    <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;">Title: </span><em2><c:out value="${title}"/></em2></label>
                    <label class="desc2" for="c1_startdate"><span style="color:#000; font-weight:bold;font-size:1.2em;">To: </span><em2><c:out value="${personid}"/></em2></label>
                    <br/>
                    <br/>
					<label class="desc" for="c1_startdate">Please Specify Role Information:</label>

					<div class="row">
						<label for="c1_startdate" class="startdate">Start Date <em>*</em></label>
                       	<form:input path="start" id="c1_startdate" size="10" maxlength="10" tabindex="1" />

						<label for="c1_enddate" class="enddate">End Date <em>*</em></label>
						<form:input path="end" id="c1_enddate" size="10" maxlength="10" tabindex="2" />

						<label for="c1_sponsor" class="sponsor">Sponsor <em>*</em></label>
                        <div class="select sponsor">
							<form:select path="sponsor.id" id="c1_sponsor" size="1" tabindex="4">
							    <c:forEach items="${sponsorLookup}"  var="referenceLookup">
                                    <option value="${referenceLookup.id}" ${sponsor.id == referenceLookup.id ? 'selected="selected"' : ''}>${referenceLookup.officialName.family} </option>
                                </c:forEach>
						</form:select>
                    </div>
                                       
                    <div class="row">
						
						<label for="c1_department" class="department">Department <em>*</em></label>
						<div class="select department">
                            <form:select path="department.localCode" id="c1_department" size="1" tabindex="5">
							    <c:forEach items="${departmentLookup}"  var="referenceLookup">
                                    <option value="${referenceLookup.localCode}" ${department.code == referenceLookup.localCode ? 'selected="selected"' : ''}>${referenceLookup.name} </option>
                                </c:forEach>
							</form:select>
						</div>
						
						<label for="c1_campus" class="campus">Campus</label>
						<div class="select campus">
							<form:select path="campus.code" id="c1_campus" size="1" tabindex="6">
							    <c:forEach items="${campusLookup}"  var="referenceLookup">
                                    <option value="${referenceLookup.code}" ${campus.code == referenceLookup.code ? 'selected="selected"' : ''}>${referenceLookup.name} </option>
                                </c:forEach>
							</form:select>
						</div>
                    </div>
                    
                    <div class="row">
                        <label for="c1_email">Email <em>*</em></label>
                        <form:input path="emailAddresses[0].address" id="c1_email" size="20" maxlength="30" tabindex="7" />

						<label for="c1_pt" class="pt">PT%</label>
                        <div class="select pt">
							<form:select path="percentage" id="c1_pt" size="1" tabindex="8">
                            <form:option value="100" label="100"/>
							<form:option value="75" label="75"/>
                            <form:option value="50" label="50"/>
                            <form:option value="25" label="25"/>
							</form:select>
						</div>
					</div>	

			</fieldset>	    
			<fieldset id="e_cell">
                <label class="desc" for="c1_startdate"><span style="color:#000;">Please Provide Local Address Information: </span><br /></label>

				<fieldset class="fm-h" style="margin-bottom:0;">
					
					<div class="row">
						<label for="c1_address1" class="address1">Address Line 1</label>
						<form:input path="addresses[0].line1" id="c1_address1" size="30" maxlength="30" tabindex="9" />
					</div>
                    <div class="row">
						<label for="c1_address1" class="address1">Address Line 2</label>
						<form:input path="addresses[0].line2" id="c1_address1" size="30" maxlength="30" tabindex="10" />
					</div>
                    <div class="row">
						<label for="c1_address1" class="address1">Address Line 2</label>
						<form:input path="addresses[0].line3" id="c1_address1" size="30" maxlength="30" tabindex="11" />
					</div>
					
					<div class="row">
						<label for="c1_city">City</label>
						<form:input path="addresses[0].city" id="c1_city" size="30" maxlength="30" tabindex="12" />

                        <label for="c1_state" class="state">State / Region</label>
					    <form:input path="addresses[0].region" id="c1_state" size="10" maxlength="10" tabindex="13" />

						<label for="c1_zip" class="zip">Postal / Zip Code</label>
						<form:input path="addresses[0].postalCode" id="c1_zip" size="10" maxlength="10" tabindex="14" />
					</div>

					<div class="row">
						<label for="c1_country">Country</label>
						<div class="select country">
							<form:select path="addresses[0].country" id="c1_country" size="1" tabindex="15">
                                <c:forEach items="${countryLookup}"  var="referenceLookup">
                                    <option value="${referenceLookup.code}" ${addresses[0].country.code == referenceLookup.code ? 'selected="selected"' : ''}>${referenceLookup.name} </option>
                                </c:forEach>
							</form:select>
						</div>
					</div>

					<div class="row">

						<label for="c1_phone" class="phone">Phone</label>
						<form:input path="phones[0].number" id="c1_phone" size="25" maxlength="25" tabindex="16" />

						<label for="c1_ext" class="ext">Ext</label>
						<form:input path="phones[0].extension" id="c1_ext" size="10" maxlength="10" tabindex="17" />

					</div>
				</fieldset>
			</fieldset>
			</fieldset>

			<div class="row fm-v" style="clear:both;">
				<input style="float:left;" type="submit" id="fm-search-submit1" name="fm-search-submit" class="btn-submit" value="Add Role" tabindex="18"/> 
			</div>

		</form:form>
	</div>

	<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
</body>
</html> 