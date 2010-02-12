<%--

    Copyright (C) 2009 Jasig, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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
			<div class="error" id="message">
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
