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

<%--
  Created by IntelliJ IDEA.
  User: rosey
  Date: Feb 2, 2010
  Time: 1:47:02 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript">
    $(function() {
       $("#searchtitle").button();
    });
</script>
<div id="nav" class="ui-buttonset" style="height:30px;">
    <c:set var="current" value="Page Value" scope="page" />

    <ul>
        <li id="searchtitle">
            <a href="<c:url value="/viewPersonDetail.htm" />">
                <spring:message code="manage.people.title" />
            </a>
        </li>
    </ul>
</div>
