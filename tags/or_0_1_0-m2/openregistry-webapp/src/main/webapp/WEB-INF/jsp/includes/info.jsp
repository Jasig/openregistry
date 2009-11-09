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
<c:if test='${not empty infoModel}'>
	<div class="success" id="msg">
		<h2><c:out value="${infoModel}"/></h2>
	</div>
</c:if>

<c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
    <c:if test="${message.severity eq 'Info'}">
      <div class="success" id="msg">
        <h2><c:out value="${message.text}"/></h2>
      </div>
    </c:if>
</c:forEach>