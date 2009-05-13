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