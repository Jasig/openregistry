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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    $(function() {
       $("#newPersonButton").button();
    });
</script>

<div class="ui-widget ui-widget-content ui-corner-all">
    <h4 class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="padding: 5px; margin-bottom:0;">
        <span class="ui-dialog-title">Manage People</span>
    </h4>

    <div style="padding: 5px;">
        <tiles:insertAttribute name="searchForm" />
        <div id="searchResults">
            <tiles:insertAttribute name="searchResults" flush="true" />
        </div>
    </div>
</div>
<div style="margin-top: 5px;">
    <a href="<c:url value="/addSorPerson.htm" />">
        <button id="newPersonButton"><spring:message code="new.person.title" /></button>
    </a>
</div>
