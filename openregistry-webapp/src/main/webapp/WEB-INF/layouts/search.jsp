<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<tiles:insertAttribute name="searchForm" />
<div id="searchResults">
    <tiles:insertAttribute name="searchResults" flush="true" />
</div>