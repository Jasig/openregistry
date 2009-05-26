<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<c:if test="${not empty command}">
	<spring:hasBindErrors name="${command}"/>
	<spring:bind path="${command}.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="errors" id="status">
				<h2>Your changes have not been saved!</h2>
				<p>Please fix the following errors and resubmit.</p>
				<ul>
					<c:forEach items="${status.errorMessages}" var="msg">
						<li>${msg}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</spring:bind>
</c:if>
