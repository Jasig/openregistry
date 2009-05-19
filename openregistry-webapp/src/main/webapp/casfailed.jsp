<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>

<html>
<head>
    <title>Login to CAS failed!</title>
</head>

<body>
<h2>Login to CAS failed!</h2>

<font color="red">
    Your CAS credentials were rejected.<br/><br/>
    Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
</font>

</body>
</html>