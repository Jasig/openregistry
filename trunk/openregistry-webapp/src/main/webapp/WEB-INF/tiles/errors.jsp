<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<tiles:useAttribute id="key" name="titleCode"  />
<tiles:useAttribute id="body" name="bodyValue"  />
<div class="errors" id="status">
    <h2><spring:message code="${key}" /></h2>
    <p><spring:message code="${body}" /></p>
</div>
